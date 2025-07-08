package com.cashflow.auth.repository.profile;

import com.cashflow.auth.config.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfileRepositoryTest extends BaseTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Test
    void givenValidProfileName_whenFindByNameIgnoreCase_thenReturnProfile() {
        assertTrue(profileRepository.findByNameIgnoreCase("Basic").isPresent());
    }

    @Test
    void givenInvalidProfileName_whenFindByNameIgnoreCase_thenReturnEmpty() {
        assertTrue(profileRepository.findByNameIgnoreCase("Invalid").isEmpty());
    }

}