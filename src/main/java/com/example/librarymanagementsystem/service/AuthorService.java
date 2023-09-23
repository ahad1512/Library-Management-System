package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.responseDTO.AuthorResponse;
import com.example.librarymanagementsystem.dto.responseDTO.AuthorWithBooksResponse;
import com.example.librarymanagementsystem.dto.responseDTO.BookResponse;
import com.example.librarymanagementsystem.exceptions.AuthorNotFoundException;
import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.repository.AuthorRepository;
import com.example.librarymanagementsystem.transformer.AuthorTransformer;
import com.example.librarymanagementsystem.transformer.BookTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
     @Autowired
    AuthorRepository authorRepository;
    public AuthorResponse addAuthor(String name, String email, int age) {
        Author author =  AuthorTransformer.prepareAuthor(name,email,age); //Set all attributes of author
        Author savedAuthor =authorRepository.save(author);
        return AuthorTransformer.AuthorToAuthorResponse(savedAuthor); // model -->> dto
    }

    public String updateEmail(int id, String email) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isEmpty()) {
            throw new AuthorNotFoundException("Invalid id !!");
        }
            authorOptional.get().setEmail(email);
            authorRepository.save(authorOptional.get());
            return "Your email is updated successfully";
    }

    public AuthorWithBooksResponse getAuthor(int id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isEmpty()) {
            throw new AuthorNotFoundException("Invalid id !!");
        }
        return AuthorTransformer.AuthorToAuthorWithBooksResponse(authorOptional.get());

    }

    public List<String> booksByAuthor(int id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if(authorOptional.isEmpty()){
            throw new AuthorNotFoundException("Invalid Author Id !!");
        }
        List<String> books = new ArrayList<>();
        for (Book book : authorOptional.get().getBooks()){
            books.add(book.getTitle());
        }
        return books;
    }

    public List<String> authorWithNNumberOfBooks(int n) {
        List<Author> authorList = authorRepository.findAll();
        List<String> authorNameList = new ArrayList<>();
        for(Author author : authorList){
            if(author.getBooks().size()>=n){
                authorNameList.add(author.getName());
            }
        }
        return authorNameList;
    }
}
