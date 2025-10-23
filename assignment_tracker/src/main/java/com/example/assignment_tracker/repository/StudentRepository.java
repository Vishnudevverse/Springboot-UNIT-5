package com.example.assignment_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.assignment_tracker.model.Student;

@Repository // Tells Spring this is a Repository bean
public interface StudentRepository extends JpaRepository<Student, Long> {
    // By extending JpaRepository<Student, Long>, we get all standard CRUD operations
    // (Create, Read, Update, Delete) for the Student entity,
    // which has a Primary Key of type Long.
    //
    // Spring Data JPA will automatically implement methods like:
    // - save(Student s)
    // - findById(Long id)
    // - findAll()
    // - deleteById(Long id)
    // ...and many more!
}
