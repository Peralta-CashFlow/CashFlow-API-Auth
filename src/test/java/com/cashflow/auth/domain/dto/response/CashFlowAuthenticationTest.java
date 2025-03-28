package com.cashflow.auth.domain.dto.response;

import com.cashflow.auth.core.domain.enums.RoleEnum;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CashFlowAuthenticationTest {

    @Test
    void createCashFlowAuthentication() {
        CashFlowAuthentication cashFlowAuthentication = new CashFlowAuthentication(
                1L,
                "Vinicius",
                "Peralta",
                "vinicius-peralta@hotmail.com",
                List.of(RoleEnum.CASH_FLOW_BASICS),
                true,
                "jwtToken"
        );
        assertEquals(1L, cashFlowAuthentication.id());
    }

}