package com.example.assignment_tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment_tracker.model.Faculty;
import com.example.assignment_tracker.repository.FacultyRepository;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

    @Autowired
    private FacultyRepository facultyRepository; // Using repository directly for simplicity

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty savedFaculty = facultyRepository.save(faculty);
        return new ResponseEntity<>(savedFaculty, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }
}
