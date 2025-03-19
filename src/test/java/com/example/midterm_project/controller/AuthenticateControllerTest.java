package com.example.midterm_project.controller;

import com.example.midterm_project.dto.user.UserAuthRequest;
import com.example.midterm_project.dto.user.UserAuthResponse;
import com.example.midterm_project.service.interfaces.AuthenticateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticateController.class)
public class AuthenticateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticateService authenticateService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        UserAuthRequest request = new UserAuthRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setRole("USER");

        doNothing().when(authenticateService).register(any(UserAuthRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void shouldLoginUserSuccessfully() throws Exception {
        UserAuthRequest request = new UserAuthRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setRole("USER");

        UserAuthResponse response = new UserAuthResponse();
        response.setToken("mock-jwt-token");

        when(authenticateService.login(any(UserAuthRequest.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void shouldReturnBadRequestForInvalidRegisterRequest() throws Exception {
        UserAuthRequest request = new UserAuthRequest();

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestForInvalidLoginRequest() throws Exception {
        UserAuthRequest request = new UserAuthRequest();

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}