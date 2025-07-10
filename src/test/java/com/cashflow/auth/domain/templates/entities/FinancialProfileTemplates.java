package com.cashflow.auth.domain.templates.entities;

import com.cashflow.auth.domain.dto.request.EditFinancialInformationRequest;
import com.cashflow.auth.domain.dto.response.FinancialInformationResponse;
import com.cashflow.auth.domain.entities.FinancialProfile;
import com.cashflow.auth.domain.entities.User;

import java.math.BigDecimal;

public class FinancialProfileTemplates {

    public static FinancialProfile financialProfile(User user) {
        return new FinancialProfile(
                1L,
                user,
                "Software Engineer",
                BigDecimal.valueOf(50000L),
                BigDecimal.valueOf(20000L),
                "Buy a house, save for retirement, travel the world"
        );
    }

    public static FinancialInformationResponse financialInformationResponse() {
        return new FinancialInformationResponse(
                1L,
                "Software Engineer",
                BigDecimal.valueOf(50000L),
                BigDecimal.valueOf(20000L),
                "Save for retirement"
        );
    }

    public static EditFinancialInformationRequest editFinancialInformationRequest() {
        return new EditFinancialInformationRequest(
                1L,
                "Engineer",
                BigDecimal.valueOf(50000L),
                BigDecimal.valueOf(20000L),
                "Buy a house"
        );
    }
}
