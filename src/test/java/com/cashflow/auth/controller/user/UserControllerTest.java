package com.cashflow.auth.controller.user;

import com.cashflow.auth.config.BaseTest;
import com.cashflow.auth.domain.dto.response.UserResponse;
import com.cashflow.auth.domain.templates.entities.UserTemplates;
import com.cashflow.auth.repository.profile.ProfileRepository;
import com.cashflow.auth.repository.user.UserRepository;
import com.cashflow.auth.service.user.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
class UserControllerTest extends BaseTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @MockitoBean
    private IUserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private ProfileRepository profileRepository;

    private static final String BASE_REQUEST_URL = "/auth/user";

    private final UserResponse userResponse = UserTemplates.getUserResponse();

    @Autowired
    UserControllerTest(final MockMvc mockMvc,
                       final ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    @SneakyThrows
    void givenUserCreationRequest_whenRegister_thenReturnUserResponse() {

        String jsonRequest = objectMapper.writeValueAsString(UserTemplates.getUserCreationRequest());

        when(userService.register(any())).thenReturn(userResponse);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_REQUEST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(userResponse)));

    }
}