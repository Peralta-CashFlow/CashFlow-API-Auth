package com.cashflow.auth.domain.mapper.financial;

import com.cashflow.auth.domain.dto.request.EditFinancialInformationRequest;
import com.cashflow.auth.domain.dto.response.FinancialInformationResponse;
import com.cashflow.auth.domain.entities.FinancialProfile;
import com.cashflow.auth.domain.entities.User;

public class FinancialProfileMapper {

    private FinancialProfileMapper() {}

    public static FinancialProfile createFinancialProfileFromEditRequest(
            User user,
            EditFinancialInformationRequest request
    ) {
        return FinancialProfile.builder()
                .user(user)
                .occupation(request.occupation())
                .monthlyIncome(request.income())
                .monthlyExpensesLimit(request.expense())
                .goals(request.goals())
                .build();
    }

    public static void updateFinancialProfileFromEditRequest(
            FinancialProfile financialProfile,
            EditFinancialInformationRequest request
    ) {
        financialProfile.setOccupation(request.occupation());
        financialProfile.setMonthlyIncome(request.income());
        financialProfile.setMonthlyExpensesLimit(request.expense());
        financialProfile.setGoals(request.goals());
    }

    public static FinancialInformationResponse toFinancialInformationResponse(
            FinancialProfile financialProfile
    ) {
        return new FinancialInformationResponse(
                financialProfile.getUser().getId(),
                financialProfile.getOccupation(),
                financialProfile.getMonthlyIncome(),
                financialProfile.getMonthlyExpensesLimit(),
                financialProfile.getGoals()
        );
    }
}
