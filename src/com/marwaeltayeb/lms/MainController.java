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

    TextField editId;
    TextField editTitle;
    TextField editAuthor;
    TextField editYear;
    TextField editPages;
    Button btnInsert;
    Button btnUpdate;
    Button btnDelete;
    TableView tvBooks;
    TableColumn colISBN;
    TableColumn colTitle;
    TableColumn colAuthor;
    TableColumn colYear;
    TableColumn colPages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
