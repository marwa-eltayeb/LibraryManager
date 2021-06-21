package com.marwaeltayeb.lms;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static com.marwaeltayeb.lms.Database.*;

public class MainController implements Initializable {

    @FXML
    private TextField editISBN;
    @FXML
    private TextField editTitle;
    @FXML
    private TextField editAuthor;
    @FXML
    private TextField editYear;
    @FXML
    private TextField editPages;
    @FXML
    private TextField editSearch;

    @FXML
    private TableView<Book> tvBooks;
    @FXML
    private TableColumn<Book, Integer> colISBN;
    @FXML
    private TableColumn<Book, String> colTitle;
    @FXML
    private TableColumn<Book, String> colAuthor;
    @FXML
    private TableColumn<Book, Integer> colYear;
    @FXML
    private TableColumn<Book, Integer> colPages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> editSearch.requestFocus());
        showBooks();
    }

    private void showBooks() {
        ObservableList<Book> bookList = getBooksFromDB();

        colISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colPages.setCellValueFactory(new PropertyValueFactory<>("pages"));

        searchForBooks(bookList);
    }

    @FXML
    private void handleMouseAction() {
        Book book = tvBooks.getSelectionModel().getSelectedItem();
        editISBN.setText(String.valueOf(book.getIsbn()));
        editTitle.setText(book.getTitle());
        editAuthor.setText(book.getAuthor());
        editYear.setText(String.valueOf(book.getYear()));
        editPages.setText(String.valueOf(book.getPages()));
    }

    @FXML
    private void insert() {
        String isbnStr = editISBN.getText();
        String titleStr = editTitle.getText();
        String authorStr = editAuthor.getText();
        String yearStr = editYear.getText();
        String pagesStr = editPages.getText();

        if (isbnStr.isEmpty() || titleStr.isEmpty() || authorStr.isEmpty() || yearStr.isEmpty() || pagesStr.isEmpty()) {
            showWarning();
            return;
        }

        if (!isbnStr.matches("\\d*") || !yearStr.matches("\\d*") || !pagesStr.matches("\\d*")) {
            showError();
            return;
        }

        int isbn = Integer.parseInt(editISBN.getText());
        String title = editTitle.getText();
        String author = editAuthor.getText();
        int year = Integer.parseInt(editYear.getText());
        int pages = Integer.parseInt(editPages.getText());

        Book book = new Book(isbn, title, author, year, pages);
        insertToDB(book);
        clear();
        showBooks();
    }

    @FXML
    private void delete() {
        if (tvBooks.getSelectionModel().getSelectedItem() != null) {
            Book book = tvBooks.getSelectionModel().getSelectedItem();
            System.out.println(book.getId());
            deleteFromDB(book.getId());
            showBooks();
            clear();
        }
    }

    @FXML
    private void update() {
        if (tvBooks.getSelectionModel().getSelectedItem() != null) {
            Book book = tvBooks.getSelectionModel().getSelectedItem();
            book.setIsbn(Integer.parseInt(editISBN.getText()));
            book.setTitle(editTitle.getText());
            book.setAuthor(editAuthor.getText());
            book.setYear(Integer.parseInt(editYear.getText()));
            book.setPages(Integer.parseInt(editPages.getText()));
            System.out.println("Book ID: " + book.getId());
            updateDB(book);
            showBooks();
            clear();
        }
    }

    private void clear() {
        editISBN.setText("");
        editTitle.setText("");
        editAuthor.setText("");
        editYear.setText("");
        editPages.setText("");
    }

    private void showWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Input values must not be empty", ButtonType.OK);
        alert.showAndWait();
    }

    private void showError() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Input must be only positive numbers", ButtonType.OK);
        alert.showAndWait();
    }

    private void searchForBooks(ObservableList<Book> bookList) {
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Book> filteredData = new FilteredList<>(bookList, b -> true);

        // Set the filter Predicate whenever the filter changes.
        editSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(book -> {


                // Compare first title and last title of every book with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (book.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches title.
                } else if (book.getAuthor().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches author.
                } else if (String.valueOf(book.getYear()).contains(lowerCaseFilter)) {
                    return true; // Filter matches year.
                } else
                    return false; // Does not match.
            });
        });

        // Wrap the FilteredList in a SortedList.
        SortedList<Book> sortedData = new SortedList<>(filteredData);

        // Bind the SortedList comparator to the TableView comparator.
        // Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tvBooks.comparatorProperty());

        // Add sorted (and filtered) data to the table.
        tvBooks.setItems(sortedData);
    }
}
