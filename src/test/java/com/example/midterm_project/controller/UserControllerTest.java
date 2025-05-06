package com.example.midterm_project.controller;

import com.example.midterm_project.dto.user.UserRequest;
import com.example.midterm_project.dto.user.UserResponse;
import com.example.midterm_project.service.interfaces.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetUserById() throws Exception {
        Long id = 1L;
        String token = "Bearer mock-token";
        UserResponse response = new UserResponse();
        response.setId(id);
        response.setName("John Doe");
        response.setCourse("Computer Science");
        response.setAge(25);

        when(userService.getById(id, token)).thenReturn(response);

        mockMvc.perform(get("/user/{id}", id)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void shouldDeleteUserSuccessfully() throws Exception {
        Long id = 1L;

        doNothing().when(userService).deleteById(id);

        mockMvc.perform(delete("/user/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void shouldUpdateUserSuccessfully() throws Exception {
        Long id = 1L;
        UserRequest request = new UserRequest();
        request.setEmail("john.updated@example.com");
        request.setPassword("newpassword123");

        doNothing().when(userService).updateById(eq(id), any(UserRequest.class));

        mockMvc.perform(put("/user/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        UserResponse user1 = new UserResponse();
        user1.setId(1L);
        user1.setName("John Doe");
        user1.setCourse("Computer Science");
        user1.setAge(25);
        UserResponse user2 = new UserResponse();
        user2.setId(2L);
        user2.setName("Jane Smith");
        user2.setCourse("Mathematics");
        user2.setAge(30);
        List<UserResponse> users = Arrays.asList(user1, user2);

        when(userService.getAll()).thenReturn(users);

        mockMvc.perform(get("/user/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(users)));
    }
}