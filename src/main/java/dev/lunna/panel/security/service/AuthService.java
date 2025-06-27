package dev.lunna.panel.security.service;

import dev.lunna.panel.security.dto.request.UserEmailLoginRequest;
import dev.lunna.panel.security.dto.request.UserRegisterRequest;
import dev.lunna.panel.security.dto.response.UserTokenResponse;
import dev.lunna.panel.security.exception.CredentialsNotMatchException;
import dev.lunna.panel.security.exception.TokenExpiredException;
import dev.lunna.panel.security.factory.UserModelFactory;
import dev.lunna.panel.security.repository.SessionRepository;
import dev.lunna.panel.security.service.hasher.PasswordHasherService;
import dev.lunna.panel.user.model.UserModel;
import dev.lunna.panel.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final SessionRepository sessionRepository;
  private final UserModelFactory userModelFactory;
  private final SessionService sessionService;
  private final PasswordHasherService passwordHasherService;
  private final JwtService jwtService;

  public AuthService(
      @NotNull final UserRepository userRepository,
      @NotNull final SessionRepository sessionRepository,
      @NotNull final UserModelFactory userModelFactory,
      @NotNull final SessionService sessionService,
      @NotNull final PasswordHasherService passwordHasherService,
      @NotNull final JwtService jwtService
  ) {
    this.userRepository = userRepository;
    this.sessionRepository = sessionRepository;
    this.userModelFactory = userModelFactory;
    this.sessionService = sessionService;
    this.passwordHasherService = passwordHasherService;
    this.jwtService = jwtService;
  }

  @Transactional
  public UserTokenResponse login(
      @NotNull final UserEmailLoginRequest request,
      @NotNull final HttpServletRequest httpServletRequest
  ) {
    final var user = userRepository.findByEmail(request.email())
        .orElseThrow(CredentialsNotMatchException::new);

    if (!passwordHasherService.matches(request.password(), user.getPassword())) {
      throw new CredentialsNotMatchException();
    }

    final var session = sessionService.createSession(user.getId(), httpServletRequest);

    if (session == null) {
      throw new CredentialsNotMatchException();
    }

    return new UserTokenResponse(session);
  }

  @Transactional
  public UserTokenResponse register(
      @NotNull final UserRegisterRequest request,
      @NotNull final HttpServletRequest httpServletRequest
  ) {
    final var model = userModelFactory.createUser(request);

    return new UserTokenResponse(sessionService.createSession(model.getId(), httpServletRequest));
  }

  @Transactional
  public UserModel isValidSession(
      @NotNull final String jwtToken
  ) {
    final var sessionId = jwtService.extractSessionID(jwtToken);

    final var session = sessionRepository.findById(sessionId)
        .orElseThrow(TokenExpiredException::new);

    if (session.isExpired()) {
      throw new TokenExpiredException();
    }

    return userRepository.findById(session.getUserId())
        .orElseThrow(TokenExpiredException::new);
  }
}
