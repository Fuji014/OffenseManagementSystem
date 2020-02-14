package controller;

import controller.tables.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import model.ConnectionHandler;

public class DatabaseAccessObject implements Initializable {

    // Declare var below ;

    private ConnectionHandler connector = new ConnectionHandler();
    private Connection connection;
    private PreparedStatement prs;
    private ResultSet rs;
    int counter;
    private Image image;
    private FileInputStream fileInputStream;
    private Timestamp timestamp;
    private String query;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Boolean getDatabaseTestResult() throws SQLException, ClassNotFoundException { // Test Database Result
        final String query = "SELECT 1";
        connection = connector.getConnection();
        boolean isConnected = false;
        return isConnected;
    }

    public void saveData(String query) throws SQLException, ClassNotFoundException {
        connection = connector.getConnection();
        try{
            prs = connection.prepareStatement(query);
            prs.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,null);
        }
    }
    public ResultSet selectAll(String query){

        try {
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return rs;
        }
    }

    public void admin(String crud) throws SQLException, ClassNotFoundException, FileNotFoundException { // admin
        switch (crud){
            case "create":
                fileInputStream = new FileInputStream(AdminUserAddController.getAddAdminUserController().file);
                timestamp = new Timestamp(System.currentTimeMillis());
                query = "insert into admin_tbl values (?,?,?,?,?,?,?,?)";
                connection = connector.getConnection();
                try{
                    prs = connection.prepareStatement(query);
                    prs.setString(1,null);
                    prs.setString(2, AdminUserAddController.getAddAdminUserController().nameTxt.getText());
                    prs.setString(3, AdminUserAddController.getAddAdminUserController().contactTxt.getText());
                    prs.setString(4, AdminUserAddController.getAddAdminUserController().deptComboBox.getSelectionModel().getSelectedIndex()+1+"");
                    prs.setString(5, AdminUserAddController.getAddAdminUserController().usernameTxt.getText());
                    prs.setString(6, AdminUserAddController.getAddAdminUserController().passwordTxt.getText());
                    prs.setBinaryStream(7,fileInputStream, AdminUserAddController.getAddAdminUserController().file.length());
                    prs.setTimestamp(8,timestamp);
                    prs.executeUpdate();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    connector.close(connection,prs,null);
                }
                break;
            case "update":
                if(AdminUserEditController.getEditAdminUserController().file != null){
                    fileInputStream = new FileInputStream(AdminUserEditController.getEditAdminUserController().file);
                    query = "update admin_tbl set name = ?,  contact = ?, department_key = ? , username = ?, image = ? where id = ?";
                    connection = connector.getConnection();
                    try{
                        prs = connection.prepareStatement(query);
                        prs.setString(1, AdminUserEditController.getEditAdminUserController().nameTxt.getText());
                        prs.setString(2, AdminUserEditController.getEditAdminUserController().contactTxt.getText());
                        prs.setString(3,AdminUserEditController.getEditAdminUserController().deptComboBox.getSelectionModel().getSelectedIndex()+1+"");
                        prs.setString(4, AdminUserEditController.getEditAdminUserController().usernameTxt.getText());
                        prs.setBinaryStream(5,fileInputStream, AdminUserEditController.getEditAdminUserController().file.length());
                        prs.setInt(6, AdminUserPageController.getSettingsPageController().getId());
                        prs.executeUpdate();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        connector.close(connection,prs,null);
                    }
                }else{
                    query = "update admin_tbl set name = ?, contact = ?, department_key = ?, username = ? where id = ?";
                    connection = connector.getConnection();
                    try{
                        prs = connection.prepareStatement(query);
                        prs.setString(1, AdminUserEditController.getEditAdminUserController().nameTxt.getText());
                        prs.setString(2, AdminUserEditController.getEditAdminUserController().contactTxt.getText());
                        prs.setString(3, AdminUserEditController.getEditAdminUserController().deptComboBox.getSelectionModel().getSelectedIndex()+1+"");
                        prs.setString(4, AdminUserEditController.getEditAdminUserController().usernameTxt.getText());
                        prs.setInt(5, AdminUserPageController.getSettingsPageController().getId());
                        prs.executeUpdate();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        connector.close(connection,prs,null);
                    }
                }


                break;

        }

    }
    public void student(String crud) throws SQLException, ClassNotFoundException, FileNotFoundException { // student
        switch (crud){
            case "create":

                fileInputStream = new FileInputStream(StudentAddController.getAddStudentController().file);
                query = "INSERT INTO student_tbl  VALUES (?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?);";
                connection = connector.getConnection();
                try{
                    prs = connection.prepareStatement(query);
                    prs.setString(1,null);
                    prs.setString(2, StudentAddController.getAddStudentController().studNumberTxt.getText());
                    prs.setString(3, StudentAddController.getAddStudentController().rfidTagIdTxt.getText());
                    prs.setString(4, StudentAddController.getAddStudentController().studFullnameTxt.getText());
                    prs.setString(5, StudentAddController.getAddStudentController().yearTxt.getText());
                    prs.setString(6, StudentAddController.getAddStudentController().sectionTxt.getText());
                    prs.setString(7, StudentAddController.getAddStudentController().courseTxt.getText());
                    prs.setString(8, StudentAddController.getAddStudentController().strandTxt.getText());
                    prs.setString(9, StudentAddController.getAddStudentController().studDeptComboBox.getSelectionModel().getSelectedIndex()+1+"");
                    prs.setString(10, StudentAddController.getAddStudentController().studContact.getText());
                    prs.setBinaryStream(11,fileInputStream, StudentAddController.getAddStudentController().file.length());
                    prs.setString(12, StudentAddController.getAddStudentController().parentFullnameTxt.getText());
                    prs.setString(13, StudentAddController.getAddStudentController().parentContactTxt.getText());
                    prs.setString(14, StudentAddController.getAddStudentController().parentAddressTxt.getText());
                    prs.executeUpdate();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    connector.close(connection,prs,null);
                }
                break;
            case "update":
                if(StudentEditController.getEditStudentController().file != null){
                    fileInputStream = new FileInputStream(StudentEditController.getEditStudentController().file);
                    query = "update student_tbl set student_id = ? , rfid_tag_id = ? , student_name = ?, student_year = ?, student_section = ? , student_course = ? , student_strand =?, student_department = ?, student_image = ?, parent_fullname = ?, parent_contact = ? ,parent_address =?, student_contact = ? where id = ?";
                    connection = connector.getConnection();
                    try{
                        prs = connection.prepareStatement(query);
                        prs.setInt(1, Integer.parseInt(StudentEditController.getEditStudentController().studNumberTxt.getText()));
                        prs.setInt(2, Integer.parseInt(StudentEditController.getEditStudentController().rfidTagIdTxt.getText()));
                        prs.setString(3, StudentEditController.getEditStudentController().studFullnameTxt.getText());
                        prs.setString(4, StudentEditController.getEditStudentController().yearTxt.getText());
                        prs.setString(5, StudentEditController.getEditStudentController().sectionTxt.getText());
                        prs.setString(6, StudentEditController.getEditStudentController().courseTxt.getText());
                        prs.setString(7, StudentEditController.getEditStudentController().strandTxt.getText());
                        prs.setString(8, StudentEditController.getEditStudentController().studDeptComboBox.getSelectionModel().getSelectedIndex()+1+"");
                        prs.setBinaryStream(9,fileInputStream, StudentEditController.getEditStudentController().file.length());
                        prs.setString(10, StudentEditController.getEditStudentController().parentFullnameTxt.getText());
                        prs.setString(11, StudentEditController.getEditStudentController().parentContactTxt.getText());
                        prs.setString(12, StudentEditController.getEditStudentController().parentAddressTxt.getText());
                        prs.setString(13, StudentEditController.getEditStudentController().studContact.getText());
                        prs.executeUpdate();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        connector.close(connection,prs,null);
                    }
                }else{
                    query = "update student_tbl set student_id = ? , rfid_tag_id = ? , student_name = ?, student_year = ?, student_section = ? , student_course = ? , student_strand =?, student_department = ?, parent_fullname = ?, parent_contact = ? ,parent_address =?, student_contact = ? where id = ?";
                    connection = connector.getConnection();
                    try{
                        prs = connection.prepareStatement(query);
                        prs.setInt(1, Integer.parseInt(StudentEditController.getEditStudentController().studNumberTxt.getText()));
                        prs.setString(2, StudentEditController.getEditStudentController().rfidTagIdTxt.getText());
                        prs.setString(3, StudentEditController.getEditStudentController().studFullnameTxt.getText());
                        prs.setString(4, StudentEditController.getEditStudentController().yearTxt.getText());
                        prs.setString(5, StudentEditController.getEditStudentController().sectionTxt.getText());
                        prs.setString(6, StudentEditController.getEditStudentController().courseTxt.getText());
                        prs.setString(7, StudentEditController.getEditStudentController().strandTxt.getText());
                        prs.setString(8, StudentEditController.getEditStudentController().studDeptComboBox.getSelectionModel().getSelectedIndex()+1+"");
                        prs.setString(9, StudentEditController.getEditStudentController().parentFullnameTxt.getText());
                        prs.setString(10, StudentEditController.getEditStudentController().parentContactTxt.getText());
                        prs.setString(11, StudentEditController.getEditStudentController().parentAddressTxt.getText());
                        prs.setString(12, StudentEditController.getEditStudentController().studContact.getText());
                        prs.setInt(13,StudentPageController.getStudentPageController().getId());
                        prs.executeUpdate();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        connector.close(connection,prs,null);
                    }
                }

                break;

        }

    }

    public Boolean getUserResult(String query) throws SQLException {
        boolean isAuth = false;
        try{
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            counter = 0;
            while(rs.next()){
                counter = counter + 1;
            }
            if(counter == 1){
                isAuth = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,rs);
            return isAuth;
        }
    }

    public HashMap<String,String> getUserInfo(String query) throws SQLException {
        HashMap<String,String> hashMap = new HashMap<String,String>();
        try{
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while (rs.next()){
                hashMap.put("adminId", rs.getString(1));
                hashMap.put("adminDepartment",rs.getString(4));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,rs);
            return hashMap;
        }

    }


    public ObservableList<adminUserTable> getAdminData(String query){ // addmin table
        ObservableList<adminUserTable> list = FXCollections.observableArrayList();
        try{
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while(rs.next()){
                list.add(new adminUserTable(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }
    public ObservableList<studentTable> getStudentSearch(String query){ // student Search table
        ObservableList<studentTable> list = FXCollections.observableArrayList();
        try{
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while(rs.next()){
                list.add(new studentTable(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }
    public ObservableList<adminUserTable> getAdminSearch(String query){ // admin table
        ObservableList<adminUserTable> list = FXCollections.observableArrayList();
        try{
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while(rs.next()){
                list.add(new adminUserTable(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }
    public ObservableList<studentTable> getStudentData(String query) throws SQLException { // student table
        ObservableList<studentTable> list = FXCollections.observableArrayList();
        try{
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while(rs.next()){
                list.add(new studentTable(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,rs);
            return list;
        }
    }
    public ObservableList<departmentTable> getDepartmentData(String query) throws SQLException { // department table
        ObservableList<departmentTable> list = FXCollections.observableArrayList();
        try{
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while(rs.next()){
                list.add(new departmentTable(rs.getInt(1),rs.getString(2),rs.getString(3)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,rs);
            return list;
        }
    }

    public ObservableList<offenseTable> getOffenseData(String query) throws SQLException { // offense table
        ObservableList<offenseTable> list = FXCollections.observableArrayList();
        try{
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while(rs.next()){
                list.add(new offenseTable(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,rs);
            return list;
        }
    }
    public ObservableList<scheduleTable> getScheduleData(String query) throws SQLException { // schedule table
        ObservableList<scheduleTable> list = FXCollections.observableArrayList();
        try{
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while(rs.next()){
                list.add(new scheduleTable(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,rs);
            return list;
        }

    }

    public ObservableList<studentOffenseTable> getStudentOffenseData(String query) throws SQLException { // student offense table
        ObservableList<studentOffenseTable> list = FXCollections.observableArrayList();
       try{
           connection = connector.getConnection();
       }catch (Exception e){
           e.printStackTrace();
       }
        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while(rs.next()){
                list.add(new studentOffenseTable(rs.getInt("std_offense_id"),rs.getInt("student_key"),rs.getInt("offense_key"),rs.getString("offense_severity"),rs.getString("offense_duration"),rs.getString("offense_completedTime"),rs.getString("offense_status"),rs.getString("student_offense_count"),rs.getString("student_offense_remarks"),rs.getString("student_offense_date")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,rs);
            return list;

        }
    }

    public ObservableList<StudentOffenseSearchStudentTable> getStudentOffenseStudentSearchData(String query) throws SQLException { // student search table
        ObservableList<StudentOffenseSearchStudentTable> list = FXCollections.observableArrayList();
        try{
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while(rs.next()){
                list.add(new StudentOffenseSearchStudentTable(rs.getInt(1),rs.getString(2)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,rs);
            return list;
        }
    }

    public ObservableList<StudentOffenseSearchOffenseTable> getStudentOffenseOffenseSearchData(String query) throws SQLException {
        ObservableList<StudentOffenseSearchOffenseTable> list = FXCollections.observableArrayList();
        try{
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while(rs.next()){
                list.add(new StudentOffenseSearchOffenseTable(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,rs);
            return list;
        }
    }

    public ObservableList<penaltyTable> getPenaltyData(String query) throws SQLException { // penalty table
        ObservableList<penaltyTable> list = FXCollections.observableArrayList();
        try{
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while(rs.next()){
                list.add(new penaltyTable(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,rs);
            return list;
        }
    }

    public ObservableList<notificationTable> getNotificationData(String query) throws SQLException {
        ObservableList<notificationTable> list = FXCollections.observableArrayList();
        try{
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while(rs.next()){
                list.add(new notificationTable(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,rs);
            return list;
        }
    }
    public ObservableList<String> getStudentDepartmentComboBox(String query){ // student department combox
        ObservableList list = FXCollections.observableArrayList();
        try {
            connection = connector.getConnection();
        }catch (Exception e2){
            e2.printStackTrace();
        }

        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while(rs.next()){
                list.add(rs.getString(2));
            }
        }catch(Exception e1){
            e1.printStackTrace();
        }finally {
            return list;
        }
    }

    public int getStudentOffenseCount(String query) throws SQLException {
        int count = 0;
        try{
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while(rs.next()){
                count = rs.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,rs);
            return count+1;
        }
    }

    public HashMap<String,String> getDepartmentName(String query) throws SQLException {
        HashMap<String,String> hashMap = new HashMap<>();
        try{
            connection=connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while(rs.next()){
                hashMap.put("department",rs.getString(1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,rs);
            return hashMap;
        }
    }

    public int getRfidCount(String query) throws SQLException { // rfid count
        int count = 0;
        try {
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while(rs.next()){
                count = rs.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,rs);
            return count;
        }
    }

    public HashMap<String, String> getSeverityAndDepartment(String query) throws SQLException {
        HashMap<String, String> hashMap = new HashMap<>();
        try {
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while(rs.next()){
                hashMap.put("severity",rs.getString(1));
                hashMap.put("department_key",rs.getString(2));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,rs);
            return hashMap;
        }
    }
    public ResultSet getPolicyCountAndDuration(String query) throws SQLException {
        try{
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            connector.close(connection,prs,null);
            return rs;
        }
    }

    public int officialCount(String query) throws SQLException {
        int count = 0;
        try{
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while(rs.next()){
                count = rs.getInt(1);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connector.close(connection,prs,rs);
            return count;
        }
    }

    public ResultSet getSeverityAndDeptKey(String query) throws SQLException {
        try {
            connection=connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            connector.close(connection,prs,null);
            return rs;
        }
    }

    // policy module
    public ResultSet getPolicyData(String query){
        try {
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return rs;
        }
    }

    public ResultSet getStudentInfoDetails(String query){
        try {
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            return rs;
        }
    }


    // end of policy shs
    public ObservableList<notificationmanageTable> getnotificationmanageTable(String query) throws SQLException {
        ObservableList<notificationmanageTable> list = FXCollections.observableArrayList();
        try {
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while (rs.next()){
                list.add(new notificationmanageTable(rs.getInt("id"),rs.getInt("studentNumber"),rs.getString("description"),rs.getString("status")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,rs);
            return list;
        }
    }

    public ResultSet getDisplayNotificationInfo(String query){
        try {
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return rs;
        }
    }

    public int getCountNotification(String query){
        int count = 0;
        try{
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while (rs.next()){
                count = rs.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return count;
        }
    }

    // attendance records

    public ObservableList<attendancerecordsTable> getAttendancerecordsTable(String query) throws SQLException {
        ObservableList<attendancerecordsTable> list = FXCollections.observableArrayList();
        try{
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while (rs.next()){
                list.add(new attendancerecordsTable(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,rs);
            return list;
        }
    }

}
