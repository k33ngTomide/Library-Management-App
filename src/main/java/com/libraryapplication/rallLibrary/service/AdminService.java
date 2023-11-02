package com.libraryapplication.rallLibrary.service;

import com.libraryapplication.rallLibrary.data.model.Admin;
import com.libraryapplication.rallLibrary.data.model.Book;
import com.libraryapplication.rallLibrary.dtos.request.*;

public interface AdminService {


    void registerAdminWith(RegisterAdminRequest request);

    Admin loginWith(AdminLoginRequest adminLoginRequest);

    Book addBook(AddBookRequest addBookRequest);

    void remove(RemoveBookRequest removeBookRequest);

    void   administerBook(BorrowBookRequest borrowBookRequest);


    void restoreBorrowedBook(ReturnBookRequest returnBookRequest);
}
