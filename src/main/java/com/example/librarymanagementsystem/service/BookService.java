package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.Enum.Genre;
import com.example.librarymanagementsystem.dto.requestDTO.BookRequest;
import com.example.librarymanagementsystem.dto.responseDTO.BookResponse;
import com.example.librarymanagementsystem.exceptions.AuthorNotFoundException;
import com.example.librarymanagementsystem.exceptions.BookNotFoundException;
import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.repository.AuthorRepository;
import com.example.librarymanagementsystem.repository.BookRepository;
import com.example.librarymanagementsystem.transformer.BookTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
     @Autowired
    AuthorRepository authorRepository;

     @Autowired
    BookRepository bookRepository;


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

    public BookResponse getBook(int id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty()){
            throw new BookNotFoundException("Invalid Book Id");
        }
        return BookTransformer.BookToBookResponse(bookOptional.get());
    }

    public String deleteBook(int id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if(bookOptional.isEmpty()){
            throw new BookNotFoundException("Invalid Book id");
        }
        Author author = bookOptional.get().getAuthor();

        author.getBooks().remove(bookOptional.get()); // removing book from author's list

        bookRepository.deleteById(id);
        return "Book deleted successfully";
    }

    public List<BookResponse> getByGenre(Genre genre) {
        List<Book> bookList = bookRepository.findAllByGenre(genre);
        List<BookResponse> bookResponseList = new ArrayList<>();
        for (Book book : bookList){
            bookResponseList.add(BookTransformer.BookToBookResponse(book));
        }
        return bookResponseList;
    }

    public List<BookResponse> getByGenreAndCostGreaterThan(Genre genre, double cost) {
     List<Book> bookList = bookRepository.getByGenreAndCostGreaterThan(genre,cost);

     List<BookResponse> bookResponseList = new ArrayList<>();
     for (Book book: bookList){
         bookResponseList.add(BookTransformer.BookToBookResponse(book));
     }
     return bookResponseList;
    }

    public List<BookResponse> getBookByNumberOfPagesRange(int a, int b) {
        List<Book> bookList = bookRepository.getBookByNumberOfPagesRange(a,b);

        List<BookResponse> bookResponseList = new ArrayList<>();
        for (Book book: bookList){
            bookResponseList.add(BookTransformer.BookToBookResponse(book));
        }
        return bookResponseList;
    }

    public List<String> getAuthorByGenre(Genre genre) {
        List<Book> books = bookRepository.findAllByGenre(genre);

        List<String> authorNames= new ArrayList<>();
        for(Book book:books){
            if(!authorNames.contains(book.getAuthor().getName())) {
                authorNames.add(book.getAuthor().getName());
            }
        }
        return authorNames;
    }
}
