package com.cashflow.auth.service.profile;

import com.cashflow.auth.domain.entities.Profile;
import com.cashflow.exception.core.CashFlowException;

public interface IProfileService {

    Profile getProfileByName(String name) throws CashFlowException;

}
