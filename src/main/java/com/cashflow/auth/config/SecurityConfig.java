package com.cashflow.auth.config;

import com.cashflow.auth.core.filter.JwtValidatorFilter;
import com.cashflow.auth.core.service.jwt.CashFlowJwtService;
import com.cashflow.auth.filter.CashFlowLoginFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Profile("!test")
public class SecurityConfig {

    @Value(value = "${jwt.secret}")
    private String jwtSecret;

    private static final String[] SECURITY_WHITELIST_ENDPOINT = {
            "/auth/user/register",
            "/auth/user/login",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    @Bean
    public CashFlowJwtService cashFlowJwtService() {
        return new CashFlowJwtService(jwtSecret);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                            CashFlowJwtService cashFlowJwtService,
                                            CashFlowLoginFilter cashFlowLoginFilter
    ) throws Exception {
        return httpSecurity
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers(SECURITY_WHITELIST_ENDPOINT).permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(cashFlowLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtValidatorFilter(cashFlowJwtService, SECURITY_WHITELIST_ENDPOINT), UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
