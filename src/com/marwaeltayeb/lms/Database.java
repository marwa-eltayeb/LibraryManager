package com.marwaeltayeb.lms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Database {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:db/books_db.db");
            System.out.println("Opened database successfully");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return connection;
    }

    public static ObservableList<Book> getBooksFromDB(){
        ObservableList<Book> books = FXCollections.observableArrayList();
        Connection connection = getConnection();
        Statement stmt = null;
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM books;" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                int isbn = rs.getInt("isbn");
                String  title = rs.getString("title");
                String  author = rs.getString("author");
                int year = rs.getInt("year");
                int pages = rs.getInt("pages");

                Book book = new Book(id, isbn, title, author, year, pages);
                books.add(book);
            }
            rs.close();
            stmt.close();
            connection.close();
            System.out.println("Books Selected successfully");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return books;
    }
}
