package com.libraryapplication.rallLibrary.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Librarian")
public class Admin {

    @Id
    private String id;
    private String adminName;
    private String passcode;
    private boolean isLoggedIn;
}
