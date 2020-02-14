package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class PolicyPageController implements Initializable {

    @FXML
    private AnchorPane capane;

    @FXML
    private JFXTextField cminorcountTxt;

    @FXML
    private JFXTextArea cminorremarksTxt;

    @FXML
    private JFXTextField cminordurationTxt;

    @FXML
    private JFXTextField cmajorcountTxt;

    @FXML
    private JFXTextArea cmajorremarksTxt;

    @FXML
    private JFXTextField cmajordurationTxt;

    @FXML
    private JFXTextField cseriouscountTxt;

    @FXML
    private JFXTextArea cseriousremarksTxt;

    @FXML
    private JFXTextField cseriousdurationTxt;

    @FXML
    private JFXButton csaveBtn;

    @FXML
    private AnchorPane shsapane;

    @FXML
    private JFXTextField shsminorcountTxt;

    @FXML
    private JFXTextArea shsminorremarksTxt;

    @FXML
    private JFXTextField shsminordurationTxt;

    @FXML
    private JFXTextField shsmajorcountTxt;

    @FXML
    private JFXTextArea shsmajorremarksTxt;

    @FXML
    private JFXTextField shsmajordurationTxt;

    @FXML
    private JFXTextField shsseriouscountTxt;

    @FXML
    private JFXTextArea shsseriousremarksTxt;

    @FXML
    private JFXTextField shsseriousdurationTxt;

    @FXML
    private JFXButton shssaveBtn;

    @FXML
    private AnchorPane jhsapane;

    @FXML
    private JFXTextField jhsminorcountTxt;

    @FXML
    private JFXTextArea jhsminorremarksTxt;

    @FXML
    private JFXTextField jhsminordurationTxt;

    @FXML
    private JFXTextField jhsmajorcountTxt;

    @FXML
    private JFXTextArea jhsmajorremarksTxt;

    @FXML
    private JFXTextField jhsmajordurationTxt;

    @FXML
    private JFXTextField jhsseriouscountTxt;

    @FXML
    private JFXTextArea jhsseriousremarksTxt;

    @FXML
    private JFXTextField jhsseriousdurationTxt;

    @FXML
    private JFXButton jhssaveBtn;

    @FXML
    private AnchorPane gsapane;

    @FXML
    private JFXTextField gsminorcountTxt;

    @FXML
    private JFXTextArea gsminorremarksTxt;

    @FXML
    private JFXTextField gsminordurationTxt;

    @FXML
    private JFXTextField gsmajorcountTxt;

    @FXML
    private JFXTextArea gsmajorremarksTxt;

    @FXML
    private JFXTextField gsmajordurationTxt;

    @FXML
    private JFXTextField gsseriouscountTxt;

    @FXML
    private JFXTextArea gsseriousremarksTxt;

    @FXML
    private JFXTextField gsseriousdurationTxt;

    @FXML
    private JFXButton gssaveBtn;


    // create var below
    private int departmentId = HomePageController.getHomePageController().departmentId;
    private DatabaseAccessObject dao;
    private AdminLoginController alc;
    private ResultSet rs;
    String query;
    // end of create var

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableApane();
        // init class
        dao = new DatabaseAccessObject();
        // end of init class

        // init methods
        dumpCollege();
        dumpShs();
        dumpJhs();
        dumpGs();
        // end of init methods

        // event buttons
        csaveBtn.setOnAction(event -> {
            updateC();
        });

        shssaveBtn.setOnAction(event -> {
            updateShs();
        });

        jhssaveBtn.setOnAction(event -> {
            updateJhs();
        });

        gssaveBtn.setOnAction(event -> {
            updateGs();
        });
        // end of event buttons

    }

    // init
    public void disableApane(){
        int activeDepartment = departmentId;
        System.out.println(activeDepartment);
        switch (activeDepartment){
            case 1:
                gsapane.setDisable(false);
                break;
            case 2:
                jhsapane.setDisable(false);
                break;
            case 3:
                shsapane.setDisable(false);
                break;
            case 4:
                capane.setDisable(false);
                break;
        }
    }

    public void dumpCollege(){
        // select minor
        query = "select * from policy_tbl where department_key = 4 and offense_severity = 'minor'";
        try {
            rs = dao.getPolicyData(query);
            while(rs.next()){
                cminorcountTxt.setText(rs.getString("offense_max"));
                cminordurationTxt.setText(rs.getString("penalty_duration"));
                cminorremarksTxt.setText(rs.getString("penalty_description"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        // end of select minor

        // select major
        query = "select * from policy_tbl where department_key = 4 and offense_severity = 'major'";
        try {
            rs = dao.getPolicyData(query);
            while(rs.next()){
                cmajorcountTxt.setText(rs.getString("offense_max"));
                cmajordurationTxt.setText(rs.getString("penalty_duration"));
                cmajorremarksTxt.setText(rs.getString("penalty_description"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        // end of select major

        // select serious
        query = "select * from policy_tbl where department_key = 4 and offense_severity = 'serious'";
        try {
            rs = dao.getPolicyData(query);
            while(rs.next()){
                cseriouscountTxt.setText(rs.getString("offense_max"));
                cseriousdurationTxt.setText(rs.getString("penalty_duration"));
                cseriousremarksTxt.setText(rs.getString("penalty_description"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        // end of select serious
    }

    public void dumpShs(){
        // select minor
        query = "select * from policy_tbl where department_key = 3 and offense_severity = 'minor'";
        try {
            rs = dao.getPolicyData(query);
            while(rs.next()){
                shsminorcountTxt.setText(rs.getString("offense_max"));
                shsminordurationTxt.setText(rs.getString("penalty_duration"));
                shsminorremarksTxt.setText(rs.getString("penalty_description"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        // end of select minor

        // select major
        query = "select * from policy_tbl where department_key = 3 and offense_severity = 'major'";
        try {
            rs = dao.getPolicyData(query);
            while(rs.next()){
                shsmajorcountTxt.setText(rs.getString("offense_max"));
                shsmajordurationTxt.setText(rs.getString("penalty_duration"));
                shsmajorremarksTxt.setText(rs.getString("penalty_description"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        // end of select major

        // select serious
        query = "select * from policy_tbl where department_key = 3 and offense_severity = 'serious'";
        try {
            rs = dao.getPolicyData(query);
            while(rs.next()){
                shsseriouscountTxt.setText(rs.getString("offense_max"));
                shsseriousdurationTxt.setText(rs.getString("penalty_duration"));
                shsseriousremarksTxt.setText(rs.getString("penalty_description"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        // end of select serious
    }

    public void dumpJhs(){
        // select minor
        query = "select * from policy_tbl where department_key = 2 and offense_severity = 'minor'";
        try {
            rs = dao.getPolicyData(query);
            while(rs.next()){
                jhsminorcountTxt.setText(rs.getString("offense_max"));
                jhsminordurationTxt.setText(rs.getString("penalty_duration"));
                jhsminorremarksTxt.setText(rs.getString("penalty_description"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        // end of select minor

        // select major
        query = "select * from policy_tbl where department_key = 2 and offense_severity = 'major'";
        try {
            rs = dao.getPolicyData(query);
            while(rs.next()){
                jhsmajorcountTxt.setText(rs.getString("offense_max"));
                jhsmajordurationTxt.setText(rs.getString("penalty_duration"));
                jhsmajorremarksTxt.setText(rs.getString("penalty_description"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        // end of select major

        // select serious
        query = "select * from policy_tbl where department_key = 2 and offense_severity = 'serious'";
        try {
            rs = dao.getPolicyData(query);
            while(rs.next()){
                jhsseriouscountTxt.setText(rs.getString("offense_max"));
                jhsseriousdurationTxt.setText(rs.getString("penalty_duration"));
                jhsseriousremarksTxt.setText(rs.getString("penalty_description"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        // end of select serious
    }

    public void dumpGs(){
        // select minor
        query = "select * from policy_tbl where department_key = 1 and offense_severity = 'minor'";
        try {
            rs = dao.getPolicyData(query);
            while(rs.next()){
                gsminorcountTxt.setText(rs.getString("offense_max"));
                gsminordurationTxt.setText(rs.getString("penalty_duration"));
                gsminorremarksTxt.setText(rs.getString("penalty_description"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        // end of select minor

        // select major
        query = "select * from policy_tbl where department_key = 1 and offense_severity = 'major'";
        try {
            rs = dao.getPolicyData(query);
            while(rs.next()){
                gsmajorcountTxt.setText(rs.getString("offense_max"));
                gsmajordurationTxt.setText(rs.getString("penalty_duration"));
                gsmajorremarksTxt.setText(rs.getString("penalty_description"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        // end of select major

        // select serious
        query = "select * from policy_tbl where department_key = 1 and offense_severity = 'serious'";
        try {
            rs = dao.getPolicyData(query);
            while(rs.next()){
                gsseriouscountTxt.setText(rs.getString("offense_max"));
                gsseriousdurationTxt.setText(rs.getString("penalty_duration"));
                gsseriousremarksTxt.setText(rs.getString("penalty_description"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        // end of select serious
    }

    public void updateC(){
        // update minor
        query = "update policy_tbl set offense_max  = "+cminorcountTxt.getText()+", penalty_duration = '"+cminordurationTxt.getText()+"' , penalty_description = '"+cminorremarksTxt.getText()+"' where department_key = 4 and offense_severity = 'minor'";
        try {
            dao.saveData(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        // end update minor

        // update major
        query = "update policy_tbl set offense_max  = "+cmajorcountTxt.getText()+", penalty_duration = '"+cmajordurationTxt.getText()+"' , penalty_description = '"+cmajorremarksTxt.getText()+"' where department_key = 4 and offense_severity = 'major'";
        try {
            dao.saveData(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        // end of update major

        // update serious
        query = "update policy_tbl set offense_max  = "+cseriouscountTxt.getText()+", penalty_duration = '"+cseriousdurationTxt.getText()+"' , penalty_description = '"+cseriousremarksTxt.getText()+"' where department_key = 4 and offense_severity = 'serious'";
        try {
            dao.saveData(query);
//            System.out.println(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        // end of update serious
    }
    public void updateShs(){
        // update minor
        query = "update policy_tbl set offense_max  = "+shsminorcountTxt.getText()+", penalty_duration = '"+shsminordurationTxt.getText()+"' , penalty_description = '"+shsminorremarksTxt.getText()+"' where department_key = 3 and offense_severity = 'minor'";
        try {
            dao.saveData(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        // end update minor

        // update major
        query = "update policy_tbl set offense_max  = "+shsmajorcountTxt.getText()+", penalty_duration = '"+shsmajordurationTxt.getText()+"' , penalty_description = '"+shsmajorremarksTxt.getText()+"' where department_key = 3 and offense_severity = 'major'";
        try {
            dao.saveData(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        // end of update major

        // update serious
        query = "update policy_tbl set offense_max  = "+shsseriouscountTxt.getText()+", penalty_duration = '"+shsseriousdurationTxt.getText()+"' , penalty_description = '"+shsseriousremarksTxt.getText()+"' where department_key = 3 and offense_severity = 'serious'";
        try {
            dao.saveData(query);
//            System.out.println(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        // end of update serious
    }
    public void updateJhs(){
        // update minor
        query = "update policy_tbl set offense_max  = "+jhsminorcountTxt.getText()+", penalty_duration = '"+jhsminordurationTxt.getText()+"' , penalty_description = '"+jhsminorremarksTxt.getText()+"' where department_key = 2 and offense_severity = 'minor'";
        try {
            dao.saveData(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        // end update minor

        // update major
        query = "update policy_tbl set offense_max  = "+jhsmajorcountTxt.getText()+", penalty_duration = '"+jhsmajordurationTxt.getText()+"' , penalty_description = '"+jhsmajorremarksTxt.getText()+"' where department_key = 2 and offense_severity = 'major'";
        try {
            dao.saveData(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        // end of update major

        // update serious
        query = "update policy_tbl set offense_max  = "+jhsseriouscountTxt.getText()+", penalty_duration = '"+jhsseriousdurationTxt.getText()+"' , penalty_description = '"+jhsseriousremarksTxt.getText()+"' where department_key = 2 and offense_severity = 'serious'";
        try {
            dao.saveData(query);
//            System.out.println(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        // end of update serious
    }
    public void updateGs(){
        // update minor
        query = "update policy_tbl set offense_max  = "+gsminorcountTxt.getText()+", penalty_duration = '"+gsminordurationTxt.getText()+"' , penalty_description = '"+gsminorremarksTxt.getText()+"' where department_key = 1 and offense_severity = 'minor'";
        try {
            dao.saveData(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        // end update minor

        // update major
        query = "update policy_tbl set offense_max  = "+gsmajorcountTxt.getText()+", penalty_duration = '"+gsmajordurationTxt.getText()+"' , penalty_description = '"+gsmajorremarksTxt.getText()+"' where department_key = 1 and offense_severity = 'major'";
        try {
            dao.saveData(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        // end of update major

        // update serious
        query = "update policy_tbl set offense_max  = "+gsseriouscountTxt.getText()+", penalty_duration = '"+gsseriousdurationTxt.getText()+"' , penalty_description = '"+gsseriousdurationTxt.getText()+"' where department_key = 1 and offense_severity = 'serious'";
        try {
            dao.saveData(query);
//            System.out.println(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        // end of update serious
    }
    // end init

    // custom methods

    // end of custom methods

}
