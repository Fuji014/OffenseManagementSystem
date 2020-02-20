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
import model.ConnectionHandler;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentEditController implements Initializable {
    @FXML
    private AnchorPane apaneEditStudent;

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
    private ConnectionHandler connector;
    private Connection connection;
    private PreparedStatement prs;
    private ResultSet rs;
    private _pushNotification _pushNotif;
    private String query;
    private String firstname,lastname,mi,contact,username,datecreated;
    private int id;
    private DatabaseAccessObject dao;
    private AdminLoginController alc;
    private SerialPort serialPort = new SerialPort(HomePageController.getHomePageController().rfidport);
    private FileChooser fileChooser;
    public File file;
    private Stage stage;
    private Image image;
    private static StudentEditController instance;
    // End of declare var

    // initialize itself
    public StudentEditController(){
        this.instance = this;
    }
    public static StudentEditController getEditStudentController(){
        return instance;
    }
    // end of initialize itself

    // initializable
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initFillTextBox();
        // initialize class
        dao = new DatabaseAccessObject();
        alc = new AdminLoginController();
        connector = new ConnectionHandler();
        _pushNotif = new _pushNotification();
        // end of initialize class

        // initialize method
        initFileChooser();
        try {
            initShowImagePreview();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initStudentDeptCombobox();
        // end of initialize method

        // event buttons
        uploaderBtn.setOnAction(event -> {
            uploaderEvent();
        });
        saveBtn.setOnAction(event -> {
            try {
                editEvent();
            } catch (FileNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        });
        studDeptComboBox.setOnMouseClicked(event -> {
            initStudentDeptCombobox();
        });
        cancelBtn.setOnAction(event -> {
            this.cancelBtn.getScene().getWindow().hide();
            if(serialPort.isOpened()){
                try {
                    serialPort.closePort();
                } catch (SerialPortException e) {
                    e.printStackTrace();
                }
            }
        });
        closeBtn.setOnMouseClicked(event -> {
            this.closeBtn.getScene().getWindow().hide();
            if(serialPort.isOpened()){
                try {
                    serialPort.closePort();
                } catch (SerialPortException e) {
                    e.printStackTrace();
                }
            }
        });
        scanBtn.setOnAction(event -> {
            scanEvent();
        });
        // end of event buttons
    }
    // End of initializable
    public void scanEvent(){
        System.out.println(HomePageController.getHomePageController().rfidport);
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

            System.out.println(ex);
            if(serialPort.isOpened()){
                _pushNotification.get_PushNotification().information("Port is Already Open", "You can Scan now");
            }else{
                _pushNotification.get_PushNotification().failed("Failed To Connect Serial Port","Please Check your port settings " +ex);
            }
        }
    }
    public void editEvent() throws FileNotFoundException, SQLException {
        try {
            dao.student("update");
        }catch (Exception e){
            e.printStackTrace();
            _pushNotif.failed("Update Failed", "Failed to Update "+e);
        }finally {
            _pushNotif.success("Update Success", "Successfully Updated");
//            alc.alertSuccess(null, "Successfully Updated!");
            StudentPageController.getStudentPageController().refreshTable();
        }
    }

    public void uploaderEvent(){
        stage = (Stage) apaneEditStudent.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        if(file != null){
            System.out.println(""+file.getAbsolutePath());
            InputStream is;
            image = new Image(file.getAbsoluteFile().toURI().toString(),imagePreview.getFitWidth(),imagePreview.getFitHeight(),true,true);
            imagePreview.setImage(image);
            imagePreview.setPreserveRatio(true);
        }
    }

    private void initStudentDeptCombobox(){
        studDeptComboBox.getSelectionModel().clearSelection();
        String query = "select * from department_tbl";
        studDeptComboBox.setItems(dao.getStudentDepartmentComboBox(query));
    }

    public void initShowImagePreview() throws SQLException {
        id = StudentPageController.getStudentPageController().getId();
        try {
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        query = "select student_image from student_tbl where id = '"+id+"'";
        try {
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            prs = connection.prepareStatement(query);
            rs = prs.executeQuery();
            while(rs.next()){
                InputStream is = rs.getBinaryStream(1);
                OutputStream os = new FileOutputStream(new File("photo.jpg"));
                byte[] contents = new byte[1024];
                int size = 0;
                while((size = is.read(contents)) !=-1){
                    os.write(contents,0,size);
                }
                image = new Image("file:photo.jpg",imagePreview.getFitWidth(),imagePreview.getFitHeight(),true,true);
                imagePreview.setImage(image);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,rs);
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

    public void initFillTextBox(){
        studNumberTxt.setText(Integer.toString(StudentPageController.getStudentPageController().getStudId()));
        rfidTagIdTxt.setText(StudentPageController.getStudentPageController().getRfid());
        studFullnameTxt.setText(StudentPageController.getStudentPageController().getStudentName());
        studContact.setText(StudentPageController.getStudentPageController().getParentContact());
        yearTxt.setText(StudentPageController.getStudentPageController().getStudentYear());
        sectionTxt.setText(StudentPageController.getStudentPageController().getStudentSection());
        courseTxt.setText(StudentPageController.getStudentPageController().getStudentCourse());
        strandTxt.setText(StudentPageController.getStudentPageController().getStudentStrand());
        studDeptComboBox.getSelectionModel().select(StudentPageController.getStudentPageController().getStudentDept());
        parentFullnameTxt.setText(StudentPageController.getStudentPageController().getParentName());
        parentContactTxt.setText(StudentPageController.getStudentPageController().getParentContact());
        parentAddressTxt.setText(StudentPageController.getStudentPageController().getParentAddress());
        studContact.setText(StudentPageController.getStudentPageController().getStudentContact());
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
