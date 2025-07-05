package dev.lunna.panel.user.controller;

import com.netflix.graphql.dgs.*;
import com.netflix.graphql.dgs.context.DgsContext;
import dev.lunna.panel.security.dto.request.CreateUserInput;
import dev.lunna.panel.user.dgs.context.CurrentUserContext;
import dev.lunna.panel.user.dto.input.UpdateUserInput;
import dev.lunna.panel.user.exception.UserNotFoundException;
import dev.lunna.panel.user.model.UserModel;
import dev.lunna.panel.user.repository.UserRepository;
import dev.lunna.panel.user.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

@DgsComponent
public class UserController {
  private static final Logger log = LoggerFactory.getLogger(UserController.class);
  private final UserRepository userRepository;
  private final UserService userService;

  @Autowired
  public UserController(@NotNull final UserRepository userRepository, UserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @DgsQuery
  public UserModel me(DgsDataFetchingEnvironment dfe) {
    final CurrentUserContext currentUserContext = DgsContext.getCustomContext(dfe);

    return currentUserContext.user().getUser();
  }

  @DgsQuery
  Page<UserModel> users(@InputArgument Integer page) {
    if (page != null && page < 1) {
      log.warn("Invalid page number: {}", page);
      throw new IllegalArgumentException("Page number must be greater than or equal to 1");
    }

    Pageable pageable = Pageable.ofSize(10).withPage(page != null ? page - 1 : 0);

    return userRepository.findAll(pageable);
  }

  @DgsQuery
  public UserModel user(@InputArgument Long id) {
    return userRepository.findById(id).orElseGet(() -> null);
  }

  @DgsQuery
  public UserModel userByEmail(@InputArgument String email) {
    return userRepository.findByEmail(email).orElseGet(() -> null);
  }

  @DgsQuery
  public UserModel userByUsername(@InputArgument String username) {
    return userRepository.findByUsername((username)).orElseGet(() -> null);
  }

  @DgsMutation
  public UserModel createUser(@InputArgument @Validated CreateUserInput input) {
    return userService.createUser(input, true);
  }

  @DgsMutation
  public UserModel updateUser(@InputArgument Long id, @InputArgument @Validated UpdateUserInput input) {
    return userService.updateUser(id, input);
  }

  @DgsMutation
  public void deleteUser(@InputArgument Long id) {
    if (!userRepository.existsById(id)) {
      throw new UserNotFoundException();
    }

    userRepository.deleteById(id);
  }

  @DgsMutation
  public UserModel addRoleToUser(
      @InputArgument Long userId,
      @InputArgument Long roleId
  ) {
    return userService.addRolesToUser(userId, roleId);
  }

  @DgsMutation
  public UserModel removeRoleFromUser(
      @InputArgument Long userId,
      @InputArgument Long roleId
  ) {
    return userService.removeRolesFromUser(userId, roleId);
  }
}
