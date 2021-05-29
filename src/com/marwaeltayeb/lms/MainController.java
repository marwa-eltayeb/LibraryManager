package com.marwaeltayeb.lms;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static com.marwaeltayeb.lms.Database.*;

public class MainController implements Initializable {

    @FXML
    TextField editISBN;
    @FXML
    TextField editTitle;
    @FXML
    TextField editAuthor;
    @FXML
    TextField editYear;
    @FXML
    TextField editPages;
    @FXML
    Button btnInsert;
    @FXML
    Button btnUpdate;
    @FXML
    Button btnDelete;
    @FXML
    TableView<Book> tvBooks;
    @FXML
    TableColumn<Book, Integer> colISBN;
    @FXML
    TableColumn<Book, String> colTitle;
    @FXML
    TableColumn<Book, String> colAuthor;
    @FXML
    TableColumn<Book, Integer> colYear;
    @FXML
    TableColumn<Book, Integer> colPages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showBooks();
    }

    public void showBooks() {
        ObservableList<Book> list = getBooksFromDB();

        colISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colPages.setCellValueFactory(new PropertyValueFactory<>("pages"));

        tvBooks.setItems(list);
    }

    public void handleMouseAction(MouseEvent mouseEvent) {
        Book book = tvBooks.getSelectionModel().getSelectedItem();
        editISBN.setText(String.valueOf(book.getIsbn()));
        editTitle.setText(book.getTitle());
        editAuthor.setText(book.getAuthor());
        editYear.setText(String.valueOf(book.getYear()));
        editPages.setText(String.valueOf(book.getPages()));
    }

    public void insert(ActionEvent actionEvent) {
        int isbn = Integer.parseInt(editISBN.getText());
        String title = editTitle.getText();
        String author = editAuthor.getText();
        int year = Integer.parseInt(editYear.getText());
        int pages = Integer.parseInt(editPages.getText());

        Book book = new Book(isbn, title, author, year, pages);
        insertToDB(book);
        showBooks();
    }

}
