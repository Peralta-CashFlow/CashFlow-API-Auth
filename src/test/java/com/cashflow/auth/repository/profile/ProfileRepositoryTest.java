package com.cashflow.auth.repository.profile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class ProfileRepositoryTest {

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