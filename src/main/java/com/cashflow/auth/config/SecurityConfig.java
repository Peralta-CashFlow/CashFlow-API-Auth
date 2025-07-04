package com.cashflow.auth.config;

import com.cashflow.auth.core.config.CashFlowSecurityConfig;
import com.cashflow.auth.filter.CashFlowLoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@Profile("!test")
@Import(CashFlowSecurityConfig.class)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   CashFlowLoginFilter cashFlowLoginFilter)
            throws Exception {
        return CashFlowSecurityConfig.securityFilterChain(
                httpSecurity,
                List.of(
                        cashFlowLoginFilter
                ),
                securityWhitelistEndpoints()
        );
    }

    private String[] securityWhitelistEndpoints() {
        return new String[]{
                "/auth/user/register",
                "/auth/user/login"
        };
    }
}
