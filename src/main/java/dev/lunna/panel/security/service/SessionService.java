package dev.lunna.panel.security.service;

import dev.lunna.panel.core.service.IpService;
import dev.lunna.panel.security.model.SessionModel;
import dev.lunna.panel.security.repository.SessionRepository;
import dev.lunna.panel.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
  private final SessionRepository sessionRepository;
  private final JwtService jwtService;
  private final IpService ipService;
  private final UserRepository userRepository;

  @Autowired
  public SessionService(
      @NotNull final SessionRepository sessionRepository,
      @NotNull final JwtService jwtService,
      @NotNull final IpService ipService,
      @NotNull final UserRepository userRepository
  ) {
    this.sessionRepository = sessionRepository;
    this.jwtService = jwtService;
    this.ipService = ipService;
    this.userRepository = userRepository;
  }

  /**
   * Creates a new session for the user and returns a JWT token.
   *
   * @param userId  The ID of the user for whom the session is being created.
   * @param request The HTTP request containing client information.
   * @return A JWT token representing the session.
   */
  public String createSession(@NotNull final Long userId, @NotNull final HttpServletRequest request) {
    SessionModel sessionModel = new SessionModel();

    sessionModel.setUser(userRepository.findById(userId).orElseThrow());
    sessionModel.setIpAddress(ipService.getClientIp(request));
    sessionModel.setUserAgent(request.getHeader("User-Agent"));

    sessionModel = sessionRepository.save(sessionModel);

    return jwtService.generateToken(sessionModel);
  }
}
