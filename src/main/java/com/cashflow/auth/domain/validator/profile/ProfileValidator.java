package com.cashflow.auth.domain.validator.profile;

import com.cashflow.auth.domain.entities.Profile;
import com.cashflow.exception.core.CashFlowException;
import org.springframework.http.HttpStatus;

import java.util.Objects;

public class ProfileValidator {

    private ProfileValidator() {}

    public static void validateProfile(Profile profile) throws CashFlowException {
        if (Objects.isNull(profile)) {
            throw new CashFlowException(
                    HttpStatus.NOT_FOUND.value(),
                    "Profile Not Found",
                    "Requested profile not found.",
                    ProfileValidator.class.getName(),
                    "validateProfile"
            );
        }
    }
}
