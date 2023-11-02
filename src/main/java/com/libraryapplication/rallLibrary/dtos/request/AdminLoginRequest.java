package com.libraryapplication.rallLibrary.dtos.request;

import lombok.Data;

@Data
public class AdminLoginRequest {

    private String adminName;
    private String passcode;

}
