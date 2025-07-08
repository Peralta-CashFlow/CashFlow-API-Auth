package com.cashflow.auth.service.user;

import com.cashflow.auth.domain.dto.request.EditPersonalInformationRequest;
import com.cashflow.auth.domain.dto.request.UserCreationRequest;
import com.cashflow.auth.domain.dto.response.UserResponse;
import com.cashflow.auth.domain.entities.User;
import com.cashflow.commons.core.dto.request.BaseRequest;
import com.cashflow.exception.core.CashFlowException;

import java.util.Locale;

public interface IUserService {

    UserResponse register(BaseRequest<UserCreationRequest> baseRequest) throws CashFlowException;

    User findUserByEmailAndPassword(String email, String password, Locale locale) throws CashFlowException;

    UserResponse editPersonalInformation(BaseRequest<EditPersonalInformationRequest> baseRequest) throws CashFlowException;

    UserResponse getUserInformation(BaseRequest<Long> baseRequest) throws CashFlowException;

}
