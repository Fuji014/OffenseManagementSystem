package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PortTestController implements Initializable {

    @FXML
    private JFXComboBox<String> rfidComboBox;

    @FXML
    private JFXButton rfidBtn;

    @FXML
    private JFXComboBox<String> gsmComboBox;

    @FXML
    private JFXButton gsmBtn;

    @FXML
    private Label gsmLbl;

    @FXML
    private Label rfidLbl;

    // create var below
    private static PortTestController instance;
    private SerialPort serialPort;
    // end of var below

    // init itself
    public PortTestController(){
        this.instance = this;
    }
    public static PortTestController getPortTestController(){
        return instance;
    }
    // end of initself

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // init class
        // end of init class

        // init methods
        fillComboBox();
        // end of init methods

        // event btns
        rfidComboBox.setOnMouseClicked(event -> {
            rfidTest();
            PortTestController.getPortTestController();
        });
        gsmComboBox.setOnMouseClicked(event -> {
            gsmTest();
            PortTestController.getPortTestController();
        });
        rfidBtn.setOnAction(event -> {
            rfidEvent();
        });
        gsmBtn.setOnAction(event -> {
            gsmEvent();
        });
        // end of event btns
    }

    // custom methods
    public void gsmTest(){
        ObservableList<String> list = FXCollections.observableArrayList();
        gsmComboBox.getItems().clear();
        String[] portNames = SerialPortList.getPortNames();
        for(int i = 0; i < portNames.length; i++){
            list.add(portNames[i]);
        }
        gsmComboBox.setItems(list);
    }
    public void rfidTest(){
        ObservableList<String> list = FXCollections.observableArrayList();
        rfidComboBox.getItems().clear();
        String[] portNames = SerialPortList.getPortNames();
        for(int i = 0; i < portNames.length; i++){
            list.add(portNames[i]);
        }
        rfidComboBox.setItems(list);
    }
    public void rfidEvent(){
        String rfidPort =rfidComboBox.getSelectionModel().getSelectedItem();
        serialPort = new SerialPort(rfidPort);
        try {
            serialPort.openPort();
            _pushNotification.get_PushNotification().success("Serial Port Connect Successfully","You can use now RFID Module, Port Connected: "+rfidPort);
            rfidLbl.setText("Connected To Port Number: "+rfidPort);
            HomePageController.getHomePageController().rfidport = rfidPort;
            serialPort.closePort();
        }catch (SerialPortException e) {
            e.printStackTrace();
            _pushNotification.get_PushNotification().failed("Serial Port Error","Err "+e);
        }
        //Open serial port
    }
    public void gsmEvent(){
        String gsmPort =gsmComboBox.getSelectionModel().getSelectedItem();
        serialPort = new SerialPort(gsmPort);
        try {
            serialPort.openPort();
            _pushNotification.get_PushNotification().success("Serial Port Connect Successfully","You can use now GSM Module, Port Connected: "+gsmPort);
            gsmLbl.setText("Connected To Port Number: "+gsmPort);
            HomePageController.getHomePageController().gsmport = gsmPort;
            serialPort.closePort();
        }catch (SerialPortException e) {
            e.printStackTrace();
            _pushNotification.get_PushNotification().failed("Serial Port Error","Err "+e);
        }
        //Open serial port
    }
    public void fillComboBox(){
        String rfidPort="",gsmPort="";
        rfidPort = HomePageController.getHomePageController().rfidport;
        gsmPort = HomePageController.getHomePageController().gsmport;
        if(rfidPort == null){
            rfidLbl.setText("Not Connected");
        }else{
            rfidLbl.setText("Connected To Port Number: "+rfidPort);
        }
        if(gsmPort == null){
            gsmLbl.setText("Not Connected");
        }else{
            gsmLbl.setText("Connected To Port Number: "+gsmPort);
        }
        rfidComboBox.getSelectionModel().select(rfidPort);
        gsmComboBox.getSelectionModel().select(gsmPort);
    }
    // end of custom methods
}
