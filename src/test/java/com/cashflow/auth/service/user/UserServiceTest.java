package com.cashflow.auth.service.user;

import com.cashflow.auth.domain.dto.request.EditPasswordRequest;
import com.cashflow.auth.domain.dto.request.EditPersonalInformationRequest;
import com.cashflow.auth.domain.dto.request.UserCreationRequest;
import com.cashflow.auth.domain.dto.response.UserResponse;
import com.cashflow.auth.domain.entities.Profile;
import com.cashflow.auth.domain.entities.User;
import com.cashflow.auth.domain.mapper.user.UserMapper;
import com.cashflow.auth.domain.templates.entities.ProfileTemplates;
import com.cashflow.auth.domain.templates.entities.UserTemplates;
import com.cashflow.auth.repository.user.UserRepository;
import com.cashflow.auth.service.profile.IProfileService;
import com.cashflow.commons.core.dto.request.BaseRequest;
import com.cashflow.exception.core.CashFlowException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private IProfileService profileService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MessageSource messageSource;

    private final BaseRequest<UserCreationRequest> baseRequest = UserTemplates.getBaseUserCreationRequest();

    private final BaseRequest<EditPersonalInformationRequest> editPersonalInformationRequestBaseRequest = UserTemplates.getBaseEditPersonalInformationRequest();

    private final Profile profile = ProfileTemplates.getProfile();

    private final User user = UserTemplates.getUser();

    private final BaseRequest<EditPasswordRequest> editPasswordRequestBaseRequest = UserTemplates.getBaseEditPasswordRequest();

    @Test
    void givenDuplicatedUserCreationRequest_whenRegister_thenExceptionIsThrown() {
        when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(user));
        assertThrows(CashFlowException.class, () -> userService.register(baseRequest));
        verify(messageSource, times(2)).getMessage(anyString(), any(), any(Locale.class));
    }

    @Test
    void givenNewUserCreationRequest_whenRegister_thenUserIsCreated() throws CashFlowException {
        when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.empty());
        when(profileService.getProfileByName(anyString())).thenReturn(profile);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        user.setAvatar(null);
        when(userRepository.save(any())).thenReturn(user);

        UserResponse expectedResponse = UserMapper.mapToUserResponse(user);

        assertEquals(expectedResponse, userService.register(baseRequest));
    }

    @Test
    void givenProfileNotFound_whenRegister_thenExceptionIsThrown() {
        when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.empty());
        when(profileService.getProfileByName(anyString())).thenReturn(null);
        assertThrows(CashFlowException.class, () -> userService.register(baseRequest));
    }

    @Test
    @SneakyThrows
    void givenRegisteredEmailAndCorrectPassword_whenFindUserByEmailAndPassword_thenUserIsReturned() {
        String email = "email";
        String password = "password";
        when(userRepository.findByEmailIgnoreCase(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        assertEquals(user, userService.findUserByEmailAndPassword(email, password, Locale.getDefault()));
    }

    @Test
    void givenRegisteredEmailAndIncorrectPassword_whenFindUserByEmailAndPassword_thenExceptionIsThrown() {
        String email = "email";
        String password = "wrongPassword";
        when(userRepository.findByEmailIgnoreCase(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        assertThrows(CashFlowException.class, () -> userService.findUserByEmailAndPassword(email, password, Locale.getDefault()));
        verify(messageSource, times(2)).getMessage(anyString(), any(), any(Locale.class));
    }

    @Test
    void givenNotRegisteredEmail_whenFindUserByEmailAndPassword_thenExceptionIsThrown() {
        String email = "email";
        String password = "wrongPassword";
        when(userRepository.findByEmailIgnoreCase(email)).thenReturn(Optional.empty());

        assertThrows(CashFlowException.class, () -> userService.findUserByEmailAndPassword(email, password, Locale.getDefault()));
        verify(messageSource, times(2)).getMessage(anyString(), any(), any(Locale.class));
    }

    @Test
    void givenInvalidUserId_whenEditPersonalInformation_thenExceptionIsThrown() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CashFlowException.class, () -> userService.editPersonalInformation(editPersonalInformationRequestBaseRequest));
    }

    @Test
    @SneakyThrows
    void givenValidUserId_whenEditPersonalInformation_thenUserIsUpdated() {

        EditPersonalInformationRequest request = editPersonalInformationRequestBaseRequest.getRequest();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse response = userService.editPersonalInformation(editPersonalInformationRequestBaseRequest);

        assertAll(() -> {
                    assertEquals(request.firstName(), response.firstName());
                    assertEquals(request.lastName(), response.lastName());
                    assertEquals(request.birthDay(), response.birthDay());
                    assertEquals(request.taxNumber(), response.taxRegistration());
                    assertNotNull(request.gender());
                    assertEquals(request.gender().getDescription(), response.gender());
                    assertEquals("", response.avatar());
                }
        );
    }

    @Test
    @SneakyThrows
    void givenNullValuesForNullableFields_whenEditPersonalInformation_thenUserIsUpdatedWithNulls() {

        EditPersonalInformationRequest request = new EditPersonalInformationRequest(
                1L,
                "Vinicius",
                "Peralta",
                null,
                null,
                null,
                null
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse response = userService.editPersonalInformation(new BaseRequest<>(Locale.ENGLISH, request));

        assertAll(() -> {
            assertNull(response.gender());
            assertNull(response.birthDay());
            assertNull(response.taxRegistration());
            assertEquals("", response.avatar());
        });
    }

    @Test
    void givenInvalidUserId_whenGetUserInformation_thenExceptionIsThrown() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CashFlowException.class, () -> userService.getUserInformation(new BaseRequest<>(Locale.ENGLISH, 1L)));
    }

    @Test
    @SneakyThrows
    void givenValidUserId_whenGetUserInformation_thenUserInformationIsReturned() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserResponse response = userService.getUserInformation(new BaseRequest<>(Locale.ENGLISH, 1L));
        assertEquals(UserMapper.mapToUserResponse(user), response);
    }

    @Test
    @SneakyThrows
    void givenInvalidPassword_whenChangePassword_thenExceptionIsThrown() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        assertThrows(CashFlowException.class, () -> userService.changePassword(editPasswordRequestBaseRequest));
    }

    @Test
    @SneakyThrows
    void givenValidPassword_whenChangePassword_thenPasswordIsChanged() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("newEncodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.changePassword(editPasswordRequestBaseRequest);
        assertEquals("newEncodedPassword", user.getPassword());
    }

}