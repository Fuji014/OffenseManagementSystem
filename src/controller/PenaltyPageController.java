package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.tables.penaltyTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PenaltyPageController implements Initializable {

    @FXML
    private TableView<penaltyTable> tableView;

    @FXML
    private TableColumn<penaltyTable, Integer> idCol;

    @FXML
    private TableColumn<penaltyTable, String> nameCol;

    @FXML
    private TableColumn<penaltyTable, String> severityCol;

    @FXML
    private TableColumn<penaltyTable, String> descriptionCol;

    @FXML
    private TableColumn<penaltyTable, String> durationCol;

    @FXML
    private TableColumn<penaltyTable, String> completedCol;

    @FXML
    private TableColumn<penaltyTable, String> statusCol;

    @FXML
    private TableColumn<penaltyTable, String> dateCol;

    @FXML
    private MenuItem updateBtn;

    @FXML
    private MenuItem deleteBtn;

    @FXML
    private JFXButton newBtn;

    @FXML
    private JFXComboBox<String> searchComboBox;

    @FXML
    private JFXTextField searchTxt;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXButton refreshBtn;

    // declare var
    private DatabaseAccessObject dao;
    private String query;
    // end of declare var

    // initialize itself

    // end of initialize itself

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // init class
        dao = new DatabaseAccessObject();
        // end of init class

        // init methods
        try {
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // end of init methods

        // event buttons

        // end of event buttons
    }

    //  init
    public void initTable(){
        idCol.setCellValueFactory(cell -> cell.getValue().studIdProperty().asObject());
        nameCol.setCellValueFactory(cell -> cell.getValue().studNameProperty());
        severityCol.setCellValueFactory(cell -> cell.getValue().severityProperty());
        descriptionCol.setCellValueFactory(cell -> cell.getValue().descriptionProperty());
        durationCol.setCellValueFactory(cell -> cell.getValue().durationProperty());
        completedCol.setCellValueFactory(cell -> cell.getValue().completedProperty());
        statusCol.setCellValueFactory(cell -> cell.getValue().statusProperty());
        dateCol.setCellValueFactory(cell -> cell.getValue().dateProperty());
    }
    public void refreshTable() throws SQLException {
        initTable();
        query = "select so.student_key,s.student_name, p.penalty_severity, p.penalty_description, p.penalty_duration, p.penalty_completed, p.penalty_status, p.penalty_date from penalty_tbl as p inner join student_offense_tbl as so on p.student_offense_key = so.std_offense_id inner join student_tbl as s on so.student_key = s.student_id";
        tableView.setItems(dao.getPenaltyData(query));
    }
    // end init

    // custom methods

    // end of custom methods

    // getters

    // end of getters

}
