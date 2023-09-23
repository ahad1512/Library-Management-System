package com.example.librarymanagementsystem.dto.responseDTO;
import com.example.librarymanagementsystem.Enum.Genre;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BookResponse {
    String title;

    int noOfPages;

    double cost;

    Genre genre;

    String authorName;
}
