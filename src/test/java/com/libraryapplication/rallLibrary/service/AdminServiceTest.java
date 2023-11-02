package com.libraryapplication.rallLibrary.service;

import com.libraryapplication.rallLibrary.data.model.Admin;
import com.libraryapplication.rallLibrary.data.model.Book;
import com.libraryapplication.rallLibrary.data.model.BorrowedBook;
import com.libraryapplication.rallLibrary.data.repository.AdminRepository;
import com.libraryapplication.rallLibrary.data.repository.BookRepository;
import com.libraryapplication.rallLibrary.data.repository.BorrowedBookRepository;
import com.libraryapplication.rallLibrary.data.repository.UserRepository;
import com.libraryapplication.rallLibrary.dtos.request.*;
import com.libraryapplication.rallLibrary.exception.AdminNameAlreadyExistsException;
import com.libraryapplication.rallLibrary.exception.UserAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceTest {

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
    public void testThatAdminsCanRegister(){
        RegisterAdminRequest request = new RegisterAdminRequest();
        request.setAdminName("Librarian001");
        request.setPasscode("RallLibrary001");

        adminService.registerAdminWith(request);
        assertThat(adminRepository.count(), is(1L));

    }

    @Test
    public void testThatAdminRegistrationIsUnique(){
        RegisterAdminRequest request = new RegisterAdminRequest();
        request.setAdminName("Librarian001");
        request.setPasscode("RallLibrary001");

        adminService.registerAdminWith(request);
        assertThat(adminRepository.count(), is(1L));

        RegisterAdminRequest registerAdminRequest = new RegisterAdminRequest();
        registerAdminRequest.setAdminName("Librarian001");
        registerAdminRequest.setPasscode("RallLibrary001");

        assertThrows(AdminNameAlreadyExistsException.class, () -> adminService.registerAdminWith(registerAdminRequest));
        assertThat(adminRepository.count(), is(1L));

    }

    @Test
    public void testThatAdminCanLogin(){
        RegisterAdminRequest request = new RegisterAdminRequest();
        request.setAdminName("Librarian001");
        request.setPasscode("RallLibrary001");

        adminService.registerAdminWith(request);
        assertThat(adminRepository.count(), is(1L));

        AdminLoginRequest adminLoginRequest = new AdminLoginRequest();
        adminLoginRequest.setAdminName("Librarian001");
        adminLoginRequest.setPasscode("RallLibrary001");
        Admin admin = adminService.loginWith(adminLoginRequest);
        assertThat(admin.isLoggedIn(), is(true));
    }

    @Test
    public void testThatAdminCanAddBooks(){
        RegisterAdminRequest request = new RegisterAdminRequest();
        request.setAdminName("Librarian001");
        request.setPasscode("RallLibrary001");

        adminService.registerAdminWith(request);
        assertThat(adminRepository.count(), is(1L));

        AddBookRequest addBookRequest = new AddBookRequest();
        addBookRequest.setBookName("Things Fall Apart");
        addBookRequest.setAuthorName("Chinua Achebe");
        addBookRequest.setNumberAvailable(2);
        addBookRequest.setBorrowPrice(BigDecimal.valueOf(3_000));
        addBookRequest.setShelveNumber(1);

        adminService.addBook(addBookRequest);
        assertThat(bookRepository.count(), is(1L));
    }

    @Test
    public void testThatAdminCanUpdateTheNumberOfBooksAvailable(){
        RegisterAdminRequest request = new RegisterAdminRequest();
        request.setAdminName("Librarian001");
        request.setPasscode("RallLibrary001");

        adminService.registerAdminWith(request);
        assertThat(adminRepository.count(), is(1L));

        AddBookRequest addBookRequest = new AddBookRequest();
        addBookRequest.setBookName("Things Fall Apart");
        addBookRequest.setAuthorName("Chinua Achebe");
        addBookRequest.setNumberAvailable(2);
        addBookRequest.setBorrowPrice(BigDecimal.valueOf(3_000));
        addBookRequest.setShelveNumber(1);

        adminService.addBook(addBookRequest);
        assertThat(bookRepository.count(), is(1L));

        AddBookRequest bookRequest = new AddBookRequest();
        bookRequest.setBookName("Things Fall Apart");
        bookRequest.setAuthorName("Chinua Achebe");
        bookRequest.setNumberAvailable(6);
        bookRequest.setBorrowPrice(BigDecimal.valueOf(3_000));
        bookRequest.setShelveNumber(1);
        Book book = adminService.addBook(bookRequest);

        assertThat(book.getNumberAvailable(), is(8));
    }

    @Test
    public void testThatRemoveBookFromListOfBook(){
        RegisterAdminRequest request = new RegisterAdminRequest();
        request.setAdminName("Librarian001");
        request.setPasscode("RallLibrary001");

        adminService.registerAdminWith(request);
        assertThat(adminRepository.count(), is(1L));

        AddBookRequest addBookRequest = new AddBookRequest();
        addBookRequest.setBookName("Things Fall Apart");
        addBookRequest.setAuthorName("Chinua Achebe");
        addBookRequest.setNumberAvailable(2);
        addBookRequest.setBorrowPrice(BigDecimal.valueOf(3_000));
        addBookRequest.setShelveNumber(1);

        adminService.addBook(addBookRequest);
        assertThat(bookRepository.count(), is(1L));

        AddBookRequest bookRequest = new AddBookRequest();
        bookRequest.setBookName("Ire Ati Anu");
        bookRequest.setAuthorName("Alapeni Osa");
        bookRequest.setNumberAvailable(7);
        bookRequest.setBorrowPrice(BigDecimal.valueOf(5_000));
        bookRequest.setShelveNumber(1);

        adminService.addBook(bookRequest);
        assertThat(bookRepository.count(), is(2L));

        RemoveBookRequest removeBookRequest = new RemoveBookRequest();
        removeBookRequest.setBookName("Ire Ati Anu");
        removeBookRequest.setBookAuthor("Alapeni Osa");
        adminService.remove(removeBookRequest);
        assertThat(bookRepository.count(),is(1L));
    }

    @Test
    public void testThatAdminCanBorrowBookToUser(){
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
        assertThat(borrowedBookRepository.count(), is(1L));

    }

    @Test
    public void testThatAdminCanRestoreBookWhenUserReturnBook(){
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
        assertThat(borrowedBookRepository.count(), is(1L));

        ReturnBookRequest returnBookRequest = new ReturnBookRequest();
        returnBookRequest.setReturner("Akintomide");
        returnBookRequest.setBookName("Things Fall Apart");
        returnBookRequest.setBookAuthor("Chinua Achebe");
        adminService.restoreBorrowedBook(returnBookRequest);
        assertThat(borrowedBookRepository.count(), is(0L));
    }

}