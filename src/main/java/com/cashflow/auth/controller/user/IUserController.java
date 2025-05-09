package com.cashflow.auth.controller.user;

import com.cashflow.auth.core.domain.authentication.CashFlowAuthentication;
import com.cashflow.auth.domain.dto.request.UserCreationRequest;
import com.cashflow.auth.domain.dto.response.UserResponse;
import com.cashflow.exception.core.CashFlowException;
import com.cashflow.exception.core.domain.dto.response.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

public interface IUserController {

    @Operation(
            summary = "Register a new user",
            description = "Should register a new user from the provided request data.",
            parameters = {
                    @Parameter(name = "Accept-Language", description = "Language to be used on response messages", in = ParameterIn.HEADER, example = "en")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
            ),
            @ApiResponse(responseCode = "409", description = "User already registered",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
            )
    })
    UserResponse register(
            @Valid @RequestBody UserCreationRequest userCreationRequest,
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") Locale language
    ) throws CashFlowException;

    @Operation(
            summary = "Login a user",
            description = "Should login a user from the provided request data.",
            parameters = {
                    @Parameter(name = "email", description = "User e-mail", example = "vinicius-peralta@hotmail.com", required = true),
                    @Parameter(name = "password", description = "User password", example = "Password123!", required = true),
                    @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, description = "Language to be used on response messages", example = "en")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.cashflow.auth.domain.dto.response.CashFlowAuthentication.class))
            ),
            @ApiResponse(responseCode = "401", description = "Email or password invalid",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
            ),
            @ApiResponse(responseCode = "500", description = "Error while generation JWT token",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
            )
    })
    CashFlowAuthentication login(
            @RequestParam @NotEmpty String email,
            @RequestParam @NotEmpty String password,
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") Locale language
    );

}
