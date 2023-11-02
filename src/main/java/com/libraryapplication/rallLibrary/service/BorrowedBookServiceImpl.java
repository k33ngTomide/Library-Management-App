package com.libraryapplication.rallLibrary.service;


import com.libraryapplication.rallLibrary.data.model.Book;
import com.libraryapplication.rallLibrary.data.model.BorrowedBook;
import com.libraryapplication.rallLibrary.data.model.User;
import com.libraryapplication.rallLibrary.data.repository.BorrowedBookRepository;
import com.libraryapplication.rallLibrary.dtos.request.BorrowBookRequest;
import com.libraryapplication.rallLibrary.dtos.request.ReturnBookRequest;
import com.libraryapplication.rallLibrary.exception.BookNotBorrowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

import static com.libraryapplication.rallLibrary.utils.Mapper.map;

@Service
public class BorrowedBookServiceImpl implements BorrowedBookService{

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;
    @Override
    public void borrowBook(BorrowBookRequest borrowBookRequest) {
        User user = userService.findUser(borrowBookRequest.getBorrower());
        Book book = bookService.findBookToBorrow(borrowBookRequest);

        BorrowedBook borrowedBook = map(borrowBookRequest, user, book);
        borrowedBookRepository.save(borrowedBook);
    }

    @Override
    public void returnBook(ReturnBookRequest returnBookRequest) {
        User user = userService.findUser(returnBookRequest.getReturner());
        Book book = bookService.findBookToReturn(returnBookRequest);

        Optional<BorrowedBook> returnedBook = borrowedBookRepository.findBookByBookNameAndBorrower(book, user);
        if(returnedBook.isEmpty()) throw new BookNotBorrowedException("User did not borrow this book, arrest him");
        borrowedBookRepository.delete(returnedBook.get());
    }

    @Override
    public List<BorrowedBook> findBookBorrowedBy(User user) {
        return borrowedBookRepository.findBookByBorrower(user);
    }

}
