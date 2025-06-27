package dev.lunna.panel.core.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public record JsonError(
    LocalDateTime timestamp,
    int status,
    String tl,
    String msg
) {
  public Map<String, Object> toMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("timestamp", timestamp);
    map.put("status", status);
    map.put("tl", tl);
    map.put("msg", msg);
    return map;
  }
}
