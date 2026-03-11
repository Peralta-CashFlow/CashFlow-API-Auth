package com.cashflow.auth.filter;

import com.cashflow.auth.domain.dto.request.LoginRequest;
import com.cashflow.auth.domain.entities.User;
import com.cashflow.auth.domain.mapper.user.UserMapper;
import com.cashflow.auth.service.jwt.IJwtService;
import com.cashflow.auth.service.user.IUserService;
import com.cashflow.exception.core.CashFlowException;
import com.cashflow.exception.core.domain.mapper.ExceptionResponseMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Locale;

@Component
public class CashFlowLoginFilter extends OncePerRequestFilter {

    private final IJwtService iJwtService;
    private final IUserService userService;
    private final ObjectMapper objectMapper;

    public CashFlowLoginFilter(final IJwtService iJwtService,
                               final IUserService userService,
                               final ObjectMapper objectMapper) {
        this.iJwtService = iJwtService;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Value("${application.cross.origin}")
    private String crossOrigin;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, @NonNull FilterChain filterChain) throws IOException {

        Locale locale = request.getLocale();
        LoginRequest loginRequest = objectMapper.readValue(
                request.getInputStream(),
                LoginRequest.class
        );

        try {
            User user = userService.findUserByEmailAndPassword(
                    loginRequest.email(),
                    loginRequest.password(),
                    locale
            );

            String jwtToken = iJwtService.generateJwtToken(user, locale);
            SecurityContextHolder.getContext().setAuthentication(UserMapper.mapToCashFlowAuthentication(user, jwtToken));

            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(
                    new ObjectMapper()
                            .registerModule(new JavaTimeModule())
                            .writeValueAsString(UserMapper.mapToLoginResponse(user, jwtToken))
            );

        } catch (CashFlowException exception) {
            response.setStatus(exception.getHttpStatusCode());
            response.setContentType("application/json");
            response.getWriter().write(
                    new ObjectMapper()
                            .writeValueAsString(ExceptionResponseMapper.fromCashFlowException(exception))
            );
            response.setHeader("Access-Control-Allow-Origin", crossOrigin);
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/auth/user/login");
    }

}
