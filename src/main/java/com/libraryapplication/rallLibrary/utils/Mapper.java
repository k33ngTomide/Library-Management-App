package com.libraryapplication.rallLibrary.utils;

import com.libraryapplication.rallLibrary.data.model.Book;
import com.libraryapplication.rallLibrary.data.model.BorrowedBook;
import com.libraryapplication.rallLibrary.data.model.User;
import com.libraryapplication.rallLibrary.dtos.request.AddBookRequest;
import com.libraryapplication.rallLibrary.dtos.request.BorrowBookRequest;

import java.time.LocalDate;

public class Mapper {

    public static Book map(AddBookRequest addBookRequest) {
        Book book = new Book();
        book.setBookName(addBookRequest.getBookName());
        book.setAuthorName(addBookRequest.getAuthorName());
        book.setBorrowPrice(addBookRequest.getBorrowPrice());
        book.setShelveNumber(addBookRequest.getShelveNumber());
        book.setNumberAvailable(addBookRequest.getNumberAvailable());
        return book;
    }

    public static BorrowedBook map(BorrowBookRequest borrowBookRequest, User user, Book book) {
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setBorrower(user);
        borrowedBook.setBookName(book);
        borrowedBook.setBorrowDate(LocalDate.now());
        borrowedBook.setDueDate(borrowBookRequest.getDueDate());
        return borrowedBook;
    }
}
