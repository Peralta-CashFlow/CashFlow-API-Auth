package com.cashflow.auth.domain.templates.entities;

import com.cashflow.auth.domain.entities.Profile;

import java.util.List;

public class ProfileTemplates {
    public static Profile getProfile() {
        return new Profile(
                1L,
                "profile",
                List.of(RoleTemplates.getRole())
        );
    }
}
