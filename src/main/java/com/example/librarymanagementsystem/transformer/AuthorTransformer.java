package com.example.librarymanagementsystem.transformer;

import com.example.librarymanagementsystem.dto.responseDTO.AuthorResponse;
import com.example.librarymanagementsystem.model.Author;

public class AuthorTransformer {


 public static AuthorResponse AuthorToAuthorResponse(Author author){
     return AuthorResponse.builder()
             .name(author.getName())
             .age(author.getAge())
             .email(author.getEmail())
             .build();
 }
}
