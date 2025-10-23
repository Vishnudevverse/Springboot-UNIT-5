package com.example.assignment_tracker.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment_tracker.model.Assignment;
import com.example.assignment_tracker.service.AssignmentService;

/**
 * REST Controller for managing Assignments.
 * Exposes endpoints at /api/assignments
 */
@RestController
@RequestMapping("/api/assignments") // All endpoints in this class start with /api/assignments
public class AssignmentController {

    @Autowired // Spring injects the AssignmentService instance
    private AssignmentService assignmentService;

    /**
     * GET /api/assignments
     * Retrieves all assignments.
     * @return A list of all assignments.
     */
    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    /**
     * GET /api/assignments/{id}
     * Retrieves a specific assignment by its ID.
     * @param id The ID of the assignment.
     * @return A ResponseEntity containing the assignment or 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable Long id) {
        // In a real app, you'd handle the EntityNotFoundException with an @ControllerAdvice
        // For simplicity, we just return the object. The service will throw an exception if not found.
        return ResponseEntity.ok(assignmentService.getAssignmentById(id));
    }

    /**
     * POST /api/assignments
     * Creates a new assignment.
     * The request body should be a JSON object like:
     * {
     * "title": "New Assignment",
     * "description": "Details...",
     * "dueDate": "2025-12-31",
     * "facultyId": 1
     * }
     * @param payload A Map representing the JSON request body.
     * @return A ResponseEntity with the created assignment and 201 Created status.
     */
    @PostMapping
    public ResponseEntity<Assignment> createAssignment(@RequestBody Map<String, Object> payload) {
        // This is simplified. A DTO (Data Transfer Object) is the recommended approach.
        try {
            // Validate and extract fields
            String title = (String) payload.get("title");
            String description = (String) payload.get("description");
            String dueDateStr = (String) payload.get("dueDate");
            if (title == null || dueDateStr == null) {
                return ResponseEntity.badRequest().body(null);
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> faculty = (Map<String, Object>) payload.get("faculty");
            if (faculty == null || !(faculty.get("id") instanceof Number)) {
                return ResponseEntity.badRequest().body(null);
            }
            Long facultyId = ((Number) faculty.get("id")).longValue();

            Assignment assignment = new Assignment();
            assignment.setTitle(title);
            assignment.setDescription(description);
            assignment.setDueDate(LocalDate.parse(dueDateStr));

            Assignment createdAssignment = assignmentService.createAssignment(assignment, facultyId);
            return new ResponseEntity<>(createdAssignment, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * PUT /api/assignments/{id}
     * Updates an existing assignment.
     * @param id The ID of the assignment to update.
     * @param assignmentDetails The new details for the assignment (from the request body).
     * @return The updated assignment.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable Long id, @RequestBody Assignment assignmentDetails) {
        Assignment updatedAssignment = assignmentService.updateAssignment(id, assignmentDetails);
        return ResponseEntity.ok(updatedAssignment);
    }

    /**
     * DELETE /api/assignments/{id}
     * Deletes an assignment.
     * @param id The ID of the assignment to delete.
     * @return A ResponseEntity with 204 No Content status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build(); // 204 No Content is standard for a successful delete
    }

    /**
     * GET /api/assignments/faculty/{facultyId}
     * Gets all assignments for a specific faculty member.
     * @param facultyId The ID of the faculty.
     * @return A list of assignments.
     */
    @GetMapping("/faculty/{facultyId}")
    public List<Assignment> getAssignmentsByFaculty(@PathVariable Long facultyId) {
        return assignmentService.getAssignmentsByFaculty(facultyId);
    }
}
