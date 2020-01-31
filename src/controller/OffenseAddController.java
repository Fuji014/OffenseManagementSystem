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

    @FXML
    private JFXTextField sanctionTxt;

    // declare var below
    private DatabaseAccessObject dao;
    private AdminLoginController alc;
    private String query;
    // end declare var below

    // initialize itself

    // end of initialize itself

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize class
        dao = new DatabaseAccessObject();
        alc = new AdminLoginController();
        // end of initialize class

        // methods
        // end of methods

        // event buttons
        departmentComboBox.setOnMouseClicked(event -> {
            initDepartmentComboBox();
        });
        saveBtn.setOnAction(event -> {
            saveEvent();
        });
        closeBtn.setOnMouseClicked(event -> {
            this.closeBtn.getScene().getWindow().hide();
        });

        // end of event buttons
    }

    // init
    public void initDepartmentComboBox(){
        departmentComboBox.getSelectionModel().clearSelection();
        String query = "select * from department_tbl";
        departmentComboBox.setItems(dao.getStudentDepartmentComboBox(query));
    }
    public void saveEvent(){
        String dept =  departmentComboBox.getSelectionModel().getSelectedIndex()+1+"";
        query = "INSERT INTO offense_tbl  VALUES (NULL, '"+descriptionTxt.getText()+"', '"+severityTxt.getText()+"', "+dept+",'"+sanctionTxt.getText()+"')";
        try {
            dao.saveData(query);
            OffensePageController.getOffensePageController().refreshTable();
            clearFields();
        }catch (Exception e){
            alc.alertErr(null,"Exception Err"+e);
        }finally {
            alc.alertSuccess(null, "User Added Successfully ");
        }
    }
    // end of init

    // custom methods
    public void clearFields(){
        descriptionTxt.setText("");
        severityTxt.setText("");
        departmentComboBox.getSelectionModel().clearSelection();
        sanctionTxt.setText("");
    }
    // end of custom methods
}