package com.example.midterm_project.controller;

import com.example.midterm_project.dto.cars.CarsRequest;
import com.example.midterm_project.dto.cars.CarsResponse;
import com.example.midterm_project.entities.Car;
import com.example.midterm_project.entities.Repairer;
import com.example.midterm_project.mapper.interfaces.CarsMapper;
import com.example.midterm_project.repositories.RepairerRepository;
import com.example.midterm_project.service.interfaces.CarsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarsController.class)
public class CarsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarsService carsService;

    @MockBean
    private CarsMapper carsMapper;

    @MockBean
    private RepairerRepository repairerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAddCarSuccessfully() throws Exception {
        CarsRequest request = new CarsRequest();
        request.setModel("Camry");
        request.setAge(5);
        request.setNumber("ABC123");
        String token = "Bearer mock-token";

        doNothing().when(carsService).add(any(CarsRequest.class), eq(token));

        mockMvc.perform(post("/car/add")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void shouldDeleteCarSuccessfully() throws Exception {
        Long id = 1L;
        doNothing().when(carsService).delete(id);

        mockMvc.perform(delete("/car/delete")
                        .param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void shouldUpdateCarSuccessfully() throws Exception {
        Long id = 1L;
        CarsRequest request = new CarsRequest();
        request.setModel("Civic");
        request.setAge(3);
        request.setNumber("XYZ789");

        doNothing().when(carsService).update(eq(id), any(CarsRequest.class));

        mockMvc.perform(get("/car/update")
                        .param("id", id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void shouldFindCarById() throws Exception {
        Long id = 1L;
        CarsResponse response = new CarsResponse();
        response.setId(id);
        response.setModel("Camry");
        response.setAge(5);
        response.setNumber("ABC123");

        when(carsService.find(id)).thenReturn(response);

        mockMvc.perform(get("/car/find/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void shouldGetAllCars() throws Exception {
        CarsResponse car1 = new CarsResponse();
        car1.setId(1L);
        car1.setModel("Camry");
        car1.setAge(5);
        car1.setNumber("ABC123");
        CarsResponse car2 = new CarsResponse();
        car2.setId(2L);
        car2.setModel("Civic");
        car2.setAge(3);
        car2.setNumber("XYZ789");
        List<CarsResponse> cars = Arrays.asList(car1, car2);

        when(carsService.findAll()).thenReturn(cars);

        mockMvc.perform(get("/car/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(cars)));
    }

    @Test
    void shouldRegisterCarSuccessfully() throws Exception {
        Long repairerId = 1L;
        CarsRequest request = new CarsRequest();
        request.setModel("X5");
        request.setAge(2);
        request.setNumber("DEF456");

        doNothing().when(carsService).register(any(CarsRequest.class), eq(repairerId));

        mockMvc.perform(post("/car/register/{repairerId}", repairerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void shouldDropCarSuccessfully() throws Exception {
        Long repairerId = 1L;
        Long id = 1L;

        doNothing().when(carsService).drop(eq(repairerId), eq(id));

        mockMvc.perform(post("/car/{repairerId}/{id}", repairerId, id))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void shouldGetRepairerCars() throws Exception {
        Long repairerId = 1L;
        Repairer repairer = new Repairer();
        repairer.setId(repairerId);
        Car car = new Car();
        car.setId(1L);
        car.setModel("Camry");
        car.setAge(5);
        car.setNumber("ABC123");
        repairer.setRepairercars(Arrays.asList(car));

        CarsResponse response = new CarsResponse();
        response.setId(1L);
        response.setModel("Camry");
        response.setAge(5);
        response.setNumber("ABC123");
        List<CarsResponse> responses = Arrays.asList(response);

        when(repairerRepository.findById(repairerId)).thenReturn(Optional.of(repairer));
        when(carsMapper.toDtos(anyList())).thenReturn(responses);

        mockMvc.perform(get("/car/repairerCars/{repairerId}", repairerId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responses)));
    }
}