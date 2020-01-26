package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    @FXML
    private JFXButton dashboardBtn;

    @FXML
    private JFXButton reportOffenseBtn;

    @FXML
    private JFXButton offenseListBtn;

    @FXML
    private JFXButton studentBtn;

    @FXML
    private JFXButton settingsBtn;

    @FXML
    private Text usernameTxt;

    @FXML
    private Pane apaneHolder;

    @FXML
    private JFXButton portSettingsBtn;

    @FXML
    private JFXButton departmentBtn;

    // Declare var below;
    AnchorPane home;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createPage(home,"/views/Dashboard.fxml");
        usernameTxt.setText(AdminLoginController.getAdminLoginController().getInfo().get("username"));

        // click event
        settingsBtn.setOnAction(event -> { // event btn settingsBtn
            createPage(home, "/views/SettingsPage.fxml");
        });
        studentBtn.setOnAction(event -> { // event btn studentBtn
            createPage(home, "/views/StudentPage.fxml");
        });
        portSettingsBtn.setOnAction(event -> {
            createPage(home, "/views/PortSettingPage.fxml");
        });
        departmentBtn.setOnAction(event -> {
            createPage(home, "/views/DepartmentPage.fxml");
        });
        offenseListBtn.setOnAction(event -> {
            createPage(home, "/views/OffensePage.fxml");
        });
    }


    private void setNode(Node node){
        apaneHolder.getChildren().clear();
        apaneHolder.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
    public void createPage(AnchorPane home, String loc) {
        try{
            home = FXMLLoader.load(getClass().getResource(loc));
            setNode(home);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
