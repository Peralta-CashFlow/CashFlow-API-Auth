package com.cashflow.auth.controller.financial;

import com.cashflow.auth.config.BaseTest;
import com.cashflow.auth.domain.dto.response.FinancialInformationResponse;
import com.cashflow.auth.domain.templates.entities.FinancialProfileTemplates;
import com.cashflow.auth.domain.templates.entities.UserTemplates;
import com.cashflow.auth.repository.financial.FinancialProfileRepository;
import com.cashflow.auth.repository.profile.ProfileRepository;
import com.cashflow.auth.repository.user.UserRepository;
import com.cashflow.auth.service.financial.IFinancialProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(FinancialProfileController.class)
class FinancialProfileControllerTest extends BaseTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @MockitoBean
    private IFinancialProfileService iFinancialProfileService;

    private static final String BASE_REQUEST_URL = "/auth/financial-profile";

    private final FinancialInformationResponse response = FinancialProfileTemplates.financialInformationResponse();

    @Autowired
    FinancialProfileControllerTest(final MockMvc mockMvc,
                       final ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    @SneakyThrows
    void givenEditFinancialInformationRequest_whenEditFinancialProfile_thenFinancialInformationResponseIsReturned() {

        String jsonRequest = objectMapper.writeValueAsString(FinancialProfileTemplates.editFinancialInformationRequest());

        when(iFinancialProfileService.editUserFinancialInformation(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.patch(BASE_REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(response)));

    }

}