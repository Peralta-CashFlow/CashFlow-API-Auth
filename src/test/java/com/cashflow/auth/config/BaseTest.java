package com.cashflow.auth.config;

import com.cashflow.auth.core.service.jwt.CashFlowJwtService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@ActiveProfiles("test")
public abstract class BaseTest {

    @MockitoBean
    private CashFlowJwtService cashFlowJwtService;

}
