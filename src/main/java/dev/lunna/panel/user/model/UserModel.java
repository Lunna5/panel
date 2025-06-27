package dev.lunna.panel.user.model;

import dev.lunna.panel.security.model.RoleModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

@Table
@Entity(name = "users")
@Schema(description = "User model representing a user in the database.")
public class UserModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = true)
  private String lastName;

  @Column
  private String password;

  @Column(name = "totp_secret")
  private String totpSecret;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<RoleModel> roles = new HashSet<>();

  public UserModel() {}

  public UserModel(String email, String username, String firstName, String lastName, String password, String totpSecret) {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getTotpSecret() {
    return totpSecret;
  }

  public void setTotpSecret(String totpSecret) {
    this.totpSecret = totpSecret;
  }

  public Set<RoleModel> getRoles() {
    return roles;
  }

  public void setRoles(Set<RoleModel> roles) {
    this.roles = roles;
  }

  public void addRole(@NotNull final RoleModel role) {
    requireNonNull(role, "role must not be null");

    if (roles == null) {
      roles = new HashSet<>();
    }

    roles.add(role);
  }

  public void removeRole(@NotNull final RoleModel role) {
    requireNonNull(role, "role must not be null");

    if (roles != null) {
      roles.remove(role);
    }
  }
}
