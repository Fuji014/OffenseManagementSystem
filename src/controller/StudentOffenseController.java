package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.tables.studentOffenseTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentOffenseController implements Initializable {

    @FXML
    private JFXComboBox<String> searchComboBox;

    @FXML
    private JFXTextField searchTxt;

    @FXML
    private JFXButton refreshBtn;


    @FXML
    private TableView<studentOffenseTable> studoffenseTableView;

    @FXML
    private TableColumn<studentOffenseTable, Integer> idCol;

    @FXML
    private TableColumn<studentOffenseTable, String> studidCol;

    @FXML
    private TableColumn<studentOffenseTable, String> offenseCol;

    @FXML
    private TableColumn<studentOffenseTable, String> severityCol;

    @FXML
    private TableColumn<studentOffenseTable, String> offensecountCol;

    @FXML
    private TableColumn<studentOffenseTable, String> dateCol;

    @FXML
    private TableColumn<studentOffenseTable, String> remarksCol;

    @FXML
    private MenuItem updateBtn;

    @FXML
    private MenuItem deleteBtn;

    @FXML
    private JFXButton newBtn;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXButton cancelBtn;

    // declare var below
    private DatabaseAccessObject dao;
    private MainController mc;
    // end of declare var

    // initialize itself
    private String query;
    // end of initialize itself

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // initialize class
        dao = new DatabaseAccessObject();
        mc = new MainController();
        // end of initialize class

        // methods
        try {
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // end of methods

        // event button
        newBtn.setOnAction(event -> {
            try {
                mc.createPage(null, "/views/StudentOffenseAdd.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        // end of event button

    }

    // init
    public void initTable(){
        idCol.setCellValueFactory(cell -> cell.getValue().studOffIdProperty().asObject());
        studidCol.setCellValueFactory(cell -> cell.getValue().studIdProperty());
        offenseCol.setCellValueFactory(cell -> cell.getValue().studOffProperty());
        severityCol.setCellValueFactory(cell -> cell.getValue().studSeverityProperty());
        offensecountCol.setCellValueFactory(cell -> cell.getValue().studCountProperty());
        dateCol.setCellValueFactory(cell -> cell.getValue().studDateProperty());
        remarksCol.setCellValueFactory(cell -> cell.getValue().studRemarksProperty());
    }
    public void refreshTable() throws SQLException {
        initTable();
        query = "select so.std_offense_id, s.student_id, o.offense_description, o.offense_severity, so.student_offense_count, so.student_offense_date, so.student_offense_remarks from student_offense_tbl as so join student_tbl as s on so.student_key = s.id join offense_tbl as o on so.offense_key = o.id";
        studoffenseTableView.setItems(dao.getStudentOffenseData(query));
    }

    // end of init

    // custom methods

    // end of custom methods

    // getters

    // end of getters
}
