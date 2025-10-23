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

@Entity // Tells JPA this is a database table
@Data   // From Lombok: creates getters, setters, toString, etc.
@NoArgsConstructor // From Lombok: creates a default no-argument constructor
public class Student {
    @Id // Marks this as the Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments the ID
    private Long id;
    
    private String name;
    private String email;
    private String department;

    // Establishes the relationship: One Student can have Many Submissions
    @OneToMany(mappedBy = "student")
    @ToString.Exclude
    @JsonManagedReference("student-submissions")
    private List<Submission> submissions;
}
