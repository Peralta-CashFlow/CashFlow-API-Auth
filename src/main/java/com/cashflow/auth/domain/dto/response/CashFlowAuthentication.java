package com.cashflow.auth.domain.dto.response;

import com.cashflow.auth.core.domain.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "CashFlow authentication information")
public record CashFlowAuthentication(

        @Schema(description = "User ID", example = "1")
        Long id,

        @Schema(description = "User first name", example = "Vinicius")
        String firstName,

        @Schema(description = "User last name", example = "Peralta")
        String lastName,

        @Schema(description = "User e-mail", example = "vinicius-peralta@hotmail.com")
        String email,

        @Schema(description = "User roles", example = "[\"CASH_FLOW_BASICS\"]")
        List<RoleEnum> roles,

        @Schema(description = "User authentication status", example = "true")
        Boolean authenticated,

        @Schema(description = "JWT token", example = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJDYXNoRmxvdyIsInN1YiI6IjQiLCJpYXQiOjE3NDI2NjYyNTEsImV4cCI6MTc0MjY3NzA1MSwiaWQiOjQsImZpcnN0TmFtZSI6IlZpbmljaXVzIiwibGFzdE5hbWUiOiJQZXJhbHRhIiwiZW1haWwiOiJ2aW5pY2l1cy1wZXJhbHRhQGhvdG1haWwuY29tIiwicm9sZXMiOiJDQVNIX0ZMT1dfQkFTSUNTIn0.AVRjCp_feXr0qCx06AFxL4S5BkfK45qvNwiYGe3EfVMjKbYgKaKn9IaphUKv4kkpCBNGEXuZZrCRi3s7H1aizg")
        String jwtToken
) {
}
