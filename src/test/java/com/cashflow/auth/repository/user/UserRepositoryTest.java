package com.cashflow.auth.repository.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void givenValidEmail_whenFindByEmailIgnoreCase_thenUserFound() {
        assertTrue(userRepository.findByEmailIgnoreCase("vinicius-peralta@email.com").isPresent());
    }

    @Test
    void givenInvalidEmail_whenFindByEmailIgnoreCase_thenUserNotFound() {
        assertTrue(userRepository.findByEmailIgnoreCase("invalid@email.com").isEmpty());
    }
}