package dev.lunna.panel.user.service;

import dev.lunna.panel.security.authority.DefaultRoles;
import dev.lunna.panel.security.dto.request.CreateUserInput;
import dev.lunna.panel.security.exception.EmailAlreadyInUseException;
import dev.lunna.panel.security.exception.RoleNotFoundException;
import dev.lunna.panel.security.exception.UsernameAlreadyInUseException;
import dev.lunna.panel.security.model.RoleModel;
import dev.lunna.panel.security.repository.RoleRepository;
import dev.lunna.panel.security.service.hasher.PasswordHasherService;
import dev.lunna.panel.user.dto.input.UpdateUserInput;
import dev.lunna.panel.user.exception.UserNotFoundException;
import dev.lunna.panel.user.model.UserModel;
import dev.lunna.panel.user.model.builder.UserModelBuilder;
import dev.lunna.panel.user.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public final class UserService {
  private static final Logger log = LoggerFactory.getLogger(UserService.class);
  private final UserRepository userRepository;
  private final PasswordHasherService passwordHasherService;
  private final RoleRepository roleRepository;

  public UserService(
      @NotNull final UserRepository userRepository,
      @NotNull final PasswordHasherService passwordHasherService,
      @NotNull final RoleRepository roleRepository
  ) {
    this.userRepository = userRepository;
    this.passwordHasherService = passwordHasherService;
    this.roleRepository = roleRepository;
  }

  public @NotNull UserModel createUser(@NotNull CreateUserInput createUserInput, boolean createdByAdmin) {
    if (userRepository.existsByEmail(createUserInput.email())) {
      throw new EmailAlreadyInUseException();
    }

    if (userRepository.existsByUsername(createUserInput.username())) {
      throw new UsernameAlreadyInUseException();
    }

    UserModel userModel = UserModelBuilder.builder()
        .setUsername(createUserInput.username())
        .setPassword(passwordHasherService.hash(createUserInput.password()))
        .setEmail(createUserInput.email())
        .setFirstName(createUserInput.firstName())
        .setLastName(createUserInput.lastName())
        .build();

    userModel = addRoleByName(userModel, DefaultRoles.USER);

    if (createdByAdmin) {
      if (createUserInput.roles() == null) {
        return userRepository.save(userModel);
      }

      for (final Long role : createUserInput.roles()) {
        addRolesToUser(userModel, role);
      }
    }

    return userRepository.save(userModel);
  }

  public @NotNull UserModel updateUser(@NotNull final Long id, @NotNull UpdateUserInput updateUserInput) {
    final UserModel user = userRepository.findById(id)
        .orElseThrow(UserNotFoundException::new);

    return updateUser(user, updateUserInput);
  }

  public @NotNull UserModel updateUser(@NotNull final UserModel user, @NotNull UpdateUserInput updateUserInput) {
    if (updateUserInput.email() != null && !updateUserInput.email().equals(user.getEmail())) {
      if (userRepository.existsByEmail(updateUserInput.email())) {
        throw new EmailAlreadyInUseException();
      }
      user.setEmail(updateUserInput.email());
    }

    if (updateUserInput.username() != null && !updateUserInput.username().equals(user.getUsername())) {
      if (userRepository.existsByUsername(updateUserInput.username())) {
        throw new UsernameAlreadyInUseException();
      }
      user.setUsername(updateUserInput.username());
    }

    if (updateUserInput.firstName() != null) {
      user.setFirstName(updateUserInput.firstName());
    }

    if (updateUserInput.lastName() != null) {
      user.setLastName(updateUserInput.lastName());
    }

    if (updateUserInput.password() != null) {
      user.setPassword(passwordHasherService.hash(updateUserInput.password()));
    }

    return userRepository.save(user);
  }

  @NotNull
  public UserModel addRolesToUser(Long userId, @NotNull Long... rolesId) {
    final UserModel user = userRepository.findById(userId)
        .orElseThrow(UserNotFoundException::new);

    return addRolesToUser(user, rolesId);
  }

  public UserModel addRoleByName(UserModel userModel, @NotNull String roleName) {
    final RoleModel roleModel = roleRepository.findByName(roleName)
        .orElseThrow(RoleNotFoundException::new);
    userModel.addRole(roleModel);
    return userRepository.save(userModel);
  }

  @NotNull
  public UserModel addRolesToUser(UserModel user, @NotNull Long... rolesId) {
    for (Long role : rolesId) {
      final RoleModel roleModel = roleRepository.findById(role)
          .orElseThrow(RoleNotFoundException::new);
      user.addRole(roleModel);
    }

    return userRepository.save(user);
  }

  public @NotNull UserModel removeRolesFromUser(Long userId, Long... rolesId) {
    final UserModel user = userRepository.findById(userId)
        .orElseThrow(UserNotFoundException::new);

    return removeRolesFromUser(user, rolesId);
  }

  public @NotNull UserModel removeRolesFromUser(UserModel user,  @NotNull Long... rolesId) {
    for (Long role : rolesId) {
      final RoleModel roleModel = roleRepository.findById(role)
          .orElseThrow(RoleNotFoundException::new);

      user.removeRole(roleModel);
    }

    return userRepository.save(user);
  }
}
