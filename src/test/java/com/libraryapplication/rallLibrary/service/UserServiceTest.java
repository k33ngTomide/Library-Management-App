package com.libraryapplication.rallLibrary.service;

import com.libraryapplication.rallLibrary.data.model.Book;
import com.libraryapplication.rallLibrary.data.model.BorrowedBook;
import com.libraryapplication.rallLibrary.data.model.User;
import com.libraryapplication.rallLibrary.data.repository.AdminRepository;
import com.libraryapplication.rallLibrary.data.repository.BookRepository;
import com.libraryapplication.rallLibrary.data.repository.BorrowedBookRepository;
import com.libraryapplication.rallLibrary.data.repository.UserRepository;
import com.libraryapplication.rallLibrary.dtos.request.*;
import com.libraryapplication.rallLibrary.exception.UserAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class UserServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;


    @BeforeEach
    void setUp() {
        adminRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();
        borrowedBookRepository.deleteAll();
    }

    @Test
    public void testThatUserCanRegister(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Akintomide");
        registerUserRequest.setPassword("1234Tomide");

        userService.registerUser(registerUserRequest);
        assertThat(userRepository.count(), is(1L));

    }

    @Test
    public void testThatUserCanLogin(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Akintomide");
        registerUserRequest.setPassword("1234Tomide");

        userService.registerUser(registerUserRequest);
        assertThat(userRepository.count(), is(1L));

        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setUsername("Akintomide");
        loginRequest.setPassword("1234Tomide");
        User user = userService.login(loginRequest);
        assertThat(user.isLoggedIn(), is(true));

    }

    @Test
    public void testThatUsernameIsUnique(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Akintomide");
        registerUserRequest.setPassword("1234Tomide");

        userService.registerUser(registerUserRequest);
        assertThat(userRepository.count(), is(1L));

        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("Akintomide");
        request.setPassword("1234Tomide");

        assertThrows(UserAlreadyExistException.class, () -> userService.registerUser(request));
        assertThat(userRepository.count(), is(1L));
    }

    @Test
    public void testThatUserCanCheckAvailableBooks(){
        RegisterAdminRequest request = new RegisterAdminRequest();
        request.setAdminName("Librarian001");
        request.setPasscode("RallLibrary001");
        adminService.registerAdminWith(request);

        AddBookRequest addBookRequest = new AddBookRequest();
        addBookRequest.setBookName("Things Fall Apart");
        addBookRequest.setAuthorName("Chinua Achebe");
        addBookRequest.setNumberAvailable(7);
        addBookRequest.setBorrowPrice(BigDecimal.valueOf(3_000));
        addBookRequest.setShelveNumber(1);
        adminService.addBook(addBookRequest);

        AddBookRequest bookRequest = new AddBookRequest();
        bookRequest.setBookName("Ire Ati Anu");
        bookRequest.setAuthorName("Alapeni Osa");
        bookRequest.setNumberAvailable(2);
        bookRequest.setBorrowPrice(BigDecimal.valueOf(1_000));
        bookRequest.setShelveNumber(2);
        adminService.addBook(bookRequest);
        assertThat(bookRepository.count(), is(2L));

        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Akintomide");
        registerUserRequest.setPassword("1234Tomide");
        userService.registerUser(registerUserRequest);

        List<Book> availableBooks = userService.checkListOfBook();
        assertThat(availableBooks.size(), is(2));

    }

    @Test
    public void testThatUserCanSearchForBook(){
        RegisterAdminRequest request = new RegisterAdminRequest();
        request.setAdminName("Librarian001");
        request.setPasscode("RallLibrary001");
        adminService.registerAdminWith(request);

        AddBookRequest addBookRequest = new AddBookRequest();
        addBookRequest.setBookName("Things Fall Apart");
        addBookRequest.setAuthorName("Chinua Achebe");
        addBookRequest.setNumberAvailable(7);
        addBookRequest.setBorrowPrice(BigDecimal.valueOf(3_000));
        addBookRequest.setShelveNumber(1);
        adminService.addBook(addBookRequest);

        AddBookRequest bookRequest = new AddBookRequest();
        bookRequest.setBookName("Ire Ati Anu");
        bookRequest.setAuthorName("Alapeni Osa");
        bookRequest.setNumberAvailable(2);
        bookRequest.setBorrowPrice(BigDecimal.valueOf(1_000));
        bookRequest.setShelveNumber(2);
        adminService.addBook(bookRequest);
        assertThat(bookRepository.count(), is(2L));

        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Akintomide");
        registerUserRequest.setPassword("1234Tomide");
        userService.registerUser(registerUserRequest);

        Book book = userService.searchBook("Things Fall Apart", "Chinua Achebe");
        assertThat(book.getBookName(), is("Things Fall Apart"));

    }

    @Test
    public void testThatUserCanCheckListOfBookBorrowed(){
        RegisterAdminRequest request = new RegisterAdminRequest();
        request.setAdminName("Librarian001");
        request.setPasscode("RallLibrary001");
        adminService.registerAdminWith(request);

        AddBookRequest addBookRequest = new AddBookRequest();
        addBookRequest.setBookName("Things Fall Apart");
        addBookRequest.setAuthorName("Chinua Achebe");
        addBookRequest.setNumberAvailable(7);
        addBookRequest.setBorrowPrice(BigDecimal.valueOf(3_000));
        addBookRequest.setShelveNumber(1);
        adminService.addBook(addBookRequest);

        AddBookRequest bookRequest = new AddBookRequest();
        bookRequest.setBookName("Ire Ati Anu");
        bookRequest.setAuthorName("Alapeni Osa");
        bookRequest.setNumberAvailable(2);
        bookRequest.setBorrowPrice(BigDecimal.valueOf(1_000));
        bookRequest.setShelveNumber(2);
        adminService.addBook(bookRequest);
        assertThat(bookRepository.count(), is(2L));

        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Akintomide");
        registerUserRequest.setPassword("1234Tomide");
        userService.registerUser(registerUserRequest);

        BorrowBookRequest borrowBookRequest = new BorrowBookRequest();
        borrowBookRequest.setBorrower("Akintomide");
        borrowBookRequest.setBookName("Things Fall Apart");
        borrowBookRequest.setBookAuthor("Chinua Achebe");
        borrowBookRequest.setDueDate(LocalDate.of(2023, 11, 8));
        adminService.administerBook(borrowBookRequest);

        BorrowBookRequest bookRequest1 = new BorrowBookRequest();
        bookRequest1.setBorrower("Akintomide");
        bookRequest1.setBookName("Ire Ati Anu");
        bookRequest1.setBookAuthor("Alapeni Osa");
        bookRequest1.setDueDate(LocalDate.of(2023, 11, 8));
        adminService.administerBook(bookRequest1);

        List<BorrowedBook> borrowedBooks = userService.checkListOfBorrowedBooks("Akintomide");
        assertThat(borrowedBooks.size(), is(2));
    }


}