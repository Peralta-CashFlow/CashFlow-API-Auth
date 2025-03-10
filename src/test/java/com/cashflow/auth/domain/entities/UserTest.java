package com.cashflow.auth.domain.entities;

import com.cashflow.auth.domain.templates.entities.UserTemplates;
import com.cashflow.core.domain.enums.RoleEnum;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UserTest {
    private final User user = UserTemplates.getUser();
    @Test
    void givenUser_whenGetAuthoritiesIsCalled_thenRoleListIsReturned() {
        List<Role> authorities = user.getAuthorities();
        assertFalse(authorities.isEmpty());
        assertEquals(RoleEnum.CASH_FLOW_BASICS, authorities.getFirst().getRoleEnum());
    }
}