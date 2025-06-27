package dev.lunna.panel.security.model;

import dev.lunna.panel.core.model.BaseEntity;
import dev.lunna.panel.user.model.UserModel;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "sessions")
public class SessionModel extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, name = "user_id", updatable = false, insertable = false)
  private Long userId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private UserModel user;

  @Column
  private String userAgent;

  @Column(name = "ip_address")
  private String ipAddress;

  @Column(name = "expires_at", nullable = true)
  private Long expiresAt;

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
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

  public Long getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(Long expiresAt) {
    this.expiresAt = expiresAt;
  }

  public boolean isExpired() {
    return expiresAt != null && System.currentTimeMillis() > expiresAt;
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
