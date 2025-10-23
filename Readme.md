# Assignment Tracker

This is a simple Spring Boot application for tracking assignments and submissions. It provides a REST API for managing faculty, students, assignments, and submissions, as well as a simple web interface for interacting with the application.

## Prerequisites

*   Java 17 or higher
*   Maven 3.6 or higher

## How to Run

1.  Clone the repository.
2.  Open a terminal and navigate to the `assignment_tracker` directory.
3.  Run the application using the following command:

    ```bash
    ./mvnw spring-boot:run
    ```

4.  The application will be available at `http://localhost:8080`.

## Using the Web Interface

The web interface provides a simple way to interact with the application. You can use it to:

*   Create faculty, students, and assignments.
*   Submit assignments.
*   View lists of faculty, students, assignments, and submissions.

## API Documentation

The application provides a REST API for managing faculty, students, assignments, and submissions.

### Faculty

*   **GET /api/faculty** - Get all faculty.
*   **POST /api/faculty** - Create a new faculty.

    *Example Request Body:*

    ```json
    {
        "name": "Dr. Smith",
        "email": "smith@example.com",
        "department": "Computer Science"
    }
    ```

### Students

*   **GET /api/students** - Get all students.
*   **POST /api/students** - Create a new student.

    *Example Request Body:*

    ```json
    {
        "name": "John Doe",
        "email": "john.doe@example.com",
        "department": "Computer Science"
    }
    ```

### Assignments

*   **GET /api/assignments** - Get all assignments.
*   **POST /api/assignments** - Create a new assignment.

    *Example Request Body:*

    ```json
    {
        "title": "Final Project",
        "description": "Final project for the course.",
        "dueDate": "2025-12-31",
        "faculty": {
            "id": 1
        }
    }
    ```

### Submissions

*   **GET /api/submissions** - Get all submissions.
*   **POST /api/submissions** - Create a new submission.

    *Example Request Body:*

    ```json
    {
        "student": {
            "id": 1
        },
        "assignment": {
            "id": 1
        },
        "fileUrl": "https://example.com/submission"
    }
    ```