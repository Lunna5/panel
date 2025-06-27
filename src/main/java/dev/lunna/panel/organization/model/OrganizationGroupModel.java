package dev.lunna.panel.organization.model;

import dev.lunna.panel.server.model.ServerPermissionModel;
import dev.lunna.panel.user.model.UserModel;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "organization_groups")
public class OrganizationGroupModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(length = 1000)
  private String description;

  @ManyToOne
  @JoinColumn(name = "organization_id", nullable = false)
  private OrganizationModel organization;

  @ManyToMany
  @JoinTable(
      name = "group_users",
      joinColumns = @JoinColumn(name = "group_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private Set<UserModel> users = new HashSet<>();

  @ManyToMany
  @JoinTable(
      name = "group_permissions",
      joinColumns = @JoinColumn(name = "group_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id")
  )
  private Set<ServerPermissionModel> permissions = new HashSet<>();

  public OrganizationGroupModel() {
  }

  public OrganizationGroupModel(String name, OrganizationModel organization) {
    this.name = name;
    this.organization = organization;
  }

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

  public OrganizationModel getOrganization() {
    return organization;
  }

  public void setOrganization(OrganizationModel organization) {
    this.organization = organization;
  }

  public Set<UserModel> getUsers() {
    return users;
  }

  public void setUsers(Set<UserModel> users) {
    this.users = users;
  }

  public Set<ServerPermissionModel> getPermissions() {
    return permissions;
  }

  public void setPermissions(Set<ServerPermissionModel> permissions) {
    this.permissions = permissions;
  }
}