package com.libraryapplication.rallLibrary.data.repository;

import com.libraryapplication.rallLibrary.data.model.Book;
import com.libraryapplication.rallLibrary.data.model.BorrowedBook;
import com.libraryapplication.rallLibrary.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowedBookRepository extends MongoRepository<BorrowedBook, String> {
    Optional<BorrowedBook> findBookByBookNameAndBorrower(Book book, User user);

    List<BorrowedBook> findBookByBorrower(User user);
}
