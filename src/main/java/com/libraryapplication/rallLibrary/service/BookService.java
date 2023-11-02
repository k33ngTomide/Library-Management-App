package com.libraryapplication.rallLibrary.service;

import com.libraryapplication.rallLibrary.data.model.Book;
import com.libraryapplication.rallLibrary.dtos.request.AddBookRequest;
import com.libraryapplication.rallLibrary.dtos.request.BorrowBookRequest;
import com.libraryapplication.rallLibrary.dtos.request.RemoveBookRequest;
import com.libraryapplication.rallLibrary.dtos.request.ReturnBookRequest;

import java.util.List;

public interface BookService {
    Book addBook(AddBookRequest addBookRequest);

    void remove(RemoveBookRequest removeBookRequest);

    Book findBookToBorrow(BorrowBookRequest borrowBookRequest);

    Book findBookToReturn(ReturnBookRequest returnBookRequest);

    List<Book> getAllBooks();

    Book findBook(String bookName, String authorName);
}
