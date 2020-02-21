package controller;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class _Gsm implements Initializable {
    private SerialPort serialPort;
    private String query;
    private DatabaseAccessObject dao = new DatabaseAccessObject();
    private ResultSet rs;
    private boolean isPort = false;
    private static _Gsm instance;

    public _Gsm(){this.instance = this;}
    public static _Gsm get_Gsm(){return instance;}

    public boolean sendSMS(String student_key,String severity,String offense, String punishment,String remarks)  {
        serialPort = new SerialPort(HomePageController.getHomePageController().gsmport);
        try {
            serialPort.openPort();
            _pushNotification.get_PushNotification().success("Serial Port Status Connected!","You can now use this module");
            isPort = true;
            String student_name="",student_contact="",parent_fullname="",parent_contact="",message="";
            query = "select student_id,student_name,student_contact,parent_fullname,parent_contact from student_tbl where student_id = "+student_key+"";
            System.out.println(query);
            rs = dao.getStudentInfoDetails(query);
            if(rs.next()){
                student_name = rs.getString("student_name");
                student_contact = rs.getString("student_contact");
                parent_fullname = rs.getString("parent_fullname");
                parent_contact = rs.getString("parent_contact");
            }
            // message = "Greetings Mr/Ms. "+parent_fullname+", This message is to inform you that "+student_name+", with ID number "+student_key+" has committed a violation against the school's policy. He/She violated a "+severity+" offense under subjection "+offense+", and is subjected to "+punishment+". Remarks: "+remarks+". For more details and concerns, Please contact us at 09087118184. Thankyou";
            if (serialPort.isOpened()) { // check if open
                System.out.println("Port is open :)");
            } else {
                System.out.println("Failed to open port :(");
            }
            try {
                switch (severity){
                    case "minor":
                        message = "Greetings! This message is to inform you that "+student_name+", has committed a minor offense and is sanctioned with Community Service. ";
                        break;
                    case "major":
                        message = "Greetings! We are to inform you that "+student_name+", has committed a major offense. Please expect a call or contact us at 9915668. Thank you";
                        break;
                    case "serious":
                        message = "Greetings! We are to inform you that "+student_name+", has committed a serious offense. Please expect a call or contact us at 9915668. Thank you";
                        break;
                }
                serialPort.setParams(SerialPort.BAUDRATE_9600,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
                String messageString1 = "AT";
                String messageString2 = "AT+CMGF=1";
//            String messageString2 = "AT+CPIN=\"7078\"";
                String messageString3 = "AT+CSCS=\"GSM\"";
                String messageString4 = "AT+CMGS=\"+63"+parent_contact+"\"";
                String messageString5 = message;
                char enter = 13;
                char CTRLZ = 26;

//                if(message.length() > 150){
//                    int count = 0;
//
//                    while(_Spliter.getSplit(message).size() > count) {
//                        serialPort.writeBytes((messageString1 + enter).getBytes());
//                        Thread.sleep(1000);
//                        serialPort.writeBytes((messageString2 + enter).getBytes());
//                        Thread.sleep(1000);
//                        serialPort.writeBytes((messageString3 + enter).getBytes());
//                        Thread.sleep(1000);
//                        serialPort.writeBytes((messageString4 + enter).getBytes());
//                        Thread.sleep(1000);
//                        serialPort.writeBytes(( _Spliter.getSplit(message).get(count) + CTRLZ).getBytes());
//                        Thread.sleep(1000);
//                        System.out.println("JEROMEEEeeeee...");
//                        Thread.sleep(1000);
//                        System.out.println("JEROMEEEeeeee... complete");
//                        Thread.sleep(1000);
//                        serialPort.addEventListener(new SerialPortReader());
//                        Thread.sleep(3000);
//                        count++;
//                    }
//                }else{
                    for(int i = 1; i <= 2; i++){
                        if(i == 1){
                            messageString4 = "AT+CMGS=\"+63"+parent_contact+"\"";
                        }else{
                            messageString4 = "AT+CMGS=\"+63"+student_contact+"\"";
                        }
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
                        Thread.sleep(1000);
                        System.out.println("JEROMEEEeeeee... complete");
                        serialPort.addEventListener(new SerialPortReader());
                        Thread.sleep(3000);
                    }
//                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                serialPort.closePort();
            }
        }catch (SerialPortException | SQLException e){
            e.printStackTrace();
            _pushNotification.get_PushNotification().failed("Failed To Connect Serial Port","Please Check your port settings");
            isPort = false;
        }finally {
            return true;
        }
    }

    public boolean response(String data){
        boolean status = true;
        if(data.contains("ERROR")){
            status = false;
        }
        return status;
    }

    // Port Listener
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // init class
        dao = new DatabaseAccessObject();
        // end of init class

        // init methods

        // end of init methods


    }
}
