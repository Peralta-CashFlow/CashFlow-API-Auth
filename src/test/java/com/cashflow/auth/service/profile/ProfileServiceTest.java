package com.cashflow.auth.service.profile;

import com.cashflow.auth.domain.entities.Profile;
import com.cashflow.auth.domain.templates.entities.ProfileTemplates;
import com.cashflow.auth.repository.profile.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @InjectMocks
    private ProfileService profileService;

    @Mock
    private ProfileRepository profileRepository;

    @Test
    void givenExistingProfileName_whenGetProfileByName_thenProfileIsReturned() {

        String profileName = "Basic";
        Profile profile = ProfileTemplates.getProfile();

        when(profileRepository.findByNameIgnoreCase(profileName)).thenReturn(Optional.of(profile));

        Profile response = profileService.getProfileByName(profileName);

        assertAll(() -> {
            assertNotNull(response);
            assertEquals(profile, response);
        });
    }

    @Test
    void givenInvalidProfileName_whenGetProfileByName_thenProfileIsNull() {
        String profileName = "Invalid";
        when(profileRepository.findByNameIgnoreCase(profileName)).thenReturn(Optional.empty());
        assertNull(profileService.getProfileByName(profileName));
    }
}