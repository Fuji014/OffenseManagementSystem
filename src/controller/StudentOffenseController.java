package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.tables.studentOffenseTable;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentOffenseController implements Initializable {

    @FXML
    private JFXComboBox<String> searchComboBox;

    @FXML
    private JFXTextField searchTxt;

    @FXML
    private JFXButton refreshBtn;

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

    @FXML
    private TableView<studentOffenseTable> studoffenseTableView;

    @FXML
    private TableColumn<studentOffenseTable, Integer> idCol;

    @FXML
    private TableColumn<studentOffenseTable, Integer> studidCol;

    @FXML
    private TableColumn<studentOffenseTable, Integer> offenseCol;

    @FXML
    private TableColumn<studentOffenseTable, String> severityCol;

    @FXML
    private TableColumn<studentOffenseTable, String> durationCol;

    @FXML
    private TableColumn<studentOffenseTable, String> completedCol;

    @FXML
    private TableColumn<studentOffenseTable, String> statusCol;

    @FXML
    private TableColumn<studentOffenseTable, String> countCol;

    @FXML
    private TableColumn<studentOffenseTable, String> remarksCol;

    @FXML
    private TableColumn<studentOffenseTable, String> dateCol;

    // declare var below
    private DatabaseAccessObject dao;
    private MainController mc;
    private String query,severity,duration,completed,status,count,remarks,date;
    public int id,student_key,offense_key,departmentId = HomePageController.getHomePageController().departmentId;
    private boolean isConfirm;
    private static StudentOffenseController instance;
    // end of declare var

    // initialize itself
    public StudentOffenseController(){
        this.instance = this;
    }
    public static StudentOffenseController getStudentOffenseController(){
        return instance;
    }
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
        updateBtn.setOnAction(event -> {
            editEvent();
            try {
                mc.createPage(null, "/views/StudentOffenseEdit.fxml");
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        deleteBtn.setOnAction(event -> {
            try {
                deleteEvent();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        refreshBtn.setOnAction(event -> {
            try {
                searchTxt.setStyle("-fx-text-inner-color: #000;");
                refreshTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        searchBtn.setOnAction(event -> {
            try {
                initSearch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        // end of event button

    }

    // init
    public void initTable(){
        idCol.setCellValueFactory(cell -> cell.getValue().main_idProperty().asObject());
        studidCol.setCellValueFactory(cell -> cell.getValue().student_keyProperty().asObject());
        offenseCol.setCellValueFactory(cell -> cell.getValue().offense_keyProperty().asObject());
        severityCol.setCellValueFactory(cell -> cell.getValue().severityProperty());
        durationCol.setCellValueFactory(cell -> cell.getValue().durationProperty());
        completedCol.setCellValueFactory(cell -> cell.getValue().completedProperty());
        statusCol.setCellValueFactory(cell -> cell.getValue().statusProperty());
        countCol.setCellValueFactory(cell -> cell.getValue().countProperty());
        remarksCol.setCellValueFactory(cell -> cell.getValue().remarksProperty());
        dateCol.setCellValueFactory(cell -> cell.getValue().dateProperty());
    }
    public void refreshTable() throws SQLException {
        initTable();
        query = "select so.* from student_offense_tbl as so inner join offense_tbl as o on so.offense_key = o.id where o.dept_key = "+departmentId+"";
        studoffenseTableView.setItems(dao.getStudentOffenseData(query));
    }

    // end of init

    // custom methods
    public  void editEvent(){
        studentOffenseTable selected = studoffenseTableView.getSelectionModel().getSelectedItem();
        id = selected.main_idProperty().get();
        student_key = selected.student_keyProperty().get();
        offense_key = selected.offense_keyProperty().get();
        severity = selected.severityProperty().get();
        duration = selected.durationProperty().get();
        completed = selected.completedProperty().get();
        status = selected.statusProperty().get();
        count = selected.countProperty().get();
        remarks = selected.remarksProperty().get();
        date = selected.dateProperty().get();

    }

    public void initSearch() throws SQLException {
            if(searchTxt.getText() != ""){
                initTable();
                query = "select so.* from student_offense_tbl as so inner join offense_tbl as o on so.offense_key = o.id where o.dept_key = "+departmentId+" and student_key =  "+searchTxt.getText()+"";
                studoffenseTableView.setItems(dao.getStudentOffenseData(query));
            }else{
                searchTxt.setStyle("-fx-text-inner-color: red;");
            }
    }

    public void deleteEvent() throws SQLException {
        alertConfirmation(null, "Are you sure you want to delete?");
        if(isConfirm){
            studentOffenseTable selected = studoffenseTableView.getSelectionModel().getSelectedItem();
            id = selected.main_idProperty().get();
            query = "delete from student_offense_tbl where std_offense_id = '"+id+"'";
            System.out.println(query);
            try {
                dao.saveData(query);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                refreshTable();
            }
        }
    }

    public void alertConfirmation(String headerText, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(headerText);
        alert.setContentText(content);
        Optional<ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK){
            isConfirm = true;
        }else{
            isConfirm = false;
        }
    }

    // end of custom methods

    // getters

    public String getSeverity() {
        return severity;
    }

    public String getDuration() {
        return duration;
    }

    public String getCompleted() {
        return completed;
    }

    public String getStatus() {
        return status;
    }

    public String getCount() {
        return count;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public int getStudent_key() {
        return student_key;
    }

    public int getOffense_key() {
        return offense_key;
    }

    // end of getters
}
