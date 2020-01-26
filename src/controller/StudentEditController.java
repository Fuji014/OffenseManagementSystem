package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.ConnectionHandler;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentEditController implements Initializable {
    @FXML
    private AnchorPane apaneEditStudent;

    @FXML
    private Label closeBtn;

    @FXML
    public JFXTextField studNumberTxt;

    @FXML
    public JFXTextField rfidTagIdTxt;

    @FXML
    private JFXButton scanBtn;

    @FXML
    public JFXTextField studFullnameTxt;

    @FXML
    public JFXTextField studContact;

    @FXML
    public JFXTextField yearTxt;

    @FXML
    public JFXTextField sectionTxt;

    @FXML
    public JFXTextField courseTxt;

    @FXML
    public JFXTextField strandTxt;

    @FXML
    public JFXComboBox<String> studDeptComboBox;

    @FXML
    public JFXTextField parentFullnameTxt;

    @FXML
    public JFXTextField parentContactTxt;

    @FXML
    public JFXTextArea parentAddressTxt;

    @FXML
    private ImageView imagePreview;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXButton uploaderBtn;

    // Declare var below
    private ConnectionHandler connector;
    private Connection connection;
    private PreparedStatement prs;
    private ResultSet rs;
    private String query;
    private String firstname,lastname,mi,contact,username,datecreated;
    private int id;
    private DatabaseAccessObject dao;
    private AdminLoginController alc;
    private FileChooser fileChooser;
    public File file;
    private Stage stage;
    private Image image;
    private static StudentEditController instance;
    // End of declare var

    // initialize itself
    public StudentEditController(){
        this.instance = this;
    }
    public static StudentEditController getEditStudentController(){
        return instance;
    }
    // end of initialize itself

    // initializable
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initFillTextBox();
        // initialize class
        dao = new DatabaseAccessObject();
        alc = new AdminLoginController();
        connector = new ConnectionHandler();
        // end of initialize class

        // initialize method
        initFileChooser();
        try {
            initShowImagePreview();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initStudentDeptCombobox();
        // end of initialize method

        // event buttons
        uploaderBtn.setOnAction(event -> {
            uploaderEvent();
        });
        saveBtn.setOnAction(event -> {
            try {
                editEvent();
            } catch (FileNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        });
        studDeptComboBox.setOnMouseClicked(event -> {
            initStudentDeptCombobox();
        });
        cancelBtn.setOnAction(event -> {
            this.cancelBtn.getScene().getWindow().hide();
        });
        closeBtn.setOnMouseClicked(event -> {
            this.closeBtn.getScene().getWindow().hide();
        });
        // end of event buttons
    }
    // End of initializable
    public void editEvent() throws FileNotFoundException, SQLException {
        try {
            dao.student("update");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            alc.alertSuccess(null, "Successfully Updated!");
            StudentPageController.getStudentPageController().refreshTable();
        }
    }

    public void uploaderEvent(){
        stage = (Stage) apaneEditStudent.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        if(file != null){
            System.out.println(""+file.getAbsolutePath());
            InputStream is;
            image = new Image(file.getAbsoluteFile().toURI().toString(),imagePreview.getFitWidth(),imagePreview.getFitHeight(),true,true);
            imagePreview.setImage(image);
            imagePreview.setPreserveRatio(true);
        }
    }

    private void initStudentDeptCombobox(){
        studDeptComboBox.getSelectionModel().clearSelection();
        String query = "select * from department_tbl";
        studDeptComboBox.setItems(dao.getStudentDepartmentComboBox(query));
    }

    public void initShowImagePreview() throws SQLException {
        id = StudentPageController.getStudentPageController().getId();
        try {
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        query = "select student_image from student_tbl where id = '"+id+"'";
        try {
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while(rs.next()){
                InputStream is = rs.getBinaryStream(1);
                File file;
                OutputStream os = new FileOutputStream(new File("photo.jpg"));
                byte[] contents = new byte[1024];
                int size = 0;
                while((size = is.read(contents)) !=-1){
                    os.write(contents,0,size);
                }
                image = new Image("file:photo.jpg",imagePreview.getFitWidth(),imagePreview.getFitHeight(),true,true);
                imagePreview.setImage(image);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,rs);
        }
    }

    public void initFileChooser(){
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files","*.*"),
                new FileChooser.ExtensionFilter("Images","*.png","*.jpg"),
                new FileChooser.ExtensionFilter("Text File","*.txt")
        );
    }

    public void initFillTextBox(){
        studNumberTxt.setText(Integer.toString(StudentPageController.getStudentPageController().getStudId()));
        rfidTagIdTxt.setText(Integer.toString(StudentPageController.getStudentPageController().getRfid()));
        studFullnameTxt.setText(StudentPageController.getStudentPageController().getStudentName());
        studContact.setText(StudentPageController.getStudentPageController().getParentContact());
        yearTxt.setText(StudentPageController.getStudentPageController().getStudentYear());
        sectionTxt.setText(StudentPageController.getStudentPageController().getStudentSection());
        courseTxt.setText(StudentPageController.getStudentPageController().getStudentCourse());
        strandTxt.setText(StudentPageController.getStudentPageController().getStudentStrand());
        studDeptComboBox.getSelectionModel().select(StudentPageController.getStudentPageController().getStudentDept());
        parentFullnameTxt.setText(StudentPageController.getStudentPageController().getParentName());
        parentContactTxt.setText(StudentPageController.getStudentPageController().getParentContact());
        parentAddressTxt.setText(StudentPageController.getStudentPageController().getParentAddress());
        studContact.setText(StudentPageController.getStudentPageController().getStudentContact());

    }
}
