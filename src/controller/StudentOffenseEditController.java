package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentOffenseEditController implements Initializable {

    @FXML
    private JFXTextField studentidTxt;

    @FXML
    private JFXTextField offensenameTxt;

    @FXML
    private JFXTextField offensecountTxt;

    @FXML
    private JFXTextField offensedateTxt;

    @FXML
    private JFXTextArea remarksTxt;

    @FXML
    private JFXTextField severityTxt;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXTextField durationTxt;

    @FXML
    private JFXTextField completedtimeTxt;

    @FXML
    private JFXTextField statusTxt;

    @FXML
    private Label closeBtn;

    // declare var below
    private DatabaseAccessObject dao;
    private AdminLoginController alc;
    private String query;
    // end of var below

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initFill();
        // init class
        alc = new AdminLoginController();
        dao = new DatabaseAccessObject();
        // end of init class

        // init methods

        // end of init methods

        // event buttons
        saveBtn.setOnAction(event -> {
            try {
                editEvent();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        closeBtn.setOnMouseClicked(event -> {
            this.closeBtn.getScene().getWindow().hide();
        });

        cancelBtn.setOnAction(event -> {
            this.cancelBtn.getScene().getWindow().hide();
        });


        // end of event buttons

    }

    // init
    public  void editEvent() throws SQLException {
        String offenseStatus = "";
        if(durationTxt.getText().equals(completedtimeTxt.getText())){
            offenseStatus = "complete";
            statusTxt.setText("complete");
        }else{
            offenseStatus = "not complete";
            statusTxt.setText("not complete");
        }

        query = "update student_offense_tbl set offense_duration ='"+durationTxt.getText()+"',offense_completedTime ='"+completedtimeTxt.getText()+"', offense_status = '"+offenseStatus+"', student_offense_remarks = '"+remarksTxt.getText()+"' where std_offense_id = "+StudentOffenseController.getStudentOffenseController().getId()+"";
        System.out.println(query);
        try {
            dao.saveData(query);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            alc.alertSuccess(null, "Successfully Updated!");
            StudentOffenseController.getStudentOffenseController().refreshTable();
        }
    }
    public void initFill(){
        studentidTxt.setText(String.valueOf(StudentOffenseController.getStudentOffenseController().getStudent_key()));
        offensenameTxt.setText(String.valueOf(StudentOffenseController.getStudentOffenseController().getOffense_key()));
        offensecountTxt.setText(String.valueOf(StudentOffenseController.getStudentOffenseController().getCount()));
        offensedateTxt.setText(StudentOffenseController.getStudentOffenseController().getDate());
        severityTxt.setText(StudentOffenseController.getStudentOffenseController().getSeverity());
        remarksTxt.setText(StudentOffenseController.getStudentOffenseController().getRemarks());
        durationTxt.setText(StudentOffenseController.getStudentOffenseController().getDuration());
        completedtimeTxt.setText(StudentOffenseController.getStudentOffenseController().getCompleted());
        statusTxt.setText(StudentOffenseController.getStudentOffenseController().getStatus());
    }
    // end of init

    // custom methods
    public void clearFields(){
        remarksTxt.setText("");
        durationTxt.setText("");
    }

    // end of custom methods

    // getters

    // end of getters
}
