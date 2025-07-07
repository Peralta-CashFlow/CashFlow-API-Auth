package com.cashflow.auth.domain.dto.request;

import com.cashflow.auth.domain.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Edit user personal information request")
public record EditPersonalInformationRequest(
        @NotNull(message = "{user.id.null}")
        @Schema(description = "User ID", example = "1")
        Long userId,
        @NotEmpty(message = "{user.first.name.blank}")
        @Size(max = 30, message = "{user.first.name.greater.than.valid}")
        @Schema(description = "User first name", example = "Vinicius")
        String firstName,
        @NotEmpty(message = "{user.last.name.blank}")
        @Size(max = 30, message = "{user.last.name.greater.than.valid}")
        @Schema(description = "User last name", example = "Peralta")
        String lastName,
        @Nullable
        Gender gender,
        @Nullable
        LocalDate birthDay,
        @Nullable
        @Pattern(regexp = "^(\\d{11}|\\d{14})$", message = "{user.tax.number.invalid}")
        String taxNumber,
        @Nullable
        @Pattern(regexp = "^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$", message = "{user.avatar.invalid}")
        String avatar
) {
}
