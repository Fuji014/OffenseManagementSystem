package controller;

import controller.tables.departmentTable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class DepartmentPageController implements Initializable {

    @FXML
    private JFXComboBox<String> searchComboBox;

    @FXML
    private JFXTextField searchTxt;

    @FXML
    private JFXButton refreshBtn;

    @FXML
    private TableView<departmentTable> deptTableView;

    @FXML
    private TableColumn<departmentTable, Integer> deptidCol;

    @FXML
    private TableColumn<departmentTable, String> deptnameCol;

    @FXML
    private TableColumn<departmentTable, String> deptdescriptionCol;

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

    // declare var below;
    private DatabaseAccessObject dao;
    private MainController mc;
    private static DepartmentPageController instance;
    private String query,deptName,deptDescription;
    private int deptId,id;
    private boolean isConfirm;
    // end of var;

    // initialize itself
    public DepartmentPageController(){
        this.instance = this;
    }
    public static DepartmentPageController getDepartmentPageController(){
        return instance;
    }
    // end of initialize itself


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchComboBox.getItems().addAll("ID","DEPT_NAME","DEPT_DESCRIPTION");

        // initialize class
        dao = new DatabaseAccessObject();
        mc = new MainController();
        // end of initialize class

        // initialize methods
        initSearch();
        try {
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // end of initialize methods

        // event button
        newBtn.setOnAction(event -> {
            try {
                mc.createPage(null, "/views/DepartmentAdd.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        updateBtn.setOnAction(event -> {
            initUpdate();
            System.out.println(getDeptName());
            try {
                mc.createPage(null, "/views/DepartmentEdit.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        deleteBtn.setOnAction(event -> {
            try {
                initDelete();
            } catch (SQLException e) {
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
        cancelBtn.setOnAction(event -> {

        });
        // end of event button
    }



    // init methods
    public void initTable(){
        deptidCol.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
        deptnameCol.setCellValueFactory(cell -> cell.getValue().deptNameProperty());
        deptdescriptionCol.setCellValueFactory(cell -> cell.getValue().deptDescriptionProperty());
    }
    public void refreshTable() throws SQLException {
        initTable();
        query = "select * from department_tbl order by id desc";
        deptTableView.setItems(dao.getDepartmentData(query));
    }
    public void initUpdate(){
        departmentTable selected = deptTableView.getSelectionModel().getSelectedItem();
        deptId = selected.idProperty().get();
        deptName = selected.deptNameProperty().get();
        deptDescription = selected.deptDescriptionProperty().get();

    }
    public void initDelete() throws SQLException {
        alertConfirmation(null, "Are you sure you want to delete?");
        if(isConfirm){
            departmentTable selected = deptTableView.getSelectionModel().getSelectedItem();
            id = selected.idProperty().get();
            query = "delete from department_tbl where id = '"+id+"'";
            try {
                dao.saveData(query);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                refreshTable();
            }
        }
    }
    public void initSearch(){
        searchTxt.textProperty().addListener((ObservableValue<? extends String> ob, String oldV, String newV) -> {
            String forSearchComboxValue = searchComboBox.getSelectionModel(

            ).getSelectedItem();
            initTable();
                query = "select * from department_tbl where "+forSearchComboxValue+" like '%"+newV+"%'";
            try {
                deptTableView.setItems(dao.getDepartmentData(query));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    // end of init methods

    // custom method
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
    // end of custom method

    // getters method
    public String getDeptName() {
        return deptName;
    }

    public String getDeptDescription() {
        return deptDescription;
    }

    public int getDeptId() {
        return deptId;
    }
}
