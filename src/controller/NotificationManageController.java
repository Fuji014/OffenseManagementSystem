package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controller.tables.notificationmanageTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class NotificationManageController implements Initializable {

    @FXML
    private TableColumn<notificationmanageTable, Integer> studidCol;

    @FXML
    private TableColumn<notificationmanageTable, String> descriptionCol;

    @FXML
    private TableColumn<notificationmanageTable, String> statusCol;

    @FXML
    private TableColumn<notificationmanageTable, Integer> idCol;

    @FXML
    private MenuItem deleteBtn;

    @FXML
    private MenuItem viewBtn;

    @FXML
    private JFXTextField studidTxt;

    @FXML
    private JFXTextArea descriptionTxt;

    @FXML
    private JFXTextField searchTxt;

    @FXML
    private JFXTextField statusTxt;

    @FXML
    private TableView<notificationmanageTable> tableView;

    @FXML
    private AnchorPane home;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXButton refreshBtn;

    // declare var below
    private DatabaseAccessObject dao;
    private String query;
    private ResultSet rs;
    private int departmentId = HomePageController.getHomePageController().departmentId;
    // end of declar var

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // init class
        dao = new DatabaseAccessObject();
        // end of init class

        // init methods
        refreshTable();
        // end of init methods

        // event buttons
        refreshBtn.setOnAction(event -> {
            clearFields();
            refreshTable();
        });
        viewBtn.setOnAction(event -> {
            displayNotificationInfo();
        });
        searchBtn.setOnAction(event -> {
            initSearch();
        });
        // end of event btns

    }

    // init
    public void initSearch(){
        if(searchTxt.getText().isEmpty()){
            searchTxt.setStyle("-jfx-unfocus-color: red;");
        }else{
            initTable();
            query = "select id,studentNumber,description, status from notification_tbl where department_key = "+departmentId+" and studentNumber = "+searchTxt.getText()+"";
            try {
                tableView.setItems(dao.getnotificationmanageTable(query));
                searchTxt.setStyle("-jfx-unfocus-color: #b1b1b1;");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void displayNotificationInfo(){
        notificationmanageTable select = tableView.getSelectionModel().getSelectedItem();
        studidTxt.setText(Integer.toString(select.notifIDProperty().get()));
        descriptionTxt.setText(select.notifDescriptionProperty().get());
        statusTxt.setText(select.notifStatusProperty().get());

    }
    public void initTable(){
        idCol.setCellValueFactory(cell -> cell.getValue().notifIDProperty().asObject());
        studidCol.setCellValueFactory(cell -> cell.getValue().notifStudIDProperty().asObject());
        descriptionCol.setCellValueFactory(cell -> cell.getValue().notifDescriptionProperty());
        statusCol.setCellValueFactory(cell -> cell.getValue().notifStatusProperty());
    }
    public void refreshTable(){
        query = "select id,studentNumber,description, status from notification_tbl where department_key = "+departmentId+"";
        try {
            initTable();
            tableView.setItems(dao.getnotificationmanageTable(query));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // end of init

    // methods
    public void clearFields(){
        studidTxt.setText("");
        descriptionTxt.setText("");
        statusTxt.setText("");

    }
    // end methods
}
