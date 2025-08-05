package com.cashflow.auth.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "User financial information response")
public record FinancialInformationResponse(
        @Schema(description = "User ID", example = "1")
        Long userId,
        @Schema(description = "User occupation", example = "Software Engineer")
        String occupation,
        @Schema(description = "User income", example = "50000.00")
        BigDecimal income,
        @Schema(description = "User expense", example = "25000.00")
        BigDecimal expense,
        @Schema(description = "User goals", example = "Save money for a house")
        String goals
) {}
