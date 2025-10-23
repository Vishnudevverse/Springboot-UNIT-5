package com.example.assignment_tracker.controller;

import com.example.assignment_tracker.model.Assignment;
import com.example.assignment_tracker.service.AssignmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AssignmentController.class)
class AssignmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssignmentService assignmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Assignment testAssignment;

    @BeforeEach
    void setUp() {
        testAssignment = new Assignment();
        testAssignment.setId(1L);
        testAssignment.setTitle("Test API Assignment");
        testAssignment.setDescription("Test desc");
        testAssignment.setDueDate(LocalDate.parse("2025-01-01"));
    }

    @Test
    void whenGetAssignmentById_withValidId_shouldReturnAssignment() throws Exception {
        // 1. Arrange
        when(assignmentService.getAssignmentById(1L)).thenReturn(testAssignment);

        // 2. Act & 3. Assert
        mockMvc.perform(get("/api/assignments/1"))
                .andExpect(status().isOk()) // Check for HTTP 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Check that the returned JSON has id=1
                .andExpect(jsonPath("$.title").value("Test API Assignment")); // And the correct title
    }

    @Test
    void whenCreateAssignment_shouldReturnCreatedAssignment() throws Exception {
        // 1. Arrange
        // When the service is called with any Assignment and facultyId 1L, return our testAssignment
        when(assignmentService.createAssignment(any(Assignment.class), eq(1L))).thenReturn(testAssignment);

        // This is the JSON payload we will send in the POST request
        String requestBody = """
        {
            "title": "Test API Assignment",
            "description": "Test desc",
            "dueDate": "2026-11-01",
            "faculty": {
                "id": 1
            }
        }
        """;

        // 2. Act & 3. Assert
        mockMvc.perform(post("/api/assignments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated()) // Check for HTTP 201 Created
                .andExpect(jsonPath("$.id").value(1));
    }
}
