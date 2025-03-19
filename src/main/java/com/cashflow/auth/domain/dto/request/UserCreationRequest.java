package com.cashflow.auth.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "User creation request")
public record UserCreationRequest(
        @NotEmpty(message = "{user.first.name.blank}")
        @Size(max = 30, message = "{user.first.name.greater.than.valid}")
        @Schema(description = "User first name", example = "Vinicius")
        String firstName,
        @NotEmpty(message = "{user.last.name.blank}")
        @Size(max = 30, message = "{user.last.name.greater.than.valid}")
        @Schema(description = "User last name", example = "Peralta")
        String lastName,
        @Email(message = "{user.email.invalid}")
        @NotEmpty(message = "{user.email.blank}")
        @Size(max = 100, message = "{user.email.greater.than.valid}")
        @Schema(description = "User e-mail", example = "vinicius-peralta@hotmail.com")
        String email,
        @NotEmpty(message = "{user.password.blank}")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$", message = "{user.password.invalid}")
        @Schema(description = "User password", example = "Password123!")
        String password
) {
}