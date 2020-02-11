package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import controller.tables.notificationTable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

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

    // declare var below
    private DatabaseAccessObject dao;
    private String query;
    // end of var below

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // init class
        dao = new DatabaseAccessObject();
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
        query = "select id,description,date from notification_tbl where status = 0 and department_key = 4;";
        tableView.setItems(dao.getNotificationData(query));
    }
    // end of init

    // custom methods

    // end of custom methods

    // getters
}
