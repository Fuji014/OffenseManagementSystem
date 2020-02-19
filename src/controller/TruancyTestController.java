package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class TruancyTestController implements Initializable {

    @FXML
    private JFXTimePicker cmon;

    @FXML
    private JFXTimePicker ctue;

    @FXML
    private JFXTimePicker cwed;

    @FXML
    private JFXTimePicker cthu;

    @FXML
    private JFXTimePicker cfri;

    @FXML
    private JFXTimePicker csat;

    @FXML
    private JFXTimePicker csun;

    @FXML
    private JFXTimePicker shsmon;

    @FXML
    private JFXTimePicker shstue;

    @FXML
    private JFXTimePicker shswed;

    @FXML
    private JFXTimePicker shsthu;

    @FXML
    private JFXTimePicker shsfri;

    @FXML
    private JFXTimePicker shssat;

    @FXML
    private JFXTimePicker shssun;

    @FXML
    private JFXTimePicker jhsmon;

    @FXML
    private JFXTimePicker jhstue;

    @FXML
    private JFXTimePicker jhswed;

    @FXML
    private JFXTimePicker jhsthu;

    @FXML
    private JFXTimePicker jhsfri;

    @FXML
    private JFXTimePicker jhssat;

    @FXML
    private JFXTimePicker jhssun;

    @FXML
    private JFXTimePicker gsmon;

    @FXML
    private JFXTimePicker gstue;

    @FXML
    private JFXTimePicker gswed;

    @FXML
    private JFXTimePicker gsthu;

    @FXML
    private JFXTimePicker gsfri;

    @FXML
    private JFXTimePicker gssat;

    @FXML
    private JFXTimePicker gssun;

    @FXML
    private JFXButton collegeBtn;

    @FXML
    private JFXButton shsBtn;

    @FXML
    private JFXButton jhsBtn;

    @FXML
    private JFXButton gsBtn;

    @FXML
    private AnchorPane capane;

    @FXML
    private AnchorPane shsapane;

    @FXML
    private AnchorPane jhsapane;

    @FXML
    private AnchorPane gsapane;


    private int departmentId = HomePageController.getHomePageController().departmentId;
    private DatabaseAccessObject dao;
    private _pushNotification _pushNotif;
    private ResultSet rs;
    private String query;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTimer24hrs();
        disableApane();

        dao = new DatabaseAccessObject();
        _pushNotif = new _pushNotification();

        try {
            CollegeFill();
            SHSFill();
            JHSFill();
            GSFill();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        collegeBtn.setOnAction(event -> {
            collegeUpdateEvent();
        });
        shsBtn.setOnAction(event -> {
            SHSUpdateEvent();
        });
        jhsBtn.setOnAction(event -> {
            JHSUpdateEvent();
        });
        gsBtn.setOnAction(event -> {
            GSUpdateEvent();
        });
    }

    public void collegeUpdateEvent(){
        query = "update tardiness_endtime_tbl set monday = '"+cmon.getValue()+"',tuesday='"+ctue.getValue()+"',wednesday='"+cwed.getValue()+"',thursday='"+cthu.getValue()+"',friday='"+cfri.getValue()+"',saturday='"+csat.getValue()+"',sunday='"+csun.getValue()+"' where dept_key = "+departmentId+"";
        try {
            dao.saveData(query);
            _pushNotif.success("Update Success","Successfully Updated");
        }catch (Exception e){
            e.printStackTrace();
            _pushNotif.failed("Update Failed","Failed To Update "+e);
        }
    }
    public void SHSUpdateEvent(){
        query = "update tardiness_endtime_tbl set monday = '"+shsmon.getValue()+"',tuesday='"+shstue.getValue()+"',wednesday='"+shswed.getValue()+"',thursday='"+shsthu.getValue()+"',friday='"+shsfri.getValue()+"',saturday='"+shssat.getValue()+"',sunday='"+shssun.getValue()+"' where dept_key = "+departmentId+"";
        System.out.println(query);
        try {
            dao.saveData(query);
            _pushNotif.success("Update Success","Successfully Updated");
        }catch (Exception e){
            e.printStackTrace();
            _pushNotif.failed("Update Failed","Failed To Update "+e);
        }
    }
    public void JHSUpdateEvent(){
        query = "update tardiness_endtime_tbl set monday = '"+jhsmon.getValue()+"',tuesday='"+jhstue.getValue()+"',wednesday='"+jhswed.getValue()+"',thursday='"+jhsthu.getValue()+"',friday='"+jhsfri.getValue()+"',saturday='"+jhssat.getValue()+"',sunday='"+jhssun.getValue()+"' where dept_key = "+departmentId+"";
        try {
            dao.saveData(query);
            _pushNotif.success("Update Success","Successfully Updated");
        }catch (Exception e){
            e.printStackTrace();
            _pushNotif.failed("Update Failed","Failed To Update "+e);
        }
    }
    public void GSUpdateEvent(){
        query = "update tardiness_endtime_tbl set monday = '"+gsmon.getValue()+"',tuesday='"+gstue.getValue()+"',wednesday='"+gswed.getValue()+"',thursday='"+gsthu.getValue()+"',friday='"+gsfri.getValue()+"',saturday='"+gssat.getValue()+"',sunday='"+gssun.getValue()+"' where dept_key = "+departmentId+"";
        try {
            dao.saveData(query);
            _pushNotif.success("Update Success","Successfully Updated");
        }catch (Exception e){
            e.printStackTrace();
            _pushNotif.failed("Update Failed","Failed To Update "+e);
        }
    }

    public void CollegeFill() throws SQLException {
        query = "select * from  tardiness_endtime_tbl WHERE dept_key = "+departmentId+" limit 1";
        rs = dao.selectAll(query);
        if(rs.next()){
            cmon.setValue(LocalTime.parse(rs.getString("monday")));
            ctue.setValue(LocalTime.parse(rs.getString("tuesday")));
            cwed.setValue(LocalTime.parse(rs.getString("wednesday")));
            cthu.setValue(LocalTime.parse(rs.getString("thursday")));
            cfri.setValue(LocalTime.parse(rs.getString("friday")));
            csat.setValue(LocalTime.parse(rs.getString("saturday")));
            csun.setValue(LocalTime.parse(rs.getString("sunday")));
        }
    }
    public void SHSFill() throws SQLException {
        query = "select * from  tardiness_endtime_tbl WHERE dept_key = "+departmentId+" limit 1";
        rs = dao.selectAll(query);
        if(rs.next()){
            shsmon.setValue(LocalTime.parse(rs.getString("monday")));
            shstue.setValue(LocalTime.parse(rs.getString("tuesday")));
            shswed.setValue(LocalTime.parse(rs.getString("wednesday")));
            shsthu.setValue(LocalTime.parse(rs.getString("thursday")));
            shsfri.setValue(LocalTime.parse(rs.getString("friday")));
            shssat.setValue(LocalTime.parse(rs.getString("saturday")));
            shssun.setValue(LocalTime.parse(rs.getString("sunday")));
        }
    }
    public void JHSFill() throws SQLException {
        query = "select * from  tardiness_endtime_tbl WHERE dept_key = "+departmentId+" limit 1";
        rs = dao.selectAll(query);
        if(rs.next()){
            jhsmon.setValue(LocalTime.parse(rs.getString("monday")));
            jhstue.setValue(LocalTime.parse(rs.getString("tuesday")));
            jhswed.setValue(LocalTime.parse(rs.getString("wednesday")));
            jhsthu.setValue(LocalTime.parse(rs.getString("thursday")));
            jhsfri.setValue(LocalTime.parse(rs.getString("friday")));
            jhssat.setValue(LocalTime.parse(rs.getString("saturday")));
            jhssun.setValue(LocalTime.parse(rs.getString("sunday")));
        }
    }
    public void GSFill() throws SQLException {
        query = "select * from  tardiness_endtime_tbl WHERE dept_key = "+departmentId+" limit 1";
        rs = dao.selectAll(query);
        if(rs.next()){
            gsmon.setValue(LocalTime.parse(rs.getString("monday")));
            gstue.setValue(LocalTime.parse(rs.getString("tuesday")));
            gswed.setValue(LocalTime.parse(rs.getString("wednesday")));
            gsthu.setValue(LocalTime.parse(rs.getString("thursday")));
            gsfri.setValue(LocalTime.parse(rs.getString("friday")));
            gssat.setValue(LocalTime.parse(rs.getString("saturday")));
            gssun.setValue(LocalTime.parse(rs.getString("sunday")));
        }
    }

    public void disableApane(){
        int activeDepartment = departmentId;
        System.out.println(activeDepartment);
        switch (activeDepartment){
            case 1:
                gsapane.setDisable(false);
                gsBtn.setDisable(false);
                break;
            case 2:
                jhsapane.setDisable(false);
                jhsBtn.setDisable(false);
                break;
            case 3:
                shsapane.setDisable(false);
                shsBtn.setDisable(false);
                break;
            case 4:
                capane.setDisable(false);
                collegeBtn.setDisable(false);
                break;
        }
    }

    public void setTimer24hrs(){
        cmon._24HourViewProperty();
        ctue._24HourViewProperty();
        cwed._24HourViewProperty();
        cthu._24HourViewProperty();
        cfri._24HourViewProperty();
        csat._24HourViewProperty();
        csun._24HourViewProperty();

        shsmon._24HourViewProperty();
        shstue._24HourViewProperty();
        shswed._24HourViewProperty();
        shsthu._24HourViewProperty();
        shsfri._24HourViewProperty();
        shssat._24HourViewProperty();
        shssun._24HourViewProperty();

        jhsmon._24HourViewProperty();
        jhstue._24HourViewProperty();
        jhswed._24HourViewProperty();
        jhsthu._24HourViewProperty();
        jhsfri._24HourViewProperty();
        jhssat._24HourViewProperty();
        jhssun._24HourViewProperty();

        gsmon._24HourViewProperty();
        gstue._24HourViewProperty();
        gswed._24HourViewProperty();
        gsthu._24HourViewProperty();
        gsfri._24HourViewProperty();
        gssat._24HourViewProperty();
        gssun._24HourViewProperty();

    }
}
