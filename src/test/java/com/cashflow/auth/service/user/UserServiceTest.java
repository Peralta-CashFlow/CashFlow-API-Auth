package com.cashflow.auth.service.user;

import com.cashflow.auth.config.BaseTest;
import com.cashflow.auth.domain.dto.request.UserCreationRequest;
import com.cashflow.auth.domain.dto.response.UserResponse;
import com.cashflow.auth.domain.entities.Profile;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest extends BaseTest {

    private UserService userService;

    @Mock
    private IProfileService profileService;

    @Mock
    private MessageSource messageSource;

    @Mock
    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final ProfileRepository profileRepository;

    private final BaseRequest<UserCreationRequest> baseRequest = UserTemplates.getBaseUserCreationRequest();

    private final UserCreationRequest userCreationRequest = baseRequest.getRequest();

    private Profile profile;

    @Autowired
    UserServiceTest(final UserRepository userRepository,
                    final ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    @BeforeEach
    void setup() {
        userService = new UserService(
                userRepository,
                profileService,
                passwordEncoder,
                messageSource
        );
        profile = profileRepository.findByName("Basic").orElse(null);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    void givenNewUserCreationRequest_whenRegister_thenUserResponse() {

        when(profileService.getProfileByName("Basic")).thenReturn(profile);
        when(passwordEncoder.encode(userCreationRequest.password())).thenReturn("encodedPassword");

        UserResponse response = userService.register(baseRequest);

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

        when(profileService.getProfileByName("Basic")).thenReturn(profile);
        when(passwordEncoder.encode(userCreationRequest.password())).thenReturn("encodedPassword");

        userService.register(baseRequest);

        assertThrows(CashFlowException.class, () -> userService.register(baseRequest));

    }
}