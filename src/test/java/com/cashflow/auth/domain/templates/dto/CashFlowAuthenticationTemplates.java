package com.cashflow.auth.domain.templates.dto;

import com.cashflow.auth.core.domain.authentication.CashFlowAuthentication;
import com.cashflow.auth.core.domain.enums.RoleEnum;

import java.util.List;

public class CashFlowAuthenticationTemplates {
    public static CashFlowAuthentication getCashFlowAuthentication() {
        return new CashFlowAuthentication(
                1L,
                "Vinicius",
                "Peralta",
                "vinicius-peralta@hotmail.com",
                List.of(RoleEnum.CASH_FLOW_BASICS),
                true,
                "jwtToken"
        );
    }
}
