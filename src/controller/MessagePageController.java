package controller;

//import com.fazecast.jSerialComm.SerialPort;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
            }
        });
        // end of envent buttons

    }

    // init
    public void sendEvent() throws IOException, InterruptedException {
//        sendTest(numberTxt.getText(),messageTxt.getText());
        clearFields();
    }
    // end of init

    // custom methods
    public void clearFields(){
        messageTxt.setText("");
        numberTxt.setText("");
    }
//    public void sendTest(String cpnumber, String data) throws IOException, InterruptedException {
//        connectionPort = connectorPort.getSerialPort();
//        connectionPort.openPort();
//        connectionPort.setComPortParameters(9600, 8, 1, 0);
//        connectionPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
//
//        if (connectionPort.openPort()) { // check if open
//            System.out.println("Port is open :)");
//        } else {
//            System.out.println("Failed to open port :(");
//            return;
//        }
//        String combineData = "AZ"+data+ "^"+cpnumber; // AZ add dummy test because of delay, ^ dont use symbol (^) seperator for number and content in arduino
//
//        if(data.length() > 150){
//            Iterator iterator = _Spliter.getSplit(data).iterator();
//            int count = 0;
//            while(_Spliter.getSplit(data).size() > count) {
//                for (int i = 0; i < 2; i++) { // need to repeat because of lost of signal
//                    String testData = "AZ"+_Spliter.getSplit(data).get(count)+"^"+cpnumber;
//                    connectionPort.getOutputStream().write(testData.getBytes());
//                    connectionPort.getOutputStream().flush();
//                    System.out.println("Sent number: " + testData);
//                    Thread.sleep(2000);
//                }
//                Thread.sleep(10000);
//                count++;
//            }
//        }else{
//            for (int i = 0; i < 2; i++) { // need to repeat because of lost of signal
//                connectionPort.getOutputStream().write(combineData.getBytes());
//                connectionPort.getOutputStream().flush();
//                System.out.println("Sent number: " + combineData);
//                Thread.sleep(4000);
//            }
//        }
//        for (Integer i = 0; i < 5; ++i) {
//            connectionPort.getOutputStream().write(i.byteValue());
//            connectionPort.getOutputStream().flush();
//            System.out.println("Sent number: " + i);
//            Thread.sleep(1000);
//        }
//        connectionPort.getOutputStream().write(combineData.getBytes());
//                connectionPort.getOutputStream().flush();
//                System.out.println("Sent number: " + combineData);
//                Thread.sleep(1000);




//        if (connectionPort.closePort()) {
//            System.out.println("Port is closed :)");
//        } else {
//            System.out.println("Failed to close port :(");
//            return;
//        }
//    }
    // end of custom methods
}
