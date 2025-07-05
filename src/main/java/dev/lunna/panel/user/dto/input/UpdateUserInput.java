package dev.lunna.panel.user.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.Nullable;

public record UpdateUserInput(
    @NotBlank
    Long id, // User ID is required

    @Email(message = "validation.email.invalid")
    @Nullable String email,

    @Length(min = 3, max = 20, message = "auth.username.length")
    @Nullable String username,

    @Length(min = 2, max = 50, message = "auth.first_name.length")
    @Nullable String firstName,

    @Length(max = 150, message = "auth.last_name.length")
    @Nullable String lastName,

    @Length(min = 8, message = "auth.password.length")
    @Nullable String password
) {}