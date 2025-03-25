package com.cashflow.auth.service.jwt;

import com.cashflow.auth.domain.entities.User;
import com.cashflow.exception.core.CashFlowException;

import java.util.Locale;

public interface IJwtService {
    String generateJwtToken(User user, Locale locale) throws CashFlowException;
}
