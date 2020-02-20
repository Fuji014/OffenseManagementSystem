package controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private JFXButton adminloginBtn;

    @FXML
    private JFXButton studentloginBtn;

    @FXML
    private JFXButton connectdbBtn;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private JFXButton minimizeBtn;

    // Declare var below
    private static MainController instance;

    // create instance
    public MainController(){
        this.instance = this;
    }
    public static MainController getMainController(){
        return instance;
    }
    // end of instance


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        connectdbBtn.setOnAction(event -> { // event btn connectBtn
            this.connectdbBtn.getScene().getWindow().hide();
            try {
                createPage("Connect Database","/views/ConnectDbFxml.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        minimizeBtn.setOnAction(event -> {
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setIconified(true);
        });

        adminloginBtn.setOnAction(event -> { // event btn adminloginBtn
            this.adminloginBtn.getScene().getWindow().hide();
            try{
                createPage("Admin Login","/views/AdminLoginFxml.fxml");
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        closeBtn.setOnAction(event -> { // event btn closeBtn
            Platform.exit();
        });
    }



    public void createPage(String title, String loc) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(loc));
        Scene sc = new Scene(root);
        sc.setFill(Color.TRANSPARENT);
        sc.getStylesheets().add(getClass().getResource("/styles/Global.css").toExternalForm());
        Stage secondaryStage = new Stage();
        secondaryStage.setScene(sc);
        secondaryStage.setTitle(title);
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.initStyle(StageStyle.TRANSPARENT);
        secondaryStage.show();
    }
}
