package com.example.midterm_project.service.interfaces;
import com.example.midterm_project.dto.cars.CarsRequest;
import com.example.midterm_project.dto.cars.CarsResponse;

import java.util.List;

public interface CarsService {

    void add(CarsRequest carsRequest);

    void delete(Long id);

    void update(Long id,CarsRequest carsRequest);

    CarsResponse find(Long id);

    List<CarsResponse> findAll();

    void register(CarsRequest carsRequest, Long repairerId);

    void drop(Long repairerId, Long id);
}
