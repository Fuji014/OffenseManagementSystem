package controller;

import controller.jserial.Test;
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

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentAddController implements Initializable {
    @FXML
    private AnchorPane apaneAddStudent;

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
    private Test t;
    private DatabaseAccessObject dao;
    private AdminLoginController alc;
    private FileChooser fileChooser;
    public File file;
    private Stage stage;
    private Image image;
    private static StudentAddController instance;
    // end of var

    // create initialize itself
    public StudentAddController(){
        this.instance = this;
    }
    public static StudentAddController getAddStudentController(){
        return instance;
    }
    // end of initialize itself

    // start of initializable
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize class
        t = new Test();
        dao = new DatabaseAccessObject();
        alc = new AdminLoginController();

        // end of initialize class

        // initialize method
        initFileChooser();
        // end of initialize method

        // event btn
        uploaderBtn.setOnAction(event -> {
            uploaderEvent();
        });
        saveBtn.setOnAction(event -> {
            saveEvent();
        });
        studDeptComboBox.setOnMouseClicked(event -> {
            initStudentDeptCombobox();
        });
        closeBtn.setOnMouseClicked(event -> {
            this.closeBtn.getScene().getWindow().hide();
        });
        cancelBtn.setOnAction(event -> {
            this.cancelBtn.getScene().getWindow().hide();
        });
        scanBtn.setOnAction(event -> {
            t.haha();
        });
        // end of event  btn
    }
    // end of initializable

    public void saveEvent()  {
        try{
            dao.student("create");
            StudentPageController.getStudentPageController().refreshTable();
            clearFields();
        }catch (Exception e){
            alc.alertErr(null,"Exception Err"+e);
        }finally {
            alc.alertSuccess(null, "User Added Successfully ");
        }


    }


    public void uploaderEvent(){
        stage = (Stage) apaneAddStudent.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        if(file != null){
            image = new Image(file.getAbsoluteFile().toURI().toString(),imagePreview.getFitWidth(),imagePreview.getFitHeight(),true,true);
            imagePreview.setImage(image);
            imagePreview.setPreserveRatio(true);
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

    private void initStudentDeptCombobox(){
        studDeptComboBox.getSelectionModel().clearSelection();
        String query = "select * from department_tbl";
        studDeptComboBox.setItems(dao.getStudentDepartmentComboBox(query));
    }

    public void clearFields(){
        studNumberTxt.setText("");
        rfidTagIdTxt.setText("");
        studFullnameTxt.setText("");
        studContact.setText("");
        yearTxt.setText("");
        sectionTxt.setText("");
        courseTxt.setText("");
        strandTxt.setText("");
        studDeptComboBox.getSelectionModel().clearSelection();
        imagePreview.setImage(null);
        parentFullnameTxt.setText("");
        parentContactTxt.setText("");
        parentAddressTxt.setText("");

    }


}
