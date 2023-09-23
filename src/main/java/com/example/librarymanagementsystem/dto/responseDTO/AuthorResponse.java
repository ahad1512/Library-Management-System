package com.example.librarymanagementsystem.dto.responseDTO;

import com.example.librarymanagementsystem.transformer.BookTransformer;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorResponse {

    String name;

    String email;

    int age;

}
