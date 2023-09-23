package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.Enum.Genre;
import com.example.librarymanagementsystem.dto.requestDTO.BookRequest;
import com.example.librarymanagementsystem.dto.responseDTO.BookResponse;
import com.example.librarymanagementsystem.exceptions.AuthorNotFoundException;
import com.example.librarymanagementsystem.exceptions.BookNotFoundException;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
   @Autowired
    BookService bookService;
    @PostMapping("/add")
    public ResponseEntity addBook(@RequestBody BookRequest bookRequest){
        try {
            BookResponse response = bookService.addBook(bookRequest);
            return new ResponseEntity(response, HttpStatus.CREATED);
        }
        catch (AuthorNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get")
    public ResponseEntity getBook(@RequestParam("id") int id){
        try {
            BookResponse response = bookService.getBook(id);
            return new ResponseEntity(response,HttpStatus.FOUND);
        }
        catch (BookNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete-book")
    public ResponseEntity deleteBook(@RequestParam("id") int id){
        try {
            String response = bookService.deleteBook(id);
            return new ResponseEntity(response,HttpStatus.ACCEPTED);
        }
        catch (BookNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-by-genre/genre/{genre}")
    public List<BookResponse> getByGenre(@PathVariable("genre") Genre genre){
        return bookService.getByGenre(genre);
    }

    @GetMapping("/get-by-genre-cost")
    public List<BookResponse> getByGenreAndCostGreaterThan(@RequestParam("genre") Genre genre ,@RequestParam("cost") double cost){

        return bookService.getByGenreAndCostGreaterThan(genre,cost);
    }
    @GetMapping("/get-book-by-pages")
    public List<BookResponse> getBookByNumberOfPagesRange(@RequestParam("a")int a, @RequestParam("b") int b){
        return bookService.getBookByNumberOfPagesRange(a,b);
    }

    @GetMapping("/get-author-by-genre/genre/{genre}")
    public List<String> getAuthorByGenre(@PathVariable("genre") Genre genre){
        return bookService.getAuthorByGenre(genre);
    }
}
