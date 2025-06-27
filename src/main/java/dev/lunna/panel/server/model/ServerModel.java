package dev.lunna.panel.server.model;

import dev.lunna.panel.core.model.BaseEntity;
import dev.lunna.panel.node.model.AllocationModel;
import dev.lunna.panel.node.model.NodeModel;
import dev.lunna.panel.organization.model.OrganizationGroupModel;
import dev.lunna.panel.user.model.UserModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "servers")
public class ServerModel extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, updatable = false)
  private UUID uuid = UUID.randomUUID();

  @Column(name = "node_id", nullable = false)
  private Long nodeId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "node_id", insertable = false, updatable = false)
  private NodeModel node;

  @Column(nullable = false)
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Enumerated(EnumType.ORDINAL)
  @Column(nullable = false)
  private ServerStatus status = ServerStatus.INSTALLING;

  @Column(name = "skip_scripts")
  private Boolean skipScripts = false;

  @Column(name = "owner_id")
  private Long ownerId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id", insertable = false, updatable = false)
  private UserModel user;

  @Column(name = "organization_group_id")
  private Long organizationGroupId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "organization_group_id", insertable = false, updatable = false)
  private OrganizationGroupModel organizationGroup;

  @Column(nullable = false)
  private Integer memory;

  @Column(nullable = false)
  private Integer swap;

  @Column(nullable = false)
  private Integer disk;

  @Column(nullable = false)
  private Integer io;

  @Column(nullable = false)
  private Integer cpu;

  @Column(nullable = false)
  private Integer threads = 0;

  @Column(name = "oom_disabled", nullable = false)
  private Boolean oomDisabled = true;

  @Column(name = "main_allocation_id", nullable = false)
  private Long mainAllocationId;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "main_allocation_id", insertable = false, updatable = false)
  private AllocationModel mainAllocation;

  @Column(name = "allocation_limit")
  private Integer allocationLimit;

  @Column(name = "database_limit")
  private Integer databaseLimit;

  @Column(name = "backup_limit", nullable = false)
  private Integer backupLimit;

  @Column(name = "installed_at")
  private LocalDateTime installedAt;

  @OneToMany(mappedBy = "server", cascade = CascadeType.ALL)
  private Set<AllocationModel> allocations = new HashSet<>();

  //<editor-fold desc="Getters and Setters">
  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public Long getNodeId() {
    return nodeId;
  }

  public void setNodeId(Long nodeId) {
    this.nodeId = nodeId;
  }

  public NodeModel getNode() {
    return node;
  }

  public void setNode(NodeModel node) {
    this.node = node;
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

  public ServerStatus getStatus() {
    return status;
  }

  public void setStatus(ServerStatus status) {
    this.status = status;
  }

  public Boolean getSkipScripts() {
    return skipScripts;
  }

  public void setSkipScripts(Boolean skipScripts) {
    this.skipScripts = skipScripts;
  }

  public Long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Long ownerId) {
    this.ownerId = ownerId;
  }

  public UserModel getUser() {
    return user;
  }

  public void setUser(UserModel user) {
    this.user = user;
  }

  public Long getOrganizationGroupId() {
    return organizationGroupId;
  }

  public void setOrganizationGroupId(Long organizationGroupId) {
    this.organizationGroupId = organizationGroupId;
  }

  public OrganizationGroupModel getOrganizationGroup() {
    return organizationGroup;
  }

  public void setOrganizationGroup(OrganizationGroupModel organizationGroup) {
    this.organizationGroup = organizationGroup;
  }

  public Integer getMemory() {
    return memory;
  }

  public void setMemory(Integer memory) {
    this.memory = memory;
  }

  public Integer getSwap() {
    return swap;
  }

  public void setSwap(Integer swap) {
    this.swap = swap;
  }

  public Integer getDisk() {
    return disk;
  }

  public void setDisk(Integer disk) {
    this.disk = disk;
  }

  public Integer getIo() {
    return io;
  }

  public void setIo(Integer io) {
    this.io = io;
  }

  public Integer getCpu() {
    return cpu;
  }

  public void setCpu(Integer cpu) {
    this.cpu = cpu;
  }

  public Integer getThreads() {
    return threads;
  }

  public void setThreads(Integer threads) {
    this.threads = threads;
  }

  public Boolean getOomDisabled() {
    return oomDisabled;
  }

  public void setOomDisabled(Boolean oomDisabled) {
    this.oomDisabled = oomDisabled;
  }

  public Long getMainAllocationId() {
    return mainAllocationId;
  }

  public void setMainAllocationId(Long mainAllocationId) {
    this.mainAllocationId = mainAllocationId;
  }

  public AllocationModel getMainAllocation() {
    return mainAllocation;
  }

  public void setMainAllocation(AllocationModel mainAllocation) {
    this.mainAllocation = mainAllocation;
  }

  public Integer getAllocationLimit() {
    return allocationLimit;
  }

  public void setAllocationLimit(Integer allocationLimit) {
    this.allocationLimit = allocationLimit;
  }

  public Integer getDatabaseLimit() {
    return databaseLimit;
  }

  public void setDatabaseLimit(Integer databaseLimit) {
    this.databaseLimit = databaseLimit;
  }

  public Integer getBackupLimit() {
    return backupLimit;
  }

  public void setBackupLimit(Integer backupLimit) {
    this.backupLimit = backupLimit;
  }

  public LocalDateTime getInstalledAt() {
    return installedAt;
  }

  public void setInstalledAt(LocalDateTime installedAt) {
    this.installedAt = installedAt;
  }

  public Set<AllocationModel> getAllocations() {
    return allocations;
  }

  public void setAllocations(Set<AllocationModel> allocations) {
    this.allocations = allocations;
  }
  //</editor-fold>
}
