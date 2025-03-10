package com.cashflow.auth.domain.templates.entities;

import com.cashflow.auth.domain.entities.Role;
import com.cashflow.core.domain.enums.RoleEnum;

public class RoleTemplates {
    public static Role getRole() {
        return new Role(
                1L,
                RoleEnum.CASH_FLOW_BASICS
        );
    }
}
