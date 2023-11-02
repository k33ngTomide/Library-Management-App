package com.libraryapplication.rallLibrary.controller;

import com.libraryapplication.rallLibrary.dtos.request.*;
import com.libraryapplication.rallLibrary.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register-admin")
    public String registerAdmin(@RequestBody RegisterAdminRequest registerAdminRequest){
        try{
            adminService.registerAdminWith(registerAdminRequest);
            return "Admin Registered Successfully";
        } catch(Exception exception){
            return exception.getMessage();
        }
    }

    @PostMapping("/admin-Login")
    public String adminLogin(@RequestBody AdminLoginRequest adminLoginRequest){
        try{
            adminService.loginWith(adminLoginRequest);
            return "Admin Login Succesfully";
        } catch (Exception exception){
            return exception.getMessage();
        }
    }

    @PostMapping("/addBook")
    public String addBooks(@RequestBody AddBookRequest addBookRequest){
        try{
            adminService.addBook(addBookRequest);
            return "Book Added Successfully";
        } catch(Exception exception){
            return exception.getMessage();
        }
    }

    @DeleteMapping("/remove-book")
    public String removeBook(@RequestBody RemoveBookRequest removeBookRequest){
        try{
            adminService.remove(removeBookRequest);
            return "Book Removed Successfully";
        } catch(Exception exception){
            return exception.getMessage();
        }
    }

    @PostMapping("/administer-book")
    public String borrowBook(@RequestBody BorrowBookRequest borrowBookRequest){
        try{
            adminService.administerBook(borrowBookRequest);
            return "Successful";
        } catch(Exception exception){
            return exception.getMessage();
        }
    }

    @PostMapping("/restore-book")
    public String restoreBook(@RequestBody ReturnBookRequest returnBookRequest){
        try{
            adminService.restoreBorrowedBook(returnBookRequest);
            return "Successful";
        } catch(Exception exception){
            return exception.getMessage();
        }
    }



}
