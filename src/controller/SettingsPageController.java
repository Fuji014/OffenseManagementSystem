package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.tables.adminUserTable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class SettingsPageController implements Initializable {
    @FXML
    private JFXButton refreshBtn;

    @FXML
    private TableView<adminUserTable> tableView;

    @FXML
    private MenuItem updateBtn;

    @FXML
    private MenuItem deleteBtn;

    @FXML
    private TableColumn<adminUserTable, Integer> idCol;

    @FXML
    private TableColumn<adminUserTable, String> firstnameCol;

    @FXML
    private TableColumn<adminUserTable, String> lastnameCol;

    @FXML
    private TableColumn<adminUserTable, String> miCol;

    @FXML
    private TableColumn<adminUserTable, String> contactCol;

    @FXML
    private TableColumn<adminUserTable, String> usernameCol;

    @FXML
    private TableColumn<adminUserTable, String> datecreatedCol;

    @FXML
    private JFXButton newBtn;

    @FXML
    private JFXTextField searchTxt;

    @FXML
    private JFXComboBox<String> searchComboBox;





    // Declare var below;
    private static SettingsPageController instance;
    private String query;
    private DatabaseAccessObject dao;
    private MainController mc;
    private String firstname,lastname,mi,contact,username,datecreated;
    private int id;
    private boolean isConfirm;
    


    // create instance
    public SettingsPageController(){
        this.instance = this;
    }
    public static SettingsPageController getSettingsPageController(){
        return instance;
    }
    // end of instance

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize object                                                                                  
        searchComboBox.getItems().addAll("ID","FIRSTNAME","LASTNAME","MI","CONTACT","USERNAME","CREATED_AT");
        // initialize classes
        dao = new DatabaseAccessObject();
        mc = new MainController();
        // end of initialization
        refreshTable();
        initSearch();


        // event btn click
        newBtn.setOnAction(event -> {
            try {
                mc.createPage("Add User", "/views/AddAdminUser.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        refreshBtn.setOnAction(event -> {
            refreshTable();
        });

        updateBtn.setOnAction(event -> {
            updateEvent();
            try {
                mc.createPage(null, "/views/EditAdminUser.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteBtn.setOnAction(event -> {
            deleteEvent();
        });


    }

    // end of initialize

    public void initTable(){
        idCol.setCellValueFactory(cell -> cell.getValue().getAdminId().asObject());
        firstnameCol.setCellValueFactory(cell -> cell.getValue().getFirstname());
        lastnameCol.setCellValueFactory(cell -> cell.getValue().getLastname());
        miCol.setCellValueFactory(cell -> cell.getValue().getMi());
        contactCol.setCellValueFactory(cell -> cell.getValue().getContact());
        usernameCol.setCellValueFactory(cell -> cell.getValue().getUsername());
        datecreatedCol.setCellValueFactory(cell -> cell.getValue().getDatecreated());
    }

    public void refreshTable(){
        initTable();
        query = "select id,firstname,lastname,mi,contact,username,created_at from admin_tbl order by id desc;";
        tableView.setItems(dao.getAdminData(query));
    }

    public void updateEvent(){
        adminUserTable selected = tableView.getSelectionModel().getSelectedItem();
        id = selected.getAdminId().get();
        firstname = selected.getFirstname().get();
        lastname = selected.getLastname().get();
        mi = selected.getMi().get();
        contact = selected.getContact().get();
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
             query = "select id,firstname,lastname,mi,contact,username,created_at from admin_tbl where "+forSearchComboxValue+" like '%"+newV+"%'";
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
    public String getFirstname(){
        return firstname;
    }
    public String getLastname(){
        return lastname;
    }
    public String getMi(){
        return mi;
    }
    public String getContact(){
        return contact;
    }
    public String getUsername(){
        return username;
    }
    public String getDatecreated(){
        return datecreated;
    }


}