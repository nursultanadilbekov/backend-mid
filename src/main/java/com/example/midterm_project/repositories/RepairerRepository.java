package com.example.midterm_project.repositories;

import com.example.midterm_project.entities.Repairer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairerRepository extends JpaRepository<Repairer,Long> {
}
