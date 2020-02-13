package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import jssc.SerialPort;
import jssc.SerialPortException;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class StudentOffenseAddController implements Initializable {

    @FXML
    public JFXTextField studentidTxt;

    @FXML
    private JFXButton studentIdSearch;

    @FXML
    public JFXTextField offensenameTxt;

    @FXML
    private JFXButton offensenameSearch;

    @FXML
    private JFXTextField offensecountTxt;

    @FXML
    private JFXTextField offensedateTxt;

    @FXML
    private JFXTextArea remarksTxt;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton offensecountBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXTextField durationTxt;

    @FXML
    private JFXTextField completedtimeTxt;

    @FXML
    private JFXTextField statusTxt;

    @FXML
    private JFXTextField severityTxt;

    @FXML
    private Label warningLbl;

    @FXML
    private Label warningLbl1;

    @FXML
    private JFXButton sendmessagesaveBtn;

    // declare var below;
    private DatabaseAccessObject dao;
    private MainController mc;
    private AdminLoginController alc;
    private static StudentOffenseAddController instance;
    private String query;
    private Timestamp timestamp;
    private ResultSet rs;
    public String offName,offSanction;
    private int departmentId = HomePageController.getHomePageController().departmentId;
    private SerialPort serialPort;
    // end of declare var below

    // create instance itself
    public StudentOffenseAddController(){
        this.instance = this;
    }
    public static StudentOffenseAddController getStudentOffenseAddController(){
        return instance;
    }
    // end of instance itself


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        warningLbl.setVisible(false);
        warningLbl1.setVisible(false);
        dateInit();
        // initialize class
        mc = new MainController();
        dao = new DatabaseAccessObject();
        alc = new AdminLoginController();
        // end of initialize class

        // methods

        // end of methods

        // event buttons
        studentIdSearch.setOnAction(event -> {
            try {
                mc.createPage(null, "/views/StudentOffenseSearchStudent.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        offensenameSearch.setOnAction(event -> {
            try {
                mc.createPage(null, "/views/StudentOffenseSearchOffense.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        cancelBtn.setOnAction(event -> {
            this.cancelBtn.getScene().getWindow().hide();
        });
        offensecountBtn.setOnAction(event -> {
            try {
                countEvent();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        saveBtn.setOnAction(event -> {
                saveEvent();
        });
        sendmessagesaveBtn.setOnAction(event -> {
            saveandsendsmsEvent();
        });

        // end of event buttons
    }

    // init
    public void saveEvent() {
        query = "insert into student_offense_tbl values (null,"+studentidTxt.getText()+","+offensenameTxt.getText()+","+offensecountTxt.getText()+",'"+severityTxt.getText()+"','"+durationTxt.getText()+"','00:00','not complete','"+remarksTxt.getText()+"','"+offensedateTxt.getText()+"')";
        System.out.println(query);
        try {
            dao.saveData(query);
            clearFields();
            StudentOffenseController.getStudentOffenseController().refreshTable();
            alc.alertSuccess(null,"Insert Successfully");
        }catch (Exception e){
            e.printStackTrace();
            alc.alertErr(null, "Err while Inserting"+e);
        }
    }
    public void saveandsendsmsEvent() {
        query = "insert into student_offense_tbl values (null,"+studentidTxt.getText()+","+offensenameTxt.getText()+","+offensecountTxt.getText()+",'"+severityTxt.getText()+"','"+durationTxt.getText()+"','00:00','not complete','"+remarksTxt.getText()+"','"+offensedateTxt.getText()+"')";
        try {
            dao.saveData(query);
            sendSMS(studentidTxt.getText(),severityTxt.getText(),offName,offSanction,remarksTxt.getText());
            clearFields();
            StudentOffenseController.getStudentOffenseController().refreshTable();
            alc.alertSuccess(null,"Insert Successfully");
        }catch (Exception e){
            e.printStackTrace();
            alc.alertErr(null, "Error while Inserting "+e);
        }
    }
    public void sendSMS(String student_key,String severity,String offense, String punishment,String remarks) throws SQLException, SerialPortException {
        serialPort = new SerialPort("COM5");
        serialPort.openPort();
        String student_name="",parent_fullname="",parent_contact="",message="";
        query = "select student_id,student_name,parent_fullname,parent_contact from student_tbl where student_id = "+student_key+"";
        System.out.println(query);
        rs = dao.getStudentInfoDetails(query);
        if(rs.next()){
            student_name = rs.getString("student_name");
            parent_fullname = rs.getString("parent_fullname");
            parent_contact = rs.getString("parent_contact");
        }
        message = "Greetings Mr/Ms. "+parent_fullname+", This message is to inform you that "+student_name+", with ID number "+student_key+" has committed a violation against the school's policy. He/She violated a "+severity+" offense under subjection "+offense+", and is subjected to "+punishment+". Remarks: "+remarks+". For more details and concerns, Please contact us at 09087118184. Thankyou";
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
            String messageString1 = "AT";
//            String messageString2 = "AT+CPIN=\"7078\"";
            String messageString3 = "AT+CSCS=\"GSM\"";
//            String messageString3 = "AT+CMGF=1";
            String messageString4 = "AT+CMGS=\"+63"+parent_contact+"\"";
            String messageString5 = message;
            char enter = 13;
            char CTRLZ = 26;

            if(message.length() > 150){
                int count = 0;

                while(_Spliter.getSplit(message).size() > count) {
                    serialPort.writeBytes((messageString1 + enter).getBytes());
                    Thread.sleep(1000);
                    serialPort.writeBytes((messageString3 + enter).getBytes());
                    Thread.sleep(1000);
                    serialPort.writeBytes((messageString4 + enter).getBytes());
                    Thread.sleep(1000);
                    serialPort.writeBytes(( _Spliter.getSplit(message).get(count) + CTRLZ).getBytes());
                    Thread.sleep(1000);
                    System.out.println("JEROMEEEeeeee...");
                    Thread.sleep(3000);
//            serialPort.closePort();
                    System.out.println("JEROMEEEeeeee... complete");
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
                System.out.println("JEROMEEEeeeee...");
                Thread.sleep(3000);
//            serialPort.closePort();
                System.out.println("JEROMEEEeeeee... complete");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            serialPort.closePort();
        }



    }

    public void dateInit(){
        timestamp = new Timestamp(System.currentTimeMillis());
        offensedateTxt.setText(String.valueOf(timestamp));
    }
    // end of initx

    // custom methods
    public void countEvent() throws SQLException {
        if(studentidTxt.getText() != "" && offensenameTxt.getText() != "") {
            // selct severity and dept key of offense_tbl
            String severity = "";
            int departmentKey = 0;
            query = "select offense_severity,dept_key from offense_tbl where id = "+offensenameTxt.getText()+" limit 1";
            rs = dao.getSeverityAndDeptKey(query);
            while (rs.next()){
                severity = rs.getString(1);
                departmentKey = rs.getInt(2);
            }

            // select max and duration from policy
            String penaltyDescription="",penaltyDuration = "";
            int offense_max = 0;
            query = "select offense_max,penalty_duration,penalty_description  from policy_tbl WHERE department_key = "+departmentKey+" and offense_severity = '"+severity+"'";
            rs = dao.getPolicyCountAndDuration(query);
            while (rs.next()){
                offense_max = rs.getInt(1);
                penaltyDuration = rs.getString(2);
                penaltyDescription = rs.getString(3);

            }

            // count in current table
            int countStudOffense = 0;
            query = "SELECT count(*) FROM student_offense_tbl as so inner join offense_tbl as o on so.offense_key = o.id where student_key = "+studentidTxt.getText()+" and offense_key = "+offensenameTxt.getText()+" and o.offense_severity = '"+severity+"'";
            countStudOffense = dao.officialCount(query) + 1;
            if(countStudOffense >= offense_max){
                durationTxt.setDisable(false);
                durationTxt.setText(penaltyDuration);
                severityTxt.setText(severity);
                offensecountTxt.setText("1");
                remarksTxt.setText(penaltyDescription);
                System.out.println(severity);
                if (severity.equals("minor")) {
                    System.out.println("true");
                    severityTxt.setText("major");
                    severityTxt.setStyle("-fx-text-inner-color: red;");
                    offensecountTxt.setText("1");
                    warningLbl.setText("Student ID "+studentidTxt.getText()+" Already Exceed maximum limit for minor offense ,");
                    warningLbl.setVisible(true);
                    warningLbl1.setVisible(true);
                }
                severityTxt.setStyle("-fx-text-inner-color: red;");
            }else {
                offensecountTxt.setText(Integer.toString(countStudOffense));
                durationTxt.setText(penaltyDuration);
                severityTxt.setText(severity);
                remarksTxt.setText(penaltyDescription);
            }
        }
    }

    public void clearFields(){
        studentidTxt.setText("");
        offensenameTxt.setText("");
        offensecountTxt.setText("");
        remarksTxt.setText("");
        severityTxt.setText("");
        durationTxt.setText("");
        completedtimeTxt.setText("");
        statusTxt.setText("");
    }

    public void initCount(){
        offensecountTxt.textProperty().addListener((ObservableValue<? extends String> ob, String oldV, String newV) -> {
            ///
        });
    }
    public void getSeverityDeptKey(){
        query = "select offense_severity,dept_key from offense_tbl where id = "+offensenameTxt.getText()+" limit 1";
    }
    // end of custom methods
}
