package dev.lunna.panel.security.model;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "roles")
public class RoleModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  private String description;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "role_permissions",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id")
  )
  private Set<PermissionModel> permissions = new HashSet<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<PermissionModel> getPermissions() {
    return permissions;
  }

  public void setPermissions(Set<PermissionModel> permissions) {
    this.permissions = permissions;
  }

  public void addPermission(@NotNull final PermissionModel permission) {
    requireNonNull(permission, "permission must not be null");

    if (permissions == null) {
      permissions = new HashSet<>();
    }

    permissions.add(permission);
  }

  public void removePermission(@NotNull final PermissionModel permission) {
    requireNonNull(permission, "permission must not be null");

    if (permissions != null) {
      permissions.remove(permission);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof RoleModel other)) return false;

    if (!id.equals(other.id)) return false;
    return name.equals(other.name);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name);
  }
}
