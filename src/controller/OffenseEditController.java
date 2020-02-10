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

public class OffenseEditController implements Initializable {

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
    private JFXTextField sanctionTxt;

    @FXML
    private JFXComboBox<String> departmentComboBox;

    // declare var below
    private DatabaseAccessObject dao;
    private AdminLoginController alc;
    private String query;
    private int departmentId = HomePageController.getHomePageController().departmentId;
    // end of declare var

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initFillTextFields();

        // initialize class
        dao = new DatabaseAccessObject();
        alc = new AdminLoginController();
        // end of initialize class

        // methods
        initOffenseDeptCombobox();
        // end of methods

        // event button
        saveBtn.setOnAction(event -> { saveEvent(); });
        closeBtn.setOnMouseClicked(event -> { this.closeBtn.getScene().getWindow().hide(); });
        // end of event button

    }

    // init
    public void initFillTextFields(){
        descriptionTxt.setText(OffensePageController.getOffensePageController().getOffenseDescription());
        severityTxt.setText(OffensePageController.getOffensePageController().getOffenseSeverity());
        departmentComboBox.getSelectionModel().select(OffensePageController.getOffensePageController().getDeptName());
        sanctionTxt.setText(OffensePageController.getOffensePageController().getOffenseSanction());
    }
    private void initOffenseDeptCombobox(){
        departmentComboBox.getSelectionModel().clearSelection();
        String query = "select * from department_tbl";
        departmentComboBox.setItems(dao.getStudentDepartmentComboBox(query));
    }
    public void saveEvent(){
        String dept = departmentComboBox.getSelectionModel().getSelectedIndex()+1+"";
        query = "update offense_tbl set offense_description = '"+descriptionTxt.getText()+"', offense_severity = '"+severityTxt.getText()+"', dept_key = "+dept+", offense_sanction = '"+sanctionTxt.getText()+"' where id = "+OffensePageController.getOffensePageController().getId()+"";
        try {
            dao.saveData(query);
            OffensePageController.getOffensePageController().refreshTable();
        }catch (Exception e){
            alc.alertErr(null, "Err"+e);
        }finally {
            alc.alertSuccess(null, "Successfully Updated!");
        }

    }
    // end of init

    // custom methods
    // end of custom methods

}
