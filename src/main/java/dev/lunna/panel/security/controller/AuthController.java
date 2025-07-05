package dev.lunna.panel.security.controller;

import dev.lunna.panel.core.exception.JsonError;
import dev.lunna.panel.security.dto.request.EmailLoginUserInput;
import dev.lunna.panel.security.dto.request.CreateUserInput;
import dev.lunna.panel.security.dto.response.UserTokenResponse;
import dev.lunna.panel.security.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "API for user registration and login")
public class AuthController {
  private final AuthService authService;

  @Autowired
  public AuthController(@NotNull final AuthService authService) {
    this.authService = authService;
  }

  @Operation(
      summary = "Register a new user",
      description = "Creates a new user account and returns an authentication token",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "User registration details",
          content = @Content(schema = @Schema(implementation = CreateUserInput.class), examples = {
              @ExampleObject(
                  name = "Lunna",
                  value = """
                      {
                        "email": "test@lunna.dev",
                        "username": "lunna",
                        "password": "password123",
                        "firstName": "Lunna",
                        "lastName": "Martín"
                      }
                      """
              )
          })
      )
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "User registered successfully",
          content = @Content(schema = @Schema(implementation = UserTokenResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid registration data",
          content = @Content(schema = @Schema(implementation = JsonError.class))),
      @ApiResponse(responseCode = "409", description = "Email already in use",
          content = @Content(schema = @Schema(implementation = JsonError.class)))
  })
  @PostMapping("/register")
  public UserTokenResponse register(
      @NotNull @Valid @RequestBody final CreateUserInput request,
      @NotNull final HttpServletRequest httpServletRequest
  ) {
    return authService.register(request, httpServletRequest);
  }

  @Operation(
      summary = "Login user",
      description = "Authenticates a user using email and password and returns an access token"
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Authentication successful",
          content = @Content(schema = @Schema(implementation = UserTokenResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid login data",
          content = @Content(schema = @Schema(implementation = JsonError.class))),
      @ApiResponse(responseCode = "401", description = "Incorrect credentials",
          content = @Content(schema = @Schema(implementation = JsonError.class)))
  })
  @PostMapping("/login")
  public UserTokenResponse login(
      @NotNull @Valid @RequestBody final EmailLoginUserInput request,
      @NotNull final HttpServletRequest httpServletRequest
  ) {
    return authService.login(request, httpServletRequest);
  }
}
