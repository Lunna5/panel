package dev.lunna.panel.user.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.context.DgsContext;
import dev.lunna.panel.user.dgs.context.CurrentUserContext;
import dev.lunna.panel.user.model.UserModel;
import dev.lunna.panel.user.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@DgsComponent
public class UserController {
  private static final Logger log = LoggerFactory.getLogger(UserController.class);
  private final UserRepository userRepository;

  @Autowired
  public UserController(@NotNull final UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  
  @DgsQuery
  public UserModel me(DgsDataFetchingEnvironment dfe) {
    final CurrentUserContext currentUserContext = DgsContext.getCustomContext(dfe);

    return currentUserContext.getUser().getUser();
  }
}
