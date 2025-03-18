package com.cashflow.auth.service.profile;

import com.cashflow.auth.domain.entities.Profile;
import com.cashflow.auth.repository.profile.ProfileRepository;
import com.cashflow.exception.core.CashFlowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
    public Profile getProfileByName(String name) throws CashFlowException {
        log.info("Going to get profile with name: {}", name);
        Optional<Profile> profile = profileRepository.findByName(name);
        if (profile.isPresent()) {
            log.info("Profile with name {} found.", name);
            return profile.get();
        } else {
            log.error("Profile with name {} not found.", name);
            throw new CashFlowException(
                    HttpStatus.NOT_FOUND.value(),
                    "Profile Not Found",
                    "Profile with name " + name + " not found.",
                    ProfileService.class.getName(),
                    "getProfileByName"
            );
        }
    }

}
