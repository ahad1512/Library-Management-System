package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.Enum.CardStatus;
import com.example.librarymanagementsystem.dto.requestDTO.StudentRequest;
import com.example.librarymanagementsystem.dto.responseDTO.StudentResponse;
import com.example.librarymanagementsystem.exceptions.StudentNotFoundException;
import com.example.librarymanagementsystem.model.LibraryCard;
import com.example.librarymanagementsystem.service.StudentService;
import com.example.librarymanagementsystem.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    public StudentResponse addStudent(@RequestBody StudentRequest studentRequest){
        StudentResponse studentResponse = studentService.addStudent(studentRequest);
        return studentResponse;
    }

    @GetMapping("/get")
    public ResponseEntity getStudent(@RequestParam("id") int regNo){
        try {
            StudentResponse studentResponse = studentService.getStudent(regNo);
            return new ResponseEntity(studentResponse, HttpStatus.FOUND);
        }
        catch (StudentNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-student-by-id")
    public ResponseEntity deleteStudent(@RequestParam("id") int regNo){
        try {
            String response = studentService.deleteStudent(regNo);
            return new ResponseEntity<>(response, HttpStatus.IM_USED);
        }
        catch (StudentNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @PutMapping("/update-age")
    public ResponseEntity updateAge(@RequestParam("id") int reqNo,@RequestParam("age") int age){
        try {
            String response = studentService.updateAge(reqNo, age);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
        catch (StudentNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/get-all-student")
    public List<StudentResponse> getAllStudent(){
        List<StudentResponse> studentResponseList = studentService.getAllStudent();
        return studentResponseList;
    }

    @GetMapping("/get-males")
    public List<String> getAllMales(){
        List<String> males = studentService.getAllMales();
        return males;
    }
}
