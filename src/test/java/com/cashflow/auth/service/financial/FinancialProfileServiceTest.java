package com.cashflow.auth.service.financial;

import com.cashflow.auth.config.BaseTest;
import com.cashflow.auth.domain.dto.request.EditFinancialInformationRequest;
import com.cashflow.auth.domain.dto.response.FinancialInformationResponse;
import com.cashflow.auth.domain.entities.FinancialProfile;
import com.cashflow.auth.domain.entities.User;
import com.cashflow.auth.domain.templates.entities.FinancialProfileTemplates;
import com.cashflow.auth.domain.templates.entities.UserTemplates;
import com.cashflow.auth.repository.financial.FinancialProfileRepository;
import com.cashflow.auth.service.user.IUserService;
import com.cashflow.commons.core.dto.request.BaseRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinancialProfileServiceTest extends BaseTest {

    @InjectMocks
    private FinancialProfileService financialProfileService;

    @Mock
    private IUserService iUserService;

    @Mock
    private FinancialProfileRepository financialProfileRepository;

    private final BaseRequest<EditFinancialInformationRequest> baseRequest = FinancialProfileTemplates.baseEditFinancialInformationRequest();

    private final EditFinancialInformationRequest editFinancialInformationRequest = baseRequest.getRequest();

    @Test
    @SneakyThrows
    void givenEditFinancialInformationRequestUserWithoutFinancialProfile_whenEditUserFinancialInformation_thenReturnFinancialInformationResponse() {
        User user = UserTemplates.getUser();
        user.setFinancialProfile(null);
        when(iUserService.findUserById(editFinancialInformationRequest.userId(), baseRequest.getLanguage()))
                .thenReturn(user);
        FinancialInformationResponse response = financialProfileService.editUserFinancialInformation(baseRequest);
        assertEditFinancialProfile(response);
    }

    @Test
    @SneakyThrows
    void givenEditFinancialInformationRequestUserWithFinancialProfile_whenEditUserFinancialInformation_thenReturnFinancialInformationResponse() {
        User user = UserTemplates.getUser();
        when(iUserService.findUserById(editFinancialInformationRequest.userId(), baseRequest.getLanguage()))
                .thenReturn(user);
        FinancialInformationResponse response = financialProfileService.editUserFinancialInformation(baseRequest);
        assertEditFinancialProfile(response);
    }

    private void assertEditFinancialProfile(FinancialInformationResponse response) {
        assertAll(() -> {
            assertNotNull(response);
            assertEquals(editFinancialInformationRequest.userId(), response.userId());
            assertEquals(editFinancialInformationRequest.income(), response.income());
            assertEquals(editFinancialInformationRequest.expense(), response.expense());
            assertEquals(editFinancialInformationRequest.goals(), response.goals());
            assertEquals(editFinancialInformationRequest.occupation(), response.occupation());
        });
    }

    @Test
    @SneakyThrows
    void givenBaseRequestForUserWithFinancialProfile_whenGetUserFinancialInformation_thenReturnFinancialInformationResponse() {
        User user = UserTemplates.getUser();
        BaseRequest<Long> longBaseRequest = new BaseRequest<>(Locale.ENGLISH, 1L);
        when(iUserService.findUserById(1L, baseRequest.getLanguage()))
                .thenReturn(user);

        FinancialProfile financialProfile = user.getFinancialProfile();
        FinancialInformationResponse response = financialProfileService.getUserFinancialInformation(longBaseRequest);

        assertAll(() -> {
            assertNotNull(response);
            assertEquals(financialProfile.getUser().getId(), response.userId());
            assertEquals(financialProfile.getMonthlyIncome(), response.income());
            assertEquals(financialProfile.getOccupation(), response.occupation());
            assertEquals(financialProfile.getMonthlyExpensesLimit(), response.expense());
            assertEquals(financialProfile.getGoals(), response.goals());
        });
    }

    @Test
    @SneakyThrows
    void givenBaseRequestForUserWithoutFinancialProfile_whenGetUserFinancialInformation_thenReturnFinancialInformationResponse() {
        User user = UserTemplates.getUser();
        user.setFinancialProfile(null);
        BaseRequest<Long> longBaseRequest = new BaseRequest<>(Locale.ENGLISH, 1L);
        when(iUserService.findUserById(1L, baseRequest.getLanguage()))
                .thenReturn(user);
        assertNull(financialProfileService.getUserFinancialInformation(longBaseRequest));
    }
}