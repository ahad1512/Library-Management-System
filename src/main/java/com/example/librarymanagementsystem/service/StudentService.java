package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.requestDTO.StudentRequest;
import com.example.librarymanagementsystem.dto.responseDTO.StudentResponse;

import java.util.List;

public interface StudentService {
    StudentResponse addStudent(StudentRequest studentRequest);

    StudentResponse getStudent(int regNo);

    String deleteStudent(int regNo);

    String updateAge(int reqNo, int age);

    List<StudentResponse> getAllStudent();

    List<String> getAllMales();
}
