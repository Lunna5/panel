package dev.lunna.panel.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "panel.proxy")
public class ProxySettings {
  public boolean underProxy = false;

  public List<String> ipHeaderNames = List.of("X-Forwarded-For", "X-Real-IP");

  public boolean isUnderProxy() {
    return underProxy;
  }

  public void setUnderProxy(boolean underProxy) {
    this.underProxy = underProxy;
  }

  public List<String> getIpHeaderNames() {
    return ipHeaderNames;
  }

  public void setIpHeaderNames(List<String> ipHeaderNames) {
    this.ipHeaderNames = ipHeaderNames;
  }
}
