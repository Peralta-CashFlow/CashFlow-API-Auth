package com.cashflow.auth.config;

import com.cashflow.auth.repository.financial.FinancialProfileRepository;
import com.cashflow.auth.repository.profile.ProfileRepository;
import com.cashflow.auth.repository.user.UserRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@ActiveProfiles("test")
public abstract class BaseTest {

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private ProfileRepository profileRepository;

    @MockitoBean
    private FinancialProfileRepository financialProfileRepository;

}
