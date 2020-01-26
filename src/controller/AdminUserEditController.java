package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
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

public class AdminUserEditController implements Initializable {

    @FXML
    private AnchorPane apaneEditUserId;

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
    public JFXTextField usernameTxt;

    @FXML
    private JFXPasswordField passwordTxt;

    @FXML
    private JFXPasswordField confirmpassTxt;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private ImageView imagePreview;

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
    private FileInputStream fileInputStream;
    private static AdminUserEditController instance;

    // create instance itself
    public AdminUserEditController(){this.instance = this;}
    public static AdminUserEditController getEditAdminUserController(){
        return instance;
    }
    // end instance itself

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initFillTextBox();
        // initialize class
        dao = new DatabaseAccessObject();
        alc = new AdminLoginController();
        connector = new ConnectionHandler();
        // end of initilization
        // initialize object
        initFileChooser();
        try {
            initShowImagePreview();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // end of initilize object
        // event button
        closeBtn.setOnMouseClicked(event -> {
            this.closeBtn.getScene().getWindow().hide();
        });
        saveBtn.setOnAction(event -> {
            try {
                editEvent();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        uploaderBtn.setOnAction(event -> {
            uploaderEvent();
        });
    }

    public void initFillTextBox(){
        firstnameTxt.setText(SettingsPageController.getSettingsPageController().getFirstname());
        lastnameTxt.setText(SettingsPageController.getSettingsPageController().getLastname());
        miTxt.setText(SettingsPageController.getSettingsPageController().getMi());
        contactTxt.setText(SettingsPageController.getSettingsPageController().getContact());
        usernameTxt.setText(SettingsPageController.getSettingsPageController().getUsername());
    }
    public void uploaderEvent(){
        stage = (Stage) apaneEditUserId.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        if(file != null){
            System.out.println(""+file.getAbsolutePath());
            InputStream is;
            image = new Image(file.getAbsoluteFile().toURI().toString(),imagePreview.getFitWidth(),imagePreview.getFitHeight(),true,true);
            imagePreview.setImage(image);
            imagePreview.setPreserveRatio(true);
        }
    }
    public void editEvent() throws FileNotFoundException {

        try {
            dao.admin("update");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            alc.alertSuccess(null, "Successfully Updated!");
            SettingsPageController.getSettingsPageController().refreshTable();
        }
    }

    public void initShowImagePreview() throws SQLException {
        id = SettingsPageController.getSettingsPageController().getId();
        try {
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        query = "select image from admin_tbl where id = '"+id+"'";
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


}
