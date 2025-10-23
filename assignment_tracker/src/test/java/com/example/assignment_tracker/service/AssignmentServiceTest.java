package com.example.assignment_tracker.service;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.assignment_tracker.model.Assignment;
import com.example.assignment_tracker.model.Faculty;
import com.example.assignment_tracker.repository.AssignmentRepository;
import com.example.assignment_tracker.repository.FacultyRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class) // This initializes Mockito
class AssignmentServiceTest {

    @Mock // Creates a "fake" version of this repository
    private AssignmentRepository assignmentRepository;

    @Mock // Creates a "fake" version of this repository
    private FacultyRepository facultyRepository;

    @InjectMocks // Creates an instance of AssignmentService and injects the mocks into it
    private AssignmentService assignmentService;

    // We'll create some re-usable test data
    private Faculty testFaculty;
    private Assignment testAssignment;

    @BeforeEach // This runs before each @Test method
    void setUp() {
        testFaculty = new Faculty();
        testFaculty.setId(1L);
        testFaculty.setName("Dr. Smith");

        testAssignment = new Assignment();
        testAssignment.setId(10L);
        testAssignment.setTitle("Test Assignment");
        testAssignment.setDueDate(LocalDate.parse("2025-10-31"));
        testAssignment.setFaculty(testFaculty);
    }

    @Test
    void whenCreateAssignment_withValidFaculty_shouldReturnSavedAssignment() {
        // 1. Arrange (Set up the "when... then..." logic for our mocks)
        
        // When facultyRepository.findById(1L) is called, then return our testFaculty
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(testFaculty));
        
        // When assignmentRepository.save(...) is called with any Assignment, 
        // then return that same assignment (for this test)
        when(assignmentRepository.save(any(Assignment.class))).thenReturn(testAssignment);

        // 2. Act (Call the method we are testing)
        Assignment createdAssignment = assignmentService.createAssignment(testAssignment, 1L);

        // 3. Assert (Check if the result is what we expected)
        assertNotNull(createdAssignment);
        assertEquals("Test Assignment", createdAssignment.getTitle());
        assertEquals(1L, createdAssignment.getFaculty().getId());
    }

    @Test
    void whenCreateAssignment_withInvalidFaculty_shouldThrowException() {
        // 1. Arrange
        // When facultyRepository.findById(99L) is called, then return an empty Optional
        when(facultyRepository.findById(99L)).thenReturn(Optional.empty());

        // 2. Act & 3. Assert
        // Check that an EntityNotFoundException is thrown when we call the method
        assertThrows(EntityNotFoundException.class, () -> {
            assignmentService.createAssignment(testAssignment, 99L);
        });
    }

    @Test
    void whenGetAssignmentById_withValidId_shouldReturnAssignment() {
        // 1. Arrange
        when(assignmentRepository.findById(10L)).thenReturn(Optional.of(testAssignment));

        // 2. Act
        Assignment found = assignmentService.getAssignmentById(10L);

        // 3. Assert
        assertNotNull(found);
        assertEquals(10L, found.getId());
    }

    @Test
    void whenGetAssignmentById_withInvalidId_shouldThrowException() {
        // 1. Arrange
        when(assignmentRepository.findById(99L)).thenReturn(Optional.empty());

        // 2. Act & 3. Assert
        assertThrows(EntityNotFoundException.class, () -> {
            assignmentService.getAssignmentById(99L);
        });
    }
}
