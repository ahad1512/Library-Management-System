package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.responseDTO.AuthorResponse;
import com.example.librarymanagementsystem.exceptions.AuthorNotFoundException;
import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.repository.AuthorRepository;
import com.example.librarymanagementsystem.transformer.AuthorTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {
     @Autowired
    AuthorRepository authorRepository;
    public AuthorResponse addAuthor(String name, String email, int age) {
        Author author =  Author.builder().name(name).email(email).age(age).build(); //Set all attributes of author
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
}
