package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.tables.studentTable;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentPageController implements Initializable {
    @FXML
    private TableView<studentTable> studentTableView;

    @FXML
    private TableColumn<studentTable, Integer> idCol;

    @FXML
    private TableColumn<studentTable, String> rfidCol;

    @FXML
    private TableColumn<studentTable, Integer> studidCol;

    @FXML
    private TableColumn<studentTable, String> studnameCol;

    @FXML
    private TableColumn<studentTable, String> studyearCol;

    @FXML
    private TableColumn<studentTable, String> sectionCol;

    @FXML
    private TableColumn<studentTable, String> courseCol;

    @FXML
    private TableColumn<studentTable, String> strandCol;

    @FXML
    private TableColumn<studentTable, String> departmentCol;

    @FXML
    private TableColumn<studentTable, String> parentnameCol;

    @FXML
    private TableColumn<studentTable, String> parentcontactCol;

    @FXML
    private MenuItem updateBtn;

    @FXML
    private MenuItem deleteBtn;

    @FXML
    private JFXButton newBtn;

    @FXML
    private JFXButton searchBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXButton refreshBtn;

    @FXML
    private JFXComboBox<String> searchComboBox;

    @FXML
    private TableColumn<studentTable, String> studentcontactCol;

    @FXML
    private TableColumn<studentTable, String> parentaddressCol;

    @FXML
    private JFXTextField searchTxt;

    // Declare var below;
    private DatabaseAccessObject dao;
    private MainController mc;
    private String query,studentName,studentYear,studentDept,studentSection,studentCourse,studentStrand,parentName,parentContact,studentContact,parentAddress;
    private static StudentPageController instance;
    private boolean isConfirm;
    private int id,studId;
    private String rfid;
    private _pushNotification _pushNotif;
    private int departmentId = HomePageController.getHomePageController().departmentId;
    // end of declare var

    // initialize itself
    public StudentPageController(){
        this.instance = this;
    }
    public static StudentPageController getStudentPageController(){
        return instance;
    }
    // end of initialize itself


    // initializable
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchComboBox.getItems().addAll("STUDENT ID","RFID TAGID","STUDENT NAME","STUDENT YEAR","STUDENT SECTION","STUDENT COURSE","STUDENT STRAND","DEPT NAME","PARENT FULLNAME","PARENT CONTACT","PARENT ADDRESS","STUDENT CONTACT");
        // initalize class
        mc = new MainController();
        dao = new DatabaseAccessObject();
        _pushNotif= new _pushNotification();
        // end of initalize class

        // initialize method()
        try {
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initSearch();
        // end of initialize method()

        // event button
        newBtn.setOnAction(event -> {
            try {
                mc.createPage("Add User", "/views/StudentAdd.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        updateBtn.setOnAction(event -> {
            updateEvent();
            System.out.println(getStudentDept());
            try {
                mc.createPage(null, "/views/StudentEdit.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        deleteBtn.setOnAction(event -> {
            try {
                deleteEvent();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        refreshBtn.setOnAction(event -> {
            try {
                refreshTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // end of event button
    }

    // end of initializable

    public void initTable(){
        idCol.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
        rfidCol.setCellValueFactory(cell -> cell.getValue().studentRfidProperty());
        studidCol.setCellValueFactory(cell -> cell.getValue().studentIdProperty().asObject());
        studnameCol.setCellValueFactory(cell -> cell.getValue().studentNameProperty());
        studyearCol.setCellValueFactory(cell -> cell.getValue().studentYearProperty());
        sectionCol.setCellValueFactory(cell -> cell.getValue().studentSectionProperty());
        courseCol.setCellValueFactory(cell -> cell.getValue().studentCourseProperty());
        strandCol.setCellValueFactory(cell -> cell.getValue().studentStrandProperty());
        departmentCol.setCellValueFactory(cell -> cell.getValue().studentDepartmentProperty());
        parentnameCol.setCellValueFactory(cell -> cell.getValue().parentNameProperty());
        parentcontactCol.setCellValueFactory(cell -> cell.getValue().parentContactProperty());
        studentcontactCol.setCellValueFactory(cell -> cell.getValue().studentContactProperty());
        parentaddressCol.setCellValueFactory(cell -> cell.getValue().parentAdressProperty());
    }

    public void refreshTable() throws SQLException {
        initTable();
        query = "select s.id,s.rfid_tag_id,s.student_id,s.student_name,s.student_year,s.student_section,s.student_course,s.student_strand,d.dept_name,s.parent_fullname,s.parent_contact,s.student_contact,s.parent_address from student_tbl as s inner join department_tbl as d on s.student_department = d.id where s.student_department = "+departmentId+" order by id desc";
//        System.out.println(query);
        studentTableView.setItems(dao.getStudentData(query));
    }

    public void initSearch(){
        searchTxt.textProperty().addListener((ObservableValue<? extends String> ob, String oldV, String newV) ->{
            String forSearchComboxValue = searchComboBox.getSelectionModel().getSelectedItem();
            switch (forSearchComboxValue){
                case "STUDENT ID":
                    forSearchComboxValue = "STUDENT_ID";
                    break;
                case "RFID TAGID":
                    forSearchComboxValue = "RFID_TAG_ID";
                    break;
                case "STUDENT NAME":
                    forSearchComboxValue = "STUDENT_NAME";
                    break;
                case "STUDENT YEAR":
                    forSearchComboxValue = "STUDENT_YEAR";
                    break;
                case "STUDENT SECTION":
                    forSearchComboxValue = "STUDENT_SECTION";
                    break;
                case "STUDENT COURSE":
                    forSearchComboxValue = "STUDENT_COURSE";
                    break;
                case "STUDENT STRAND":
                    forSearchComboxValue = "STUDENT_STRAND";
                    break;
                case "DEPT NAME":
                    forSearchComboxValue = "DEPT_NAME";
                    break;
                case "PARENT FULLNAME":
                    forSearchComboxValue = "PARENT_FULLNAME";
                    break;
                case "PARENT CONTACT":
                    forSearchComboxValue = "PARENT_CONTACT";
                    break;
                case "PARENT ADDRESS":
                    forSearchComboxValue = "PARENT_ADDRESS";
                    break;
                case "STUDENT CONTACT":
                    forSearchComboxValue = "STUDENT_CONTACT";
                    break;
            }
            initTable();
            query = "select s.id,s.rfid_tag_id,s.student_id,s.student_name,s.student_year,s.student_section,s.student_course,s.student_strand,d.dept_name,s.parent_fullname,s.parent_contact,s.student_contact,s.parent_address from student_tbl as s inner join department_tbl as d on s.student_department = d.id where "+forSearchComboxValue+" like '%"+newV+"%' and student_department = "+departmentId+"";
            studentTableView.setItems(dao.getStudentSearch(query));
        });
    }

    public void deleteEvent() throws SQLException {
        alertConfirmation(null, "Are you sure you want to delete?");
        if(isConfirm){
            studentTable selected = studentTableView.getSelectionModel().getSelectedItem();
            id = selected.idProperty().get();
            query = "delete from student_tbl where id = '"+id+"'";
            try {
                dao.saveData(query);
            }catch (Exception e){
                e.printStackTrace();
                _pushNotif.failed("Delete Failed", "Failed to Delete ID Number: "+id+" "+e);
            }finally {
                refreshTable();
                _pushNotif.success("Delete Success", "Successfully Deleted ID NUmber: "+id);

            }
        }
    }

    public void updateEvent(){
        studentTable selected = studentTableView.getSelectionModel().getSelectedItem();
        id = selected.idProperty().get();
        studId = selected.studentIdProperty().get();
        rfid = selected.studentRfidProperty().get();
        studentName = selected.studentNameProperty().get();
        studentYear = selected.studentYearProperty().get();
        studentSection = selected.studentSectionProperty().get();
        studentCourse = selected.studentCourseProperty().get();
        studentStrand = selected.studentStrandProperty().get();
        studentDept = selected.studentDepartmentProperty().get();
        parentName = selected.parentNameProperty().get();
        parentContact = selected.parentContactProperty().get();
        studentContact = selected.studentContactProperty().get();
        parentAddress = selected.parentAdressProperty().get();

    }
    
    public void alertConfirmation(String headerText, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(headerText);
        alert.setContentText(content);
        Optional<ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK){
            isConfirm = true;
        }else{
            isConfirm = false;
        }
    }


    // getters for update
    public String getStudentContact() {
        return studentContact;
    }
    public String getParentAddress() {
        return parentAddress;
    }
    public String getStudentName(){
        return studentName;
    }
    public String getStudentYear() {
        return studentYear;
    }

    public String getStudentSection() {
        return studentSection;
    }

    public String getStudentCourse() {
        return studentCourse;
    }

    public String getStudentStrand() {
        return studentStrand;
    }

    public String getStudentDept() {
        return studentDept;
    }

    public String getParentName() {
        return parentName;
    }

    public String getParentContact() {
        return parentContact;
    }

    public int getId() {
        return id;
    }

    public String getRfid() {
        return rfid;
    }
    public int getStudId() {
        return studId;
    }
}
