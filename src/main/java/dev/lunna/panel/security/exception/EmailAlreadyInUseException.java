package dev.lunna.panel.security.exception;

import dev.lunna.panel.core.exception.JsonBaseException;

public class EmailAlreadyInUseException extends JsonBaseException {
  public EmailAlreadyInUseException() {
    super(409, "auth.register.email.already_in_use", "The provided email address is already associated with an existing account.");
  }
}
