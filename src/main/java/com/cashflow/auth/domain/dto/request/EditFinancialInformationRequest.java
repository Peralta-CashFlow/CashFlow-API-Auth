package com.cashflow.auth.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Edit user financial information request")
public record EditFinancialInformationRequest(
        @NotNull(message = "{user.id.null}")
        @Schema(description = "User ID", example = "1")
        Long userId,
        @Nullable
        @Size(max = 50, message = "{user.occupation.greater.than.valid}")
        @Schema(description = "User occupation", example = "Software Engineer")
        String occupation,
        @Nullable
        @Min(value = 1L, message = "{user.income.invalid}")
        @Schema(description = "User income", example = "50000.00")
        BigDecimal income,
        @Nullable
        @Min(value = 1L, message = "{user.expense.invalid}")
        @Schema(description = "User expense", example = "25000.00")
        BigDecimal expense,
        @Nullable
        @Size(max = 100, message = "{user.goals.greater.than.valid}")
        @Schema(description = "User goals", example = "Save money for a house")
        String goals
) {}
