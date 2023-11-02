package com.libraryapplication.rallLibrary.service;

import com.libraryapplication.rallLibrary.data.model.Book;
import com.libraryapplication.rallLibrary.data.model.BorrowedBook;
import com.libraryapplication.rallLibrary.data.model.User;
import com.libraryapplication.rallLibrary.data.repository.UserRepository;
import com.libraryapplication.rallLibrary.dtos.request.UserLoginRequest;
import com.libraryapplication.rallLibrary.dtos.request.RegisterUserRequest;
import com.libraryapplication.rallLibrary.exception.IncorrectPasswordException;
import com.libraryapplication.rallLibrary.exception.UserAlreadyExistException;
import com.libraryapplication.rallLibrary.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.libraryapplication.rallLibrary.utils.UserMapper.map;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowedBookService borrowedBookService;

    @Override
    public void registerUser(RegisterUserRequest registerUserRequest) {
        checkIfUserAlreadyExists(registerUserRequest.getUsername());
        User user = map(registerUserRequest);
        userRepository.save(user);
    }

    @Override
    public User login(UserLoginRequest loginRequest) {
        Optional<User> user = getUser(loginRequest.getUsername());
        if (user.isEmpty()) throw new UserNotFoundException("User not found");
       if(!user.get().getPassword().equals(loginRequest.getPassword()))
           throw new IncorrectPasswordException("Incorrect Password");

       user.get().setLoggedIn(true);
       userRepository.save(user.get());
       return user.get();
    }

    @Override
    public User findUser(String borrower) {
        Optional<User> user = getUser(borrower);
        if (user.isEmpty()) throw new UserNotFoundException("User not found");
        return user.get();
    }

    @Override
    public List<Book> checkListOfBook() {
        return bookService.getAllBooks();
    }

    @Override
    public Book searchBook(String bookName, String authorName) {
        Book book = bookService.findBook(bookName, authorName);
        return book;
    }

    @Override
    public List<BorrowedBook> checkListOfBorrowedBooks(String username) {
        return borrowedBookService.findBookBorrowedBy(findUser(username));
    }

    private Optional<User> getUser(String username) {
        Optional<User> user =  userRepository.findByUsername(username);
        return user;
    }

    private void checkIfUserAlreadyExists(String username) {
        Optional<User> user = getUser(username);
        if(user.isPresent()) throw new UserAlreadyExistException("Username Already Exist, Try Another Username");
    }


}
