package controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controller.tables.departmentTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class DepartmentTestController implements Initializable {

    @FXML
    private TableView<departmentTable> tableView;

    @FXML
    private TableColumn<departmentTable, Integer> idCol;

    @FXML
    private TableColumn<departmentTable, String> deptnameCol;

    @FXML
    private TableColumn<departmentTable, String> descriptionCol;

    @FXML
    private MenuItem selectBtn;

//    @FXML
//    private MenuItem updateBtn;

    @FXML
    private JFXTextField idTxt;

    @FXML
    private JFXTextField deptnameTxt;

    @FXML
    private JFXTextArea descriptionTxt;

    private DatabaseAccessObject dao;
    private String query;
    private int departmentId = HomePageController.getHomePageController().departmentId;
    private _pushNotification _pushNotif;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dao = new DatabaseAccessObject();
        _pushNotif = new _pushNotification();

        try {
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        selectBtn.setOnAction(event -> {
            selectEvent();
        });
    }
    public void initTable(){
        idCol.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
        deptnameCol.setCellValueFactory(cell -> cell.getValue().deptNameProperty());
        descriptionCol.setCellValueFactory(cell -> cell.getValue().deptDescriptionProperty());
    }
    public void refreshTable() throws SQLException {
        initTable();
        query = "select * from department_tbl order by id desc";
        tableView.setItems(dao.getDepartmentData(query));
    }
    public void selectEvent(){
        departmentTable select = tableView.getSelectionModel().getSelectedItem();
        idTxt.setText(Integer.toString(select.idProperty().get()));
        deptnameTxt.setText(select.deptNameProperty().get());
        descriptionTxt.setText(select.deptDescriptionProperty().get());
    }
    public void updateEvent(){
        departmentTable select = tableView.getSelectionModel().getSelectedItem();
        int id = 0;
        id = select.idProperty().get();
        query = "update department_tbl set dept_name = '"+deptnameTxt.getText()+"', dept_description = '"+descriptionTxt.getText()+"' where id = "+id+"";
        try {
            dao.saveData(query);
            _pushNotif.success("Update Success", "Successfully Updated");
            refreshTable();
        }catch (Exception e){
            e.printStackTrace();
            _pushNotif.failed("Update Failed", "Failed to Update "+e);
        }
    }
    public void clearFields(){
        idTxt.setText("");
        deptnameTxt.setText("");
        descriptionTxt.setText("");
    }
}
