package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.tables.scheduleTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    private String query;
    // end of declare var below

    // initialize itself
    public void SchedulePageController(){
        this.instance = this;
    }
    public static SchedulePageController getSchedulePageController(){
        return instance;
    }
    // initialize itself

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
                mc.createPage(null, "/views/ScheduleAdd.fxml");
            } catch (IOException e) {
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
        query = "select s.schedule_id,d.dept_name,s.monday,s.tuesday,s.wednesday,s.thursday,s.friday from schedule_tbl as s inner join department_tbl as d on s.schedule_id = d.id order by s.schedule_id desc";
        scheduleTableView.setItems(dao.getScheduleData(query));
    }
    // end of init

    // custom methods

    // end of custom methods

    // getters

}
