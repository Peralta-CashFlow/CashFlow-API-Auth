package com.cashflow.auth.service.profile;

import com.cashflow.auth.config.BaseTest;
import com.cashflow.auth.domain.entities.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfileServiceTest extends BaseTest {

    private final ProfileService profileService;

    @Autowired
    ProfileServiceTest(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Test
    void givenExistingProfileName_whenGetProfileByName_thenProfileIsReturned() {
        String profileName = "Basic";

        Profile profile = profileService.getProfileByName(profileName);

        assertAll(() -> {
            assertNotNull(profile);
            assertEquals(profileName, profile.getName());
        });
    }

    @Test
    void givenInvalidProfileName_whenGetProfileByName_thenProfileIsNull() {
        assertNull(profileService.getProfileByName("Invalid"));
    }
}