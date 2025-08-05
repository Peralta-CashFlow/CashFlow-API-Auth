package com.cashflow.auth.controller.financial;

import com.cashflow.auth.domain.dto.request.EditFinancialInformationRequest;
import com.cashflow.auth.domain.dto.response.FinancialInformationResponse;
import com.cashflow.auth.service.financial.IFinancialProfileService;
import com.cashflow.commons.core.dto.request.BaseRequest;
import com.cashflow.exception.core.CashFlowException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Validated
@RestController
@RequestMapping(value = "/auth/financial-profile")
@CrossOrigin(origins = "${application.cross.origin}")
@Tag(name = "Financial Profile", description = "Financial profile related operations")
public class FinancialProfileController implements IFinancialProfileController {

    private final Logger log = LoggerFactory.getLogger(FinancialProfileController.class);

    private final IFinancialProfileService financialProfileService;

    public FinancialProfileController(final IFinancialProfileService financialProfileService) {
        this.financialProfileService = financialProfileService;
    }

    @Override
    @PatchMapping
    public FinancialInformationResponse editFinancialProfile(
            @Valid @RequestBody EditFinancialInformationRequest request,
            Locale language
    ) throws CashFlowException {
        log.info("Received request to edit financial profile for user with ID: {}", request.userId());
        return financialProfileService.editUserFinancialInformation(new BaseRequest<>(language, request));
    }

    @Override
    @GetMapping("/{userId}")
    public FinancialInformationResponse getFinancialProfile(
            @PathVariable("userId") Long userId,
            @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") Locale language
    ) throws CashFlowException {
        log.info("Received request to get financial profile for user with ID: {}", userId);
        return financialProfileService.getUserFinancialInformation(new BaseRequest<>(language, userId));
    }

}
