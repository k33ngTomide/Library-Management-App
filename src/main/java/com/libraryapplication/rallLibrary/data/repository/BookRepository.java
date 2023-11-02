package com.libraryapplication.rallLibrary.data.repository;

import com.libraryapplication.rallLibrary.data.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {
    Optional<Book> findBookByBookNameAndAuthorName(String bookName, String authorName);
}
