package dev.lunna.panel.user;

import dev.lunna.panel.security.model.RoleModel;
import dev.lunna.panel.user.model.UserModel;
import dev.lunna.panel.user.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static java.util.Objects.requireNonNull;

public class ApplicationUser implements UserDetails {
  private final Long id; // Unique identifier for the user
  private final String ip;
  private final UserRepository userRepository;

  public ApplicationUser(
      @NotNull final Long id,
      @NotNull final String ip,
      @NotNull final UserRepository userRepository
  ) {
    this.id = requireNonNull(id, "id must not be null");
    this.ip = requireNonNull(ip, "ip must not be null");
    this.userRepository = requireNonNull(userRepository, "userRepository must not be null");
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return getUser().getRoles().stream()
        .map(RoleModel::getPermissions)
        .flatMap(Collection::stream)
        .map(permission -> new SimpleGrantedAuthority(permission.getName()))
        .toList();
  }

  public boolean hasPermission(@NotNull final String permissionName) {
    return getAuthorities().stream()
        .anyMatch(authority -> authority.getAuthority().equals(permissionName));
  }

  public boolean hasPermissions(@NotNull final String... permissionNames) {
    for (String permissionName : permissionNames) {
      if (!hasPermission(permissionName)) {
        return false;
      }
    }

    return true;
  }

  @Override
  public String getPassword() {
    return getUser().getPassword();
  }

  @Override
  public String getUsername() {
    return getUser().getEmail();
  }

  @NotNull
  public String getIp() {
    return ip;
  }

  @NotNull
  public UserModel getUser() {
    return userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " does not exist."));
  }
}
