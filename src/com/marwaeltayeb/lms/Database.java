package com.marwaeltayeb.lms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Database {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite::resource:books.db");
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

    public static void insertToDB(Book book){
        Connection connection = getConnection();
        Statement stmt = null;
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            String sql = "INSERT INTO books (isbn,title, author, year, pages) " +
                    "VALUES ('" + book.getIsbn() + "', '" + book.getTitle() + "', '" + book.getAuthor() + "' ,'" + book.getYear() + "', '" + book.getPages() + "')";
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
            connection.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    public static void deleteFromDB(int rawIndex) {
        Connection connection = getConnection();
        try {
            String sql = "DELETE from books where id = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(sql);

            // information needed to delete the row
            deleteStatement.setInt(1, rawIndex);
            deleteStatement.executeUpdate();

            connection.close();
            System.out.println("Record successfully deleted");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static void updateDB(Book book) {
        Connection connection = getConnection();
        try {
            String sql = "UPDATE books SET isbn = '" + book.getIsbn() + "' , title = '" + book.getTitle()  + "' ,author = '"
                    + book.getAuthor() + "' , year = '" + book.getYear()  + "' , pages = '" + book.getPages() +"' where id = " + book.getId() + "";

            PreparedStatement updateStatement = connection.prepareStatement(sql);

            // execute and update
            updateStatement.executeUpdate();
            connection.close();

            System.out.println("Record successfully updated");
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}
