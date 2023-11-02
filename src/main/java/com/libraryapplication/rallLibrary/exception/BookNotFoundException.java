package com.libraryapplication.rallLibrary.exception;

public class BookNotFoundException extends LibraryException{
    public BookNotFoundException(String message) {
        super(message);
    }
}
