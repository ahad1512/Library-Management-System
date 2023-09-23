package com.example.librarymanagementsystem.transformer;

import com.example.librarymanagementsystem.Enum.CardStatus;
import com.example.librarymanagementsystem.dto.responseDTO.LibraryCardResponse;
import com.example.librarymanagementsystem.model.LibraryCard;
import com.example.librarymanagementsystem.model.Student;

import java.util.UUID;

public class LibraryCardTransformer {

    public static LibraryCard prepareLibraryCard(){
        return  LibraryCard.builder()
                .cardNumber(String.valueOf(UUID.randomUUID()))
                .cardStatus(CardStatus.ACTIVE)
                .build();
    }

    public static LibraryCardResponse LibraryCardToLibraryCardResponse(LibraryCard libraryCard){
        return LibraryCardResponse.builder()
                .cardNumber(libraryCard.getCardNumber())
                .cardStatus(libraryCard.getCardStatus())
                .issueDate(libraryCard.getIssueDate())
                .build();

    }
}
