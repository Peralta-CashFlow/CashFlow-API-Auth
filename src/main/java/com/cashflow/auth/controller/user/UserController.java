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
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
public class UserController implements IUserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final IUserService userService;

    public UserController(final IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserResponse register(
            @Valid @RequestBody UserCreationRequest userCreationRequest,
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") Locale language
    ) throws CashFlowException {
        log.info("Received request to register a new user with e-mail: {}", userCreationRequest.email());
        return userService.register(new BaseRequest<>(language, userCreationRequest));
    }

    @Override
    public CashFlowAuthentication login(
            @RequestParam @NotEmpty String email,
            @RequestParam @NotEmpty String password,
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") Locale language
    ) {
        log.info("User successfully logged in via CashFlowLoginFilter.");
        return (CashFlowAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
