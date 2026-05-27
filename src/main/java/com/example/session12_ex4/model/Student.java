package com.example.session12_ex4.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Long id;
    private String studentCode;
    private String fullName;
    private String major;
    private Double gpa;
}
