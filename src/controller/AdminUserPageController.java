package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.tables.adminUserTable;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminUserPageController implements Initializable {

    @FXML
    private JFXButton newBtn;

    @FXML
    private JFXButton refreshBtn;

    @FXML
    private TableView<adminUserTable> tableView;

    @FXML
    private MenuItem updateBtn1;

    @FXML
    private MenuItem deleteBtn1;

    @FXML
    private TableColumn<adminUserTable, Integer> idCol;

    @FXML
    private TableColumn<adminUserTable, String> nameCol;

    @FXML
    private TableColumn<adminUserTable, String> contactCol;

    @FXML
    private TableColumn<adminUserTable, String> usernameCol;

    @FXML
    private TableColumn<adminUserTable, String> datecreatedCol;

    @FXML
    private TableColumn<adminUserTable, String> deptCol;

    @FXML
    private JFXComboBox<String> searchComboBox;

    @FXML
    private JFXTextField searchTxt;





    // Declare var below;
    private static AdminUserPageController instance;
    private String query;
    private DatabaseAccessObject dao;
    private MainController mc;
    private String name,contact,deptname,username,datecreated;
    private int id;
    private boolean isConfirm;
    


    // create instance
    public AdminUserPageController(){
        this.instance = this;
    }
    public static AdminUserPageController getSettingsPageController(){
        return instance;
    }
    // end of instance

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize object                                                                                  
        searchComboBox.getItems().addAll("NAME","CONTACT","DEPARTMENT","USERNAME");
        // initialize classes
        dao = new DatabaseAccessObject();
        mc = new MainController();
        // end of initialization
        refreshTable();
        initSearch();


        // event btn click
        newBtn.setOnAction(event -> {
            try {
                mc.createPage("Add User", "/views/AdminUserAdd.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        refreshBtn.setOnAction(event -> {
            refreshTable();
        });

        updateBtn1.setOnAction(event -> {
            updateEvent();
            try {
                mc.createPage(null, "/views/AdminUserEdit.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteBtn1.setOnAction(event -> {
            deleteEvent();
        });


    }

    // end of initialize

    public void initTable(){
        idCol.setCellValueFactory(cell -> cell.getValue().getAdminId().asObject());
        nameCol.setCellValueFactory(cell -> cell.getValue().getName());
        contactCol.setCellValueFactory(cell -> cell.getValue().getContact());
        deptCol.setCellValueFactory(cell -> cell.getValue().getDeptname());
        usernameCol.setCellValueFactory(cell -> cell.getValue().getUsername());
        datecreatedCol.setCellValueFactory(cell -> cell.getValue().getDatecreated());
    }

    public void refreshTable(){
        initTable();
        query = "select a.id,a.name,a.contact,d.dept_name,a.username,a.created_at from admin_tbl as a inner join department_tbl as d on a.department_key = d.id order by id desc";
        tableView.setItems(dao.getAdminData(query));
    }

    public void updateEvent(){
        adminUserTable selected = tableView.getSelectionModel().getSelectedItem();
        id = selected.getAdminId().get();
        name = selected.getName().get();
        contact = selected.getContact().get();
        deptname = selected.getDeptname().get();
        username = selected.getUsername().get();
        datecreated = selected.getDatecreated().get();
    }

    public void deleteEvent(){
        alertConfirmation(null, "Are you sure you want to delete?");
        if(isConfirm){
            adminUserTable selected = tableView.getSelectionModel().getSelectedItem();
            id = selected.getAdminId().get();
            query = "delete from admin_tbl where id = '"+id+"'";
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
         searchTxt.textProperty().addListener((ObservableValue<? extends String>ob,String oldV, String newV) ->{
             String forSearchComboxValue = searchComboBox.getSelectionModel().getSelectedItem();
             initTable();
             query = "select a.id,a.name,a.contact,d.dept_name,a.username,a.created_at from admin_tbl as a inner join department_tbl as d on a.department_key = d.id where "+forSearchComboxValue+" like '%"+newV+"%'";
             tableView.setItems(dao.getAdminSearch(query));
         });
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

    // getters for info to fill in edit box ;
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getContact(){
        return contact;
    }
    public String getDeptname(){return deptname;}
    public String getUsername(){
        return username;
    }
    public String getDatecreated(){
        return datecreated;
    }


}
