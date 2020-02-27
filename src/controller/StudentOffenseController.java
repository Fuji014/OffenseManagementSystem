package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.tables.studentOffenseTable;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.URL;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentOffenseController implements Initializable {

    @FXML
    private JFXComboBox<String> searchComBox;

    @FXML
    private JFXTextField searchTxt;

    @FXML
    private JFXButton refreshBtn;

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
    private TableView<studentOffenseTable> studoffenseTableView;

    @FXML
    private TableColumn<studentOffenseTable, Integer> idCol;

    @FXML
    private TableColumn<studentOffenseTable, Integer> studidCol;

    @FXML
    private TableColumn<studentOffenseTable, Integer> offenseCol;

    @FXML
    private TableColumn<studentOffenseTable, String> severityCol;

    @FXML
    private TableColumn<studentOffenseTable, String> durationCol;

    @FXML
    private TableColumn<studentOffenseTable, String> completedCol;

    @FXML
    private TableColumn<studentOffenseTable, String> statusCol;

    @FXML
    private TableColumn<studentOffenseTable, String> countCol;

    @FXML
    private TableColumn<studentOffenseTable, String> remarksCol;

    @FXML
    private TableColumn<studentOffenseTable, String> dateCol;

    @FXML
    private ImageView imageView;

    @FXML
    private Label studentidLbl;

    @FXML
    private Label nameLbl;

    @FXML
    private Label yearLbl;

    @FXML
    private Label sectionLbl;

    @FXML
    private Label unservetimeLbl;

    @FXML
    private JFXButton searchtblBtn;

    @FXML
    private JFXTextField searchtblTxt;
    // declare var below
    private DatabaseAccessObject dao;
    private MainController mc;
    private String query,severity,duration,completed,status,count,remarks,date;
    public int id,student_key,offense_key,departmentId = HomePageController.getHomePageController().departmentId;
    private boolean isConfirm;
    private ResultSet rs;
    private Image image;
    private static StudentOffenseController instance;
    // end of declare var

    // initialize itself
    public StudentOffenseController(){
        this.instance = this;
    }
    public static StudentOffenseController getStudentOffenseController(){
        return instance;
    }
    // end of initialize itself

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchComBox.getItems().addAll("ID NUMBER","OFFENSE NUMBER","OFFENSE SEVERITY");
        // initialize class
        dao = new DatabaseAccessObject();
        mc = new MainController();
        // end of initialize class

        // methods
        initSearchTable();
        try {
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // end of methods

        // event button
        newBtn.setOnAction(event -> {
            try {
                mc.createPage(null, "/views/StudentOffenseAdd.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        updateBtn.setOnAction(event -> {
            editEvent();
            try {
                mc.createPage(null, "/views/StudentOffenseEdit.fxml");
            }catch (Exception e){
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
                searchTxt.setStyle("-jfx-unfocus-color: #b1b1b1;");
                refreshTable();
                clearLabel();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        searchBtn.setOnAction(event -> {
            try {
                initSearch();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        });
        // end of event button

    }

    // init
    public void initTable(){
        idCol.setCellValueFactory(cell -> cell.getValue().main_idProperty().asObject());
        studidCol.setCellValueFactory(cell -> cell.getValue().student_keyProperty().asObject());
        offenseCol.setCellValueFactory(cell -> cell.getValue().offense_keyProperty().asObject());
        severityCol.setCellValueFactory(cell -> cell.getValue().severityProperty());
        durationCol.setCellValueFactory(cell -> cell.getValue().durationProperty());
        completedCol.setCellValueFactory(cell -> cell.getValue().completedProperty());
        statusCol.setCellValueFactory(cell -> cell.getValue().statusProperty());
        countCol.setCellValueFactory(cell -> cell.getValue().countProperty());
        remarksCol.setCellValueFactory(cell -> cell.getValue().remarksProperty());
        dateCol.setCellValueFactory(cell -> cell.getValue().dateProperty());
    }
    public void refreshTable() throws SQLException {
        initTable();
        query = "select so.* from student_offense_tbl as so inner join offense_tbl as o on so.offense_key = o.id where o.dept_key = "+departmentId+"";
        studoffenseTableView.setItems(dao.getStudentOffenseData(query));
    }

    // end of init

    // custom methods
    public  void editEvent(){
        studentOffenseTable selected = studoffenseTableView.getSelectionModel().getSelectedItem();
        id = selected.main_idProperty().get();
        student_key = selected.student_keyProperty().get();
        offense_key = selected.offense_keyProperty().get();
        severity = selected.severityProperty().get();
        duration = selected.durationProperty().get();
        completed = selected.completedProperty().get();
        status = selected.statusProperty().get();
        count = selected.countProperty().get();
        remarks = selected.remarksProperty().get();
        date = selected.dateProperty().get();

    }

    public void initSearchTable(){
        searchtblTxt.textProperty().addListener((ObservableValue<? extends String> ob, String oldV, String newV) ->{
            String forSearchComboxValue = searchComBox.getSelectionModel().getSelectedItem();
            switch(forSearchComboxValue){
                case "ID NUMBER":
                    forSearchComboxValue = "student_key";
                    break;
                case "OFFENSE NUMBER":
                    forSearchComboxValue = "offense_key";
                    break;
                case "OFFENSE SEVERITY":
                    forSearchComboxValue = "s.offense_severity";
                    break;
            }
            initTable();
                query = "select s.* from student_offense_tbl as s inner join offense_tbl as o on s.offense_key = o.id where o.dept_key = "+departmentId+" and "+forSearchComboxValue+" like '%"+newV+"%'";
            try {
                studoffenseTableView.setItems(dao.getStudentOffenseData(query));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void initSearch() throws SQLException, IOException {
            if(searchTxt.getText().isEmpty()){
                searchTxt.setStyle("-jfx-unfocus-color: red;");
            }else{
                initTable();
                query = "select so.* from student_offense_tbl as so inner join offense_tbl as o on so.offense_key = o.id where o.dept_key = "+departmentId+" and student_key =  "+searchTxt.getText()+"";
                studoffenseTableView.setItems(dao.getStudentOffenseData(query));
                displayStudInfo(Integer.parseInt(searchTxt.getText()));
                searchTxt.setStyle("-jfx-unfocus-color: #b1b1b1;");
            }
    }

    public void deleteEvent() throws SQLException {
        alertConfirmation(null, "Are you sure you want to delete?");
        if(isConfirm){
            studentOffenseTable selected = studoffenseTableView.getSelectionModel().getSelectedItem();
            id = selected.main_idProperty().get();
            query = "delete from student_offense_tbl where std_offense_id = '"+id+"'";
            System.out.println(query);
            try {
                dao.saveData(query);
            }catch (Exception e){
                e.printStackTrace();
                _pushNotification.get_PushNotification().failed("Delete Failed", "Failed To Delete ID Number: "+id+e);
            }finally {
                refreshTable();
                _pushNotification.get_PushNotification().success("Delete Success", "Successfully Deleted ID Number: "+id);
            }
        }
    }

    public void displayStudInfo(int student_key) throws SQLException, IOException {
        query = "select s.student_id,s.student_name,s.student_year,s.student_section,s.student_image from student_tbl as s inner join student_offense_tbl as so on s.student_id = so.student_key where so.offense_severity = 'minor' and s.student_department = "+departmentId+" and s.student_id = "+student_key+" limit 1";
        rs = dao.getStudentInfoDetails(query);
        if(rs.next()){
                studentidLbl.setText(rs.getString("student_id"));
                nameLbl.setText(rs.getString("student_name"));
                yearLbl.setText(rs.getString("student_year"));
                sectionLbl.setText(rs.getString("student_section"));
                // for image processing
                InputStream is = rs.getBinaryStream("student_image");
                OutputStream os = new FileOutputStream(new File("photo.jpg"));
                byte[] contents = new byte[1024];
                int size = 0;
                while((size = is.read(contents)) !=-1){
                    os.write(contents,0,size);
                }
                image = new Image("file:photo.jpg",imageView.getFitWidth(),imageView.getFitHeight(),true,true);
                imageView.setImage(image);
            // for computing unserve time and dumpimg
            query = "select so.offense_duration from student_tbl as s inner join student_offense_tbl as so on s.student_id = so.student_key where so.offense_severity = 'minor' and so.offense_status = 'not Complete' and s.student_department = "+departmentId+" and s.student_id = "+student_key+"";
            rs = dao.getStudentInfoDetails(query);
            ArrayList<String> arrayList = new ArrayList<>();
            while (rs.next()){
                arrayList.add(rs.getString(1));
            }
            System.out.println(arrayList);
            int total1 = 0;
            for(String test1 : arrayList){
                total1 += getTotalMinutes(test1);
            }
            // for completedTime
            query = "select so.offense_completedTime from student_tbl as s inner join student_offense_tbl as so on s.student_id = so.student_key where so.offense_severity = 'minor' and so.offense_status = 'not Complete' and s.student_department = "+departmentId+" and s.student_id = "+student_key+"";
            rs = dao.getStudentInfoDetails(query);
            ArrayList<String> arrayList1 = new ArrayList<>();
            while (rs.next()){
                arrayList1.add(rs.getString(1));
            }
            System.out.println(arrayList1);
            int total2 = 0;
            for(String test2 : arrayList1){
                total2 += getTotalMinutes(test2);
            }
            int diff = total1 - total2;
            String result = getResult(diff);
            unservetimeLbl.setText(result);
        }else{
            studentidLbl.setText("Not found");
            nameLbl.setText("Not found");
            yearLbl.setText("Not found");
            sectionLbl.setText("Not found");
            unservetimeLbl.setText("Not found");
            imageView.setImage(null);
        }

    }
    public int getTotalMinutes(String time) {
        String[] t = time.split(":");
        return Integer.valueOf(t[0]) * 60 + Integer.valueOf(t[1]);
    }

    public String getResult(int total) {
        int minutes = total % 60;
        int hours = ((total - minutes) / 60);
        return String.format("%02d:%02d", hours, minutes);
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

    public void clearLabel(){
        studentidLbl.setText("");
        nameLbl.setText("");
        yearLbl.setText("");
        sectionLbl.setText("");
        unservetimeLbl.setText("");
        imageView.setImage(null);
    }

    // end of custom methods

    // getters

    public String getSeverity() {
        return severity;
    }

    public String getDuration() {
        return duration;
    }

    public String getCompleted() {
        return completed;
    }

    public String getStatus() {
        return status;
    }

    public String getCount() {
        return count;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public int getStudent_key() {
        return student_key;
    }

    public int getOffense_key() {
        return offense_key;
    }

    // end of getters
}
