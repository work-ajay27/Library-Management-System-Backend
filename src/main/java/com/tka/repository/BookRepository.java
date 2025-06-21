package com.tka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tka.entity.Book;
import com.tka.entity.User;

public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findByUser(User user);

}
