package controller;

import controller.tables.notificationTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NotificationPageController implements Initializable {

    @FXML
    private Label exitBtn;

    @FXML
    private TableView<notificationTable> tableView;

    @FXML
    private TableColumn<notificationTable, Integer> idCol;

    @FXML
    private TableColumn<notificationTable, String> descriptionCol;

    @FXML
    private TableColumn<notificationTable, String> dateCol;

    @FXML
    private MenuItem viewBtn;

    @FXML
    private MenuItem closeBtn;

    @FXML
    private Label viewallLbl;

    @FXML
    private AnchorPane home;

    // declare var below
    private DatabaseAccessObject dao;
    private HomePageController homePageController;
    private String query;
    private int departmentId = HomePageController.getHomePageController().departmentId;
    private static  NotificationPageController instance;
    // end of var below

    // init itself
    public NotificationPageController(){
        this.instance = this;
    }
    public static NotificationPageController getNotificationPageController(){
        return instance;
    }
    // init itself

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // init class
        dao = new DatabaseAccessObject();
        homePageController = new HomePageController();
        // end of init class

        // init methods
        try {
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // end of init methods

        // event btns
        exitBtn.setOnMouseClicked(event -> {
            this.exitBtn.getScene().getWindow().hide();
        });
//        viewallLbl.setOnMouseClicked(event -> {
//            this.viewallLbl.getScene().getWindow().hide();
//            HomePageController.getHomePageController().viewNotification();
//        });
        // end of event btns
    }

    // init
    public void initTable(){
        idCol.setCellValueFactory(cell -> cell.getValue().notifIdProperty().asObject());
        descriptionCol.setCellValueFactory(cell -> cell.getValue().notifDescriptionProperty());
        dateCol.setCellValueFactory(cell -> cell.getValue().notifDateProperty());
    }
    public void refreshTable() throws SQLException {
        initTable();
        query = "select id,description,status from notification_tbl where department_key = "+departmentId+"";
        tableView.setItems(dao.getNotificationData(query));
    }
    public int countNotification(){
        int count = 0;
        query = "select count(*) from notification_tbl where status = 'unread'";
        try {
            count = dao.getCountNotification(query);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return count;
        }
    }
    // end of init

    // custom methods

    // end of custom methods

    // getters
}
