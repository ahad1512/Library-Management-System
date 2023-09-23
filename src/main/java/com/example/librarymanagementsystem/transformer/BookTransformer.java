package com.example.librarymanagementsystem.transformer;

import com.example.librarymanagementsystem.dto.requestDTO.BookRequest;
import com.example.librarymanagementsystem.dto.responseDTO.BookResponse;
import com.example.librarymanagementsystem.model.Book;

public class BookTransformer {

    public static Book BookRequestToBook (BookRequest bookRequest){
        return   Book.builder()
                .genre(bookRequest.getGenre())
                .title(bookRequest.getTitle())
                .cost(bookRequest.getCost())
                .noOfPages(bookRequest.getNoOfPages())
                .build();
    }
    public static BookResponse BookToBookResponse (Book book){
        return BookResponse.builder()
                .title(book.getTitle())
                .cost(book.getCost())
                .genre(book.getGenre())
                .noOfPages(book.getNoOfPages())
                .authorName(book.getAuthor().getName())
                .build();
    }

}
