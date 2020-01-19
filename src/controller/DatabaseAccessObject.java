package controller;

import controller.tables.adminUserTable;
import controller.tables.studentTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.io.*;
import java.net.URL;
import java.sql.*;
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

    public void admin(String crud) throws SQLException, ClassNotFoundException, FileNotFoundException {
        switch (crud){
            case "create":
                fileInputStream = new FileInputStream(AddAdminUserController.getAddAdminUserController().file);
                timestamp = new Timestamp(System.currentTimeMillis());
                query = "insert into admin_tbl values (?,?,?,?,?,?,?,?,?)";
                connection = connector.getConnection();
                try{
                    prs = connection.prepareStatement(query);
                    prs.setString(1,null);
                    prs.setString(2,AddAdminUserController.getAddAdminUserController().firstnameTxt.getText());
                    prs.setString(3, AddAdminUserController.getAddAdminUserController().lastnameTxt.getText());
                    prs.setString(4,AddAdminUserController.getAddAdminUserController().miTxt.getText());
                    prs.setString(5,AddAdminUserController.getAddAdminUserController().contactTxt.getText());
                    prs.setString(6,AddAdminUserController.getAddAdminUserController().usernameTxt.getText());
                    prs.setString(7,AddAdminUserController.getAddAdminUserController().passwordTxt.getText());
                    prs.setBinaryStream(8,fileInputStream,AddAdminUserController.getAddAdminUserController().file.length());
                    prs.setTimestamp(9,timestamp);
                    prs.executeUpdate();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    connector.close(connection,prs,null);
                }
                break;
            case "update":
                if(EditAdminUserController.getEditAdminUserController().file != null){
                    fileInputStream = new FileInputStream(EditAdminUserController.getEditAdminUserController().file);
                    query = "update admin_tbl set firstname = ?, lastname = ?, mi = ?, contact = ?, username = ?, image = ? where id = ?";
                    connection = connector.getConnection();
                    try{
                        prs = connection.prepareStatement(query);
                        prs.setString(1,EditAdminUserController.getEditAdminUserController().firstnameTxt.getText());
                        prs.setString(2,EditAdminUserController.getEditAdminUserController().lastnameTxt.getText());
                        prs.setString(3,EditAdminUserController.getEditAdminUserController().miTxt.getText());
                        prs.setString(4,EditAdminUserController.getEditAdminUserController().contactTxt.getText());
                        prs.setString(5,EditAdminUserController.getEditAdminUserController().usernameTxt.getText());
                        prs.setBinaryStream(6,fileInputStream,EditAdminUserController.getEditAdminUserController().file.length());
                        prs.setInt(7,SettingsPageController.getSettingsPageController().getId());
                        prs.executeUpdate();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        connector.close(connection,prs,null);
                    }
                }else{
                    query = "update admin_tbl set firstname = ?, lastname = ?, mi = ?, contact = ?, username = ? where id = ?";
                    connection = connector.getConnection();
                    try{
                        prs = connection.prepareStatement(query);
                        prs.setString(1,EditAdminUserController.getEditAdminUserController().firstnameTxt.getText());
                        prs.setString(2,EditAdminUserController.getEditAdminUserController().lastnameTxt.getText());
                        prs.setString(3,EditAdminUserController.getEditAdminUserController().miTxt.getText());
                        prs.setString(4,EditAdminUserController.getEditAdminUserController().contactTxt.getText());
                        prs.setString(5,EditAdminUserController.getEditAdminUserController().usernameTxt.getText());
                        prs.setInt(6,SettingsPageController.getSettingsPageController().getId());
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
    public void student(String crud) throws SQLException, ClassNotFoundException, FileNotFoundException {
        switch (crud){
            case "create":

                fileInputStream = new FileInputStream(AddStudentController.getAddStudentController().file);
                query = "INSERT INTO student_tbl  VALUES (?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?);";
                connection = connector.getConnection();
                try{
                    prs = connection.prepareStatement(query);
                    prs.setString(1,null);
                    prs.setString(2,AddStudentController.getAddStudentController().studNumberTxt.getText());
                    prs.setString(3, AddStudentController.getAddStudentController().rfidTagIdTxt.getText());
                    prs.setString(4,AddStudentController.getAddStudentController().studFullnameTxt.getText());
                    prs.setString(5,AddStudentController.getAddStudentController().yearTxt.getText());
                    prs.setString(6,AddStudentController.getAddStudentController().sectionTxt.getText());
                    prs.setString(7,AddStudentController.getAddStudentController().courseTxt.getText());
                    prs.setString(8,AddStudentController.getAddStudentController().strandTxt.getText());
                    prs.setString(9,AddStudentController.getAddStudentController().studDeptComboBox.getSelectionModel().getSelectedIndex()+1+"");
                    prs.setString(10,AddStudentController.getAddStudentController().studContact.getText());
                    prs.setBinaryStream(11,fileInputStream,AddStudentController.getAddStudentController().file.length());
                    prs.setString(12,AddStudentController.getAddStudentController().parentFullnameTxt.getText());
                    prs.setString(13,AddStudentController.getAddStudentController().parentContactTxt.getText());
                    prs.setString(14,AddStudentController.getAddStudentController().parentAddressTxt.getText());
                    prs.executeUpdate();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    connector.close(connection,prs,null);
                }
                break;
            case "update":
                if(EditStudentController.getEditStudentController().file != null){
                    fileInputStream = new FileInputStream(EditStudentController.getEditStudentController().file);
                    query = "update student_tbl set student_id = ? , rfid_tag_id = ? , student_name = ?, student_year = ?, student_section = ? , student_course = ? , student_strand =?, student_department = ?, student_image = ?, parent_fullname = ?, parent_contact = ? ,parent_address =?, student_contact = ? where id = ?";
                    connection = connector.getConnection();
                    try{
                        prs = connection.prepareStatement(query);
                        prs.setInt(1, Integer.parseInt(EditStudentController.getEditStudentController().studNumberTxt.getText()));
                        prs.setInt(2, Integer.parseInt(EditStudentController.getEditStudentController().rfidTagIdTxt.getText()));
                        prs.setString(3,EditStudentController.getEditStudentController().studFullnameTxt.getText());
                        prs.setString(4,EditStudentController.getEditStudentController().yearTxt.getText());
                        prs.setString(5,EditStudentController.getEditStudentController().sectionTxt.getText());
                        prs.setString(6,EditStudentController.getEditStudentController().courseTxt.getText());
                        prs.setString(7,EditStudentController.getEditStudentController().strandTxt.getText());
                        prs.setString(8,EditStudentController.getEditStudentController().studDeptComboBox.getSelectionModel().getSelectedIndex()+1+"");
                        prs.setBinaryStream(9,fileInputStream,EditStudentController.getEditStudentController().file.length());
                        prs.setString(10,EditStudentController.getEditStudentController().parentFullnameTxt.getText());
                        prs.setString(11,EditStudentController.getEditStudentController().parentContactTxt.getText());
                        prs.setString(12,EditStudentController.getEditStudentController().parentAddressTxt.getText());
                        prs.setString(13,EditStudentController.getEditStudentController().studContact.getText());
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
                        prs.setInt(1, Integer.parseInt(EditStudentController.getEditStudentController().studNumberTxt.getText()));
                        prs.setInt(2, Integer.parseInt(EditStudentController.getEditStudentController().rfidTagIdTxt.getText()));
                        prs.setString(3,EditStudentController.getEditStudentController().studFullnameTxt.getText());
                        prs.setString(4,EditStudentController.getEditStudentController().yearTxt.getText());
                        prs.setString(5,EditStudentController.getEditStudentController().sectionTxt.getText());
                        prs.setString(6,EditStudentController.getEditStudentController().courseTxt.getText());
                        prs.setString(7,EditStudentController.getEditStudentController().strandTxt.getText());
                        prs.setString(8,EditStudentController.getEditStudentController().studDeptComboBox.getSelectionModel().getSelectedIndex()+1+"");
                        prs.setString(9,EditStudentController.getEditStudentController().parentFullnameTxt.getText());
                        prs.setString(10,EditStudentController.getEditStudentController().parentContactTxt.getText());
                        prs.setString(11,EditStudentController.getEditStudentController().parentAddressTxt.getText());
                        prs.setString(12,EditStudentController.getEditStudentController().studContact.getText());
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


    public ObservableList<adminUserTable> getAdminData(String query){
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
                list.add(new adminUserTable(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }
    public ObservableList<studentTable> getStudentSearch(String query){
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
                list.add(new studentTable(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }
    public ObservableList<adminUserTable> getAdminSearch(String query){
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
                list.add(new adminUserTable(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }
    public ObservableList<studentTable> getStudentData(String query){
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
                list.add(new studentTable(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }
    public ObservableList<String> getStudentDepartmentComboBox(String query){
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
}
