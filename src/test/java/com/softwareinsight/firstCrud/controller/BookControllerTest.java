package com.softwareinsight.firstCrud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwareinsight.firstCrud.entity.Book;
import com.softwareinsight.firstCrud.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private Book testBook;

    @BeforeEach
    public void setUp() {
        testBook = new Book("Clean Code", "Robert Martin", 45.99, "978-0132350884");
        testBook.setId(1L);
    }

    @Test
    void testCreateBook() throws Exception {
        // Given
        when(bookService.createBook(any(Book.class))).thenReturn(testBook);

        // When & Then
        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBook)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert Martin"))
                .andExpect(jsonPath("$.price").value(45.99));

        verify(bookService, times(1)).createBook(any(Book.class));
    }

    @Test
    public void testGetBookById() throws Exception {
        // given
        when(bookService.getBookById(1L)).thenReturn(Optional.of(testBook));

        // When & Then
        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Clean Code"));

        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    public void testGetBookByIdNotFound() throws Exception {
        // Given
        when(bookService.getBookById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/v1/books/999"))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).getBookById(999L);
    }

    @Test
    public void testGetAllBooks() throws Exception {
        // Given
        Book book2 = new Book("Effective Java", "Joshua Bloch", 50.00, "978-0134685991");
        book2.setId(2L);
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(testBook, book2));

        // When & Then
        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Clean Code"))
                .andExpect(jsonPath("$[1].name").value("Effective Java"));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    public void testUpdateBook() throws Exception {
        // Given
        Book updatedBook = new Book("Clean Code 2nd Ed", "Robert Martin", 49.99, "978-0132350884");
        updatedBook.setId(1L);

        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(Optional.of(updatedBook));

        // When & Then
        mockMvc.perform(put("/api/v1/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Clean Code 2nd Ed"))
                .andExpect(jsonPath("$.price").value(49.99));

        verify(bookService, times(1)).updateBook(eq(1L), any(Book.class));
    }

    @Test
    public void testUpdateBookNotFound() throws Exception {
        // Given
        Book updatedBook = new Book("Clean Code 2nd Ed", "Robert Martin", 49.99, "978-0132350884");
        when(bookService.updateBook(eq(999L), any(Book.class))).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(put("/api/v1/books/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).updateBook(eq(999L), any(Book.class));
    }

    @Test
    public void testDeleteBook() throws Exception {
        // Given
        when(bookService.deleteBookById(1L)).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/api/v1/books/1"))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteBookById(1L);
    }

    @Test
    public void testDeleteBookNotFound() throws Exception {
        // Given
        when(bookService.deleteBookById(999L)).thenReturn(false);

        // When & Then
        mockMvc.perform(delete("/api/v1/books/999"))
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).deleteBookById(999L);
    }

}
