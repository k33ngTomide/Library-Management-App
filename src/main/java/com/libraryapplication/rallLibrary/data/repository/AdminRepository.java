package com.libraryapplication.rallLibrary.data.repository;

import com.libraryapplication.rallLibrary.data.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin, String> {

    Optional<Admin> findAdminByAdminName(String adminName);
}
