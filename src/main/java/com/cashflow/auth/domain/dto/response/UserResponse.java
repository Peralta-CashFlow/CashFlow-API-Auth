package com.cashflow.auth.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "User information response")
public record UserResponse(
        @Schema(description = "User first name", example = "Vinicius")
        String firstName,
        @Schema(description = "User last name", example = "Peralta")
        String lastName,
        @Schema(description = "User e-mail", example = "vinicius-peralta@hotmail.com")
        String email,
        @Schema(description = "User profile", example = "Basic")
        String profile,
        @Schema(description = "User avatar", example = "base64 text", nullable = true)
        String avatar,
        @Schema(description = "User gender", example = "Female", nullable = true)
        String gender,
        @Schema(description = "User birthday", example = "1999-02-23", nullable = true)
        LocalDate birthDay,
        @Schema(description = "User tax registration", example = "00011122233", nullable = true)
        String taxRegistration
) {
}
