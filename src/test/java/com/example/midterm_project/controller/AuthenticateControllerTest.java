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
    private ObjectMapper objectMapper; // For JSON serialization/deserialization

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        // Arrange
        UserAuthRequest request = new UserAuthRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setRole("USER");

        // Mock the service call to do nothing (void method)
        doNothing().when(authenticateService).register(any(UserAuthRequest.class));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()) // Expect 200 OK since no response body is returned
                .andExpect(content().string("")); // No content expected
    }

    @Test
    void shouldLoginUserSuccessfully() throws Exception {
        // Arrange
        UserAuthRequest request = new UserAuthRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setRole("USER");

        UserAuthResponse response = new UserAuthResponse();
        response.setToken("mock-jwt-token"); // Assuming UserAuthResponse has a token field

        // Mock the service call to return a response
        when(authenticateService.login(any(UserAuthRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()) // Expect 200 OK
                .andExpect(content().json(objectMapper.writeValueAsString(response))); // Expect JSON response
    }

    @Test
    void shouldReturnBadRequestForInvalidRegisterRequest() throws Exception {
        // Arrange: Empty request to simulate invalid input
        UserAuthRequest request = new UserAuthRequest(); // No email, password, or role

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()); // Expect 400 if validation is present
    }

    @Test
    void shouldReturnBadRequestForInvalidLoginRequest() throws Exception {
        // Arrange: Empty request to simulate invalid input
        UserAuthRequest request = new UserAuthRequest(); // No email, password, or role

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()); // Expect 400 if validation is present
    }
}