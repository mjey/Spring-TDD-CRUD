package com.softwareinsight.firstCrud.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private double price;

    @Column(nullable = true)
    private String isbn;

    public Book() {
    }

    public Book(String name, String author, double price, String isbn) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.isbn = isbn;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id;}

    public String getName() {  return name; }
    public void setName(String name) { this.name = name; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    @Override
    public String toString() {
        return "Book [id=" + id + ", name=" + name + ", author=" + author + ", price=" + price + ", isbn=" + isbn + "]";
    }
}
