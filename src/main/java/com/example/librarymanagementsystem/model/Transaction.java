package com.example.librarymanagementsystem.model;

import com.example.librarymanagementsystem.Enum.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

   String transactionNumber;

   @CreationTimestamp
   Date transactionTime;

   @Enumerated(EnumType.STRING)
   TransactionStatus transactionStatus;

   @ManyToOne
   @JoinColumn
   Book book;

   @ManyToOne
   @JoinColumn
   LibraryCard libraryCard;

}
