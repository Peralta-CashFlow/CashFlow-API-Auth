package com.cashflow.auth.service.jwt;

import com.cashflow.auth.config.BaseTest;
import com.cashflow.auth.core.service.jwt.CashFlowJwtService;
import com.cashflow.auth.domain.entities.User;
import com.cashflow.auth.domain.templates.entities.UserTemplates;
import com.cashflow.exception.core.CashFlowException;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtServiceTest extends BaseTest {

    private final JwtService jwtService;

    private final CashFlowJwtService cashFlowJwtService;

    @Autowired
    JwtServiceTest(MessageSource messageSource, CashFlowJwtService cashFlowJwtService) {
        this.cashFlowJwtService = cashFlowJwtService;
        this.jwtService = new JwtService(cashFlowJwtService, messageSource);
    }

    @Test
    @SneakyThrows
    void givenUser_whenGenerateJwtToken_thenTokenIsReturned() {
        User user = UserTemplates.getUser();
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 3L);
        SecretKey secretKey = cashFlowJwtService.generateSecretKey();
        String jwtToken = jwtService.generateJwtToken(user, null);
        assertAll(() -> {
            assertNotNull(jwtToken);
            assertDoesNotThrow(() ->
                    Jwts.parser().verifyWith(secretKey)
                            .build().parseSignedClaims(jwtToken)
            );
        });
    }

    @Test
    void givenInvalidUser_whenGenerateJwtToken_thenExceptionIsThrown() {
        assertThrows(CashFlowException.class, () ->jwtService.generateJwtToken(new User(), null));
    }
}