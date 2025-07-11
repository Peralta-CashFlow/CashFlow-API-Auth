package com.cashflow.auth.domain.validator.user;

import com.cashflow.exception.core.CashFlowException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;

public class UserValidator {

    private UserValidator() {}

    public static void validateUserPassword(String password, String userPassword,
                                            PasswordEncoder passwordEncoder, Locale locale,
                                            MessageSource messageSource
    ) throws CashFlowException {
        if (!passwordEncoder.matches(password, userPassword)) {
            throw new CashFlowException(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    messageSource.getMessage("user.old.password.incorrect.title", null, locale),
                    messageSource.getMessage("user.old.password.incorrect.message", null, locale),
                    UserValidator.class.getName(),
                    "validateUserPassword"
            );
        }
    }
}
