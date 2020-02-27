package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.tables.offenseTable;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class OffensePageController implements Initializable {

    @FXML
    private TableView<offenseTable> offenseTableView;

    @FXML
    private TableColumn<offenseTable, Integer> idCol;

    @FXML
    private TableColumn<offenseTable, String> descriptionCol;

    @FXML
    private TableColumn<offenseTable, String> severityCol;

    @FXML
    private TableColumn<offenseTable, String> deptnameCol;

    @FXML
    private TableColumn<offenseTable, String> sanctionCol;

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
    private JFXTextField searchTxt;

    @FXML
    private JFXButton refreshBtn;

    @FXML
    private JFXComboBox<String> searchComboBox;

    // declare below
    private DatabaseAccessObject dao;
    private _pushNotification _pushNotif;
    private MainController mc;
    private static OffensePageController instance;
    private String query,offenseDescription,offenseSeverity,deptName,offenseSanction;
    private int id;
    private boolean isConfirm;
    private int departmentId = HomePageController.getHomePageController().departmentId;
    // end of declare var below

    // initialize itself
    public OffensePageController(){
        this.instance = this;
    }
    public static OffensePageController getOffensePageController(){
        return instance;
    }
    // end of initialize itself



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchComboBox.getItems().addAll("ID","OFFENSE SEVERITY","OFFENSE SANCTION");
        // initialize class
        dao = new DatabaseAccessObject();
        mc = new MainController();
        _pushNotif = new _pushNotification();
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
                mc.createPage(null, "/views/OffenseAdd.fxml");
            } catch (IOException e) {
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
        updateBtn.setOnAction(event -> {
            updateEvent();
            try {
                mc.createPage(null,"/views/OffenseEdit.fxml");
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
        // end of event button
    }

    // init
    public void initTable(){
        idCol.setCellValueFactory(cell -> cell.getValue().offenseIdProperty().asObject());
        descriptionCol.setCellValueFactory(cell -> cell.getValue().offenseDescProperty());
        severityCol.setCellValueFactory(cell -> cell.getValue().offenseSeverityProperty());
        deptnameCol.setCellValueFactory(cell -> cell.getValue().deptNameProperty());
        sanctionCol.setCellValueFactory(cell -> cell.getValue().offenseSactionProperty());

    }
    public void refreshTable() throws SQLException {
        initTable();
        query = "select o.id, o.offense_description, o.offense_severity, d.dept_name, o.offense_sanction from offense_tbl as o inner join department_tbl as d on o.dept_key = d.id where dept_key = "+departmentId+" order by o.id desc";
        offenseTableView.setItems(dao.getOffenseData(query));
    }
    public void deleteEvent() throws SQLException {
        alertConfirmation(null, "Are you sure you want to delete?");
        if(isConfirm) {
            offenseTable selected = offenseTableView.getSelectionModel().getSelectedItem();
            id = selected.offenseIdProperty().get();
            query = "delete from offense_tbl where id = "+id+"";
            try {
                dao.saveData(query);
            }catch (Exception e){
                e.printStackTrace();
                _pushNotif.failed("Delete Failed", "Failed to Delete ID Number: "+id+" "+e);
            }finally {
                refreshTable();
                _pushNotif.success("Delete Success","Successfully Deleted ID Number: "+id);
            }
        }
    }
    public void updateEvent(){
        offenseTable selected = offenseTableView.getSelectionModel().getSelectedItem();
        id = selected.offenseIdProperty().get();
        offenseDescription = selected.offenseDescProperty().get();
        offenseSeverity = selected.offenseSeverityProperty().get();
        deptName = selected.deptNameProperty().get();
        offenseSanction = selected.offenseSactionProperty().get();
    }
    public void initSearch(){
        searchTxt.textProperty().addListener((ObservableValue<? extends String> ob, String oldV, String newV) -> {
            String forSearchComboxValue = searchComboBox.getSelectionModel().getSelectedItem();
            switch (forSearchComboxValue){
                case "ID":
                    forSearchComboxValue = "o.id";
                    break;
                case "OFFENSE SEVERITY":
                    forSearchComboxValue = "OFFENSE_SEVERITY";
                    break;
                case "OFFENSE SANCTION":
                    forSearchComboxValue = "OFFENSE_SANCTION";
                    break;
            }
            initTable();
            query = "select o.id,o.offense_description,o.offense_severity,d.dept_name,o.offense_sanction from offense_tbl as o inner join department_tbl as d on o.dept_key = d.id  where "+forSearchComboxValue+" like '%"+newV+"%' and dept_key = "+departmentId+" order by id desc";
            try {
                offenseTableView.setItems(dao.getOffenseData(query));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    // end of init

    // custom
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
    // end of custom

    // getters
    public String getOffenseSanction(){
        return offenseSanction;
    }

    public String getOffenseDescription() {
        return offenseDescription;
    }

    public String getOffenseSeverity() {
        return offenseSeverity;
    }

    public String getDeptName() {
        return deptName;
    }

    public int getId() {
        return id;
    }
}
