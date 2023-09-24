package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.responseDTO.AuthorResponse;
import com.example.librarymanagementsystem.dto.responseDTO.AuthorWithBooksResponse;

import java.util.List;

public interface AuthorService {
    AuthorResponse addAuthor(String name, String email, int age);

    String updateEmail(int id, String email);

    AuthorWithBooksResponse getAuthor(int id);

    List<String> booksByAuthor(int id);

    List<String> authorWithNNumberOfBooks(int n);
}
