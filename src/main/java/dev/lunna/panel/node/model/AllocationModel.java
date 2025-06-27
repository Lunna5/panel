package dev.lunna.panel.node.model;

import dev.lunna.panel.core.model.BaseEntity;
import dev.lunna.panel.server.model.ServerModel;
import jakarta.persistence.*;

/**
 * Allocation model representing IP:Port combinations assigned to nodes.
 * These allocations can be assigned to servers for network connectivity.
 */
@Entity
@Table(name = "allocations")
public class AllocationModel extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The ID of the node this allocation belongs to.
   */
  @Column(name = "node_id", nullable = false)
  private Long nodeId;

  /**
   * The IP address for this allocation.
   */
  @Column(nullable = false)
  private String ip;

  /**
   * The IP alias for this allocation, can be used for display purposes.
   */
  @Column(name = "ip_alias")
  private String ipAlias;

  /**
   * The port number for this allocation.
   */
  @Column(nullable = false)
  private Integer port;

  /**
   * Optional notes about this allocation.
   */
  @Column
  private String notes;

  /**
   * The ID of the server this allocation is assigned to, if any.
   */
  @Column(name = "server_id")
  private Long serverId;

  /**
   * Flag indicating if this is the primary allocation for its server.
   */
  @Column(name = "is_primary", columnDefinition = "BOOLEAN DEFAULT 0")
  private Boolean isPrimary = false;

  /**
   * The node this allocation belongs to.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "node_id", insertable = false, updatable = false)
  private NodeModel node;

  /**
   * The server this allocation is assigned to, if any.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "server_id", insertable = false, updatable = false)
  private ServerModel server;

  //<editor-fold desc="Getters and Setters">
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getNodeId() {
    return nodeId;
  }

  public void setNodeId(Long nodeId) {
    this.nodeId = nodeId;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getIpAlias() {
    return ipAlias;
  }

  public void setIpAlias(String ipAlias) {
    this.ipAlias = ipAlias;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public Long getServerId() {
    return serverId;
  }

  public void setServerId(Long serverId) {
    this.serverId = serverId;
  }

  public Boolean getIsPrimary() {
    return isPrimary;
  }

  public void setIsPrimary(Boolean isPrimary) {
    this.isPrimary = isPrimary;
  }

  public NodeModel getNode() {
    return node;
  }

  public void setNode(NodeModel node) {
    this.node = node;
  }

  public ServerModel getServer() {
    return server;
  }

  public void setServer(ServerModel server) {
    this.server = server;
  }
  //</editor-fold>

  /**
   * Check if this allocation is assigned to a server.
   *
   * @return true if the allocation is assigned, false otherwise
   */
  public boolean isAssigned() {
    return serverId != null;
  }

  /**
   * Check if this allocation is available for assignment.
   *
   * @return true if the allocation is available, false otherwise
   */
  public boolean isAvailable() {
    return serverId == null;
  }

  /**
   * Get the formatted representation of this allocation.
   *
   * @return The IP:Port combination
   */
  public String getAllocationAddressFormatted() {
    return ip + ":" + port;
  }

  /**
   * Get the display address for this allocation, using the alias if available.
   *
   * @return The display address for this allocation
   */
  public String getDisplayAddress() {
    if (ipAlias != null && !ipAlias.isEmpty()) {
      return ipAlias + ":" + port;
    }
    return getAllocationAddressFormatted();
  }

  /**
   * Mark this allocation as the primary one for its server.
   */
  public void markAsPrimary() {
    this.isPrimary = true;
  }

  /**
   * Assign this allocation to a server.
   *
   * @param serverId The ID of the server to assign this allocation to
   * @param isPrimary Whether this should be the primary allocation
   */
  public void assignToServer(Long serverId, boolean isPrimary) {
    this.serverId = serverId;
    this.isPrimary = isPrimary;
  }

  /**
   * Unassign this allocation from its current server.
   */
  public void unassign() {
    this.serverId = null;
    this.isPrimary = false;
  }
}