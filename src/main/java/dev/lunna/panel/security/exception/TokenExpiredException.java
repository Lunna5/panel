package dev.lunna.panel.security.exception;

import dev.lunna.panel.core.exception.JsonBaseException;

public class TokenExpiredException extends JsonBaseException {
  public TokenExpiredException() {
    super(401, "auth.token.expired", "Token has expired");
  }
}
