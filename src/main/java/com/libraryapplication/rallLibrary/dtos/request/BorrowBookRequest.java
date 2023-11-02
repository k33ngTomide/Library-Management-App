package com.libraryapplication.rallLibrary.dtos.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BorrowBookRequest {

    private String bookName;
    private String bookAuthor;
    private String borrower;
    private LocalDate dueDate;
}
