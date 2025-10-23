package com.example.assignment_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.assignment_tracker.model.Faculty;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    // All CRUD methods for Faculty are now available.
}
