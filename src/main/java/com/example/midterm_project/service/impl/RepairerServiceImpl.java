package com.example.midterm_project.service.impl;

import com.example.midterm_project.dto.repairer.RepairerResponse;
import com.example.midterm_project.repositories.RepairerRepository;
import com.example.midterm_project.service.interfaces.RepairerService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
@AllArgsConstructor
public class RepairerServiceImpl implements RepairerService {
    final private RepairerRepository repairerRepository;

    }
