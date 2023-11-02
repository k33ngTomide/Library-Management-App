package com.libraryapplication.rallLibrary.exception;

public class BookNotBorrowedException extends LibraryException{
    public BookNotBorrowedException(String message) {
        super(message);
    }
}
