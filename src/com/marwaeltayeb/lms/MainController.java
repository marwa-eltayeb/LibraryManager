package com.marwaeltayeb.lms;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    TextField editId;
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
    TableView tvBooks;
    @FXML
    TableColumn colISBN;
    @FXML
    TableColumn colTitle;
    @FXML
    TableColumn colAuthor;
    @FXML
    TableColumn colYear;
    @FXML
    TableColumn colPages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
