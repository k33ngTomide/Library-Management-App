package com.libraryapplication.rallLibrary.service;

import com.libraryapplication.rallLibrary.data.model.Book;
import com.libraryapplication.rallLibrary.data.repository.BookRepository;
import com.libraryapplication.rallLibrary.dtos.request.AddBookRequest;
import com.libraryapplication.rallLibrary.dtos.request.BorrowBookRequest;
import com.libraryapplication.rallLibrary.dtos.request.RemoveBookRequest;
import com.libraryapplication.rallLibrary.dtos.request.ReturnBookRequest;
import com.libraryapplication.rallLibrary.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.libraryapplication.rallLibrary.utils.Mapper.map;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book addBook(AddBookRequest addBookRequest) {
        Optional<Book> book = getBook(addBookRequest.getBookName(), addBookRequest.getAuthorName());
        return book.map(value -> updateBook(value, addBookRequest)).orElseGet(() -> createNewBook(addBookRequest));

    }

    private Optional<Book> getBook(String bookName, String authorName) {
        return bookRepository.findBookByBookNameAndAuthorName(bookName, authorName);
    }

    @Override
    public void remove(RemoveBookRequest removeBookRequest) {
        Optional<Book> book = getBook(removeBookRequest.getBookName(), removeBookRequest.getBookAuthor());
        if(book.isPresent()) bookRepository.delete(book.get());
        else throw new BookNotFoundException("Book Not Found");

    }

    @Override
    public Book findBookToBorrow(BorrowBookRequest borrowBookRequest) {
        Optional<Book> book = getBook(borrowBookRequest.getBookName(), borrowBookRequest.getBookAuthor());
        if(book.isEmpty()) throwNotFoundException();
        return reduceBookBorrowed(book.get());
    }

    @Override
    public Book findBookToReturn(ReturnBookRequest returnBookRequest) {
        Optional<Book> book = getBook(returnBookRequest.getBookName(), returnBookRequest.getBookAuthor());
        if(book.isEmpty()) throwNotFoundException();
        return addToBookReturned(book.get());
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book findBook(String bookName, String authorName) {
        Optional<Book> book = getBook(bookName, authorName);
        if(book.isEmpty()) throwNotFoundException();
        return book.get();
    }

    private Book addToBookReturned(Book book) {
        book.setNumberAvailable(book.getNumberAvailable() + 1);
        bookRepository.save(book);
        return book;
    }

    private void throwNotFoundException() {
        throw new BookNotFoundException("Book Not Found");
    }

    private Book reduceBookBorrowed(Book book) {
        book.setNumberAvailable(book.getNumberAvailable() - 1);
        bookRepository.save(book);
        return book;
    }

    private Book updateBook(Book book, AddBookRequest addBookRequest) {
        book.setNumberAvailable(book.getNumberAvailable() + addBookRequest.getNumberAvailable());
        bookRepository.save(book);
        return book;
    }

    private Book createNewBook(AddBookRequest addBookRequest) {
        Book book = map(addBookRequest);
        bookRepository.save(book);
        return book;
    }


}
