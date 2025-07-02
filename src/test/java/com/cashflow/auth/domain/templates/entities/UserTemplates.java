package com.cashflow.auth.domain.templates.entities;

import com.cashflow.auth.domain.dto.request.UserCreationRequest;
import com.cashflow.auth.domain.dto.response.UserResponse;
import com.cashflow.auth.domain.entities.User;
import com.cashflow.commons.core.dto.request.BaseRequest;

import java.util.Locale;

public class UserTemplates {
    public static User getUser() {
        return new User(
                1L,
                "Vinicius",
                "Peralta",
                "vinicius-peralta@hotmail.com",
                "password",
                ProfileTemplates.getProfile()
        );
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
                "Basic"
        );
    }

    public static BaseRequest<UserCreationRequest> getBaseUserCreationRequest() {
        return new BaseRequest<>(Locale.ENGLISH, getUserCreationRequest());
    }
}
