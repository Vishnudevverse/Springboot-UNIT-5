package com.example.assignment_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.assignment_tracker.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Finds all projects managed by a specific faculty ID
    List<Project> findByFacultyId(Long facultyId);
}
