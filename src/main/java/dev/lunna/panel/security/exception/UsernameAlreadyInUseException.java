package dev.lunna.panel.security.exception;

import dev.lunna.panel.core.exception.JsonBaseException;

public class UsernameAlreadyInUseException extends JsonBaseException {
  public UsernameAlreadyInUseException() {
    super(409, "auth.register.username.already_in_use", "The provided username is already associated with an existing account.");
  }
}
