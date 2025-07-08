package com.cashflow.auth.service.jwt;

import com.cashflow.auth.core.utils.AuthUtils;
import com.cashflow.auth.domain.entities.User;
import com.cashflow.auth.domain.templates.entities.UserTemplates;
import com.cashflow.exception.core.CashFlowException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private MessageSource messageSource;

    @Test
    @SneakyThrows
    void givenUser_whenGenerateJwtToken_thenTokenIsReturned() {
        try (MockedStatic<AuthUtils> mocked = Mockito.mockStatic(AuthUtils.class)) {
            SecretKey key = Keys.hmacShaKeyFor("12345678901234567890123456789012".getBytes());
            mocked.when(AuthUtils::getJwtSecretKey).thenReturn(key);
            User user = UserTemplates.getUser();
            ReflectionTestUtils.setField(jwtService, "jwtExpiration", 3L);
            SecretKey secretKey = AuthUtils.getJwtSecretKey();
            String jwtToken = jwtService.generateJwtToken(user, null);
            assertAll(() -> {
                assertNotNull(jwtToken);
                assertDoesNotThrow(() ->
                        Jwts.parser().verifyWith(secretKey)
                                .build().parseSignedClaims(jwtToken)
                );
            });
        }
    }

    @Test
    void givenInvalidUser_whenGenerateJwtToken_thenExceptionIsThrown() {
        assertThrows(CashFlowException.class, () -> jwtService.generateJwtToken(new User(), null));
    }
}