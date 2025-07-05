package dev.lunna.panel.security.exception;

import dev.lunna.panel.core.exception.JsonBaseException;

public class RoleNotFoundException extends JsonBaseException {
  public RoleNotFoundException() {
    super(404, "role.not_found", "Role not found");
  }

  public RoleNotFoundException(String message) {
    super(404, "role.not_found", message);
  }
}
