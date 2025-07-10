package com.cashflow.auth.controller.financial;

import com.cashflow.auth.domain.dto.request.EditFinancialInformationRequest;
import com.cashflow.auth.domain.dto.response.FinancialInformationResponse;
import com.cashflow.exception.core.CashFlowException;
import com.cashflow.exception.core.domain.dto.response.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Locale;

public interface IFinancialProfileController {


    @Operation(
            summary = "Edit user financial profile",
            description = "Should edit user financial profile from the provided request data.",
            security = @SecurityRequirement(name = "Authorization"),
            parameters = {
                    @Parameter(name = "Accept-Language", description = "Language to be used on response messages", in = ParameterIn.HEADER, example = "en"),
                    @Parameter(name = "Authorization", description = "JWT token", in = ParameterIn.HEADER, required = true, example = "JWT.TOKEN.HERE")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User financial profile edited successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FinancialInformationResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthenticated user",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
            ),
            @ApiResponse(responseCode = "403", description = "User not authorized to edit financial information",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
            ),
    })
    FinancialInformationResponse editFinancialProfile(
            @Valid @RequestBody EditFinancialInformationRequest request,
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") Locale language
    ) throws CashFlowException;

}
