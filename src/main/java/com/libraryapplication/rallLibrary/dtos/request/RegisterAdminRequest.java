package com.libraryapplication.rallLibrary.dtos.request;

import lombok.Data;

@Data
public class RegisterAdminRequest {

    private String adminName;
    private String passcode;

}
