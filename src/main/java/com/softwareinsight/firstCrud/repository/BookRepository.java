package com.softwareinsight.firstCrud.repository;

import com.softwareinsight.firstCrud.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository  extends JpaRepository<Book, Long> {
    Book findByName(String name);
    Book findByIsbn(String isbn);
    Book findByAuthor(String author);
    Book findByPrice(double price);
}
