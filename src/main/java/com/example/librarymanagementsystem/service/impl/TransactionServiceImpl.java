package com.example.librarymanagementsystem.service.impl;

import com.example.librarymanagementsystem.dto.responseDTO.IssueBookResponse;
import com.example.librarymanagementsystem.exceptions.BookNotAvailableException;
import com.example.librarymanagementsystem.exceptions.BookNotFoundException;
import com.example.librarymanagementsystem.exceptions.BookNotIssuedException;
import com.example.librarymanagementsystem.exceptions.StudentNotFoundException;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.model.Student;
import com.example.librarymanagementsystem.model.Transaction;
import com.example.librarymanagementsystem.repository.BookRepository;
import com.example.librarymanagementsystem.repository.StudentRepository;
import com.example.librarymanagementsystem.repository.TransactionRepository;
import com.example.librarymanagementsystem.transformer.TransactionTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionServiceImpl {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    JavaMailSender javaMailSender;

    public IssueBookResponse issueBook(int bookId, int studentId) {
        Optional<Book> bookOptional= bookRepository.findById(bookId);
        //check if book exists or not
        if(bookOptional.isEmpty()){
            throw new BookNotFoundException("Invalid book id !!");
        }
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        //check if student is present or not
        if (studentOptional.isEmpty()){
            throw new StudentNotFoundException("Invalid student id");
        }
        Book book = bookOptional.get();
        //check if is not already issued
        if(book.isIssued()){
            throw new BookNotAvailableException("Book already issued");
        }
        Student student = studentOptional.get();

        //create transaction
        Transaction transaction = TransactionTransformer.prepareTransaction(book,student);

        Transaction savedTransaction = transactionRepository.save(transaction); //save transaction to db

        book.setIssued(true); //book issued
        book.getTransactions().add(savedTransaction); //add transaction in book model

        student.getLibraryCard().getTransactions().add(savedTransaction); // add transaction in library card model

        Book savedBook = bookRepository.save(book); // save book and transaction
        Student savedStudent = studentRepository.save(student); // save student and transaction

        //  send an email
        String text = "Dear, "+ student.getName()+"\n\n"+"This is to notify you that the book: "+ book.getTitle() +" by "+book.getAuthor().getName()
                      +",has been issued to you.The transaction number is: "+savedTransaction.getTransactionNumber()+"\n\n"
                      +"Book Details:\n"+"Title: "+book.getTitle()+"\n"+"Author: "+book.getAuthor().getName()+"\n"+"Issue Date: "+savedTransaction.getTransactionTime()+"\n\n"
                      +"Please take note of the due date and ensure the book is returned by then to avoid any late fees.\n\n"+"Happy reading!";

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("springaccio18@gmail.com");
        simpleMailMessage.setTo(student.getEmail());
        simpleMailMessage.setSubject("Congrats!! Book Issued");
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);


        return TransactionTransformer.prepareIssueBookResponse(savedBook,savedStudent,savedTransaction);
    }

    public String returnBook(int bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        //check if book exist
        if(bookOptional.isEmpty()){
            throw new BookNotFoundException("Invalid book id !!");
        }
        Book book = bookOptional.get();
        if(!book.isIssued()){
            throw new BookNotIssuedException("This book is not issued !!");
        }
        //Get the student that issued the book
        Student student = book.getTransactions().get(book.getTransactions().size()-1).getLibraryCard().getStudent();
        //Now Book is valid and issued so now create transaction
        Transaction transaction = TransactionTransformer.prepareTransaction(book,student);

        //Save Transaction
        Transaction savedTransaction = transactionRepository.save(transaction);

        book.setIssued(false); // book return
        book.getTransactions().add(savedTransaction); // Add transaction in book model

        student.getLibraryCard().getTransactions().add(savedTransaction);

        Book savedBook = bookRepository.save(book);
        Student savedStudent = studentRepository.save(student);

        //Send an Email
        String text = "Hey "+savedStudent.getName()+","+"\n"+
                "Thank you for returning the book to the library promptly." +
                " Your cooperation is appreciated." +
                " The transaction number for your return is "+savedTransaction.getTransactionNumber();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("springaccio18@gmail.com");
        simpleMailMessage.setTo(savedStudent.getEmail());
        simpleMailMessage.setSubject("Book returned");
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);

        return "Book with Id- "+savedBook.getId()+" issued to "+savedStudent.getName()+
                " has been successfully returned with transaction number- "+savedTransaction.getTransactionNumber();
    }
}
