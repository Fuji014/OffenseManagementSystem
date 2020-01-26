package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.tables.scheduleTable;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class SchedulePageController implements Initializable {


    @FXML
    private TableView<scheduleTable> scheduleTableView;

    @FXML
    private TableColumn<scheduleTable, Integer> idCol;

    @FXML
    private TableColumn<scheduleTable, String> deptnameCol;

    @FXML
    private TableColumn<scheduleTable, String> modayCol;

    @FXML
    private TableColumn<scheduleTable, String> tuesdayCol;

    @FXML
    private TableColumn<scheduleTable, String> wednesdayCol;

    @FXML
    private TableColumn<scheduleTable, String> thurdayCol;

    @FXML
    private TableColumn<scheduleTable, String> fridayCol;

    @FXML
    private MenuItem updateBtn;

    @FXML
    private MenuItem deleteBtn;

    @FXML
    private JFXComboBox<String> searchComboBox;

    @FXML
    private JFXButton refreshBtn;

    @FXML
    private JFXButton newBtn;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXTextField searchTxt;

    // declare var below
    private DatabaseAccessObject dao;
    private MainController mc;
    private static SchedulePageController instance;
    private String query,schedDeptName,schedMonday,schedTuesday,schedWednesday,schedThursday,schedFriday;
    private int id;
    private boolean isConfirm;
    // end of declare var below

    // initialize itself
    public SchedulePageController(){
        this.instance = this;
    }
    public static SchedulePageController getSchedulePageController(){
        return instance;
    }
    // initialize itself

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchComboBox.getItems().addAll("SCHEDULE_ID","DEPT_NAME","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY");
        // initialize class
        dao = new DatabaseAccessObject();
        mc = new MainController();
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
        newBtn.setOnAction(event -> {
            try {
                mc.createPage(null, "/views/ScheduleAdd.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        updateBtn.setOnAction(event -> {
            updateEvent();
            try {
                mc.createPage(null, "/views/ScheduleEdit.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        refreshBtn.setOnAction(event -> {
            try {
                refreshTable();
            } catch (SQLException e) {
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
        // end of event

    }

    // init
    public void initTable(){
        idCol.setCellValueFactory(cell -> cell.getValue().schedIdProperty().asObject());
        deptnameCol.setCellValueFactory(cell -> cell.getValue().deptNameProperty());
        modayCol.setCellValueFactory(cell -> cell.getValue().schedMondayProperty());
        tuesdayCol.setCellValueFactory(cell -> cell.getValue().schedTuesdayProperty());
        wednesdayCol.setCellValueFactory(cell -> cell.getValue().schedWednesdayProperty());
        thurdayCol.setCellValueFactory(cell -> cell.getValue().schedThursdayProperty());
        fridayCol.setCellValueFactory(cell -> cell.getValue().schedFridayProperty());
    }
    public void refreshTable() throws SQLException {
        initTable();
        query = "select s.schedule_id,d.dept_name,s.monday,s.tuesday,s.wednesday,s.thursday,s.friday from schedule_tbl as s inner join department_tbl as d on s.dept_key = d.id order by s.schedule_id desc";
        scheduleTableView.setItems(dao.getScheduleData(query));
    }
    public void deleteEvent() throws SQLException {
        alertConfirmation(null, "Are you sure you want to delete?");
        if(isConfirm) {
            scheduleTable selected = scheduleTableView.getSelectionModel().getSelectedItem();
            id = selected.schedIdProperty().get();
            query = "delete from schedule_tbl where schedule_id = "+id+"";
            try {
                dao.saveData(query);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                refreshTable();
            }

        }
    }
    public void updateEvent(){
        scheduleTable selected = scheduleTableView.getSelectionModel().getSelectedItem();
        id = selected.schedIdProperty().get();
        schedDeptName = selected.deptNameProperty().get();
        schedMonday = selected.schedMondayProperty().get();
        schedTuesday = selected.schedTuesdayProperty().get();
        schedWednesday = selected.schedWednesdayProperty().get();
        schedThursday = selected.schedThursdayProperty().get();
        schedFriday = selected.schedFridayProperty().get();

    }
    public void initSearch(){
        searchTxt.textProperty().addListener((ObservableValue<? extends String> ob, String oldV, String newV) -> {
            String forSearchComboxValue = searchComboBox.getSelectionModel().getSelectedItem();
            initTable();
            query = "select s.schedule_id,d.dept_name,s.monday,s.tuesday,s.wednesday,s.thursday,s.friday from schedule_tbl as s inner join department_tbl as d on s.dept_key = d.id where "+forSearchComboxValue+" like '%"+newV+"%'";
            try {
                scheduleTableView.setItems(dao.getScheduleData(query));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    // end of init

    // custom methods
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

    public String getSchedDeptName() {
        return schedDeptName;
    }

    public String getSchedMonday() {
        return schedMonday;
    }

    public String getSchedTuesday() {
        return schedTuesday;
    }

    public String getSchedWednesday() {
        return schedWednesday;
    }

    public String getSchedThursday() {
        return schedThursday;
    }

    public String getSchedFriday() {
        return schedFriday;
    }

    public int getId() {
        return id;
    }
}
