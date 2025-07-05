package dev.lunna.panel.user.exception;

import dev.lunna.panel.core.exception.JsonBaseException;

public class UserNotFoundException extends JsonBaseException {
  public UserNotFoundException() {
    super(404, "user.not_found", "User not found");
  }
}
