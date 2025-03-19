package com.cashflow.auth.service.profile;

import com.cashflow.auth.domain.entities.Profile;
import com.cashflow.auth.repository.profile.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService implements IProfileService {

    private static final Logger log = LoggerFactory.getLogger(ProfileService.class);

    private final ProfileRepository profileRepository;

    public ProfileService(final ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile getProfileByName(String name) {
        Profile profile = null;
        log.info("Going to get profile with name: {}", name);
        Optional<Profile> profileOptional = profileRepository.findByNameIgnoreCase(name);
        if (profileOptional.isPresent()) {
            log.info("Profile with name {} found.", name);
            profile = profileOptional.get();
        } else {
            log.error("Profile with name {} not found.", name);
        }
        return profile;
    }

}
