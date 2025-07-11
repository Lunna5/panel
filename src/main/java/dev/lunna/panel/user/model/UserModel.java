package dev.lunna.panel.user.model;

import dev.lunna.panel.core.model.BaseEntity;
import dev.lunna.panel.security.model.RoleModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

@Table
@Entity(name = "users")
@Schema(description = "User model representing a user in the database.")
public class UserModel extends BaseEntity {
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

  public UserModel() {
  }

  public UserModel(
      String email,
      String username,
      String firstName,
      @Nullable String lastName,
      String password,
      @Nullable String totpSecret
  ) {
    this.email = requireNonNull(email, "email must not be null");
    this.username = requireNonNull(username, "username must not be null");
    this.firstName = requireNonNull(firstName, "firstName must not be null");
    this.lastName = lastName; // lastName can be null
    this.password = requireNonNull(password, "password must not be null");
    this.totpSecret = totpSecret; // totpSecret can be null
    this.roles = new HashSet<>();
  }

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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
