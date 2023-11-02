package com.libraryapplication.rallLibrary.service;

import com.libraryapplication.rallLibrary.data.model.Admin;
import com.libraryapplication.rallLibrary.data.model.Book;
import com.libraryapplication.rallLibrary.data.model.User;
import com.libraryapplication.rallLibrary.data.repository.AdminRepository;
import com.libraryapplication.rallLibrary.dtos.request.*;
import com.libraryapplication.rallLibrary.exception.AdminNameAlreadyExistsException;
import com.libraryapplication.rallLibrary.exception.AdminNotFoundException;
import com.libraryapplication.rallLibrary.exception.IncorrectPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.libraryapplication.rallLibrary.utils.AdminMapper.map;

@Service
public class AdminServiceImpl implements AdminService {


    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowedBookService borrowedBookService;

    @Override
    public void registerAdminWith(RegisterAdminRequest request) {
        uniqueCheck(request.getAdminName());
        Admin admin = map(request);
        adminRepository.save(admin);
    }

    @Override
    public Admin loginWith(AdminLoginRequest adminLoginRequest) {
        Optional<Admin> admin = adminRepository.findAdminByAdminName(adminLoginRequest.getAdminName());
        if(admin.isEmpty()) throw new AdminNotFoundException("Admin Not Found");
        if(!admin.get().getPasscode().equals(adminLoginRequest.getPasscode()))
            throw new IncorrectPasswordException("Incorrect Passcode");

        admin.get().setLoggedIn(true);
        adminRepository.save(admin.get());
        return admin.get();
    }

    @Override
    public Book addBook(AddBookRequest addBookRequest) {
        Book book = bookService.addBook(addBookRequest);
        return book;
    }

    @Override
    public void remove(RemoveBookRequest removeBookRequest) {
        bookService.remove(removeBookRequest);
    }

    @Override
    public void administerBook(BorrowBookRequest borrowBookRequest) {
        borrowedBookService.borrowBook(borrowBookRequest);
    }

    @Override
    public void restoreBorrowedBook(ReturnBookRequest returnBookRequest) {
        borrowedBookService.returnBook(returnBookRequest);
    }

    private void uniqueCheck(String adminName) {
        Optional<Admin> foundAdmin = adminRepository.findAdminByAdminName(adminName);
        if(foundAdmin.isPresent()) throw new AdminNameAlreadyExistsException("Librarian Already Exists");
    }


}
