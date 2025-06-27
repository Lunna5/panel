package dev.lunna.panel.organization.model;

import dev.lunna.panel.user.model.UserModel;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "organizations")
public class OrganizationModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(length = 1000)
  private String description;

  @Column
  private String logoUrl;

  @ManyToMany
  @JoinTable(
      name = "user_organization",
      joinColumns = @JoinColumn(name = "organization_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private Set<UserModel> users = new HashSet<>();

  public OrganizationModel() {}

  public OrganizationModel(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
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

  public String getLogoUrl() {
    return logoUrl;
  }

  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
  }

  public Set<UserModel> getUsers() {
    return users;
  }

  public void setUsers(Set<UserModel> users) {
    this.users = users;
  }
}
