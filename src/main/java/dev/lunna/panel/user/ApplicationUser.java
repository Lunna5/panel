package dev.lunna.panel.user;

import dev.lunna.panel.user.model.UserModel;
import dev.lunna.panel.user.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ApplicationUser implements UserDetails {
  private final Long id; // Unique identifier for the user
  private final UserRepository userRepository;

  public ApplicationUser(
      @NotNull final Long id,
      @NotNull final UserRepository userRepository
  ) {
    requireNonNull(id, "id must not be null");
    requireNonNull(userRepository, "userRepository must not be null");

    this.id = id;
    this.userRepository = userRepository;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public String getPassword() {
    return "";
  }

  @Override
  public String getUsername() {
    return "";
  }

  @NotNull
  public UserModel getUser() {
    return userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " does not exist."));
  }
}
