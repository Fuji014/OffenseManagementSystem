package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminLoginController implements Initializable {
    @FXML
    private JFXTextField usernameTxt;

    @FXML
    private JFXPasswordField passwordTxt;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXButton cencelBtn;

    @FXML
    private ImageView loadingProg;

    // Declare var below ;
    private DatabaseAccessObject dao;
    private String query,username,password;
    private static AdminLoginController instance;
    private boolean isConfirm = false;

    // create instanace
    public AdminLoginController(){ this.instance = this; }
    public static AdminLoginController getAdminLoginController(){ return instance; }
    // end of instance

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize class
        dao = new DatabaseAccessObject();
        // end of initalize class
        loadingProg.setVisible(false);

        loginBtn.setOnAction(event -> {
            loadingProg.setVisible(true);
            PauseTransition pt = new PauseTransition();
            pt.setDuration(Duration.seconds(3));
            pt.setOnFinished(event1 -> {
                try {
                    initAdminLogin();
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            });
            pt.play();

        });

    }

    public void initAdminLogin() throws SQLException, IOException {
        username = usernameTxt.getText();
        password = passwordTxt.getText();
        query = "select * from admin_tbl where username = '"+username+"' and password = '"+password+"'";
        if(dao.getUserResult(query)){
            this.loginBtn.getScene().getWindow().hide();
            clearText();
            MainController.getMainController().createPage(null, "/views/HomePage.fxml");

        }else{
            clearText();
            alertErr(null,"Username Or Password Is Not Correct!");
            loadingProg.setVisible(false);

        }
    }

    public void alertErr(String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.show();

    }

    public void alertConfirmation(String headerText, String content){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(headerText);
        alert.setContentText(content);
        alert.showAndWait();
        Optional<ButtonType> action = alert.showAndWait();
        if(action.get() == ButtonType.OK){
            isConfirm = true;
        }else{
            isConfirm = false;
        }

    }
    public boolean getConfirmation(){
        return isConfirm;
    }

    public void alertSuccess(String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.show();
    }
    public void clearText(){
        usernameTxt.setText("");
        passwordTxt.setText("");
    }
    public HashMap<String, String> getInfo(){
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("username",username);
        return hashMap;
    }
}
