package com.cashflow.auth.service.user;

import com.cashflow.auth.domain.dto.request.UserCreationRequest;
import com.cashflow.auth.domain.dto.response.UserResponse;
import com.cashflow.auth.domain.entities.Profile;
import com.cashflow.auth.domain.entities.User;
import com.cashflow.auth.domain.mapper.user.UserMapper;
import com.cashflow.auth.domain.validator.profile.ProfileValidator;
import com.cashflow.auth.repository.user.UserRepository;
import com.cashflow.auth.service.profile.IProfileService;
import com.cashflow.commons.core.dto.request.BaseRequest;
import com.cashflow.exception.core.CashFlowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final IProfileService profileService;

    private final PasswordEncoder passwordEncoder;

    private final MessageSource messageSource;

    public UserService(final UserRepository userRepository,
                       final IProfileService profileService,
                       final PasswordEncoder passwordEncoder,
                       final MessageSource messageSource) {
        this.userRepository = userRepository;
        this.profileService = profileService;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
    }

    @Override
    public UserResponse register(BaseRequest<UserCreationRequest> baseRequest) throws CashFlowException {

        UserCreationRequest userCreationRequest = baseRequest.getRequest();
        String email = userCreationRequest.email();

        log.info("Going to check if user is already registered.");

        checkIfUserAlreadyExists(email, baseRequest.getLanguage());

        log.info("E-mail not registered, proceeding with user creation");

        Profile profile = profileService.getProfileByName("Basic");
        ProfileValidator.validateProfile(profile);

        log.info("Mapping user from request...");
        User user = UserMapper.mapFromUserCreationRequestAndProfile(userCreationRequest, profile);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        log.info("Saving user on database...");
        user = userRepository.save(user);

        log.info("User successfully registered.");
        return UserMapper.mapToUserResponse(user);
    }

    private void checkIfUserAlreadyExists(String email, Locale language) throws CashFlowException {
        if (userRepository.findByEmailIgnoreCase(email).isPresent()) {
            log.error("User is already registered.");
            throw new CashFlowException(
                    HttpStatus.CONFLICT.value(),
                    messageSource.getMessage("user.already.registered.title", null, language),
                    messageSource.getMessage("user.already.registered.message", new Object[]{email}, language),
                    UserService.class.getName(),
                    "register"
            );
        }
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password, Locale locale) throws CashFlowException {
        log.info("Searching for user by e-mail: {} and password...", email);
        Optional<User> userOptional = userRepository.findByEmailIgnoreCase(email);
        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            User user = userOptional.get();
            log.info("User {} found!", user.getUsername());
            return user;
        } else {
            log.error("User {} not found!", email);
            throw new CashFlowException(
                    HttpStatus.UNAUTHORIZED.value(),
                    messageSource.getMessage("user.login.invalid.title", null, locale),
                    messageSource.getMessage("user.login.invalid.message", null, locale),
                    UserService.class.getName(),
                    "findUserByEmailAndPassword"
            );
        }
    }

}
