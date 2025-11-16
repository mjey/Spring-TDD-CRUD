package com.softwareinsight.firstCrud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwareinsight.firstCrud.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Test
    void shouldCreateBook() throws Exception {
//        Book book = new Book(null, "Clean Code", "Muzamil Hussain");
    }

}
