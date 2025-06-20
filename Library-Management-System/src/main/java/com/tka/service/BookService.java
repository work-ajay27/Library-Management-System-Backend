package com.tka.service;

import com.tka.entity.Book;
import com.tka.entity.User;
import com.tka.repository.BookRepository;
import com.tka.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private UserRepository userRepo;

    // 1. Save a new book (Admin use)
    public Book saveBook(Book book) {
        return bookRepo.save(book);
    }

    // 2. Get all books (used in both Admin and Student Dashboards)
    public List<Book> retriveBook() {
        return bookRepo.findAll();
    }

    // 3. Update existing book by ID
    public Book updateBook(Long id, Book book) {
        // First check if the book exists
        Book existing = bookRepo.findById(id).orElseThrow();
        
        // Update the fields
        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setPrice(book.getPrice());
        existing.setYear(book.getYear());

        // Save updated book
        return bookRepo.save(existing);
    }

    // 4. Delete book by ID
    public void deleteBook(Long id) {
        bookRepo.deleteById(id);
    }

    // 5. Get list of books borrowed by a specific user
    public List<Book> getBorrowedBooks(Long userId) {
        Optional<User> user = userRepo.findById(userId);
        // If user found, return their borrowed books
        return user.map(u -> bookRepo.findByUser(u)).orElse(null);
    }

    // 6. Borrow a book
    public String borrowBook(Long bookId, Long userId) {
        Optional<Book> bookOpt = bookRepo.findById(bookId);
        Optional<User> userOpt = userRepo.findById(userId);

        // Check if both book and user exist
        if (bookOpt.isPresent() && userOpt.isPresent()) {
            Book book = bookOpt.get();
            User user = userOpt.get();

            // Check if book is already borrowed
            if (book.getUser() != null) {
                return "Book is already borrowed!";
            }

            // Set the user who borrowed the book
            book.setUser(user);
            bookRepo.save(book);
            return "Book borrowed successfully!";
        }

        return "Book or User not found!";
    }

    // 7. Return a book
    public String returnBook(Long bookId, Long userId) {
        Optional<Book> bookOpt = bookRepo.findById(bookId);

        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();

            // Check if the book was borrowed by this user
            if (book.getUser() == null || book.getUser().getId() != userId) {
                return "You have not borrowed this book!";
            }

            // Remove the user from the book (return it)
            book.setUser(null);
            bookRepo.save(book);
            return "Book returned successfully!";
        }

        return "Book not found!";
    }
}
