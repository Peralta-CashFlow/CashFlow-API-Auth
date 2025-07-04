package com.cashflow.auth.service.jwt;

import com.cashflow.auth.core.utils.AuthUtils;
import com.cashflow.auth.domain.entities.User;
import com.cashflow.exception.core.CashFlowException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Locale;

@Service
public class JwtService implements IJwtService {

    @Value(value = "${jwt.expiration}")
    private Long jwtExpiration;

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    private final MessageSource messageSource;

    public JwtService(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String generateJwtToken(User user, Locale locale) throws CashFlowException {
        try {
            log.info("Generating JWT token for user: {}", user.getUsername());
            SecretKey secretKey = AuthUtils.getJwtSecretKey();
            Date date = new Date();
            String jwt = Jwts.builder()
                    .issuer("CashFlow")
                    .subject(user.getId().toString())
                    .issuedAt(date)
                    .expiration(new Date(date.getTime() + jwtExpiration * 3600000))
                    .signWith(secretKey)
                    .claim("id", user.getId())
                    .claim("firstName", user.getFirstName())
                    .claim("lastName", user.getLastName())
                    .claim("email", user.getEmail())
                    .claim("roles", AuthUtils.mapRoleListToString(user.getAuthorities()))
                    .compact();
            log.info("JWT token generated successfully, will expire in {} hours.", jwtExpiration);
            return jwt;
        } catch (Exception exception) {
            log.error("Error generating JWT token: {}", exception.getMessage());
            throw new CashFlowException(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    messageSource.getMessage("user.jwt.error.title", null, locale),
                    messageSource.getMessage("user.jwt.generation.error.message", null, locale),
                    JwtService.class.getName(),
                    "generateJwtToken"
            );
        }
    }

}
