package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.Enum.Genre;
import com.example.librarymanagementsystem.dto.requestDTO.BookRequest;
import com.example.librarymanagementsystem.dto.responseDTO.BookResponse;

import java.util.List;

public interface BookService {
    BookResponse addBook(BookRequest bookRequest);

    BookResponse getBook(int id);

    String deleteBook(int id);

    List<BookResponse> getByGenre(Genre genre);

    List<BookResponse> getByGenreAndCostGreaterThan(Genre genre, double cost);

    List<BookResponse> getBookByNumberOfPagesRange(int a, int b);

    List<String> getAuthorByGenre(Genre genre);
}
