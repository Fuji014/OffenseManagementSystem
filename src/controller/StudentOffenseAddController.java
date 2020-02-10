package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class StudentOffenseAddController implements Initializable {

    @FXML
    public JFXTextField studentidTxt;

    @FXML
    private JFXButton studentIdSearch;

    @FXML
    public JFXTextField offensenameTxt;

    @FXML
    private JFXButton offensenameSearch;

    @FXML
    private JFXTextField offensecountTxt;

    @FXML
    private JFXTextField offensedateTxt;

    @FXML
    private JFXTextArea remarksTxt;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton offensecountBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXTextField durationTxt;

    @FXML
    private JFXTextField completedtimeTxt;

    @FXML
    private JFXTextField statusTxt;

    @FXML
    private JFXTextField severityTxt;

    @FXML
    private Label warningLbl;

    @FXML
    private Label warningLbl1;

    // declare var below;
    private DatabaseAccessObject dao;
    private MainController mc;
    private AdminLoginController alc;
    private static StudentOffenseAddController instance;
    private String query;
    private Timestamp timestamp;
    private ResultSet rs;
    private int departmentId = HomePageController.getHomePageController().departmentId;
    // end of declare var below

    // create instance itself
    public StudentOffenseAddController(){
        this.instance = this;
    }
    public static StudentOffenseAddController getStudentOffenseAddController(){
        return instance;
    }
    // end of instance itself


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        warningLbl.setVisible(false);
        warningLbl1.setVisible(false);
        dateInit();
        // initialize class
        mc = new MainController();
        dao = new DatabaseAccessObject();
        alc = new AdminLoginController();
        // end of initialize class

        // methods

        // end of methods

        // event buttons
        studentIdSearch.setOnAction(event -> {
            try {
                mc.createPage(null, "/views/StudentOffenseSearchStudent.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        offensenameSearch.setOnAction(event -> {
            try {
                mc.createPage(null, "/views/StudentOffenseSearchOffense.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        cancelBtn.setOnAction(event -> {
            this.cancelBtn.getScene().getWindow().hide();
        });
        offensecountBtn.setOnAction(event -> {
            try {
                countEvent();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        saveBtn.setOnAction(event -> {
                saveEvent();

        });

        // end of event buttons
    }

    // init
    public void saveEvent() {
        query = "insert into student_offense_tbl values (null,"+studentidTxt.getText()+","+offensenameTxt.getText()+","+offensecountTxt.getText()+",'"+severityTxt.getText()+"','"+durationTxt.getText()+"',null,0,'"+remarksTxt.getText()+"','"+offensedateTxt.getText()+"')";
        try {
            dao.saveData(query);
            clearFields();
            StudentOffenseController.getStudentOffenseController().refreshTable();
            alc.alertSuccess(null,"Insert Successfully");
        }catch (Exception e){
            e.printStackTrace();
            alc.alertErr(null, "Err while Inserting"+e);
        }
    }

    public void dateInit(){
        timestamp = new Timestamp(System.currentTimeMillis());
        offensedateTxt.setText(String.valueOf(timestamp));
    }
    // end of initx

    // custom methods
    public void countEvent() throws SQLException {
        if(studentidTxt.getText() != "" && offensenameTxt.getText() != "") {
            // selct severity and dept key of offense_tbl
            String severity = "";
            int departmentKey = 0;
            query = "select offense_severity,dept_key from offense_tbl where id = "+offensenameTxt.getText()+" limit 1";
            rs = dao.getSeverityAndDeptKey(query);
            while (rs.next()){
                severity = rs.getString(1);
                departmentKey = rs.getInt(2);
            }

            // select max and duration from policy
            String penaltyDescription="",penaltyDuration = "";
            int offense_max = 0;
            query = "select offense_max,penalty_duration,penalty_description  from policy_tbl WHERE department_key = "+departmentKey+" and offense_severity = '"+severity+"'";
            rs = dao.getPolicyCountAndDuration(query);
            while (rs.next()){
                offense_max = rs.getInt(1);
                penaltyDuration = rs.getString(2);
                penaltyDescription = rs.getString(3);

            }

            // count in current table
            int countStudOffense = 0;
            query = "SELECT count(*) FROM student_offense_tbl as so inner join offense_tbl as o on so.offense_key = o.id where student_key = "+studentidTxt.getText()+" and offense_key = "+offensenameTxt.getText()+" and o.offense_severity = '"+severity+"'";
            countStudOffense = dao.officialCount(query) + 1;
            if(countStudOffense >= offense_max){
                durationTxt.setDisable(false);
                durationTxt.setText(penaltyDuration);
                severityTxt.setText(severity);
                offensecountTxt.setText("1");
                remarksTxt.setText(penaltyDescription);
                System.out.println(severity);
                if (severity.equals("minor")) {
                    System.out.println("true");
                    severityTxt.setText("major");
                    severityTxt.setStyle("-fx-text-inner-color: red;");
                    offensecountTxt.setText("1");
                    warningLbl.setText("Student ID "+studentidTxt.getText()+" Already Exceed maximum limit for minor offense ,");
                    warningLbl.setVisible(true);
                    warningLbl1.setVisible(true);
                }
                severityTxt.setStyle("-fx-text-inner-color: red;");
            }else {
                offensecountTxt.setText(Integer.toString(countStudOffense));
                durationTxt.setText(penaltyDuration);
                severityTxt.setText(severity);
                remarksTxt.setText(penaltyDescription);
            }
        }
    }

    public void clearFields(){
        studentidTxt.setText("");
        offensenameTxt.setText("");
        offensecountTxt.setText("");
        remarksTxt.setText("");
        severityTxt.setText("");
        durationTxt.setText("");
        completedtimeTxt.setText("");
        statusTxt.setText("");
    }

    public void initCount(){
        offensecountTxt.textProperty().addListener((ObservableValue<? extends String> ob, String oldV, String newV) -> {
            ///
        });
    }
    public void getSeverityDeptKey(){
        query = "select offense_severity,dept_key from offense_tbl where id = "+offensenameTxt.getText()+" limit 1";
    }
    // end of custom methods
}
