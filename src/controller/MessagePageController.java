package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import jssc.SerialPort;
import jssc.SerialPortException;
//import model.ConnectionPort;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class MessagePageController implements Initializable {

    @FXML
    private JFXTextField numberTxt;

    @FXML
    private JFXTextArea messageTxt;

    @FXML
    private JFXButton sendBtn;

    @FXML
    private JFXButton resetBtn;
//
//    // declare var below
//    private ConnectionPort connectorPort;
//    private SerialPort connectionPort;
//    // end of declare var below


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // init class
//        connectorPort = new ConnectionPort();
        // end of init class

        // init methods
        // end of init methods

        // event buttons
        sendBtn.setOnAction(event -> {
            try {
                sendEvent();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        });
        resetBtn.setOnAction(event -> {
            clearFields();
        });
        // end of envent buttons

    }

    // init
    public void sendEvent() throws IOException, InterruptedException, SerialPortException {
        sendTest(numberTxt.getText(),messageTxt.getText());
        clearFields();
    }
    // end of init

    // custom methods
    public void clearFields(){
        messageTxt.setText("");
        numberTxt.setText("");
    }
    public void sendTest(String cpnumber, String data) throws IOException, InterruptedException, SerialPortException {
        SerialPort serialPort = new SerialPort(HomePageController.getHomePageController().gsmport);
        try {
            serialPort.isOpened();
            if(serialPort.isOpened()){
                _pushNotification.get_PushNotification().success("Serial Port Connection Stablished","You can send message now");
            }
            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            String messageString1 = "AT";
//            String messageString2 = "AT+CPIN=\"7078\"";
            String messageString3 = "AT+CSCS=\"GSM\"";
//            String messageString3 = "AT+CMGF=1";
            String messageString4 = "AT+CMGS=\"+63"+cpnumber+"\"";
            String messageString5 = data;
            char enter = 13;
            char CTRLZ = 26;

            if(data.length() > 150){
                int count = 0;

                while(_Spliter.getSplit(data).size() > count) {
                    serialPort.writeBytes((messageString1 + enter).getBytes());
                    Thread.sleep(1000);
                    serialPort.writeBytes((messageString3 + enter).getBytes());
                    Thread.sleep(1000);
                    serialPort.writeBytes((messageString4 + enter).getBytes());
                    Thread.sleep(1000);
                    serialPort.writeBytes(( _Spliter.getSplit(data).get(count) + CTRLZ).getBytes());
                    Thread.sleep(1000);
                    System.out.println("Wyslano wiadomosc");
                    Thread.sleep(3000);
//            serialPort.closePort();
                    System.out.println("Port COM zamkniety");
                    count++;
                }
            }else{
                serialPort.writeBytes((messageString1 + enter).getBytes());
                Thread.sleep(1000);
                serialPort.writeBytes((messageString3 + enter).getBytes());
                Thread.sleep(1000);
                serialPort.writeBytes((messageString4 + enter).getBytes());
                Thread.sleep(1000);
                serialPort.writeBytes((messageString5 + CTRLZ).getBytes());
                Thread.sleep(1000);
                System.out.println("Wyslano wiadomosc");
                Thread.sleep(3000);
                System.out.println("Port COM zamkniety");
            }
        }
        catch (SerialPortException ex) {
            _pushNotification.get_PushNotification().failed("Failed To Connect Serial Port","Please Check your port settings " +ex);
            System.out.println(ex);
        }
    }
    // end of custom methods
}
