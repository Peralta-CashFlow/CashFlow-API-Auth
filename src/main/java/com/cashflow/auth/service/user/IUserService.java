package com.cashflow.auth.service.user;

import com.cashflow.auth.domain.dto.request.UserCreationRequest;
import com.cashflow.auth.domain.dto.response.UserResponse;
import com.cashflow.commons.core.dto.request.BaseRequest;
import com.cashflow.exception.core.CashFlowException;

public interface IUserService {

    UserResponse register(BaseRequest<UserCreationRequest> baseRequest) throws CashFlowException;

}
