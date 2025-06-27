package dev.lunna.panel.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "panel.security.jwt")
public class JWTSettings {
  private String secret;
  private long expiration;

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public long getExpiration() {
    return expiration;
  }

  public void setExpiration(long expiration) {
    this.expiration = expiration;
  }
}
