package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import java.net.URL;
import java.util.ResourceBundle;

public class PortSettingPageController implements Initializable {

    @FXML
    public JFXComboBox<String> choosePortComboBox;

    @FXML
    private JFXButton testButton;

    @FXML
    private JFXButton scanBtn;

    // declare var below
    private AdminLoginController alc;
    private static PortSettingPageController instance;
    static SerialPort serialPort = null;
    // create instance itself
    public PortSettingPageController(){ this.instance = this; }
    public static PortSettingPageController getPortSettingPageController(){
        return instance;
    }
    // end of create instance iteself


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // initialize class
        alc = new AdminLoginController();
        // end of initialize class

        // event button
        testButton.setOnAction(event -> {
            try {
                checkConnection();
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        });
        scanBtn.setOnAction(event -> {
            testEvent();
        });
        // end of event button
    }
    // initw
    public void testEvent(){
        String[] portNames = SerialPortList.getPortNames();
        String scanPort = "COM3" ;
        for(int i = 0; i < portNames.length; i++){
            System.out.println(portNames[i]);
            scanPort = portNames[i];
            choosePortComboBox.getSelectionModel().clearSelection();
            choosePortComboBox.getItems().addAll(scanPort);
        }


    }

    public void checkConnection() throws SerialPortException {
        openConnection();
        if(serialPort.isOpened()){
            alc.alertSuccess(null,"connect success");
        }else{
            alc.alertErr(null, "connection failed");
        }
    }

    public void openConnection() throws SerialPortException {
        serialPort = new SerialPort(choosePortComboBox.getSelectionModel().getSelectedItem());
        serialPort.openPort();//Open port
        serialPort.setParams(9600, 8, 1, 0);//Set params
        int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
        serialPort.setEventsMask(mask);//Set mask
    }
    // end of init

}
