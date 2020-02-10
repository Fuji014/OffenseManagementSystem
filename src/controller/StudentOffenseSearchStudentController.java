package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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

public class StudentOffenseSearchStudentController implements Initializable {

    @FXML
    private Label closeBtn;

    @FXML
    private JFXComboBox<String> searchComboBox;

    @FXML
    private JFXTextField searchTxt;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private TableView<StudentOffenseSearchStudentTable> srchstudentTableView;

    @FXML
    private TableColumn<StudentOffenseSearchStudentTable, Integer> studidCol;

    @FXML
    private TableColumn<StudentOffenseSearchStudentTable, String> studnameCol;

    @FXML
    private MenuItem selectBtn;

    // declare var below
    private int departmentId = HomePageController.getHomePageController().departmentId;
    private DatabaseAccessObject dao;
    private String query,studName;
    private Integer studId;
    private static StudentOffenseSearchStudentController instance;

    // end of declare var below

    // intialize itself
    public StudentOffenseSearchStudentController(){
        this.instance = this;
    }
    public static StudentOffenseSearchStudentController getStudentOffenseSearchStudentController(){
        return instance;
    }
    // end of initialize itself

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchComboBox.getItems().addAll("STUDENT_ID","STUDENT_NAME");
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

        // event button
        closeBtn.setOnMouseClicked(event -> {
            this.closeBtn.getScene().getWindow().hide();
        });
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

        // end of event button

    }

    // init
    public void initTable(){ // init table
        studidCol.setCellValueFactory(cell -> cell.getValue().studIdProperty().asObject());
        studnameCol.setCellValueFactory(cell -> cell.getValue().studNameProperty());
    }
    public void refreshTable() throws SQLException { // refresh table
        initTable();
        query = "select student_id,student_name from student_tbl where student_department = "+departmentId+"";
        srchstudentTableView.setItems(dao.getStudentOffenseStudentSearchData(query));
    }
    public void initSearch(){ // init search
        searchTxt.textProperty().addListener((ObservableValue<? extends String> ob, String oldV, String newV) -> {
            String forSearchComboxValue = searchComboBox.getSelectionModel().getSelectedItem();
            initTable();
            query = "select student_id,student_name from student_tbl where "+forSearchComboxValue+" like '%"+newV+"%' and student_department = "+departmentId+"";
            try {
                srchstudentTableView.setItems(dao.getStudentOffenseStudentSearchData(query));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    public void selectEvent(){ // select event button
        StudentOffenseSearchStudentTable selected = srchstudentTableView.getSelectionModel().getSelectedItem();
        studId = selected.studIdProperty().get();
        studName = selected.studNameProperty().get();
        StudentOffenseAddController.getStudentOffenseAddController().studentidTxt.setText(Integer.toString(studId));
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
