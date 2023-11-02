package com.libraryapplication.rallLibrary.utils;

import com.libraryapplication.rallLibrary.data.model.User;
import com.libraryapplication.rallLibrary.dtos.request.RegisterUserRequest;

public class UserMapper {

    public static User map(RegisterUserRequest registerUserRequest) {
        User user = new User();
        user.setUsername(registerUserRequest.getUsername());
        user.setPassword(registerUserRequest.getPassword());
        user.setPhoneNumber(registerUserRequest.getPhoneNumber());
        user.setLoggedIn(false);
        return user;
    }
}
