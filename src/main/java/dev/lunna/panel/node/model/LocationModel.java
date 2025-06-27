package dev.lunna.panel.node.model;

import dev.lunna.panel.core.model.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "locations")
public class LocationModel extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String shortCode;

  @Column(nullable = false, length = 255)
  private String description;

  @Column(nullable = false)
  private boolean maintenanceMode = false;

  @Column(nullable = false)
  private boolean enabled = true;

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String getShortCode() {
    return shortCode;
  }

  public void setShortCode(String shortCode) {
    this.shortCode = shortCode;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isMaintenanceMode() {
    return maintenanceMode;
  }

  public void setMaintenanceMode(boolean maintenanceMode) {
    this.maintenanceMode = maintenanceMode;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
