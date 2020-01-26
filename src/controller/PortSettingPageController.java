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
        // end of initialize class

        // event button
        choosePortComboBox.setOnMouseClicked(event -> {
            choosePortComboBox.setItems(t.haha());
        });
        // end of event button
    }

}
