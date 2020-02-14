package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ScheduleTestController implements Initializable {

    @FXML
    private AnchorPane capane;

    @FXML
    private JFXTimePicker ctarmondayTxt;

    @FXML
    private JFXTimePicker ctartuesdayTxt;

    @FXML
    private JFXTimePicker ctarwednesdayTxt;

    @FXML
    private JFXTimePicker ctarthursdayTxt;

    @FXML
    private JFXTimePicker ctarfridayTxt;

    @FXML
    private JFXTimePicker ctarsaturdayTxt;

    @FXML
    private JFXTimePicker ctarsundayTxt;

    @FXML
    private JFXToggleButton ctarToggle;

    @FXML
    private JFXTimePicker ctrumondayTxt;

    @FXML
    private JFXTimePicker ctrutuesdayTxt;

    @FXML
    private JFXTimePicker ctruwednesdayTxt;

    @FXML
    private JFXTimePicker ctruThursdayTxt;

    @FXML
    private JFXTimePicker ctrufridayTxt;

    @FXML
    private JFXTimePicker ctrusaturdayTxt;

    @FXML
    private JFXTimePicker ctrusundayTxt;

    @FXML
    private JFXToggleButton ctruToggle;

    @FXML
    private JFXTimePicker ccurmondayTxt;

    @FXML
    private JFXTimePicker ccurtuesdayTxt;

    @FXML
    private JFXTimePicker ccurwednesdayTxt;

    @FXML
    private JFXTimePicker ccurthursdayTxt;

    @FXML
    private JFXTimePicker ccurfridayTxt;

    @FXML
    private JFXTimePicker ccursaturdayTxt;

    @FXML
    private JFXTimePicker ccursundayTxt;

    @FXML
    private JFXToggleButton ccurToggle;

    @FXML
    private AnchorPane shsapane;

    @FXML
    private JFXTimePicker shstarmondayTxt;

    @FXML
    private JFXTimePicker shstartuesdayTxt;

    @FXML
    private JFXTimePicker shstarwednesdayTxt;

    @FXML
    private JFXTimePicker shstarthursdayTxt;

    @FXML
    private JFXTimePicker shstarfridayTxt;

    @FXML
    private JFXTimePicker shstarsaturdayTxt;

    @FXML
    private JFXTimePicker shstarsundayTxt;

    @FXML
    private JFXToggleButton shstarToggle;

    @FXML
    private JFXTimePicker shstrumondayTxt;

    @FXML
    private JFXTimePicker shstrutuesdayTxt;

    @FXML
    private JFXTimePicker shstruwednesdayTxt;

    @FXML
    private JFXTimePicker shstruThursdayTxt;

    @FXML
    private JFXTimePicker shstrufridayTxt;

    @FXML
    private JFXTimePicker shstrusaturdayTxt;

    @FXML
    private JFXTimePicker shstrusundayTxt;

    @FXML
    private JFXToggleButton shstruToggle;

    @FXML
    private JFXTimePicker shscurmondayTxt;

    @FXML
    private JFXTimePicker shscurtuesdayTxt;

    @FXML
    private JFXTimePicker shscurwednesdayTxt;

    @FXML
    private JFXTimePicker shscurthursdayTxt;

    @FXML
    private JFXTimePicker shscurfridayTxt;

    @FXML
    private JFXTimePicker shscursaturdayTxt;

    @FXML
    private JFXTimePicker shscursundayTxt;

    @FXML
    private JFXToggleButton shscurToggle;

    @FXML
    private AnchorPane jhsapane;

    @FXML
    private JFXTimePicker jhstarmondayTxt;

    @FXML
    private JFXTimePicker jhstartuesdayTxt;

    @FXML
    private JFXTimePicker jhstarwednesdayTxt;

    @FXML
    private JFXTimePicker jhstarthursdayTxt;

    @FXML
    private JFXTimePicker jhstarfridayTxt;

    @FXML
    private JFXTimePicker jhstarsaturdayTxt;

    @FXML
    private JFXTimePicker jhstarsundayTxt;

    @FXML
    private JFXToggleButton jhstarToggle;

    @FXML
    private JFXTimePicker jhstrumondayTxt;

    @FXML
    private JFXTimePicker jhstrutuesdayTxt;

    @FXML
    private JFXTimePicker jhstruwednesdayTxt;

    @FXML
    private JFXTimePicker jhstruThursdayTxt;

    @FXML
    private JFXTimePicker jhstrufridayTxt;

    @FXML
    private JFXTimePicker jhstrusaturdayTxt;

    @FXML
    private JFXTimePicker jhstrusundayTxt;

    @FXML
    private JFXToggleButton jhstruToggle;

    @FXML
    private JFXTimePicker jhscurmondayTxt;

    @FXML
    private JFXTimePicker jhscurtuesdayTxt;

    @FXML
    private JFXTimePicker jhscurwednesdayTxt;

    @FXML
    private JFXTimePicker jhscurthursdayTxt;

    @FXML
    private JFXTimePicker jhscurfridayTxt;

    @FXML
    private JFXTimePicker jhscursaturdayTxt;

    @FXML
    private JFXTimePicker jhscursundayTxt;

    @FXML
    private JFXToggleButton jhscurToggle;

    @FXML
    private AnchorPane gsapane;

    @FXML
    private JFXTimePicker gstarmondayTxt;

    @FXML
    private JFXTimePicker gstartuesdayTxt;

    @FXML
    private JFXTimePicker gstarwednesdayTxt;

    @FXML
    private JFXTimePicker gstarthursdayTxt;

    @FXML
    private JFXTimePicker gstarfridayTxt;

    @FXML
    private JFXTimePicker gstarsaturdayTxt;

    @FXML
    private JFXTimePicker gstarsundayTxt;

    @FXML
    private JFXToggleButton gstarToggle;

    @FXML
    private JFXTimePicker gstrumondayTxt;

    @FXML
    private JFXTimePicker gstrutuesdayTxt;

    @FXML
    private JFXTimePicker gstruwednesdayTxt;

    @FXML
    private JFXTimePicker gstruThursdayTxt;

    @FXML
    private JFXTimePicker gstrufridayTxt;

    @FXML
    private JFXTimePicker gstrusaturdayTxt;

    @FXML
    private JFXTimePicker gstrusundayTxt;

    @FXML
    private JFXToggleButton gstruToggle;

    @FXML
    private JFXTimePicker gscurmondayTxt;

    @FXML
    private JFXTimePicker gscurtuesdayTxt;

    @FXML
    private JFXTimePicker gscurwednesdayTxt;

    @FXML
    private JFXTimePicker gscurthursdayTxt;

    @FXML
    private JFXTimePicker gscurfridayTxt;

    @FXML
    private JFXTimePicker gscursaturdayTxt;

    @FXML
    private JFXTimePicker gscursundayTxt;

    @FXML
    private JFXToggleButton gscurToggle;

    @FXML
    private JFXButton updatecollegeBtn;

    @FXML
    private JFXButton updateshsBtn;

    @FXML
    private JFXButton updatejhsBtn;

    @FXML
    private JFXButton updategsBtn;

    // create var
    private DatabaseAccessObject dao;
    private int departmentId = HomePageController.getHomePageController().departmentId;
    private ResultSet rs;
    private String query,query1,query2;
    private int status;
    // end of create var

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableApane();
        setTimer24hrs();
        // init class
        dao = new DatabaseAccessObject();
        // end of init class

        // init methods
        try {
            fillSHS();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // end of init methods

        // event buttons
        updatecollegeBtn.setOnAction(event -> {

        });
        updateshsBtn.setOnAction(event -> {
            updateSHS();
        });
        updatejhsBtn.setOnAction(event -> {

        });
        updategsBtn.setOnAction(event -> {

        });
        // end of event buttons
    }

    // init
    public void updateCollege(){

    }
    public void fillSHS() throws SQLException {
        // tardiness
        query = "select * from schedule_tbl where schedule_id = 10";
        rs = dao.selectAll(query);
        if(rs.next()){
            shstarmondayTxt.setValue(LocalTime.parse(rs.getString("monday")));
            shstartuesdayTxt.setValue(LocalTime.parse(rs.getString("tuesday")));
            shstarwednesdayTxt.setValue(LocalTime.parse(rs.getString("wednesday")));
            shstarthursdayTxt.setValue(LocalTime.parse(rs.getString("thursday")));
            shstarfridayTxt.setValue(LocalTime.parse(rs.getString("friday")));
            shstarsaturdayTxt.setValue(LocalTime.parse(rs.getString("saturday")));
            shstarsundayTxt.setValue(LocalTime.parse(rs.getString("sunday")));
            status = rs.getInt("status");
            if(status == 1){
                shstarToggle.getToggleLineColor();
            }else{
                shstarToggle.getUnToggleLineColor();
            }
        }
        // end of tardiness

    }
    public void updateSHS(){
        int shstarToggleValue=0,shstruToggleValue=0,shscurToggleValue = 0;
        if(shstarToggle.isSelected()){
            shstarToggleValue = 1;
        }
        if(shstruToggle.isSelected()){
            shstruToggleValue = 1;
        }
        if(shscurToggle.isSelected()){
            shscurToggleValue = 1;
        }
        query = "update schedule_tbl set monday = "+shstarmondayTxt.getValue()+",tuesday = "+shstartuesdayTxt.getValue()+",wednesday="+shstarwednesdayTxt.getValue()+",thursday="+shstarthursdayTxt.getValue()+",friday="+shstarfridayTxt.getValue()+",saturday="+shstarsaturdayTxt.getValue()+",sunday="+shstarsundayTxt.getValue()+",status="+shstarToggleValue+" where schedule_id = 10";
        query1 = "update schedule_tbl set monday = "+shstrumondayTxt.getValue()+",tuesday = "+shstrutuesdayTxt.getValue()+",wednesday="+shstruwednesdayTxt.getValue()+",thursday="+shstruThursdayTxt.getValue()+",friday="+shstrufridayTxt.getValue()+",saturday="+shstrusaturdayTxt.getValue()+",sunday="+shstrusundayTxt.getValue()+",status="+shstruToggleValue+" where schedule_id = 11";
        query2 = "update schedule_tbl set monday = "+shscurmondayTxt.getValue()+",tuesday = "+shscurtuesdayTxt.getValue()+",wednesday="+shscurwednesdayTxt.getValue()+",thursday="+shscurthursdayTxt.getValue()+",friday="+shscurfridayTxt.getValue()+",saturday="+shscursaturdayTxt.getValue()+",sunday="+shscursundayTxt.getValue()+",status="+shscurToggleValue+" where schedule_id = 12";
        try {
            dao.saveData(query);
            dao.saveData(query1);
            dao.saveData(query2);
        }catch (Exception e){
            e.printStackTrace();
            _pushNotification.get_PushNotification().failed("Error While Updating","Err "+e);
        }finally {
            _pushNotification.get_PushNotification().success("Update Success","Successfully edited data number SHS");
        }

    }
    public void updateJHS(){

    }
    public void updateGS(){

    }
    // end of init

    // custom methods
    public void disableApane(){
        int activeDepartment = departmentId;
        System.out.println(activeDepartment);
        switch (activeDepartment){
            case 1:
                gsapane.setDisable(false);
                updategsBtn.setDisable(false);
                break;
            case 2:
                jhsapane.setDisable(false);
                updatejhsBtn.setDisable(false);
                break;
            case 3:
                shsapane.setDisable(false);
                updateshsBtn.setDisable(false);
                break;
            case 4:
                capane.setDisable(false);
                updatecollegeBtn.setDisable(false);
                break;
        }
    }
    public void setTimer24hrs(){
        ccurmondayTxt._24HourViewProperty();
        ctarmondayTxt._24HourViewProperty();
        ctrumondayTxt._24HourViewProperty();
        gscurmondayTxt._24HourViewProperty();
        gstarmondayTxt._24HourViewProperty();
        gstrumondayTxt._24HourViewProperty();
        jhscurmondayTxt._24HourViewProperty();
        jhstarmondayTxt._24HourViewProperty();
        jhstrumondayTxt._24HourViewProperty();
        shscurmondayTxt._24HourViewProperty();
        shstarmondayTxt._24HourViewProperty();
        shstrumondayTxt._24HourViewProperty();

        ccurtuesdayTxt._24HourViewProperty();
        ctrutuesdayTxt._24HourViewProperty();
        ctartuesdayTxt._24HourViewProperty();
        shscurtuesdayTxt._24HourViewProperty();
        shstartuesdayTxt._24HourViewProperty();
        shstrutuesdayTxt._24HourViewProperty();
        jhscurtuesdayTxt._24HourViewProperty();
        jhstartuesdayTxt._24HourViewProperty();
        jhstrutuesdayTxt._24HourViewProperty();
        gscurtuesdayTxt._24HourViewProperty();
        gstartuesdayTxt._24HourViewProperty();
        gstrutuesdayTxt._24HourViewProperty();

        ccurwednesdayTxt._24HourViewProperty();
        ctarwednesdayTxt._24HourViewProperty();
        ctruwednesdayTxt._24HourViewProperty();
        gscurwednesdayTxt._24HourViewProperty();
        gstarwednesdayTxt._24HourViewProperty();
        gstruwednesdayTxt._24HourViewProperty();
        jhscurwednesdayTxt._24HourViewProperty();
        jhstarwednesdayTxt._24HourViewProperty();
        jhstruwednesdayTxt._24HourViewProperty();
        shscurwednesdayTxt._24HourViewProperty();
        shstarwednesdayTxt._24HourViewProperty();
        shstruwednesdayTxt._24HourViewProperty();

        ctruThursdayTxt._24HourViewProperty();
        gstruThursdayTxt._24HourViewProperty();
        jhstruThursdayTxt._24HourViewProperty();
        shstruThursdayTxt._24HourViewProperty();
        ccurthursdayTxt._24HourViewProperty();
        ctarthursdayTxt._24HourViewProperty();
        gscurthursdayTxt._24HourViewProperty();
        gstarthursdayTxt._24HourViewProperty();
        jhscurthursdayTxt._24HourViewProperty();
        jhstarthursdayTxt._24HourViewProperty();
        shscurthursdayTxt._24HourViewProperty();
        shstarthursdayTxt._24HourViewProperty();

        ccurfridayTxt._24HourViewProperty();
        ctarfridayTxt._24HourViewProperty();
        ctrufridayTxt._24HourViewProperty();
        gscurfridayTxt._24HourViewProperty();
        gstarfridayTxt._24HourViewProperty();
        gstrufridayTxt._24HourViewProperty();
        jhscurfridayTxt._24HourViewProperty();
        jhstarfridayTxt._24HourViewProperty();
        jhstrufridayTxt._24HourViewProperty();
        shscurfridayTxt._24HourViewProperty();
        shstarfridayTxt._24HourViewProperty();
        shscurfridayTxt._24HourViewProperty();

        ccursaturdayTxt._24HourViewProperty();
        ctarsaturdayTxt._24HourViewProperty();
        ctrusaturdayTxt._24HourViewProperty();
        gstarsaturdayTxt._24HourViewProperty();
        gscursaturdayTxt._24HourViewProperty();
        gstrusaturdayTxt._24HourViewProperty();
        shscursaturdayTxt._24HourViewProperty();
        shstarsaturdayTxt._24HourViewProperty();
        shstrusaturdayTxt._24HourViewProperty();
        jhscursaturdayTxt._24HourViewProperty();
        jhstarsaturdayTxt._24HourViewProperty();
        jhstrusaturdayTxt._24HourViewProperty();

        ccursundayTxt._24HourViewProperty();
        ctarsundayTxt._24HourViewProperty();
        ctrusundayTxt._24HourViewProperty();
        gscursundayTxt._24HourViewProperty();
        gstarsundayTxt._24HourViewProperty();
        gstrusundayTxt._24HourViewProperty();
        jhscursundayTxt._24HourViewProperty();
        jhstarsundayTxt._24HourViewProperty();
        jhstrusundayTxt._24HourViewProperty();
        shscursundayTxt._24HourViewProperty();
        shstarsundayTxt._24HourViewProperty();
        shstrusundayTxt._24HourViewProperty();





    }
    // end of custom methods


}
