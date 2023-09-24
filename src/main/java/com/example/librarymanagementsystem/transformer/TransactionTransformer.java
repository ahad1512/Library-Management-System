package com.example.librarymanagementsystem.transformer;

import com.example.librarymanagementsystem.Enum.TransactionStatus;
import com.example.librarymanagementsystem.dto.responseDTO.IssueBookResponse;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.model.Student;
import com.example.librarymanagementsystem.model.Transaction;

import java.util.UUID;

public class TransactionTransformer {

    public static Transaction prepareTransaction(Book book, Student student){
      return    Transaction.builder()
                .transactionNumber(String.valueOf(UUID.randomUUID()))
                .transactionStatus(TransactionStatus.SUCCESS)
                .book(book)
                .libraryCard(student.getLibraryCard())
                .build();
    }

    public static IssueBookResponse prepareIssueBookResponse(Book book, Student student, Transaction transaction){
        return IssueBookResponse.builder()
                .bookName(book.getTitle())
                .transactionTime(transaction.getTransactionTime())
                .transactionNumber(transaction.getTransactionNumber())
                .transactionStatus(transaction.getTransactionStatus())
                .authorName(book.getAuthor().getName())
                .studentName(student.getName())
                .libraryCardNumber(student.getLibraryCard().getCardNumber())
                .build();
    }
}
