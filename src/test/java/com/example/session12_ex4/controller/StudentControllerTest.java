package com.example.session12_ex4.controller;

import com.example.session12_ex4.model.Student;
import com.example.session12_ex4.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllStudents() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))))
                .andExpect(jsonPath("$[0].studentCode", is("SV001")));
    }

    @Test
    void testGetStudentById_Success() throws Exception {
        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.studentCode", is("SV001")))
                .andExpect(jsonPath("$.fullName", is("Nguyen Van A")));
    }

    @Test
    void testGetStudentById_NotFound() throws Exception {
        mockMvc.perform(get("/api/students/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", containsString("Student not found with ID: 999")));
    }

    @Test
    void testCreateStudent() throws Exception {
        String studentJson = """
                {
                    "studentCode": "SV004",
                    "fullName": "Tran Van D",
                    "major": "Cyber Security",
                    "gpa": 3.5
                }
                """;

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.studentCode", is("SV004")))
                .andExpect(jsonPath("$.fullName", is("Tran Van D")));
    }

    @Test
    void testUpdateStudent_Success() throws Exception {
        String updateJson = """
                {
                    "studentCode": "SV001-Updated",
                    "fullName": "Nguyen Van A Updated",
                    "major": "Software Engineering",
                    "gpa": 3.9
                }
                """;

        mockMvc.perform(put("/api/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.studentCode", is("SV001-Updated")))
                .andExpect(jsonPath("$.fullName", is("Nguyen Van A Updated")));
    }

    @Test
    void testUpdateStudent_NotFound() throws Exception {
        String updateJson = """
                {
                    "studentCode": "SV001-Updated",
                    "fullName": "Nguyen Van A Updated",
                    "major": "Software Engineering",
                    "gpa": 3.9
                }
                """;

        mockMvc.perform(put("/api/students/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", containsString("Student not found with ID: 999")));
    }

    @Test
    void testDeleteStudent_Success() throws Exception {
        mockMvc.perform(delete("/api/students/2"))
                .andExpect(status().isNoContent());

        // Verify it was deleted
        mockMvc.perform(get("/api/students/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteStudent_NotFound() throws Exception {
        mockMvc.perform(delete("/api/students/999"))
                .andExpect(status().isNotFound());
    }
}
