package com.example.librarymanagementsystem.exceptions;

public class BookNotIssuedException extends RuntimeException{
    public BookNotIssuedException(String message) {
        super(message);
    }
}
