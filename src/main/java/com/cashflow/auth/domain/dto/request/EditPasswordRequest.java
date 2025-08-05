package com.cashflow.auth.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Edit password request")
public record EditPasswordRequest(
        @NotNull(message = "{user.id.null}")
        @Schema(description = "User ID", example = "1")
        Long userId,
        @NotNull(message = "{user.old.password.blank}")
        @Schema(description = "User old password", example = "OldPassword123!")
        String oldPassword,
        @NotEmpty(message = "{user.new.password.blank}")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$", message = "{user.password.invalid}")
        @Schema(description = "User new password", example = "NewPassword123!")
        String newPassword
) {
}
