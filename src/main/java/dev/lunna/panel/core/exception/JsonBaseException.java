package dev.lunna.panel.core.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class JsonBaseException extends RuntimeException {
  private final LocalDateTime timestamp;
  private final int status;
  private final String tl;
  private final String msg;

  public JsonBaseException(int status, String tl, String msg) {
    super(msg);
    this.timestamp = LocalDateTime.now();
    this.status = status;
    this.tl = tl;
    this.msg = msg;
  }

  public JsonBaseException(String tl, String msg) {
    this(500, tl, msg);
  }

  public Map<String, Object> toMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("timestamp", timestamp);
    map.put("status", status);
    map.put("tl", tl);
    map.put("msg", msg);
    return map;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public int getStatus() {
    return status;
  }

  public String getTl() {
    return tl;
  }

  public String getMsg() {
    return msg;
  }
}
