package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
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
    private JFXButton cancelBtn;

    // declare var below;
    private MainController mc;
    private static StudentOffenseAddController instance;
    private String query;
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
        // initialize class
        mc = new MainController();
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

        // end of event buttons
    }

    // init
    // end of init
    public void initCount(){
        query = "SELECT count(*) FROM student_offense_tbl where student_key = '"+studentidTxt.getText()+"' and offense_key = '"+offensenameTxt.getText()+"'";
        
    }
    // custom methods

    // end of custom methods
}
