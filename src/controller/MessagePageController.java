package controller;

//import com.fazecast.jSerialComm.SerialPort;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
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
        private SerialPort serialPort = new SerialPort("COM3");
//    // end of declare var below


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // init class
//        connectorPort = new ConnectionPort();
        // end of init class

        // init methods
        try {
            serialPort.openPort();//Open serial port
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
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

        if (serialPort.isOpened()) { // check if open
            System.out.println("Port is open :)");
        } else {
            System.out.println("Failed to open port :(");
            return;
        }

        try {

            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);

            String combineData = "AZ"+data+ "^"+cpnumber; // AZ add dummy test because of delay, ^ dont use symbol (^) seperator for number and content in arduino

//            if(data.length() > 150){
//                int count = 0;
//                while(_Spliter.getSplit(data).size() > count) {
//                    for (int i = 0; i < 2; i++) { // need to repeat because of lost of signal
//                        String testData = "AZ"+_Spliter.getSplit(data).get(count)+"^"+cpnumber;
//                        serialPort.writeBytes(testData.getBytes());//Write data to port
////                        serialPort.closePort();//Close serial port
////                        connectionPort.getOutputStream().write(testData.getBytes());
////                        connectionPort.getOutputStream().flush();
//                        System.out.println("Sent number: " + testData);
////                        Thread.sleep(2000);
//                    }
////                    Thread.sleep(10000);
//                    count++;
//                }
//            }else{
//                for (int i = 0; i < 2; i++) { // need to repeat because of lost of signal
//                    serialPort.writeBytes(combineData.getBytes());//Write data to port
////                    serialPort.closePort();//Close serial port
////                    connectionPort.getOutputStream().write(combineData.getBytes());
////                    connectionPort.getOutputStream().flush();
//                    System.out.println("Sent number: " + combineData);
////                    Thread.sleep(4000);
//                }
//            }
//            for (Integer i = 0; i < 5; ++i) {
//                serialPort.writeInt(i.byteValue());//Write data to port
//                serialPort.closePort();//Close serial port
////                connectionPort.getOutputStream().write(i.byteValue());
////                connectionPort.getOutputStream().flush();
//                System.out.println("Sent number: " + i);
//                Thread.sleep(1000);
//            }
            Platform.runLater(() -> {
                for(int z = 0; z <= 1; z++){
                    try {
                        serialPort.writeBytes(combineData.getBytes());//Write data to port
                    } catch (SerialPortException e) {
                        e.printStackTrace();
                    }
//                serialPort.closePort();//Close serial port
                    System.out.println("Sent number: " + combineData);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });


        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }


//        if (serialPort.isOpened()) {
//            System.out.println("Port is open :)");
//        } else {
//            System.out.println(" close port :(");
//            return;
//        }
    }
    // end of custom methods
}
