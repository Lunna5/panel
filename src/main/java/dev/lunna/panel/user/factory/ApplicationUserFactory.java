package dev.lunna.panel.user.factory;

import dev.lunna.panel.user.ApplicationUser;
import dev.lunna.panel.user.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserFactory {
  private final UserRepository userRepository;

  @Autowired
  public ApplicationUserFactory(@NotNull final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @NotNull
  public ApplicationUser createUser(@NotNull final Long id, @NotNull final String ip) {
    if (!userRepository.existsById(id)) {
      throw new IllegalArgumentException("User with id " + id + " does not exist.");
    }

    return new ApplicationUser(id, ip, userRepository);
  }
}
