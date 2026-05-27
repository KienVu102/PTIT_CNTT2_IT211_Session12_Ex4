package com.example.session12_ex4.service;

import com.example.session12_ex4.exception.StudentNotFoundException;
import com.example.session12_ex4.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StudentService {
    private final List<Student> students = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public StudentService() {
        // Pre-populate with some initial student records
        students.add(new Student(idGenerator.getAndIncrement(), "SV001", "Nguyen Van A", "Information Technology", 3.2));
        students.add(new Student(idGenerator.getAndIncrement(), "SV002", "Tran Thi B", "Computer Science", 3.6));
        students.add(new Student(idGenerator.getAndIncrement(), "SV003", "Le Van C", "Data Science", 2.8));
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Student getStudentById(Long id) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Student createStudent(Student student) {
        student.setId(idGenerator.getAndIncrement());
        students.add(student);
        return student;
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        Student existing = getStudentById(id);
        existing.setStudentCode(updatedStudent.getStudentCode());
        existing.setFullName(updatedStudent.getFullName());
        existing.setMajor(updatedStudent.getMajor());
        existing.setGpa(updatedStudent.getGpa());
        return existing;
    }

    public void deleteStudent(Long id) {
        Student existing = getStudentById(id);
        students.remove(existing);
    }
}
