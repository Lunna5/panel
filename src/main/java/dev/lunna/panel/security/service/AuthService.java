package dev.lunna.panel.security.service;

import dev.lunna.panel.security.dto.request.UserEmailLoginRequest;
import dev.lunna.panel.security.dto.response.UserTokenResponse;
import dev.lunna.panel.security.factory.UserModelFactory;
import dev.lunna.panel.security.repository.SessionRepository;
import dev.lunna.panel.user.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

public class AuthService {
  private final UserRepository userRepository;
  private final SessionRepository sessionRepository;
  private final UserModelFactory userModelFactory;

  public AuthService(
      @NotNull final UserRepository userRepository,
      @NotNull final SessionRepository sessionRepository,
      @NotNull final UserModelFactory userModelFactory
  ) {
    this.userRepository = userRepository;
    this.sessionRepository = sessionRepository;
    this.userModelFactory = userModelFactory;
  }

  @Transactional
  public UserTokenResponse login(@NotNull final UserEmailLoginRequest request) {
    return null;
  }

  @Transactional
  public UserTokenResponse register(@NotNull final UserEmailLoginRequest request) {
    if (userRepository.existsByEmail(request.email())) {
      throw new IllegalArgumentException("User with email " + request.email() + " already exists.");
    }

    return null;
  }
}
