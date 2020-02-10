package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.tables.StudentOffenseSearchOffenseTable;
import controller.tables.StudentOffenseSearchStudentTable;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentOffenseSearchOffenseController implements Initializable {

    @FXML
    private Label closeBtn;

    @FXML
    private JFXComboBox<String> searchComboBox;

    @FXML
    private JFXTextField searchTxt;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private TableView<StudentOffenseSearchOffenseTable> srchstudentTableView;

    @FXML
    private TableColumn<StudentOffenseSearchOffenseTable, String> descriptionCol;

    @FXML
    private TableColumn<StudentOffenseSearchOffenseTable, String> severityCol;

    @FXML
    private TableColumn<StudentOffenseSearchOffenseTable, String> deptCol;

    @FXML
    private TableColumn<StudentOffenseSearchOffenseTable, Integer> offenseId;

    @FXML
    private MenuItem selectBtn;

    // declare var below
    private DatabaseAccessObject dao;
    private String query,studId;
    private int offId;
    private int departmentId = HomePageController.getHomePageController().departmentId;
    // end of var below

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchComboBox.getItems().addAll("OFFENSE_DESCRIPTION","OFFENSE_SEVERITY","DEPT_NAME");
        // initialize class
        dao = new DatabaseAccessObject();
        // end of initialize class

        // methods
        initSearch();
        try {
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // end of methods

        // event buttons
        searchBtn.setOnAction(event -> {
            try {
                searchActivAndDeact();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        selectBtn.setOnAction(event -> {
            selectEvent();
            this.closeBtn.getScene().getWindow().hide();
        });
        closeBtn.setOnMouseClicked(event -> {
            this.closeBtn.getScene().getWindow().hide();
        });
        // end of event buttons

    }

    // init
    public void initTable(){
        descriptionCol.setCellValueFactory(cell -> cell.getValue().offeseDescriptionProperty());
        severityCol.setCellValueFactory(cell -> cell.getValue().offeseSeverityProperty());
        deptCol.setCellValueFactory(cell -> cell.getValue().deptNameProperty());
        offenseId.setCellValueFactory(cell -> cell.getValue().OffenseIdProperty().asObject());
    }
    public void refreshTable() throws SQLException {
        initTable();
        query = "select o.offense_description,o.offense_severity,d.dept_name,o.id from offense_tbl as o inner JOIN department_tbl as d on  o.dept_key = d.id where dept_key = "+departmentId+" order by o.id desc";
        srchstudentTableView.setItems(dao.getStudentOffenseOffenseSearchData(query));
    }
    public void initSearch(){ // init search
        searchTxt.textProperty().addListener((ObservableValue<? extends String> ob, String oldV, String newV) -> {
            String forSearchComboxValue = searchComboBox.getSelectionModel().getSelectedItem();
            initTable();
            query = "select o.offense_description,o.offense_severity,d.dept_name,o.id from offense_tbl as o inner JOIN department_tbl as d on o.dept_key = d.id where "+forSearchComboxValue+" like '%"+newV+"%' where dept_key = "+departmentId+" order by o.id desc";
            try {
                srchstudentTableView.setItems(dao.getStudentOffenseOffenseSearchData(query));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    public void selectEvent(){ // select event button
        StudentOffenseSearchOffenseTable selected = srchstudentTableView.getSelectionModel().getSelectedItem();
         offId = selected.OffenseIdProperty().get();
        StudentOffenseAddController.getStudentOffenseAddController().offensenameTxt.setText(Integer.toString(offId));
    }
    // end of init

    // custom methods
    public void searchActivAndDeact() throws SQLException {
        if(searchComboBox.isDisable() && searchTxt.isDisable()){
            searchComboBox.setDisable(false);
            searchTxt.setDisable(false);
        }else{
            searchComboBox.setDisable(true);
            searchTxt.setDisable(true);
            searchComboBox.getSelectionModel().clearSelection();
            searchTxt.setText("");
            refreshTable();
        }
    }

    // end of custom methods

}
