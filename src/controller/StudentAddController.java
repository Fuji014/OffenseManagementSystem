package controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentAddController implements Initializable {
    @FXML
    private AnchorPane apaneAddStudent;

    @FXML
    private Label closeBtn;

    @FXML
    public JFXTextField studNumberTxt;

    @FXML
    public JFXTextField rfidTagIdTxt;

    @FXML
    private JFXButton scanBtn;

    @FXML
    public JFXTextField studFullnameTxt;

    @FXML
    public JFXTextField studContact;

    @FXML
    public JFXTextField yearTxt;

    @FXML
    public JFXTextField sectionTxt;

    @FXML
    public JFXTextField courseTxt;

    @FXML
    public JFXTextField strandTxt;

    @FXML
    public JFXComboBox<String> studDeptComboBox;

    @FXML
    public JFXTextField parentFullnameTxt;

    @FXML
    public JFXTextField parentContactTxt;

    @FXML
    public JFXTextArea parentAddressTxt;

    @FXML
    private ImageView imagePreview;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXButton uploaderBtn;

    // Declare var below
    private DatabaseAccessObject dao;
    private AdminLoginController alc;
    private _pushNotification _pushNotif;
    private FileChooser fileChooser;
    public File file;
    private Stage stage;
    private Image image;
    private String query;
    private int departmentId = HomePageController.getHomePageController().departmentId;
    static SerialPort serialPort;
    private static StudentAddController instance;
    // end of var

    // create initialize itself
    public StudentAddController(){
        this.instance = this;
    }
    public static StudentAddController getAddStudentController(){
        return instance;
    }
    // end of initialize itself

    // start of initializable
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // initialize class
        dao = new DatabaseAccessObject();
        alc = new AdminLoginController();
        _pushNotif = new _pushNotification();

        // end of initialize class

        // initialize method
        initFileChooser();
        initStudentDeptCombobox();
        try {
            initFill();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // end of initialize method

        // event btn
        uploaderBtn.setOnAction(event -> {
            uploaderEvent();
        });
        saveBtn.setOnAction(event -> {
            try {
                saveEvent();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        studDeptComboBox.setOnMouseClicked(event -> {
            initStudentDeptCombobox();
        });
        closeBtn.setOnMouseClicked(event -> {
            this.closeBtn.getScene().getWindow().hide();
            try {
                if(serialPort.isOpened()){
                    serialPort.closePort();
                }
            } catch (SerialPortException e) {
                e.printStackTrace();
            }

        });
        cancelBtn.setOnAction(event -> {
            this.cancelBtn.getScene().getWindow().hide();
            try {
                if(serialPort.isOpened()){
                    serialPort.closePort();
                }
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        });
        scanBtn.setOnAction(event -> {
            try {
                initRfid();
            } catch (SerialPortException e) {
                e.printStackTrace();
            }

        });
        // end of event  btn
    }
    // end of initializable
    public void initRfid() throws SerialPortException {
        serialPort = new SerialPort(HomePageController.getHomePageController().gsmport);
        try {
            serialPort.openPort();//Open port
            _pushNotification.get_PushNotification().success("Serial Port Connection Stablished","You can scan now");
            serialPort.setParams(9600, 8, 1, 0);//Set params
            int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
            serialPort.setEventsMask(mask);//Set mask
            serialPort.addEventListener(
                    new SerialPortReader()
            );//Add SerialPortEventListener

        }
        catch (SerialPortException ex) {
            _pushNotification.get_PushNotification().failed("Failed To Connect Serial Port","Please Check your port settings " +ex);
            System.out.println(ex);
        }
    }

    public void saveEvent() throws SQLException {
        query = "select * from student_tbl where rfid_tag_id = '"+rfidTagIdTxt.getText()+"'";
        if(dao.getRfidCount(query) == 0 ){
            try{
                dao.student("create");
                StudentPageController.getStudentPageController().refreshTable();
                clearFields();
            }catch (Exception e){
                _pushNotif.failed("Insert Failed","Failed to Insert Student "+e);
//                alc.alertErr(null,"Exception Err"+e);
            }finally {
                _pushNotif.success("Insert Success", "Successfully Inserted");
//                alc.alertSuccess(null, "User Added Successfully ");
            }
        }else{
            alc.alertErr(null, "RFID already registered!");
        }



    }


    public void uploaderEvent(){
        stage = (Stage) apaneAddStudent.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        if(file != null){
            image = new Image(file.getAbsoluteFile().toURI().toString(),imagePreview.getFitWidth(),imagePreview.getFitHeight(),true,true);
            imagePreview.setImage(image);
            imagePreview.setPreserveRatio(true);
        }
    }

    public void initFileChooser(){
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files","*.*"),
                new FileChooser.ExtensionFilter("Images","*.png","*.jpg"),
                new FileChooser.ExtensionFilter("Text File","*.txt")
        );
    }
    public  void initFill() throws SQLException {
        query = "select dept_name from department_tbl WHERE id = "+departmentId+"";
        studDeptComboBox.getSelectionModel().select(dao.getDepartmentName(query).get("department"));
    }

    private void initStudentDeptCombobox(){
        studDeptComboBox.getSelectionModel().clearSelection();
        String query = "select * from department_tbl";
//        System.out.println(query);
        studDeptComboBox.setItems(dao.getStudentDepartmentComboBox(query));
    }
    // end of init

    // custom methods

    public void clearFields(){
        studNumberTxt.setText("");
        rfidTagIdTxt.setText("");
        studFullnameTxt.setText("");
        studContact.setText("");
        yearTxt.setText("");
        sectionTxt.setText("");
        courseTxt.setText("");
        strandTxt.setText("");
        studDeptComboBox.getSelectionModel().clearSelection();
        imagePreview.setImage(null);
        parentFullnameTxt.setText("");
        parentContactTxt.setText("");
        parentAddressTxt.setText("");

    }
    class SerialPortReader implements SerialPortEventListener {
        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR() && event.getEventValue()>0){//If data is available
                if(event.getEventValue() == 10){//Check bytes count in the input buffer
                    //Read data, if 10 bytes available
                    try {
                        byte buffer[] = serialPort.readBytes(event.getEventValue());
                        String str = new String(buffer).split("\n", 2)[0].replaceAll("\\s+", "");
                        int byteSize = 0;
                        try {
                            byteSize = str.getBytes("UTF-8").length;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        if (byteSize == 8){
                            System.out.println(str);
                            Thread.sleep(1000);
                            rfidTagIdTxt.setText(str);

                        }
                    }
                    catch (SerialPortException | InterruptedException ex) {
                        System.out.println(ex);
                    }
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
}
