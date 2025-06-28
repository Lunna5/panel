package dev.lunna.panel.security.exception;

import dev.lunna.panel.core.exception.JsonBaseException;

public class AuthenticationNeeded extends JsonBaseException {
  public AuthenticationNeeded() {
    super(401, "auth.authentication_needed", "Authentication is required to access this resource.");
  }
}
