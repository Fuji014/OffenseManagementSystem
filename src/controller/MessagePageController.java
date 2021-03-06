package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
//import model.ConnectionPort;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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

//    declare var below
//    private ConnectionPort connectorPort;

    private static SerialPort serialPort;
    private _alert _alertBox;
    private _pushNotification _pushNotif;
    private boolean status;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        // init class
    
        _alertBox = new _alert();
    
        _pushNotif = new _pushNotification();

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

    public void sendTest(String cpnumber, String data) throws InterruptedException, SerialPortException {
        serialPort = new SerialPort(HomePageController.getHomePageController().gsmport);
        System.out.println(HomePageController.getHomePageController().gsmport);
        try {
            serialPort.openPort();
            if(serialPort.isOpened()){
                _pushNotification.get_PushNotification().success("Serial Port Connection Stablished","You can send message now");
            }
            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
            serialPort.setEventsMask(mask);//Set mask
            String messageString1 = "AT";
            String messageString2 = "AT+CMGF=1";
//            String messageString2 = "AT+CPIN=\"7078\"";
            String messageString3 = "AT+CSCS=\"GSM\"";
            String messageString4 = "AT+CMGS=\"+63"+cpnumber+"\"";
            String messageString5 = data;
            char enter = 13;
            char CTRLZ = 26;

            if(data.length() > 150){
                int count = 0;
                while(_Spliter.getSplit(data).size() > count) {
                    serialPort.writeBytes((messageString1 + enter).getBytes());
                    Thread.sleep(1000);
                    serialPort.writeBytes((messageString2 + enter).getBytes());
                    Thread.sleep(1000);
                    serialPort.writeBytes((messageString3 + enter).getBytes());
                    Thread.sleep(1000);
                    serialPort.writeBytes((messageString4 + enter).getBytes());
                    Thread.sleep(1000);
                    serialPort.writeBytes(( _Spliter.getSplit(data).get(count) + CTRLZ).getBytes());
                    Thread.sleep(1000);
                    System.out.println("JEROMEee..");
                    Thread.sleep(3000);
                    System.out.println("JEROMEee... done");
                    Thread.sleep(1000);
                    serialPort.addEventListener(new SerialPortReader());
                    Thread.sleep(3000);
                    count++;
                }
            }else{
                serialPort.writeBytes((messageString1 + enter).getBytes());
                Thread.sleep(1000);
                serialPort.writeBytes((messageString2 + enter).getBytes());
                Thread.sleep(1000);
                serialPort.writeBytes((messageString3 + enter).getBytes());
                Thread.sleep(1000);
                serialPort.writeBytes((messageString4 + enter).getBytes());
                Thread.sleep(1000);
                serialPort.writeBytes((messageString5 + CTRLZ).getBytes());
                Thread.sleep(1000);
                System.out.println("JEROMEEEeeeee...");
                Thread.sleep(100);
                System.out.println("JEROMEEEeeeee... complete");
                Thread.sleep(1000);
                serialPort.addEventListener(new SerialPortReader());
                Thread.sleep(3000);
            }
        }
        catch (SerialPortException ex) {
            _pushNotification.get_PushNotification().failed("Failed To Connect Serial Port","Please Check your port settings " +ex);
            System.out.println(ex);
        }finally {
            serialPort.closePort();
        }
    }

    public void response(String data){
        if(data.contains("ERROR")){
            _pushNotif.failed("Failed","Message Failed to Sent");
        }else{
            _pushNotif.success("Sent","Message Sent Success");
        }
    }

    class SerialPortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR() && event.getEventValue() > 0){
                    try {
                        byte buffer[] = serialPort.readBytes(event.getEventValue());
                        String receivedData = new String(buffer, StandardCharsets.UTF_8);

                            Thread.sleep(4000);
                             System.out.println(receivedData.length());
                             System.out.println(receivedData);
                            Platform.runLater(new Runnable() {
                                @Override   
                                public void run() {
                                    response(receivedData);
                                }
                            });
                    }
                    catch (SerialPortException | InterruptedException ex) {
                        System.out.println(ex);
                    }
            }
            else if(event.isCTS()){//If CTS line has changed state
                if(event.getEventValue() == 1){//If line is ON
                    System.out.println("CTS - ON");
                }
                else {
                    System.out.println("CTS - OFF");
                }
            }
            else if(event.isDSR()){///If DSR line has changed state
                if(event.getEventValue() == 1){//If line is ON
                    System.out.println("DSR - ON");
                }
                else {
                    System.out.println("DSR - OFF");
                }
            }
        }
    }
    // end of custom methods
}
