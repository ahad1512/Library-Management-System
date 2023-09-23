package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.Enum.CardStatus;
import com.example.librarymanagementsystem.Enum.Gender;
import com.example.librarymanagementsystem.dto.requestDTO.StudentRequest;
import com.example.librarymanagementsystem.dto.responseDTO.LibraryCardResponse;
import com.example.librarymanagementsystem.dto.responseDTO.StudentResponse;
import com.example.librarymanagementsystem.exceptions.StudentNotFoundException;
import com.example.librarymanagementsystem.model.LibraryCard;
import com.example.librarymanagementsystem.repository.StudentRepository;
import com.example.librarymanagementsystem.model.Student;
import com.example.librarymanagementsystem.transformer.LibraryCardTransformer;
import com.example.librarymanagementsystem.transformer.StudentTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    public StudentResponse addStudent(StudentRequest studentRequest) {
        Student student = StudentTransformer.StudentRequestToStudent(studentRequest); // convert request dto -->> model
        LibraryCard libraryCard = LibraryCardTransformer.prepareLibraryCard(); // prepare a library card
        libraryCard.setStudent(student); //give student a library card
        student.setLibraryCard(libraryCard); // set library card for student
        Student savedStudent = studentRepository.save(student); //save both

        //convert model -->> response dto
        StudentResponse studentResponse = StudentTransformer.StudentToStudentResponse(savedStudent);
        return studentResponse;
    }

    public StudentResponse getStudent(int regNo) {
       Optional<Student> studentOptional= studentRepository.findById(regNo);
       if(studentOptional.isPresent()){
           //convert model -->> response dto
           StudentResponse studentResponse = StudentTransformer.StudentToStudentResponse(studentOptional.get());
          return studentResponse;
       }
       throw new StudentNotFoundException("Invalid Registration Number !!");
    }

    public String deleteStudent(int regNo) {
        Optional<Student> studentOptional = studentRepository.findById(regNo);
        if(studentOptional.isPresent()) {
            studentRepository.deleteById(regNo);
            return "Student deleted successfully";
        }
        throw new StudentNotFoundException("Invalid Registration Number !!");
    }

    public String updateAge(int reqNo, int age) {
        Optional<Student> studentOptional = studentRepository.findById(reqNo);
        if(studentOptional.isPresent()){
            studentOptional.get().setAge(age);
            Student savedStudent= studentRepository.save(studentOptional.get());
            return "The updated age of "+savedStudent.getName()+ " is "+savedStudent.getAge();
        }
        throw new StudentNotFoundException("Invalid Registration Number !!");
    }

    public List<StudentResponse> getAllStudent() {
           List<StudentResponse> studentResponseList = new ArrayList<>();
           List<Student> savedStudent = studentRepository.findAll();
            for (Student student: savedStudent){
                  StudentResponse studentResponse = StudentTransformer.StudentToStudentResponse(student);
                  studentResponseList.add(studentResponse);
            }
            return studentResponseList;
    }

    public List<String> getAllMales() {
        List<String> males = new ArrayList<>();
        List<Student> studentList = studentRepository.findAllByGender(Gender.MALE);
        for (Student student : studentList){
            males.add(student.getName());
        }
        return males;
    }
}
