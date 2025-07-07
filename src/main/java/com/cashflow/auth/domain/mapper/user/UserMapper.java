package com.cashflow.auth.domain.mapper.user;

import com.cashflow.auth.core.domain.authentication.CashFlowAuthentication;
import com.cashflow.auth.domain.dto.request.EditPersonalInformationRequest;
import com.cashflow.auth.domain.dto.request.UserCreationRequest;
import com.cashflow.auth.domain.dto.response.UserResponse;
import com.cashflow.auth.domain.entities.Profile;
import com.cashflow.auth.domain.entities.User;
import com.cashflow.auth.utils.ImageUtils;
import com.cashflow.exception.core.CashFlowException;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

public class UserMapper {

    private UserMapper() {
    }

    public static User mapFromUserCreationRequestAndProfile(UserCreationRequest userCreationRequest, Profile profile) {
        return User.builder()
                .firstName(userCreationRequest.firstName())
                .lastName(userCreationRequest.lastName())
                .email(userCreationRequest.email())
                .password(userCreationRequest.password())
                .profile(profile)
                .build();
    }

    public static UserResponse mapToUserResponse(User user) {
        return new UserResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getProfile().getName(),
                Objects.nonNull(user.getAvatar()) ? Base64.getEncoder().encodeToString(user.getAvatar()) : null,
                Objects.nonNull(user.getGender()) ? user.getGender().getDescription() : null,
                user.getBirthday(),
                user.getTaxNumber()
        );
    }

    public static CashFlowAuthentication mapToCashFlowAuthentication(User user, String jwtToken) {
        return new CashFlowAuthentication(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAuthorities(),
                true,
                jwtToken
        );
    }

    public static void editPersonalInformationFromRequest(User user, EditPersonalInformationRequest request) {
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setGender(request.gender());
        user.setBirthday(request.birthDay());
        user.setTaxNumber(request.taxNumber());
        user.setAvatar(ImageUtils.convertBase64ToByteArray(request.avatar()));
    }

}
