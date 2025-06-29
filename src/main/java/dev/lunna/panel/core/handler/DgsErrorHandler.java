package dev.lunna.panel.core.handler;

import com.netflix.graphql.types.errors.ErrorType;
import dev.lunna.panel.core.exception.JsonBaseException;
import graphql.GraphQLError;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class DgsErrorHandler {
  @GraphQlExceptionHandler
  public GraphQLError handle(JsonBaseException ex) {
    return GraphQLError.newError().errorType(ErrorType.BAD_REQUEST)
        .message(ex.getError().tl())
        .extensions(ex.getError().toMap())
        .build();
  }
}