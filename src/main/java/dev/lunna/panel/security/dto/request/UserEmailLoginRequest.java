package dev.lunna.panel.security.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserEmailLoginRequest(
    @NotBlank(message = "auth.login.email.not_blank")
    String email,
    @NotBlank(message = "auth.login.password.not_blank")
    String password
) {}
