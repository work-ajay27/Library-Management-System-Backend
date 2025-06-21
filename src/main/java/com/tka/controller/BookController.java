package com.tka.controller;

import com.tka.entity.Book;
import com.tka.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173") // Use your React port (5173 for Vite)
@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookservice;

    // 1. Add Book
    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = bookservice.saveBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    // 2. Retrieve All Books
    @GetMapping("/retrive")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookservice.retriveBook();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // 3. Update Book
    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updated = bookservice.updateBook(id, book);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // 4. Delete Book
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookservice.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 5. Get Borrowed Books
    @GetMapping("/borrowed")
    public ResponseEntity<List<Book>> getBorrowedBooks(@RequestParam Long studentId) {
        List<Book> borrowedBooks = bookservice.getBorrowedBooks(studentId);
        return new ResponseEntity<>(borrowedBooks, HttpStatus.OK);
    }

    // 6. Borrow Book
    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestParam Long bookId, @RequestParam Long studentId) {
        String response = bookservice.borrowBook(bookId, studentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 7. Return Book
    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestParam Long bookId, @RequestParam Long studentId) {
        String response = bookservice.returnBook(bookId, studentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
