package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentAddController implements Initializable {

    @FXML
    private Label closeBtn;

    @FXML
    private JFXTextField deptnameTxt;

    @FXML
    private JFXTextArea deptdescriptionTxt;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton cancelBtn;

    // declare var below
    private DatabaseAccessObject dao;
    private AdminLoginController alc;
    private String query;
    // end of declare var

    // initialize itself

    // end of initialize itself


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // initilize class
        dao = new DatabaseAccessObject();
        alc = new AdminLoginController();
        // end of initialize class

        // methods

        // end of methods

        // event button
        closeBtn.setOnMouseClicked(event -> {
            this.closeBtn.getScene().getWindow().hide();
        });
        saveBtn.setOnAction(event -> {
            initSave();
        });
        // end of event button

    }

    // init methods
    public void initSave(){
        query = "insert into department_tbl values (null,'"+deptnameTxt.getText()+"','"+deptdescriptionTxt.getText()+"')";
        try{
            dao.saveData(query);
            DepartmentPageController.getDepartmentPageController().refreshTable();
            clearFields();
        }catch (Exception e){
            alc.alertErr(null,"Exception Err"+e);
        }finally {
            alc.alertSuccess(null, "User Added Successfully ");
        }


    }
    // end of init methods

    // custom methods
    public void clearFields(){
        deptnameTxt.setText("");
        deptdescriptionTxt.setText("");
    }
    // end of custom methods

    // getters
}
