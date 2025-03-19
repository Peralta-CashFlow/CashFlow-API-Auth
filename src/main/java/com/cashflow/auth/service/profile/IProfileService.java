package com.cashflow.auth.service.profile;

import com.cashflow.auth.domain.entities.Profile;

public interface IProfileService {

    Profile getProfileByName(String name);

}
