package com.example.session12_ex4.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) {
        super("Student not found with ID: " + id);
    }
}
