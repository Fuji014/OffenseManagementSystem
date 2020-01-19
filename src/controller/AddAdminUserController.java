package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.fxml.expression.BinaryExpression;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sun.misc.IOUtils;

import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class AddAdminUserController implements Initializable {

    @FXML
    private Label closeBtn;

    @FXML
    public JFXTextField firstnameTxt;

    @FXML
    public JFXTextField lastnameTxt;

    @FXML
    public JFXTextField miTxt;

    @FXML
    public JFXTextField contactTxt;

    @FXML
    public JFXPasswordField passwordTxt;

    @FXML
    public JFXTextField usernameTxt;

    @FXML
    private JFXPasswordField  confirmpassTxt;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private ImageView imagePreview;

    @FXML
    private JFXButton uploaderBtn;

    @FXML
    private AnchorPane apaneId;





    // Declare var below;
    private MainController mc;
    private DatabaseAccessObject dao;
    private AdminLoginController alc;
    private String query,firstname,lastname,mi,contact,username,password,confirmpassword,path;
    private Timestamp timestamp,datecreated;
    private FileChooser fileChooser;
    public File file;
    private Stage stage;
    private Image image;
    private FileInputStream fileInputStream;
    private InputStream inputStream;
    private Blob blob;
    private static AddAdminUserController instance;

    // create instance itself
    public AddAdminUserController(){ this.instance = this; }
    public static  AddAdminUserController getAddAdminUserController(){
        return instance;
    }


    // end instance itself

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initilize class
        mc = new MainController();
        dao = new DatabaseAccessObject();
        alc = new AdminLoginController();
        // end of initialization

        // initilize object
        initFileChooser();
        // end of initilize object

        // event buttons
        closeBtn.setOnMouseClicked(event -> {
            this.closeBtn.getScene().getWindow().hide();
        });

        saveBtn.setOnAction(event -> {
            try {
                saveEvent();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        uploaderBtn.setOnAction(event -> {
            uploaderEvent();
        });
    }


    public void saveEvent() throws SQLException, ClassNotFoundException, IOException {

        if(passwordTxt.getText().equals(confirmpassTxt.getText())){
            dao.admin("create");
            SettingsPageController.getSettingsPageController().refreshTable();
            alc.alertSuccess(null, "User Added Successfully ");
            clearFields();
        }else{
            alc.alertErr(null,"Password not match!");
        }

    }

    public void uploaderEvent(){
        stage = (Stage) apaneId.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        if(file != null){
            path = file.getAbsolutePath();
            image = new Image(file.getAbsoluteFile().toURI().toString(),imagePreview.getFitWidth(),imagePreview.getFitHeight(),true,true);
            imagePreview.setImage(image);
            imagePreview.setPreserveRatio(true);
        }
    }

    public void clearFields(){
        firstnameTxt.setText("");
        lastnameTxt.setText("");
        miTxt.setText("");
        contactTxt.setText("");
        usernameTxt.setText("");
        passwordTxt.setText("");
        confirmpassTxt.setText("");
        imagePreview.setImage(null);
    }

    public void initFileChooser(){
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files","*.*"),
                new FileChooser.ExtensionFilter("Images","*.png","*.jpg"),
                new FileChooser.ExtensionFilter("Text File","*.txt")
        );
    }


}
