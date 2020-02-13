package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class AttendancerecordsPageController implements Initializable {

    @FXML
    private TableView<?> tableView;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> studIDCol;

    @FXML
    private TableColumn<?, ?> dateCol;

    @FXML
    private TableColumn<?, ?> timeinCol;

    @FXML
    private TableColumn<?, ?> timeoutCol;

    @FXML
    private TableColumn<?, ?> loginremarksCol;

    @FXML
    private TableColumn<?, ?> logoutremarksCol;

    @FXML
    private MenuItem selectBtn;

    @FXML
    private MenuItem excuseBtn;

    @FXML
    private MenuItem unexcuseBtn;

    @FXML
    private JFXTextField searchTxt;

    @FXML
    private JFXTextField studentIDTxt;

    @FXML
    private JFXTextField dateTxt;

    @FXML
    private JFXTextField timeinTxt;

    @FXML
    private JFXTextField timeoutTxt;

    @FXML
    private JFXTextArea loginremarksTxt;

    @FXML
    private JFXTextArea logoutremarksTxt;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXButton refreshBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
