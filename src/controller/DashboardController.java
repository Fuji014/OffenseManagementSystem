package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label notifLbl;

    @FXML
    private Label policyLbl;

    @FXML
    private Label reportedoffenseLbl;

    @FXML
    private Label offenseLbl;

    @FXML
    private Label studentLbl;

    @FXML
    private Label attendanceLbl;

    private String query;
    private DatabaseAccessObject dao;
    private int departmentId = HomePageController.getHomePageController().departmentId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao = new DatabaseAccessObject();
        getNotifCount();getPolicyCount();getStudentCount();getOffenseCount();getReportedCount();getAttendaceCount();
    }

    public void getNotifCount(){
        query = "select count(*) from notification_tbl WHERE department_key = "+departmentId+"";
        try {
            notifLbl.setText(Integer.toString(dao.getCountsDashboard(query)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void getPolicyCount(){
        query = "select count(*) from policy_tbl WHERE department_key = "+departmentId+"";
        try {
            policyLbl.setText(Integer.toString(dao.getCountsDashboard(query)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void getStudentCount(){
        query = "select count(*) from student_tbl where student_department = "+departmentId+"";
        try {
            studentLbl.setText(Integer.toString(dao.getCountsDashboard(query)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void getOffenseCount(){
        query = "select count(*) from offense_tbl WHERE dept_key = "+departmentId+"";
        try {
            offenseLbl.setText(Integer.toString(dao.getCountsDashboard(query)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void getReportedCount(){
        query = "select count(*) from student_offense_tbl as so inner join offense_tbl as o on so.offense_key = o.id where o.dept_key = "+departmentId+"";
        try {
            reportedoffenseLbl.setText(Integer.toString(dao.getCountsDashboard(query)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void getAttendaceCount(){
        query = "select count(*) from record_tbl as r inner join student_tbl as s on r.student_key = s.student_id where s.student_department = "+departmentId+"";
        try {
            attendanceLbl.setText(Integer.toString(dao.getCountsDashboard(query)));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
