package com.libraryapplication.rallLibrary.service;


import com.libraryapplication.rallLibrary.data.model.Book;
import com.libraryapplication.rallLibrary.data.model.BorrowedBook;
import com.libraryapplication.rallLibrary.data.model.User;
import com.libraryapplication.rallLibrary.dtos.request.UserLoginRequest;
import com.libraryapplication.rallLibrary.dtos.request.RegisterUserRequest;

import java.util.List;

public interface UserService {
    void registerUser(RegisterUserRequest registerUserRequest);

    User login(UserLoginRequest loginRequest);

    User findUser(String borrower);

    List<Book> checkListOfBook();

    Book searchBook(String bookName,String authorName);

    List<BorrowedBook> checkListOfBorrowedBooks(String username);
}
