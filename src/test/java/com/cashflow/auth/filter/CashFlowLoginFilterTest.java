package com.cashflow.auth.filter;

import com.cashflow.auth.core.domain.authentication.CashFlowAuthentication;
import com.cashflow.auth.core.domain.authentication.CashFlowCredentials;
import com.cashflow.auth.domain.entities.User;
import com.cashflow.auth.domain.templates.entities.UserTemplates;
import com.cashflow.auth.service.jwt.IJwtService;
import com.cashflow.auth.service.user.IUserService;
import com.cashflow.exception.core.CashFlowException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CashFlowLoginFilterTest {

    @InjectMocks
    private CashFlowLoginFilter cashFlowLoginFilter;

    @Mock
    private IJwtService iJwtService;

    @Mock
    private IUserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private final Locale locale = Locale.ENGLISH;

    private final String email = "email";

    private final String password = "password";

    @Test
    @SneakyThrows
    void givenRequest_whenDoFilterInternal_thenUserIsAuthenticated() {

        String jwtToken = "jwtToken";
        User user = UserTemplates.getUser();

        when(request.getLocale()).thenReturn(locale);
        when(request.getParameter(email)).thenReturn(email);
        when(request.getParameter(password)).thenReturn(password);
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        when(userService.findUserByEmailAndPassword(email, password, locale)).thenReturn(user);
        when(iJwtService.generateJwtToken(user, locale)).thenReturn(jwtToken);

        cashFlowLoginFilter.doFilterInternal(request, response, filterChain);

        CashFlowAuthentication authentication = (CashFlowAuthentication) SecurityContextHolder.getContext().getAuthentication();
        CashFlowCredentials credentials = authentication.getCredentials();

        assertAll(() -> {
            assertEquals(user.getId(), credentials.id());
            assertEquals(user.getFirstName(), credentials.firstName());
            assertEquals(user.getLastName(), credentials.lastName());
            assertEquals(user.getEmail(), credentials.email());
            assertEquals(user.getAuthorities(), authentication.getAuthorities());
            assertTrue(authentication.isAuthenticated());
            verify(filterChain, times(1)).doFilter(request, response);
            verify(response, times(1)).setContentType("application/json");
            verify(response, times(1)).getWriter();
        });
    }

    @Test
    @SneakyThrows
    void givenUserInvalidRequest_whenDoFilterInternal_thenExceptionIsThrown() {

        when(request.getLocale()).thenReturn(locale);
        when(request.getParameter(email)).thenReturn(email);
        when(request.getParameter(password)).thenReturn(password);
        when(response.getWriter()).thenReturn(mock(PrintWriter.class));
        when(userService.findUserByEmailAndPassword(email, password, locale))
                .thenThrow(new CashFlowException(
                        404,
                        "User not found",
                        "User not found",
                        "User not found",
                        "User not found"
                ));

        cashFlowLoginFilter.doFilterInternal(request, response, filterChain);

        assertAll(() -> {
            verify(response, times(1)).setStatus(404);
            verify(response, times(1)).setContentType("application/json");
            verify(response, times(1)).getWriter();
            verify(filterChain, times(0)).doFilter(request, response);
        });
    }

    @Test
    void givenRequest_whenShouldNotFilter_thenFilterIsApplied() {
        when(request.getServletPath()).thenReturn("/auth/user/login");
        assertFalse(cashFlowLoginFilter.shouldNotFilter(request));
    }

    @Test
    void givenInvalidRequest_whenShouldNotFilter_thenFilterIsNotApplied() {
        when(request.getServletPath()).thenReturn("/auth/user/register");
        assertTrue(cashFlowLoginFilter.shouldNotFilter(request));
    }
}