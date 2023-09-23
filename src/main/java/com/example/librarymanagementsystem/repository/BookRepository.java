package com.example.librarymanagementsystem.repository;

import com.example.librarymanagementsystem.Enum.Genre;
import com.example.librarymanagementsystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BookRepository extends JpaRepository<Book,Integer> {

    List<Book> findAllByGenre(Genre genre);

    @Query(value = "select b from Book b where b.genre = :genre and b.cost > :cost")
    List<Book> getByGenreAndCostGreaterThan(Genre genre, double cost);

    @Query(value = "select b from Book b where b.noOfPages >= :a and b.noOfPages <= :b")
    List<Book> getBookByNumberOfPagesRange(int a, int b);
}
