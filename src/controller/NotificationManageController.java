package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controller.tables.notificationmanageTable;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import javafx.util.Duration;

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
    private JFXTextArea descriptionTxt;

    @FXML
    private JFXTextField searchTxt;

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
    private int id;
    private _alert _alert;
    private int departmentId = HomePageController.getHomePageController().departmentId;
    private static NotificationManageController instance;
    // end of declar var
    public NotificationManageController(){this.instance = this;}
    public static NotificationManageController getNotificationManageController(){return instance;}


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // init class
        dao = new DatabaseAccessObject();
        _alert = new _alert();
        // end of init class

        // init methods
        refreshTable();
//        initClock();
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
        deleteBtn.setOnAction(event -> {
            deleteEvent();
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
        id = select.notifIDProperty().get();
        descriptionTxt.setText(select.notifDescriptionProperty().get());
        NotifUpdate(id);
    }

    public void deleteEvent(){
        notificationmanageTable select = tableView.getSelectionModel().getSelectedItem();
        id=select.notifIDProperty().get();
        boolean isConfirm = false;
        isConfirm=  _alert.alertConfirmation(null, "Are you sure you want to delete ID Number? " +id);
        if(isConfirm){
            query = "DELETE FROM `notification_tbl` WHERE `notification_tbl`.`id` = "+id+"";
            try {
                dao.saveData(query);
            }catch (Exception e){
                e.printStackTrace();
                _pushNotification.get_PushNotification().failed("Delete Failed", "Failed To Delete ID Number: "+id);
            }finally {
                refreshTable();
                _pushNotification.get_PushNotification().success("Delete Success","Successfully deleted ID Number:" +id);
            }
        }

    }
    public void NotifUpdate(int id){
        query = "update notification_tbl set status = 'read' where id = "+id+"";
        try {
            dao.saveData(query);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            refreshTable();
        }

    }
    public void initTable(){
        idCol.setCellValueFactory(cell -> cell.getValue().notifIDProperty().asObject());
        studidCol.setCellValueFactory(cell -> cell.getValue().notifStudIDProperty().asObject());
        descriptionCol.setCellValueFactory(cell -> cell.getValue().notifDescriptionProperty());
        statusCol.setCellValueFactory(cell -> cell.getValue().notifStatusProperty());
    }
    public void refreshTable(){
        query = "select id,studentNumber,description, status from notification_tbl where department_key = "+departmentId+" order by id desc";
        try {
            initTable();
            tableView.setItems(dao.getnotificationmanageTable(query));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void initClock(){
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            refreshTable();

        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    // end of init

    // methods
    public void clearFields(){
        descriptionTxt.setText("");
    }
    // end methods
}
