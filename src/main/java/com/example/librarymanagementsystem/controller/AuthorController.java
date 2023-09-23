package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.responseDTO.AuthorResponse;
import com.example.librarymanagementsystem.dto.responseDTO.AuthorWithBooksResponse;
import com.example.librarymanagementsystem.dto.responseDTO.BookResponse;
import com.example.librarymanagementsystem.exceptions.AuthorNotFoundException;
import com.example.librarymanagementsystem.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;
    @PostMapping("/add")
    public ResponseEntity addAuthor(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("age") int age){
        AuthorResponse response = authorService.addAuthor(name,email,age);
        if(response!=null)
            return new ResponseEntity(response,HttpStatus.CREATED);

        return new  ResponseEntity("failed",HttpStatus.FORBIDDEN);
    }

    @GetMapping("/get")
    public ResponseEntity getAuthor(@RequestParam("id") int id){
        try {
            AuthorWithBooksResponse response = authorService.getAuthor(id);
            return new ResponseEntity(response, HttpStatus.FOUND);
        }
        catch(AuthorNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-email/id/{id}/email/{email}")
    public String updateEmail(@PathVariable("id") int id,@PathVariable("email") String email){
        try {
            String response = authorService.updateEmail(id, email);
            return response;
        }
        catch (AuthorNotFoundException e){
            return e.getMessage();
    }
    }
    @GetMapping("/books-by-author")
    public ResponseEntity booksByAuthor(@RequestParam("id") int id) {
        try {
            List<String> books = authorService.booksByAuthor(id);
            return new ResponseEntity(books, HttpStatus.FOUND);
        }
        catch (AuthorNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/author-with-n-books")
    public List<String> authorWithNNumberOfBooks(@RequestParam("n") int n){
         List<String> authors = authorService.authorWithNNumberOfBooks(n);
         return authors;
    }

}
