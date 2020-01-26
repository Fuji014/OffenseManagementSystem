package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ScheduleEditController implements Initializable {

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
    // end of declare var below

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillFields();
        // initialize class
        dao = new DatabaseAccessObject();
        alc = new AdminLoginController();
        // end of initialize class

        // methods
        initDepartmentComboBox();
        // end of methods

        // event button
        saveBtn.setOnAction(event -> {
            saveEvent();
        });
        closeBtn.setOnMouseClicked(event -> {
            this.closeBtn.getScene().getWindow().hide();
        });
        cancelBtn.setOnAction(event -> {
            this.cancelBtn.getScene().getWindow().hide();
        });
        // end of event button

    }

    // init
    public void saveEvent(){
        String dept = departmentComboBox.getSelectionModel().getSelectedIndex()+1+"";
        query = "update schedule_tbl set dept_key = "+dept+", monday = '"+SchedulePageController.getSchedulePageController().getSchedMonday()+"', tuesday = '"+SchedulePageController.getSchedulePageController().getSchedTuesday()+"', wednesday = '"+SchedulePageController.getSchedulePageController().getSchedWednesday()+"', thursday = '"+SchedulePageController.getSchedulePageController().getSchedThursday()+"', friday = '"+SchedulePageController.getSchedulePageController().getSchedFriday()+"' where schedule_id = "+SchedulePageController.getSchedulePageController().getId()+"";
        try{
            dao.saveData(query);
            SchedulePageController.getSchedulePageController().refreshTable();
        }catch (Exception e){
            alc.alertErr(null, "Err"+e);
        }finally {
            alc.alertSuccess(null, "Successfully Added!");
        }
    }
    public void fillFields(){
        departmentComboBox.getSelectionModel().select(SchedulePageController.getSchedulePageController().getSchedDeptName());
        mondayTimer.setValue(LocalTime.parse(SchedulePageController.getSchedulePageController().getSchedMonday()));
        tuesdayTimer.setValue(LocalTime.parse(SchedulePageController.getSchedulePageController().getSchedTuesday()));
        wednesdayTimer.setValue(LocalTime.parse(SchedulePageController.getSchedulePageController().getSchedWednesday()));
        thursdayTimer.setValue(LocalTime.parse(SchedulePageController.getSchedulePageController().getSchedThursday()));
        fridayTimer.setValue(LocalTime.parse(SchedulePageController.getSchedulePageController().getSchedFriday()));
    }
    public void initDepartmentComboBox(){
        departmentComboBox.getSelectionModel().clearSelection();
        String query = "select * from department_tbl";
        departmentComboBox.setItems(dao.getStudentDepartmentComboBox(query));
    }
    // end of init

    // custom methods
    // end of custom methods
}
