package com.cashflow.auth.domain.entities;

import com.cashflow.auth.core.domain.enums.RoleEnum;
import com.cashflow.auth.domain.templates.entities.UserTemplates;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UserTest {

    private final User user = UserTemplates.getUser();

    @Test
    void givenUser_whenGetAuthoritiesIsCalled_thenRoleListIsReturned() {
        List<RoleEnum> authorities = user.getAuthorities();
        assertFalse(authorities.isEmpty());
        assertEquals(RoleEnum.CASH_FLOW_BASICS, authorities.getFirst());
    }

    @Test
    void givenUser_whenGetUsernameIsCalled_thenFullNameIsReturned() {
        assertEquals("Vinicius Peralta", user.getUsername());
    }
}