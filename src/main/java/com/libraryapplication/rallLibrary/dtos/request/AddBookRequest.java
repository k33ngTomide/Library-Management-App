package com.libraryapplication.rallLibrary.dtos.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddBookRequest {

    private String bookName;
    private int shelveNumber;
    private String authorName;
    private int numberAvailable;
    private BigDecimal borrowPrice;
}
