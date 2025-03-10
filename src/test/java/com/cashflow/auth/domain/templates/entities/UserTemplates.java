package com.cashflow.auth.domain.templates.entities;

import com.cashflow.auth.domain.entities.User;
import com.cashflow.auth.domain.enums.AccountType;

public class UserTemplates {
    public static User getUser() {
        return new User(
                1L,
                "username",
                "password",
                AccountType.CASH_FLOW,
                ProfileTemplates.getProfile()
        );
    }
}
