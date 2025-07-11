package com.cashflow.auth.service.financial;

import com.cashflow.auth.domain.dto.request.EditFinancialInformationRequest;
import com.cashflow.auth.domain.dto.response.FinancialInformationResponse;
import com.cashflow.auth.domain.entities.FinancialProfile;
import com.cashflow.auth.domain.entities.User;
import com.cashflow.auth.domain.mapper.financial.FinancialProfileMapper;
import com.cashflow.auth.repository.financial.FinancialProfileRepository;
import com.cashflow.auth.service.user.IUserService;
import com.cashflow.commons.core.dto.request.BaseRequest;
import com.cashflow.exception.core.CashFlowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FinancialProfileService implements IFinancialProfileService {

    private final Logger log = LoggerFactory.getLogger(FinancialProfileService.class);

    private final IUserService iUserService;

    private final FinancialProfileRepository financialProfileRepository;

    public FinancialProfileService(final IUserService iUserService,
                                   final FinancialProfileRepository financialProfileRepository) {
        this.iUserService = iUserService;
        this.financialProfileRepository = financialProfileRepository;
    }

    @Override
    @PreAuthorize("#baseRequest.request.userId == authentication.credentials.id")
    public FinancialInformationResponse editUserFinancialInformation(BaseRequest<EditFinancialInformationRequest> baseRequest) throws CashFlowException {
        EditFinancialInformationRequest request = baseRequest.getRequest();
        User user = iUserService.findUserById(request.userId(), baseRequest.getLanguage());
        FinancialProfile financialProfile;
        if (Objects.nonNull(user.getFinancialProfile())) {
            log.info("User already has a financial profile, updating it...");
            financialProfile = user.getFinancialProfile();
            FinancialProfileMapper.updateFinancialProfileFromEditRequest(financialProfile, request);
        } else {
            log.info("User does not have a financial profile, creating a new one...");
            financialProfile = FinancialProfileMapper.createFinancialProfileFromEditRequest(user, request);
        }
        financialProfileRepository.save(financialProfile);
        log.info("Financial profile saved successfully for user with ID: {}", request.userId());
        return FinancialProfileMapper.toFinancialInformationResponse(financialProfile);
    }

    @Override
    @PreAuthorize("#baseRequest.request == authentication.credentials.id")
    public FinancialInformationResponse getUserFinancialInformation(BaseRequest<Long> baseRequest) throws CashFlowException {
        User user = iUserService.findUserById(baseRequest.getRequest(), baseRequest.getLanguage());
        FinancialInformationResponse response = null;
        if (Objects.nonNull(user.getProfile())) {
            log.info("User has a financial profile, retrieving it...");
            response = FinancialProfileMapper.toFinancialInformationResponse(user.getFinancialProfile());
        } else {
            log.info("User does not have a financial profile, returning null...");
        }
        return response;
    }
}
