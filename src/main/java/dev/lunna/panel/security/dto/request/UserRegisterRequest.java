package dev.lunna.panel.security.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserRegisterRequest(
    @Email(message = "validation.email.invalid")
    @NotBlank(message = "auth.register.email.not_blank")
    String email,
    @NotBlank(message = "auth.register.username.not_blank")
    @Length(min = 3, max = 20, message = "auth.username.length")
    String username,
    @NotBlank(message = "auth.register.password.not_blank")
    @Length(min = 8, message = "auth.password.length")
    String password,
    @NotBlank(message = "auth.register.first_name.not_blank")
    @Length(min = 2, max = 50, message = "auth.first_name.length")
    String firstName,
    @Length(max = 150, message = "auth.last_name.length")
    String lastName
) {}
