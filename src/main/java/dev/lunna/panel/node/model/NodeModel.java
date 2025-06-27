package dev.lunna.panel.node.model;

import dev.lunna.panel.core.model.BaseEntity;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "nodes")
public class NodeModel extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, updatable = false)
  private UUID uuid = UUID.randomUUID();

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false, unique = true)
  private String description;

  @Column(nullable = false, name = "location_id", insertable=false, updatable = false)
  private Long locationId;

  @ManyToOne
  @JoinColumn(nullable = false, name = "location_id")
  private LocationModel location;

  @Column(nullable = false, length = 191)
  private String fqdn;

  @Column(nullable = false, name = "ssl_enabled")
  private boolean ssl = false;

  @Column(nullable = false)
  private Integer memory;

  @Column(nullable = false)
  private Integer disk;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
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

  public LocationModel getLocation() {
    return location;
  }

  public void setLocation(LocationModel location) {
    this.location = location;
  }

  public String getFqdn() {
    return fqdn;
  }

  public void setFqdn(String fqdn) {
    this.fqdn = fqdn;
  }

  public boolean isSsl() {
    return ssl;
  }

  public void setSsl(boolean ssl) {
    this.ssl = ssl;
  }

  public Integer getMemory() {
    return memory;
  }

  public void setMemory(Integer memory) {
    this.memory = memory;
  }

  public Integer getDisk() {
    return disk;
  }

  public void setDisk(Integer disk) {
    this.disk = disk;
  }
}
