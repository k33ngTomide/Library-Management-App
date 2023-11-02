package com.libraryapplication.rallLibrary.utils;

import com.libraryapplication.rallLibrary.data.model.Admin;
import com.libraryapplication.rallLibrary.dtos.request.RegisterAdminRequest;

public class AdminMapper {

    public static Admin map(RegisterAdminRequest request) {
        Admin admin = new Admin();
        admin.setAdminName(request.getAdminName());
        admin.setPasscode(request.getPasscode());
        admin.setLoggedIn(false);
        return admin;
    }
}
