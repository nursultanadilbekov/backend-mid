package com.example.midterm_project.controller;

import com.example.midterm_project.dto.repairer.RepairerRequest;
import com.example.midterm_project.dto.repairer.RepairerResponse;
import com.example.midterm_project.service.interfaces.RepairerService;
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

@WebMvcTest(RepairerController.class)
public class RepairerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepairerService repairerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRegisterRepairerSuccessfully() throws Exception {
        RepairerRequest request = new RepairerRequest();
        request.setName("Alice");
        request.setAge(30);
        request.setCost_of_work(50);
        request.setExperience("5 years");
        String token = "Bearer mock-token";

        doNothing().when(repairerService).register(any(RepairerRequest.class), eq(token));

        mockMvc.perform(post("/repairer/register")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void shouldDeleteRepairerSuccessfully() throws Exception {
        Long id = 1L;

        doNothing().when(repairerService).delete(id);

        mockMvc.perform(delete("/repairer/delete")
                        .param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void shouldUpdateRepairerSuccessfully() throws Exception {
        Long id = 1L;
        RepairerRequest request = new RepairerRequest();
        request.setName("Bob Updated");
        request.setAge(35);
        request.setCost_of_work(60);
        request.setExperience("7 years");

        doNothing().when(repairerService).update(eq(id), any(RepairerRequest.class));

        mockMvc.perform(post("/repairer/update")
                        .param("id", id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void shouldFindRepairerById() throws Exception {
        Long id = 1L;
        String token = "Bearer mock-token";
        RepairerResponse response = new RepairerResponse();
        response.setId(id);
        response.setName("Alice");
        response.setAge(30);
        response.setCost_of_work(50);
        response.setExperience("5 years");

        when(repairerService.find(id, token)).thenReturn(response);

        mockMvc.perform(get("/repairer/find/{id}", id)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void shouldGetAllRepairers() throws Exception {
        RepairerResponse repairer1 = new RepairerResponse();
        repairer1.setId(1L);
        repairer1.setName("Alice");
        repairer1.setAge(30);
        repairer1.setCost_of_work(50);
        repairer1.setExperience("5 years");
        RepairerResponse repairer2 = new RepairerResponse();
        repairer2.setId(2L);
        repairer2.setName("Bob");
        repairer2.setAge(35);
        repairer2.setCost_of_work(60);
        repairer2.setExperience("7 years");
        List<RepairerResponse> repairers = Arrays.asList(repairer1, repairer2);

        when(repairerService.getAll()).thenReturn(repairers);

        mockMvc.perform(get("/repairer/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(repairers)));
    }
}