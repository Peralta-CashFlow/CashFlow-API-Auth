package com.cashflow.auth.filter;

import com.cashflow.auth.domain.entities.User;
import com.cashflow.auth.domain.mapper.user.UserMapper;
import com.cashflow.auth.service.jwt.IJwtService;
import com.cashflow.auth.service.user.IUserService;
import com.cashflow.exception.core.CashFlowException;
import com.cashflow.exception.core.domain.mapper.ExceptionResponseMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Locale;

@Component
public class CashFlowLoginFilter extends OncePerRequestFilter {

    private final IJwtService iJwtService;

    private final IUserService userService;

    public CashFlowLoginFilter(final IJwtService iJwtService,
                               final IUserService userService) {
        this.iJwtService = iJwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Locale locale = request.getLocale();
        try {
            User user = userService.findUserByEmailAndPassword(
                    request.getParameter("email"),
                    request.getParameter("password"),
                    locale
            );
            String jwtToken = iJwtService.generateJwtToken(user, locale);
            SecurityContextHolder.getContext().setAuthentication(UserMapper.mapToCashFlowAuthentication(user, jwtToken));
        } catch (CashFlowException exception) {
            response.setStatus(exception.getHttpStatusCode());
            response.setContentType("application/json");
            response.getWriter().write(
                    new ObjectMapper()
                            .writeValueAsString(ExceptionResponseMapper.fromCashFlowException(exception))
            );
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/auth/user/login");
    }

}
