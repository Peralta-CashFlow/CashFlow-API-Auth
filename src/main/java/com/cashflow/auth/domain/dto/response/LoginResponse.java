package com.cashflow.auth.domain.dto.response;

import com.cashflow.auth.core.domain.enums.RoleEnum;

import java.util.List;

public record LoginResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        List<RoleEnum> authorities,
        String jwtToken,
        String avatar
) {}
