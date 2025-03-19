package com.example.midterm_project.mapper.interfaces;

import com.example.midterm_project.dto.repairer.RepairerResponse;
import com.example.midterm_project.entities.Repairer;

import java.util.List;

public interface RepairerMapper {
    RepairerResponse toDto(Repairer repairer);

    List<RepairerResponse> toDtos(List<Repairer>all);
}
