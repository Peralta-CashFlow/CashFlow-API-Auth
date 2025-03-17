package com.cashflow.auth.domain.templates.entities;

import com.cashflow.auth.core.domain.enums.RoleEnum;
import com.cashflow.auth.domain.entities.Role;

public class RoleTemplates {
    public static Role getRole() {
        return new Role(
                1L,
                RoleEnum.CASH_FLOW_BASICS
        );
    }
}
