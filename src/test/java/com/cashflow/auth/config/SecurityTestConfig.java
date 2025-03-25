package com.cashflow.auth.config;

import com.cashflow.auth.core.service.jwt.CashFlowJwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("test")
public class SecurityTestConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    SecurityFilterChain testSecurityFilterChain(HttpSecurity httpSecurity
    ) throws Exception {
        return httpSecurity
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                                request.anyRequest().permitAll()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CashFlowJwtService cashFlowJwtService() {
        return new CashFlowJwtService(jwtSecret);
    }

}
