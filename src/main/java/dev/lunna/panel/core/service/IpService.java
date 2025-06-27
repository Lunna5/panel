package dev.lunna.panel.core.service;

import dev.lunna.panel.core.config.ProxySettings;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public final class IpService {
  private final ProxySettings proxySettings;

  public IpService(@NotNull final ProxySettings proxySettings) {
    this.proxySettings = proxySettings;
  }

  public String getClientIp(@NotNull final HttpServletRequest request) {
    if (proxySettings.isUnderProxy()) {
      for (final var header: proxySettings.getIpHeaderNames()) {
        String ipList = request.getHeader(header);

        if (ipList != null && !ipList.isEmpty() && !ipList.equalsIgnoreCase("unknown")) {
          String ip = ipList.split(",")[0].trim();
          if (isValidIp(ip)) {
            return ip;
          }
        }
      }
    }

    return request.getRemoteAddr();
  }

  private static boolean isValidIp(String ip) {
    return ip != null && !ip.isEmpty() && !ip.equalsIgnoreCase("unknown");
  }
}
