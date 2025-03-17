package com.cashflow.auth.domain.mapper.user;

import com.cashflow.auth.domain.dto.request.UserCreationRequest;
import com.cashflow.auth.domain.dto.response.UserResponse;
import com.cashflow.auth.domain.entities.Profile;
import com.cashflow.auth.domain.entities.User;
import com.cashflow.auth.domain.enums.AccountType;

public class UserMapper {

    private UserMapper() {}

    public static User mapFromUserCreationRequestAndProfile(UserCreationRequest userCreationRequest, Profile profile) {
        return User.builder()
                .firstName(userCreationRequest.firstName())
                .lastName(userCreationRequest.lastName())
                .email(userCreationRequest.email())
                .password(userCreationRequest.password())
                .accountType(AccountType.CASH_FLOW)
                .profile(profile)
                .build();
    }

    public static UserResponse mapToUserResponse(User user) {
        return new UserResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAccountType().name(),
                user.getProfile().getName()
        );
    }

}
