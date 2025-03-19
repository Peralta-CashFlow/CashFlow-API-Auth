package com.cashflow.auth.repository.profile;

import com.cashflow.auth.domain.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByNameIgnoreCase(String name);
}
