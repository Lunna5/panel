package dev.lunna.panel.security.exception;

import dev.lunna.panel.core.exception.JsonBaseException;

public class CredentialsNotMatchException extends JsonBaseException {
  public CredentialsNotMatchException() {
    super(401, "auth.login.credentials_not_match", "The provided credentials do not match any existing account.");
  }
}
