package dev.lunna.panel.user.dgs.context;

import com.netflix.graphql.dgs.context.DgsContext;
import com.netflix.graphql.dgs.context.DgsCustomContextBuilder;
import dev.lunna.panel.user.ApplicationUser;

public class CurrentUserContext {
  private final ApplicationUser user;

  public CurrentUserContext(ApplicationUser user) {
    this.user = user;
  }

  public ApplicationUser getUser() {
    return user;
  }
}
