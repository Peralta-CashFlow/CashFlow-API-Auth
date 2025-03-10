package com.cashflow.auth.domain.entities;

import com.cashflow.auth.domain.templates.entities.RoleTemplates;
import com.cashflow.core.domain.enums.RoleEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    private final Role role = RoleTemplates.getRole();

    @Test
    void givenRole_whenGetAuthorityIsCalled_thenRoleEnumValueIsReturned() {
        assertEquals(RoleEnum.CASH_FLOW_BASICS.getValue(), role.getAuthority());
    }
}