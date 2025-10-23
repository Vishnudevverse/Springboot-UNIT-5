package com.example.assignment_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.assignment_tracker.model.Assignment;
import com.example.assignment_tracker.model.Student;
import com.example.assignment_tracker.model.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    
    // Finds all submissions for a specific assignment
    List<Submission> findByAssignmentId(Long assignmentId);

    // Finds all submissions from a specific student
    List<Submission> findByStudentId(Long studentId);

    // Checks if a submission exists for a student and assignment combination
    boolean existsByStudentAndAssignment(Student student, Assignment assignment);
}
