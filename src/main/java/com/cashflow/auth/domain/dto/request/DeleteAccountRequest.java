package com.cashflow.auth.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Delete account request")
public record DeleteAccountRequest(
        @NotNull(message = "{user.id.null}")
        @Schema(description = "User ID", example = "1")
        Long userId,
        @NotEmpty(message = "{user.password.blank}")
        @Schema(description = "User password", example = "Password123!")
        String password
) {
}
