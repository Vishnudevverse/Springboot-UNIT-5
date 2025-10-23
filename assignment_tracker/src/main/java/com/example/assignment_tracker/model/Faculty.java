package com.example.assignment_tracker.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String email;
    private String department;

    // One Faculty can create Many Assignments
    @OneToMany(mappedBy = "faculty")
    @ToString.Exclude
    @JsonManagedReference("faculty-assignments")
    private List<Assignment> assignments;

    // One Faculty can manage Many Projects
    @OneToMany(mappedBy = "faculty")
    @ToString.Exclude
    @JsonManagedReference("faculty-projects")
    private List<Project> projects;
}
