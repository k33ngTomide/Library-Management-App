package com.libraryapplication.rallLibrary.service;

import com.libraryapplication.rallLibrary.data.model.BorrowedBook;
import com.libraryapplication.rallLibrary.data.model.User;
import com.libraryapplication.rallLibrary.dtos.request.BorrowBookRequest;
import com.libraryapplication.rallLibrary.dtos.request.ReturnBookRequest;

import java.util.List;

public interface BorrowedBookService {
    void borrowBook(BorrowBookRequest borrowBookRequest);

    void returnBook(ReturnBookRequest returnBookRequest);

    List<BorrowedBook> findBookBorrowedBy(User user);
}
