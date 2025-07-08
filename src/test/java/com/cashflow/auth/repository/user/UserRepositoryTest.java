package com.cashflow.auth.repository.user;

import com.cashflow.auth.config.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest extends BaseTest {

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