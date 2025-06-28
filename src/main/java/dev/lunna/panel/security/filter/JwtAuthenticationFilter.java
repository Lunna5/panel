package dev.lunna.panel.security.filter;

import dev.lunna.panel.core.service.IpService;
import dev.lunna.panel.security.exception.TokenExpiredException;
import dev.lunna.panel.security.service.AuthService;
import dev.lunna.panel.user.ApplicationUser;
import dev.lunna.panel.user.factory.ApplicationUserFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final AuthService authService;
  private final IpService ipService;
  private final ApplicationUserFactory userFactory;

  @Autowired
  public JwtAuthenticationFilter(
      @NotNull final AuthService authService,
      @NotNull final IpService ipService,
      @NotNull final ApplicationUserFactory userFactory
  ) {
    this.authService = authService;
    this.ipService = ipService;
    this.userFactory = userFactory;
  }

  @Override
  protected void doFilterInternal(
      @NotNull HttpServletRequest request,
      @NotNull HttpServletResponse response,
      @NotNull FilterChain filterChain
  ) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    final String token = authHeader.substring(7);

    try {
      final var model = authService.isValidSession(token);
      final ApplicationUser user = userFactory.createUser(model.getId(), ipService.getClientIp(request));

      final var authToken = new UsernamePasswordAuthenticationToken(
          user, null, user.getAuthorities()
      );

      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);
    } catch (TokenExpiredException e) {
      // This exception is thrown when the token is expired.
    } catch (Exception e) {
      logger.error(e.getMessage());
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
      return;
    }

    filterChain.doFilter(request, response);
  }
}
