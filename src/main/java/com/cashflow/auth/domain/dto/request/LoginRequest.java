package com.cashflow.auth.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Login request")
public record LoginRequest(

        @Schema(description = "User e-mail", example = "vinicius-peralta@hotmail.com")
        String email,

        @Schema(description = "User password", example = "Password123!")
        String password

) {}