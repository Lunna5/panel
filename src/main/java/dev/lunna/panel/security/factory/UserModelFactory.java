package dev.lunna.panel.security.factory;

import dev.lunna.panel.security.dto.request.UserRegisterRequest;
import dev.lunna.panel.security.exception.EmailAlreadyInUseException;
import dev.lunna.panel.security.exception.UsernameAlreadyInUseException;
import dev.lunna.panel.security.service.hasher.PasswordHasherService;
import dev.lunna.panel.user.model.UserModel;
import dev.lunna.panel.user.model.builder.UserModelBuilder;
import dev.lunna.panel.user.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.SimpleErrors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;

@Service
public final class UserModelFactory {
  private static final Logger log = LoggerFactory.getLogger(UserModelFactory.class);
  private final UserRepository userRepository;
  private final SmartValidator smartValidator;
  private final PasswordHasherService passwordHasherService;

  public UserModelFactory(
      @NotNull final UserRepository userRepository,
      @NotNull final SmartValidator smartValidator,
      @NotNull final PasswordHasherService passwordHasherService
  ) {
    this.userRepository = userRepository;
    this.smartValidator = smartValidator;
    this.passwordHasherService = passwordHasherService;
  }

  public UserModel createUser(@NotNull @Validated UserRegisterRequest userRegisterRequest) {
    SimpleErrors errors = new SimpleErrors(userRegisterRequest);
    smartValidator.validate(userRegisterRequest, errors);

    if (errors.hasErrors()) {
      log.error("Validation errors occurred:");

      for (final var error : errors.getAllErrors()) {
        log.error("Error: {}", error.getDefaultMessage());
      }
    }

    if (userRepository.existsByEmail(userRegisterRequest.email())) {
      throw new EmailAlreadyInUseException();
    }

    if (userRepository.existsByUsername(userRegisterRequest.username())) {
      throw new UsernameAlreadyInUseException();
    }

    final UserModel userModel = UserModelBuilder.builder()
        .setUsername(userRegisterRequest.username())
        .setPassword(passwordHasherService.hash(userRegisterRequest.password()))
        .setEmail(userRegisterRequest.email())
        .setFirstName(userRegisterRequest.firstName())
        .setLastName(userRegisterRequest.lastName())
        .build();

    return userRepository.save(userModel);
  }
}
