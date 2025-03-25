package com.cashflow.auth.service.user;

import com.cashflow.auth.config.BaseTest;
import com.cashflow.auth.domain.dto.request.UserCreationRequest;
import com.cashflow.auth.domain.dto.response.UserResponse;
import com.cashflow.auth.domain.entities.Profile;
import com.cashflow.auth.domain.entities.User;
import com.cashflow.auth.domain.enums.AccountType;
import com.cashflow.auth.domain.templates.entities.UserTemplates;
import com.cashflow.auth.repository.profile.ProfileRepository;
import com.cashflow.auth.repository.user.UserRepository;
import com.cashflow.auth.service.profile.IProfileService;
import com.cashflow.commons.core.dto.request.BaseRequest;
import com.cashflow.exception.core.CashFlowException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest extends BaseTest {

    private UserService userService;

    @Mock
    private IProfileService profileService;

    private final UserRepository userRepository;

    private final BaseRequest<UserCreationRequest> baseRequest = UserTemplates.getBaseUserCreationRequest();

    private final UserCreationRequest userCreationRequest = baseRequest.getRequest();

    private final Profile profile;

    private final Locale locale = Locale.ENGLISH;

    private final PasswordEncoder passwordEncoder;

    private final MessageSource messageSource;

    @Autowired
    UserServiceTest(final UserRepository userRepository,
                    final ProfileRepository profileRepository,
                    final PasswordEncoder passwordEncoder,
                    final MessageSource messageSource) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
        this.profile = profileRepository.findByNameIgnoreCase("Basic").orElse(null);
    }

    @BeforeEach
    void setUp() {
        this.userService = new UserService(
                userRepository,
                profileService,
                passwordEncoder,
                messageSource
        );
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    void givenNewUserCreationRequest_whenRegister_thenUserResponse() {

        UserResponse response = registerUser();

        assertAll(() -> {
                    assertNotNull(response);
                    assertEquals(response.accountType(), AccountType.CASH_FLOW.name());
                    assertEquals(response.email(), userCreationRequest.email());
                    assertEquals(response.profile(), profile.getName());
                    assertEquals(response.firstName(), userCreationRequest.firstName());
                    assertEquals(response.lastName(), userCreationRequest.lastName());
                }
        );
    }

    @Test
    @SneakyThrows
    void givenExistingUserCreationRequest_whenRegister_thenCashFlowException() {
        registerUser();
        assertThrows(CashFlowException.class, () -> userService.register(baseRequest));

    }

    @Test
    @SneakyThrows
    void givenInvalidProfile_whenRegister_thenCashFlowException() {

        when(profileService.getProfileByName("Basic")).thenReturn(null);

        assertThrows(CashFlowException.class, () -> userService.register(baseRequest));

    }

    @Test
    @SneakyThrows
    void givenValidUser_whenFindByEmailAndPassword_thenReturnUser() {
        UserResponse userResponse = registerUser();
        User user = userService.findUserByEmailAndPassword(userResponse.email(), userCreationRequest.password(), locale);
        assertEquals(user.getEmail(), userResponse.email());
    }

    @Test
    @SneakyThrows
    void givenInvalidUser_whenFindByEmailAndPassword_thenCashFlowException() {
        assertThrows(CashFlowException.class, () -> userService.findUserByEmailAndPassword("invalid", "invalid", locale));
    }

    @Test
    @SneakyThrows
    void givenInvalidPassword_whenFindByEmailAndPassword_thenCashFlowException() {
        registerUser();
        assertThrows(CashFlowException.class, () -> userService.findUserByEmailAndPassword(userCreationRequest.email(), "invalid", locale));
    }

    @SneakyThrows
    private UserResponse registerUser() {
        when(profileService.getProfileByName("Basic")).thenReturn(profile);
        return userService.register(baseRequest);
    }
}