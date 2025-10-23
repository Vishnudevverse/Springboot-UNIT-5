package com.example.assignment_tracker.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String description;
    private LocalDate dueDate; // Uses Java's modern date type

    // Establishes the relationship: Many Assignments can belong to One Faculty
    @ManyToOne(fetch = FetchType.EAGER) // Changed to EAGER loading
    @JoinColumn(name = "faculty_id") // This creates the 'faculty_id' foreign key column
    @ToString.Exclude
    @JsonBackReference("faculty-assignments")
    private Faculty faculty;

    // One Assignment can have Many Submissions
    @OneToMany(mappedBy = "assignment")
    @ToString.Exclude
    @JsonManagedReference("assignment-submissions")
    private List<Submission> submissions;
}
