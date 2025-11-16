package com.softwareinsight.firstCrud.service;

import com.softwareinsight.firstCrud.entity.Book;
import com.softwareinsight.firstCrud.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book testBook;

    @BeforeEach
    public void setUp() {
        testBook = new Book("Clean Code", "Robert Martin", 45.99, "978-0132350884");
        testBook.setId(1L);
    }

    @Test
    public void testCreateBook() {
        // Given
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        // When
        Book createdBook = bookService.createBook(testBook);

        // Then
        assertThat(createdBook).isNotNull();
        assertThat(createdBook.getName()).isEqualTo("Clean Code");
        assertThat(createdBook.getId()).isEqualTo(1L);

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testGetBookById() {
        // Given
        when(bookRepository.findById(anyLong())).thenReturn(java.util.Optional.of(testBook));

        // when
        Optional<Book> foundBook = bookService.getBookById(1L);

        // then
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getName()).isEqualTo("Clean Code");
        assertThat(foundBook.get().getId()).isEqualTo(1L);

        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllBooks() {
        // Given
        Book book2 = new Book("Clean Architecture", "", 45.99, "978-0134494166");
        when(bookRepository.findAll()).thenReturn(java.util.List.of(testBook, book2));

        // when
        List<Book> books = bookService.getAllBooks();

        // then
        assertThat(books).hasSize(2);
        assertThat(books.get(0).getName()).isEqualTo("Clean Code");
        assertThat(books.get(1).getName()).isEqualTo("Clean Architecture");

        verify(bookRepository, times(1)).findAll();
    }

}
