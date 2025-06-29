package dev.lunna.panel.security.dgs.directive;

import com.netflix.graphql.dgs.DgsDirective;
import dev.lunna.panel.security.exception.AuthenticationNeeded;
import dev.lunna.panel.user.ApplicationUser;
import dev.lunna.panel.user.dgs.context.CurrentUserContextBuilder;
import graphql.schema.*;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@DgsDirective(name = "auth")
public class AuthDirective implements SchemaDirectiveWiring {
  private final CurrentUserContextBuilder currentUserContextBuilder;

  @Autowired
  public AuthDirective(CurrentUserContextBuilder currentUserContextBuilder) {
    this.currentUserContextBuilder = currentUserContextBuilder;
  }

  @Override
  public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
    GraphQLFieldDefinition field = environment.getElement();
    DataFetcher<?> authDataFetcher = getAuthDataFetcher(environment, getPermissions(environment));

    return environment.setFieldDataFetcher(authDataFetcher);
  }

  private @NotNull DataFetcher<?> getAuthDataFetcher(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment, ArrayList<String> permissions) {
    DataFetcher<?> originalDataFetcher = environment.getFieldDataFetcher();
    final ArrayList<String> finalPermissions = permissions;

    return dataFetchingEnvironment -> {
      ApplicationUser currentUser = currentUserContextBuilder.build().user();

      if (currentUser == null) {
        throw new AuthenticationNeeded();
      }

      dataFetchingEnvironment.getGraphQlContext().put("currentUser", currentUser);

      if (finalPermissions != null && !finalPermissions.isEmpty()) {
        if (!currentUser.hasPermissions(finalPermissions.toArray(new String[0]))) {
          throw new AuthenticationNeeded(); // TODO: better exception handling
        }
      }

      return originalDataFetcher.get(dataFetchingEnvironment);
    };
  }

  @Nullable
  private ArrayList<String> getPermissions(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
    ArrayList<String> permissions = new ArrayList<>();

    if (environment.getAppliedDirective().getArgument("permissions") != null) {
      final Object permissionsArg = environment.getAppliedDirective().getArgument("permissions").getValue();

      switch (permissionsArg) {
        case null -> {
          return null;
        }
        case Iterable<?> iterable -> {
          for (Object permission : iterable) {
            if (permission instanceof String) {
              permissions.add((String) permission);
            } else {
              throw new IllegalArgumentException("Permissions must be a list of strings");
            }
          }
        }
        case String singlePermission -> permissions.add(singlePermission);
        default -> {
          throw new IllegalArgumentException("Permissions must be a string or a list of strings");
        }
      }
    } else {
      return null;
    }

    return permissions;
  }
}
