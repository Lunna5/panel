package dev.lunna.panel.security.authority;

import static dev.lunna.panel.security.authority.ApplicationAuthority.*;

public enum ApplicationRole {
  ROLE_USER(PERMISSION_SUBMIT_CARD),
  ROLE_ADMIN(PERMISSION_SUBMIT_CARD, PERMISSION_MANAGE_USERS, PERMISSION_VIEW_CARDS);

  private final ApplicationAuthority[] authorities;

  ApplicationRole(ApplicationAuthority... authorities) {
    this.authorities = authorities;
  }

  public ApplicationAuthority[] getAuthorities() {
    return authorities;
  }
}