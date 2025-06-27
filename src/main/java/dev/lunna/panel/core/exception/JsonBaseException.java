package dev.lunna.panel.core.exception;

import java.time.LocalDateTime;

public abstract class JsonBaseException extends RuntimeException {
  private final JsonError error;
  public JsonBaseException(int status, String tl, String msg) {
    super(msg);
    this.error = new JsonError(LocalDateTime.now(), status, tl, msg);
  }

  public JsonBaseException(String tl, String msg) {
    this(500, tl, msg);
  }

  public JsonError getError() {
    return error;
  }
}
