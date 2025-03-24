package com.cashflow.auth.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User information response")
public record UserResponse(
        @Schema(description = "User first name", example = "Vinicius")
        String firstName,
        @Schema(description = "User last name", example = "Peralta")
        String lastName,
        @Schema(description = "User e-mail", example = "vinicius-peralta@hotmail.com")
        String email,
        @Schema(description = "User account type", example = "CASH_FLOW")
        String accountType,
        @Schema(description = "User profile", example = "Basic")
        String profile
) {
}
