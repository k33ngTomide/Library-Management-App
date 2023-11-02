package com.libraryapplication.rallLibrary.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Document("Books")
public class Book {

    @Id
    private String id;
    private String bookName;
    private int shelveNumber;
    private String authorName;
    private int numberAvailable;
    private BigDecimal borrowPrice;
    private LocalDate dateAdded = LocalDate.now();


}
