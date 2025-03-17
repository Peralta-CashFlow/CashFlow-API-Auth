package com.cashflow.auth.controller.user;

import com.cashflow.auth.domain.dto.request.UserCreationRequest;
import com.cashflow.auth.domain.dto.response.UserResponse;
import com.cashflow.auth.service.user.IUserService;
import com.cashflow.commons.core.dto.request.BaseRequest;
import com.cashflow.exception.core.CashFlowException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

}
