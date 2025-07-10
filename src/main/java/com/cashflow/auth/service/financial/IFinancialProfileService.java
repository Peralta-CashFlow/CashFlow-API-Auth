package com.cashflow.auth.service.financial;

import com.cashflow.auth.domain.dto.request.EditFinancialInformationRequest;
import com.cashflow.auth.domain.dto.response.FinancialInformationResponse;
import com.cashflow.commons.core.dto.request.BaseRequest;
import com.cashflow.exception.core.CashFlowException;

public interface IFinancialProfileService {

    FinancialInformationResponse editUserFinancialInformation(BaseRequest<EditFinancialInformationRequest> baseRequest) throws CashFlowException;

}
