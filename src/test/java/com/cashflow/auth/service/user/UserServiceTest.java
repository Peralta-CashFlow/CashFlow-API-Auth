package com.cashflow.auth.service.user;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private final Profile profile = ProfileTemplates.getProfile();

    private final User user = UserTemplates.getUser();

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
        when(userRepository.save(any())).thenReturn(user);

        UserResponse expectedResponse = UserMapper.mapToUserResponse(user);

        assertEquals(expectedResponse, userService.register(baseRequest));
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

}