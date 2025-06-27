package dev.lunna.panel.security.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "permissions")
public class PermissionModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column
  private String description;

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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof PermissionModel other)) return false;

    if (!id.equals(other.id)) return false;
    if (!name.equals(other.name)) return false;
    return Objects.equals(description, other.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}


