package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class OffenseAddController implements Initializable {

    @FXML
    private Label closeBtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXTextField severityTxt;

    @FXML
    private JFXTextArea descriptionTxt;

    @FXML
    private JFXComboBox<String> departmentComboBox;

    // declare var below
    private DatabaseAccessObject dao;
    // end declare var below

    // initialize itself

    // end of initialize itself

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize class
        dao = new DatabaseAccessObject();
        // end of initialize class

        // methods
        // end of methods

        // event buttons
        departmentComboBox.setOnMouseClicked(event -> {
            initDepartmentComboBox();
        });
        // end of event buttons

    }

    // init

    // end of init
    public void initDepartmentComboBox(){
        departmentComboBox.getSelectionModel().clearSelection();
        String query = "select * from department_tbl";
        departmentComboBox.setItems(dao.getStudentDepartmentComboBox(query));
    }
    // custom methods

    // end of custom methods
}
