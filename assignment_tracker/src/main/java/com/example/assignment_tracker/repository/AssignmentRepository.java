package com.example.assignment_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.assignment_tracker.model.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    
    // We can add custom "finder" methods just by defining their signature.
    // Spring Data JPA will write the query for us based on the method name.
    
    // Finds all assignments given by a specific faculty ID
    List<Assignment> findByFacultyId(Long facultyId);
}
