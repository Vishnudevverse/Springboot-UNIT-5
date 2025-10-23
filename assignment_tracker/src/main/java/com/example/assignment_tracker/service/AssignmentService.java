package com.example.assignment_tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment_tracker.model.Assignment;
import com.example.assignment_tracker.model.Faculty;
import com.example.assignment_tracker.repository.AssignmentRepository;
import com.example.assignment_tracker.repository.FacultyRepository;

import jakarta.persistence.EntityNotFoundException;

@Service // Tells Spring this is a Service bean (where business logic lives)
public class AssignmentService {

    @Autowired // This is "Dependency Injection": Spring automatically provides the repository
    private AssignmentRepository assignmentRepository;

    @Autowired
    private FacultyRepository facultyRepository; // Need this to link assignments to faculty

    /**
     * Creates a new assignment.
     * @param assignment The assignment object to save.
     * @param facultyId The ID of the faculty member creating the assignment.
     * @return The saved assignment.
     */
    public Assignment createAssignment(Assignment assignment, Long facultyId) {
        // Find the faculty member from the database
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new EntityNotFoundException("Faculty not found with id: " + facultyId));
        
        // Associate the faculty member with this assignment
        assignment.setFaculty(faculty);
        
        // Save the new assignment to the database
        return assignmentRepository.save(assignment);
    }

    /**
     * Retrieves a single assignment by its ID.
     * @param id The ID of the assignment.
     * @return The found assignment.
     * @throws EntityNotFoundException if no assignment is found.
     */
    public Assignment getAssignmentById(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found with id: " + id));
    }

    /**
     * Retrieves all assignments.
     * @return A list of all assignments.
     */
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    /**
     * Retrieves all assignments created by a specific faculty member.
     * @param facultyId The ID of the faculty.
     * @return A list of assignments.
     */
    public List<Assignment> getAssignmentsByFaculty(Long facultyId) {
        // We use the custom method we defined in the AssignmentRepository
        return assignmentRepository.findByFacultyId(facultyId);
    }

    /**
     * Updates an existing assignment.
     * @param id The ID of the assignment to update.
     * @param assignmentDetails The new details for the assignment.
     * @return The updated assignment.
     */
    public Assignment updateAssignment(Long id, Assignment assignmentDetails) {
        // First, find the existing assignment
        Assignment existingAssignment = getAssignmentById(id);
        
        // Update its fields
        existingAssignment.setTitle(assignmentDetails.getTitle());
        existingAssignment.setDescription(assignmentDetails.getDescription());
        existingAssignment.setDueDate(assignmentDetails.getDueDate());
        
        // Save the updated assignment back to the database
        return assignmentRepository.save(existingAssignment);
    }

    /**
     * Deletes an assignment.
     * @param id The ID of the assignment to delete.
     */
    public void deleteAssignment(Long id) {
        // Check if it exists first (this will throw EntityNotFoundException if it doesn't)
        Assignment assignment = getAssignmentById(id);
        
        // Delete it
        assignmentRepository.delete(assignment);
    }
}

