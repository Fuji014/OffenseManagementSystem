package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
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

    // declare var below;
    private DatabaseAccessObject dao;
    private MainController mc;
    private AdminLoginController alc;
    private static StudentOffenseAddController instance;
    private String query;
    private Timestamp timestamp;
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
    public void saveEvent(){
        query = "insert into student_offense_tbl values (null, "+studentidTxt.getText()+","+offensenameTxt.getText()+","+offensecountTxt.getText()+",'"+offensedateTxt.getText()+"','"+remarksTxt.getText()+"')";
        try{
            dao.saveData(query);
            StudentOffenseController.getStudentOffenseController().refreshTable();
            clearFields();
        }catch (Exception e){
        alc.alertErr(null,"Exception Err"+e);
    }finally {
        alc.alertSuccess(null, "User Added Successfully ");
    }
    }
    public void dateInit(){
        timestamp = new Timestamp(System.currentTimeMillis());
        offensedateTxt.setText(String.valueOf(timestamp));
    }
    // end of init

    // custom methods
    public void countEvent() throws SQLException {
        if(studentidTxt.getText() != "" && offensenameTxt.getText() != "") {
            query = "SELECT count(*) FROM student_offense_tbl where student_key = " + studentidTxt.getText() + " and offense_key = " + offensenameTxt.getText() + "";
            System.out.println(query);
            offensecountTxt.setText(Integer.toString(dao.getStudentOffenseCount(query)));
        }
    }

    public void clearFields(){
        studentidTxt.setText("");
        offensenameTxt.setText("");
        offensecountTxt.setText("");
        remarksTxt.setText("");
    }
    // end of custom methods
}
