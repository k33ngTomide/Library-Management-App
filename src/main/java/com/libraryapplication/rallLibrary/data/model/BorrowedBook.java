package com.libraryapplication.rallLibrary.data.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document("BorrowedBook")
public class BorrowedBook {

    private String id;
    @DBRef
    private Book bookName;
    @DBRef
    private User borrower;
    private LocalDate borrowDate;
    private LocalDate dueDate;

}
