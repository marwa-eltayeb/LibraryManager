package com.marwaeltayeb.lms;

public class Book {

    private int id;
    private int isbn;
    private String title;
    private String author;
    private int year;
    private int pages;

    public Book(int isbn, String title, String author, int year, int pages) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
    }

    public Book(int id, int isbn, String title, String author, int year, int pages) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
    }

    public int getId() {
        return id;
    }

    public int getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public int getPages() {
        return pages;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
