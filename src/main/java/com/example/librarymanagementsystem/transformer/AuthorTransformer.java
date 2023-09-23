package com.example.librarymanagementsystem.transformer;

import com.example.librarymanagementsystem.dto.responseDTO.AuthorResponse;
import com.example.librarymanagementsystem.dto.responseDTO.AuthorWithBooksResponse;
import com.example.librarymanagementsystem.dto.responseDTO.BookResponse;
import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.model.Book;

import java.util.ArrayList;
import java.util.List;

import static com.example.librarymanagementsystem.transformer.BookTransformer.BookToBookResponse;


public class AuthorTransformer {

    public static Author prepareAuthor(String name, String email, int age){
       return Author.builder()
                .name(name)
                .email(email)
                .age(age)
                .build();
    }
 public static AuthorResponse AuthorToAuthorResponse(Author author){
     return AuthorResponse.builder()
             .name(author.getName())
             .age(author.getAge())
             .email(author.getEmail())
             .build();
 }
 public static AuthorWithBooksResponse AuthorToAuthorWithBooksResponse (Author author){
     List<BookResponse> bookResponseList = new ArrayList<>();
     AuthorResponse authorResponse = AuthorToAuthorResponse(author);
     for(Book books:author.getBooks()){
         BookResponse bookResponse= BookToBookResponse(books);
         bookResponseList.add(bookResponse);
     }

     return AuthorWithBooksResponse.builder()
             .authorResponse(authorResponse)
             .bookResponseList(bookResponseList)
             .build();
 }
}
