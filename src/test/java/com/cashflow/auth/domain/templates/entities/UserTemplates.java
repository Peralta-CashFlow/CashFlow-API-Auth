package com.cashflow.auth.domain.templates.entities;

import com.cashflow.auth.domain.dto.request.EditPersonalInformationRequest;
import com.cashflow.auth.domain.dto.request.UserCreationRequest;
import com.cashflow.auth.domain.dto.response.UserResponse;
import com.cashflow.auth.domain.entities.User;
import com.cashflow.auth.domain.enums.Gender;
import com.cashflow.commons.core.dto.request.BaseRequest;

import java.time.LocalDate;
import java.util.Locale;

public class UserTemplates {
    public static User getUser() {
        User user = new User(
                1L,
                "Vinicius",
                "Peralta",
                "vinicius-peralta@hotmail.com",
                "password",
                ProfileTemplates.getProfile(),
                new byte[0],
                Gender.M,
                LocalDate.now(),
                "00011122233",
                null
        );
        user.setFinancialProfile(FinancialProfileTemplates.financialProfile(user));
        return user;
    }

    public static UserCreationRequest getUserCreationRequest() {
        return new UserCreationRequest(
                "Vinicius",
                "Peralta",
                "vinicius-peralta@hotmail.com",
                "Password123!"
        );
    }

    public static UserResponse getUserResponse() {
        return new UserResponse(
                "Vinicius",
                "Peralta",
                "vinicius-peralta@hotmail.com",
                "Basic",
                "base64 text",
                "Male",
                LocalDate.of(1999, 2, 23),
                "00011122233"
        );
    }

    public static BaseRequest<UserCreationRequest> getBaseUserCreationRequest() {
        return new BaseRequest<>(Locale.ENGLISH, getUserCreationRequest());
    }

    public static EditPersonalInformationRequest getEditPersonalInformationRequest() {
        return new EditPersonalInformationRequest(
                1L,
                "Vinicius",
                "Peralta",
                Gender.M,
                LocalDate.of(1999, 2, 23),
                "12345678901",
                ""
        );
    }
}
