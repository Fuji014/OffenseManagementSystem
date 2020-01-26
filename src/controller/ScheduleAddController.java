package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ScheduleAddController implements Initializable {

    @FXML
    private Label closeBtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXComboBox<String> departmentComboBox;

    @FXML
    private JFXTimePicker mondayTimer;

    @FXML
    private JFXTimePicker tuesdayTimer;

    @FXML
    private JFXTimePicker wednesdayTimer;

    @FXML
    private JFXTimePicker thursdayTimer;

    @FXML
    private JFXTimePicker fridayTimer;

    @FXML
    private JFXButton cancelBtn;

    // declare var below
    private DatabaseAccessObject dao;
    private AdminLoginController alc;
    private String query;
    // end of declare var

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setTimer24hrs();
        // initialize class
        dao = new DatabaseAccessObject();
        alc = new AdminLoginController();
        // end of initalize class

        // methods
        // end of methods

        // event button
        saveBtn.setOnAction(event -> {
            saveEvent();
        });
        departmentComboBox.setOnMouseClicked(event -> {
            initDepartmentComboBox();
        });
        // end of event button
    }

    // init
    public void saveEvent(){
        String dept = departmentComboBox.getSelectionModel().getSelectedIndex()+1+"";
        query = "INSERT INTO `schedule_tbl` VALUES (NULL, "+dept+", '"+mondayTimer.getValue()+"', '"+tuesdayTimer.getValue()+"', '"+wednesdayTimer.getValue()+"', '"+thursdayTimer.getValue()+"', '"+fridayTimer.getValue()+"');";
        try {
            dao.saveData(query);
            SchedulePageController.getSchedulePageController().refreshTable();
            clearFields();
        }catch (Exception e){
            alc.alertErr(null,"Err"+e);
        }finally {
            alc.alertSuccess(null, "Successfully Added!");
        }

    }
    public void initDepartmentComboBox(){
        departmentComboBox.getSelectionModel().clearSelection();
        String query = "select * from department_tbl";
        departmentComboBox.setItems(dao.getStudentDepartmentComboBox(query));
    }
    // end of init

    // custom methods
    public void clearFields(){
        departmentComboBox.getSelectionModel().clearSelection();
        mondayTimer.setValue(null);
        tuesdayTimer.setValue(null);
        wednesdayTimer.setValue(null);
        thursdayTimer.setValue(null);
        fridayTimer.setValue(null);
    }
    public void setTimer24hrs(){
        mondayTimer._24HourViewProperty();
        tuesdayTimer._24HourViewProperty();
        wednesdayTimer._24HourViewProperty();
        thursdayTimer._24HourViewProperty();
        fridayTimer._24HourViewProperty();
    }
    // end of custom methods
}
