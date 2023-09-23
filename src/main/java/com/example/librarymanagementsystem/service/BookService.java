package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.requestDTO.BookRequest;
import com.example.librarymanagementsystem.dto.responseDTO.BookResponse;
import com.example.librarymanagementsystem.exceptions.AuthorNotFoundException;
import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.repository.AuthorRepository;
import com.example.librarymanagementsystem.transformer.BookTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
     @Autowired
    AuthorRepository authorRepository;
    public BookResponse addBook(BookRequest bookRequest) {
       Optional<Author> authorOptional= authorRepository.findById(bookRequest.getAuthorId());
       if(authorOptional.isEmpty()){
           throw new AuthorNotFoundException("Invalid Author I'd");
       }
       Book book = BookTransformer.BookRequestToBook(bookRequest); //dto -->> model
       Author author = authorOptional.get();
       book.setAuthor(author);
       author.getBooks().add(book);

       authorRepository.save(author);
       return BookTransformer.BookToBookResponse(book);
    }
}
