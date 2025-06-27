package dev.lunna.panel.security.model;

import dev.lunna.panel.user.model.UserModel;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "sessions")
public class SessionModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private UserModel user;

  @Column
  private String userAgent;

  @Column(name = "ip_address")
  private String ipAddress;

  @Column(name = "created_at", nullable = false)
  private Long createdAt;

  @Column(name = "expires_at", nullable = true)
  private Long expiresAt;

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public UserModel getUser() {
    return user;
  }

  public void setUser(UserModel user) {
    this.user = user;
  }

  public String getUserAgent() {
    return userAgent;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public Long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Long createdAt) {
    this.createdAt = createdAt;
  }

  public Long getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(Long expiresAt) {
    this.expiresAt = expiresAt;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof SessionModel model)) return false;

    return Objects.equals(model.id, this.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
