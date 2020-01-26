package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentEditController implements Initializable {
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
        initFillTextBox();
        // initialize class
        dao = new DatabaseAccessObject();
        alc = new AdminLoginController();
        // end of initialize class

        // methods

        // end of methods

        // event button
        saveBtn.setOnAction(event -> {
            initSave();
        });
        closeBtn.setOnMouseClicked(event -> {
            this.closeBtn.getScene().getWindow().hide();
        });
        // end of event button

    }

    // init methods
    public void initFillTextBox(){
        deptnameTxt.setText(DepartmentPageController.getDepartmentPageController().getDeptName());
        deptdescriptionTxt.setText(DepartmentPageController.getDepartmentPageController().getDeptDescription());
    }
    public void initSave(){
        query = "update department_tbl set dept_name = '"+deptnameTxt.getText()+"', dept_description = '"+deptdescriptionTxt.getText()+"' where id = '"+DepartmentPageController.getDepartmentPageController().getDeptId()+"'";
        try{
            dao.saveData(query);
            DepartmentPageController.getDepartmentPageController().refreshTable();
        }catch (Exception e){
            alc.alertErr(null,"Exception Err"+e);
        }finally {
            alc.alertSuccess(null, "User Added Successfully ");
        }

    }
    // end of init methods

    // custom methods

    // end of custom methods

    // getters
}
