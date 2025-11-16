package com.softwareinsight.firstCrud.repository;

import com.softwareinsight.firstCrud.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // test on room database instead
public class BookRepositoryTest {

    @Autowired
    private  BookRepository bookRepository;

    @Test
    public void testSaveBook(){
        //Given
        Book book = new Book("Clean Code", "Robert Martin", 45.99, "978-0132350884");
        System.out.println("Book created is: " + book.toString());
        // When
        Book savedBook = bookRepository.save(book);

        // Then
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getName()).isEqualTo("Clean Code");

    }

    @Test
    public void findSavedBook() {
        Book book = new Book("Clean Code", "Robert Martin", 45.99, "978-0132350884");
        Book savedBook = bookRepository.save(book);

        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());

        assertThat(foundBook.isPresent()).isTrue();
        assertThat(foundBook.get().getName()).isEqualTo("Clean Code");

    }
}
