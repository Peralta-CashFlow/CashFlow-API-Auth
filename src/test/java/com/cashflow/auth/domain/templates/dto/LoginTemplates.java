package com.cashflow.auth.domain.templates.dto;

import com.cashflow.auth.domain.dto.request.LoginRequest;

public class LoginTemplates {

    private LoginTemplates(){}

    public static LoginRequest loginRequest() {
        return new LoginRequest("vinicius-peralta@dev.com", "Password1234!");
    }

}
