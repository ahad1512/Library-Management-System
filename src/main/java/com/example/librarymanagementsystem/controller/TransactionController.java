package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.responseDTO.IssueBookResponse;
import com.example.librarymanagementsystem.exceptions.BookNotAvailableException;
import com.example.librarymanagementsystem.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transaction")
public class TransactionController {
    @Autowired
    TransactionServiceImpl transactionService;

   @PostMapping("/issue-book/book-id/{book-id}/student-id/{student-id}")
    public ResponseEntity issueBook(@PathVariable("book-id") int bookId,@PathVariable("student-id") int studentId){
       try {
           IssueBookResponse issueBookResponse = transactionService.issueBook(bookId,studentId);
           return new ResponseEntity(issueBookResponse, HttpStatus.ACCEPTED);
       }
       catch (Exception e){
           return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
       }
    }
}
