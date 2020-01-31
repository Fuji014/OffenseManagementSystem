package controller;

import controller.jserial.Test;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class PortSettingPageController implements Initializable {

    @FXML
    public JFXComboBox<String> choosePortComboBox;

    @FXML
    private JFXButton testButton;

    // declare var below
    private Test t;
    private AdminLoginController alc;
    private static PortSettingPageController instance;

    // create instance itself
    public PortSettingPageController(){ this.instance = this; }
    public static PortSettingPageController getPortSettingPageController(){
        return instance;
    }
    // end of create instance iteself


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // initialize class
        t = new Test();
        alc = new AdminLoginController();
        // end of initialize class

        // event button
        choosePortComboBox.setOnMouseClicked(event -> {
            choosePortComboBox.setItems(t.getOpenPort());
        });
        testButton.setOnAction(event -> {
            System.out.println(choosePortComboBox.getSelectionModel().getSelectedItem());
            testEvent();
        });
        // end of event button
    }
    // init
    public void testEvent(){
        if(t.getConnectionStatus()){
            alc.alertSuccess(null,"connect success");
        }else{
            alc.alertErr(null, "connection failed");
        }
    }
    // end of init

}
