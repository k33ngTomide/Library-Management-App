package com.libraryapplication.rallLibrary.controller;


import com.libraryapplication.rallLibrary.data.model.Book;
import com.libraryapplication.rallLibrary.dtos.request.RegisterUserRequest;
import com.libraryapplication.rallLibrary.dtos.request.UserLoginRequest;
import com.libraryapplication.rallLibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rall-Library/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/userRegister")
    public String registerUser(@RequestBody RegisterUserRequest registerUserRequest){
        try{
            userService.registerUser(registerUserRequest);
            return "Registration successful";
        } catch (Exception exception){
            return exception.getMessage();
        }
    }

    @PostMapping("/userLogin")
    public String loginUser(@RequestBody UserLoginRequest userLoginRequest){
        try{
            userService.login(userLoginRequest);
            return "Login Successfully";
        } catch (Exception exception){
            return exception.getMessage();
        }
    }

    @GetMapping("/allBook")
    public List<Book> getAllBook(){
        List<Book> allBooks = userService.checkListOfBook();
        return allBooks;
    }

    @GetMapping("/searchBook/{bookName}/{authorName}")
    public Object serachBook(@PathVariable String bookName, @PathVariable String authorName){
        try{
            return userService.searchBook(bookName, authorName);
        } catch (Exception exception){
            return exception.getMessage();
        }
    }

    @GetMapping("/listOfBorrowedBooks/{username}")
    public Object borrowedBooks(@PathVariable String username){
        try{
            return userService.checkListOfBorrowedBooks(username);
        } catch (Exception exception){
            return exception.getMessage();
        }
    }
}
