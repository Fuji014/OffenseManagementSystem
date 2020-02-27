package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controller.tables.attendancerecordsTable;
import controller.tables.notificationmanageTable;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class AttendancerecordsPageController implements Initializable {

    @FXML
    private TableView<attendancerecordsTable> tableView;

    @FXML
    private TableColumn<attendancerecordsTable, Integer> idCol;

    @FXML
    private TableColumn<attendancerecordsTable, Integer> studIDCol;

    @FXML
    private TableColumn<attendancerecordsTable, String> dateCol;

    @FXML
    private TableColumn<attendancerecordsTable, String> timeinCol;

    @FXML
    private TableColumn<attendancerecordsTable, String> timeoutCol;

    @FXML
    private TableColumn<attendancerecordsTable, String> loginremarksCol;

    @FXML
    private TableColumn<attendancerecordsTable, String> logoutremarksCol;

    @FXML
    private MenuItem selectBtn;

    @FXML
    private MenuItem excuseBtn;

    @FXML
    private MenuItem unexcuseBtn;

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
    private JFXButton refreshBtn;

    @FXML
    private JFXComboBox<String> searchfiltersComboBox;

    @FXML
    private JFXTextField searchtableTxt;

    // custom var
    private DatabaseAccessObject dao;
    private String query;
    private int id;
    private int departmentId = HomePageController.getHomePageController().departmentId;
    // end of custom var

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchfiltersComboBox.getItems().addAll("STUDENT KEY","DATE");
        // init class
        dao = new DatabaseAccessObject();
        // end of init class

        // init methods
        refreshTable();
        initSearch();
        // end of init methods

        // event buttons
        selectBtn.setOnAction(event -> {
            displayAttendanceRecordsInfo();
        });
        refreshBtn.setOnAction(event -> {
            clearFields();
            refreshTable();
        });
        excuseBtn.setOnAction(event -> {
            excuseEvent();
        });
        unexcuseBtn.setOnAction(event -> {
            unexcuseEvent();
        });
        // end of event buttons

    }

    // init
    public void initTable(){
        idCol.setCellValueFactory(cell -> cell.getValue().attendaceIDProperty().asObject());
        studIDCol.setCellValueFactory(cell -> cell.getValue().student_keyProperty().asObject());
        dateCol.setCellValueFactory(cell -> cell.getValue().dateProperty());
        timeinCol.setCellValueFactory(cell -> cell.getValue().timeinProperty());
        timeoutCol.setCellValueFactory(cell -> cell.getValue().timeoutProperty());
        loginremarksCol.setCellValueFactory(cell -> cell.getValue().loginremarksProperty());
        logoutremarksCol.setCellValueFactory(cell -> cell.getValue().logoutremarksProperty());
    }
    public void refreshTable(){
        query = "select r.* from record_tbl as r inner join student_tbl as s on r.student_key = s.student_id where s.student_department = "+departmentId+" and date = '"+getDate()+"'";
        System.out.println(getDate());
        initTable();
        try {
            tableView.setItems(dao.getAttendancerecordsTable(query));
        }catch (Exception e){
            e.printStackTrace();
            _pushNotification.get_PushNotification().failed("Error","Err"+e);
        }
    }
    public void initSearch(){
        searchtableTxt.textProperty().addListener((ObservableValue<? extends String> ob, String oldV, String newV) -> {
            String forSearchComboxValue = searchfiltersComboBox.getSelectionModel().getSelectedItem();
            switch (forSearchComboxValue){
                case "STUDENT KEY":
                    forSearchComboxValue = "student_key";
                    break;
                case "DATE":
                    forSearchComboxValue = "date";
                    break;
            }
            initTable();
            query = "select r.* from record_tbl as r inner join student_tbl as s on r.student_key = s.student_id where s.student_department = "+departmentId+" and "+forSearchComboxValue+" like '%"+newV+"%'";
           try {
                tableView.setItems(dao.getAttendancerecordsTable(query));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    public void displayAttendanceRecordsInfo(){
        attendancerecordsTable select = tableView.getSelectionModel().getSelectedItem();
        studentIDTxt.setText(Integer.toString(select.student_keyProperty().get()));
        dateTxt.setText(select.dateProperty().get());
        timeinTxt.setText(select.timeinProperty().get());
        timeoutTxt.setText(select.timeoutProperty().get());
        loginremarksTxt.setText(select.loginremarksProperty().get());
        logoutremarksTxt.setText(select.logoutremarksProperty().get());
    }
    public void clearFields(){
        studentIDTxt.setText("");
        dateTxt.setText("");
        timeinTxt.setText("");
        timeoutTxt.setText("");
        loginremarksTxt.setText("");
        logoutremarksTxt.setText("");
    }
    public void excuseEvent(){
        attendancerecordsTable select = tableView.getSelectionModel().getSelectedItem();
        id = select.attendaceIDProperty().get();
        query = "update record_tbl set time_out = 'excuse' where id = "+id+"";
        try {
            dao.saveData(query);
        }catch (Exception e){
            e.printStackTrace();
            _pushNotification.get_PushNotification().failed("Error While Updating","Err"+e);
        }finally {
            refreshTable();
            clearFields();
            _pushNotification.get_PushNotification().success("Update Success!","Update Success with id number "+id);
        }
    }
    public void unexcuseEvent(){
        attendancerecordsTable select = tableView.getSelectionModel().getSelectedItem();
        id = select.attendaceIDProperty().get();
        query = "update record_tbl set time_out = ' ' where id = "+id+"";
        try {
            dao.saveData(query);
        }catch (Exception e){
            e.printStackTrace();
            _pushNotification.get_PushNotification().failed("Error While Updating","Err"+e);
        }finally {
            refreshTable();
            clearFields();
            _pushNotification.get_PushNotification().success("Update Success!","Update Success with id number "+id);
        }
    }
    public String getDate(){
        Date date = new Date();
        String logDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
        return logDate;
    }
    // end of init

    // custom methods

    // end of custom methods

    // getters

    // end of getters
}
