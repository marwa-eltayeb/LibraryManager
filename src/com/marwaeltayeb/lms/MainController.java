package com.marwaeltayeb.lms;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.marwaeltayeb.lms.Database.*;

public class MainController implements Initializable {

    @FXML private AnchorPane anchorId;

    @FXML private TextField editISBN;
    @FXML private TextField editTitle;
    @FXML private TextField editAuthor;
    @FXML private TextField editYear;
    @FXML private TextField editPages;
    @FXML private TextField editSearch;

    @FXML private TableView<Book> tvBooks;
    @FXML private TableColumn<Book, Integer> colISBN;
    @FXML private TableColumn<Book, String> colTitle;
    @FXML private TableColumn<Book, String> colAuthor;
    @FXML private TableColumn<Book, Integer> colYear;
    @FXML private TableColumn<Book, Integer> colPages;

    private ObservableList<Book> bookList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> editSearch.requestFocus());
        showBooks();
    }

    private void showBooks() {
        bookList = getBooksFromDB();
        tvBooks.refresh();

        colISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colPages.setCellValueFactory(new PropertyValueFactory<>("pages"));

        tvBooks.setItems(bookList);
        searchForBooks(bookList);
    }

    @FXML private void handleMouseAction() {
        Book book = tvBooks.getSelectionModel().getSelectedItem();
        if (book==null) return;
        editISBN.setText(String.valueOf(book.getIsbn()));
        editTitle.setText(book.getTitle());
        editAuthor.setText(book.getAuthor());
        editYear.setText(String.valueOf(book.getYear()));
        editPages.setText(String.valueOf(book.getPages()));
    }

    @FXML private void insert() {
        String isbnStr = editISBN.getText().trim();
        String titleStr = editTitle.getText().trim();
        String authorStr = editAuthor.getText().trim();
        String yearStr = editYear.getText().trim();
        String pagesStr = editPages.getText().trim();

        if (isbnStr.isEmpty() || titleStr.isEmpty() || authorStr.isEmpty() || yearStr.isEmpty() || pagesStr.isEmpty()) {
            showWarning("Input values must not be empty");
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

        if(bookList.contains(book)){
            showWarning("This book has been inserted before");
            return;
        }
        insertToDB(book);
        showBooks();
        clear();
    }

    @FXML private void delete() {
        if (tvBooks.getSelectionModel().getSelectedItem() != null) {
            Book book = tvBooks.getSelectionModel().getSelectedItem();
            deleteFromDB(book.getId());
            showBooks();
            clear();
        }
    }

    @FXML private void update() {
        if (tvBooks.getSelectionModel().getSelectedItem() != null) {
            Book book = tvBooks.getSelectionModel().getSelectedItem();
            book.setIsbn(Integer.parseInt(editISBN.getText()));
            book.setTitle(editTitle.getText());
            book.setAuthor(editAuthor.getText());
            book.setYear(Integer.parseInt(editYear.getText()));
            book.setPages(Integer.parseInt(editPages.getText()));
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

    private void showWarning(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING, text, ButtonType.OK);
        alert.initOwner(anchorId.getScene().getWindow());
        alert.showAndWait();
    }

    private void showError() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Input must be only positive numbers", ButtonType.OK);
        alert.initOwner(anchorId.getScene().getWindow());
        alert.showAndWait();
    }

    private void searchForBooks(ObservableList<Book> bookList) {
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Book> filteredData = new FilteredList<>(bookList, b -> true);

        // Set the filter Predicate whenever the filter changes.
        editSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(book -> {


                // Get new value and convert it to lowercase
                String lowerCaseFilter = newValue.toLowerCase();

                if (book.getTitle().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true; // Filter matches title.
                } else if (book.getAuthor().toLowerCase().startsWith(lowerCaseFilter)) {
                    return true; // Filter matches author.
                } else
                    return  (String.valueOf(book.getYear()).startsWith(lowerCaseFilter)) ; // Filter matches year.
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
