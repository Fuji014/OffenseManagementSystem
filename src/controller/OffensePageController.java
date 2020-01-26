package controller;

import com.jfoenix.controls.JFXButton;
import controller.tables.offenseTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    private MenuItem updateBtn;

    @FXML
    private MenuItem deleteBtn;

    @FXML
    private JFXButton newBtn;

    @FXML
    private JFXButton searchBtn;

    // declare below
    private DatabaseAccessObject dao;
    private MainController mc;
    private String query;
    // end of declare var below

    // initialize itself

    // end of initialize itself



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
                mc.createPage(null, "/views/OffenseAdd.fxml");
            } catch (IOException e) {
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
    }
    public void refreshTable() throws SQLException {
        initTable();
        query = "select o.id, o.offense_description, o.offense_severity, d.dept_name from offense_tbl as o inner join department_tbl as d on o.dept_key = d.id order by o.id desc";
        offenseTableView.setItems(dao.getOffenseData(query));
    }
    // end of init

    // custom
    // end of custom

    // getters
}
