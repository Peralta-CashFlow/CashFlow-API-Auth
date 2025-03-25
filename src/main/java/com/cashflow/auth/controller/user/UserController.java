package com.cashflow.auth.controller.user;

import com.cashflow.auth.core.domain.authentication.CashFlowAuthentication;
import com.cashflow.auth.domain.dto.request.UserCreationRequest;
import com.cashflow.auth.domain.dto.response.UserResponse;
import com.cashflow.auth.service.user.IUserService;
import com.cashflow.commons.core.dto.request.BaseRequest;
import com.cashflow.exception.core.CashFlowException;
import com.cashflow.exception.core.domain.dto.response.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Validated
@RestController
@RequestMapping(value = "/auth/user")
@CrossOrigin(origins = "${application.cross.origin}")
@Tag(name = "User", description = "User related operations")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final IUserService userService;

    public UserController(final IUserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Register a new user",
            description = "Should register a new user from the provided request data."
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
    @PostMapping("/register")
    public UserResponse register(
            @Parameter(name = "userCreationRequest", description = "User creation request", required = true)
            @Valid @RequestBody UserCreationRequest userCreationRequest,
            @Parameter(name = "Accept-Language", description = "Language to be used on response messages", example = "pt")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") Locale language
    ) throws CashFlowException {
        log.info("Received request to register a new user with e-mail: {}", userCreationRequest.email());
        return userService.register(new BaseRequest<>(language, userCreationRequest));
    }

    @Operation(
            summary = "Login a user",
            description = "Should login a user from the provided request data."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CashFlowAuthentication.class))
            ),
            @ApiResponse(responseCode = "401", description = "Email or password invalid",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
            ),
            @ApiResponse(responseCode = "500", description = "Error while generation JWT token",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
            )
    })
    @GetMapping("/login")
    public CashFlowAuthentication login(
            @Parameter(name = "email", description = "User e-mail", required = true) @RequestParam @NotEmpty String email,
            @Parameter(name = "password", description = "User password", required = true) @RequestParam @NotEmpty String password,
            @Parameter(name = "Accept-Language", description = "Language to be used on response messages", example = "pt")
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") Locale language
    ) {
        log.info("User successfully logged in via CashFlowLoginFilter.");
        return (CashFlowAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
