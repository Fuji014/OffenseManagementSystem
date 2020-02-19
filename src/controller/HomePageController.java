package controller;

import app.Main;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import jssc.SerialPort;
import model.ConnectionHandler;


import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    @FXML
    private JFXButton dashboardBtn;

    @FXML
    private JFXButton reportOffenseBtn;

    @FXML
    private JFXButton offenseListBtn;

    @FXML
    private JFXButton studentBtn;

    @FXML
    private JFXButton settingsBtn;

    @FXML
    private JFXButton policyBtn;

    @FXML
    private Text usernameTxt;

    @FXML
    private Pane apaneHolder;

    @FXML
    private JFXButton portSettingsBtn;

    @FXML
    private JFXButton departmentBtn;

    @FXML
    private JFXButton scheduleBtn;

    @FXML
    private Circle myCircle;

    @FXML
    private JFXButton logoutBtn;

    @FXML
    private JFXButton smsBtn;

    @FXML
    private JFXButton notificationBtn;

    @FXML
    private Label notificationLbl;

    @FXML
    private JFXButton recordsBtn;

    @FXML
    private JFXButton exitBtn;

    @FXML
    private JFXButton minimizeBtn;




    // Declare var below;
    private ConnectionHandler connector;
    private Connection connection;
    private PreparedStatement prs;
    private ResultSet rs;
    private DatabaseAccessObject dao;
    private MainController mc;
    private String query;
    private Image image;
    AnchorPane home;
    private int id;
    public String rfidport,gsmport;
    private _alert _alert;
    public int departmentId = Integer.parseInt(AdminLoginController.getAdminLoginController().getInfo().get("department"));
    private static HomePageController instance;

    // init itself
    public HomePageController(){
        this.instance = this;
    }
    public static HomePageController getHomePageController(){
        return instance;
    }
    // end init itself

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createPage(home,"/views/Dashboard.fxml");
        usernameTxt.setText(AdminLoginController.getAdminLoginController().getInfo().get("username"));
//        System.out.println(AdminLoginController.getAdminLoginController().getInfo().get("id"));
        System.out.println(AdminLoginController.getAdminLoginController().getInfo().get("department"));
        // initalize class
        dao = new DatabaseAccessObject();
        mc = new MainController();
        _alert = new _alert();
        connector = new ConnectionHandler();
        notificationLbl.setText(Integer.toString(countNotification()));
        // end of initalize class

        // methods
        exitBtn.setOnAction(event -> {
            if(_alert.alertConfirmation("Are you sure you want to close?","Please make sure you save your work before exit.")){
                Platform.exit();
            }
        });

        notificationLbl.textProperty().addListener((ObservableValue<? extends String> ob, String oldV, String newV) -> {
            notificationLbl.setText(Integer.toString(countNotification()));
            if(Integer.parseInt(newV) > Integer.parseInt(oldV)){
                _pushNotification.get_PushNotification().information("New Notification","Please Check your Notification");
                NotificationManageController.getNotificationManageController().refreshTable();
            }

        });
        initClock();
        try {
            initShowImagePreview();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // end of methods

        // click event
        dashboardBtn.setOnAction(event -> { // event btn dashboard
            createPage(home,"/views/Dashboard.fxml");
        });
        policyBtn.setOnAction(event -> { // event btn policy
            createPage(home, "/views/PolicyPage.fxml");
        });
        smsBtn.setOnAction(event -> {
            createPage(home, "/views/MessagePage.fxml");
        });
        notificationBtn.setOnAction(event -> {
            createPage(home, "/views/NotificationManage.fxml");
        });
        recordsBtn.setOnAction(event -> {
            createPage(home, "/views/AttendancerecordsPage.fxml");
        });
//        notificationBtn.setOnAction(event -> {
//            try {
//                mc.createPage(null, "/views/NotificationPage.fxml");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
        settingsBtn.setOnAction(event -> { // event btn AdminUser
            createPage(home, "/views/AdminUserPage.fxml");
        });
        studentBtn.setOnAction(event -> { // event btn studentBtn
            createPage(home, "/views/StudentPage.fxml");
        });
        portSettingsBtn.setOnAction(event -> {
            createPage(home, "/views/PortTest.fxml");
//            createPage(home, "/views/PortSettingPage.fxml");
        });
        departmentBtn.setOnAction(event -> {
            createPage(home, "/views/DepartmentPage.fxml");
        });
        offenseListBtn.setOnAction(event -> {
            createPage(home, "/views/OffensePage.fxml");
        });
        scheduleBtn.setOnAction(event -> {
            createPage(home, "/views/ScheduleTest.fxml");
//            createPage(home, "/views/SchedulePage.fxml");
        });
        reportOffenseBtn.setOnAction(event -> {
            createPage(home, "/views/StudentOffensePage.fxml");
        });
        logoutBtn.setOnAction(event -> {
            this.logoutBtn.getScene().getWindow().hide();
            try {
                mc.createPage(null, "/views/AdminLoginFxml.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private void setNode(Node node){
        apaneHolder.getChildren().clear();
        apaneHolder.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
    public void createPage(AnchorPane home, String loc) {
        try{
            home = FXMLLoader.load(Main.class.getResource(loc));
            setNode(home);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//    public void viewNotification(){
//        try {
//            createPage(home, "/views/NotificationManage.fxml");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    // init
    public void initClock(){
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            notificationLbl.setText(Integer.toString(countNotification()));

        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    public int countNotification(){
        int count = 0;
        query = "select count(*) from notification_tbl where department_key = "+departmentId+" and status = 'unread'";
        try {
            count = dao.getCountNotification(query);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return count;
        }
    }
    public void initShowImagePreview() throws SQLException {
        try {
            connection = connector.getConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

        query = "select image from admin_tbl where username = '"+usernameTxt.getText()+"'";
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
                File file;
                OutputStream os = new FileOutputStream(new File("photo.jpg"));
                byte[] contents = new byte[1024];
                int size = 0;
                while((size = is.read(contents)) !=-1){
                    os.write(contents,0,size);
                }
                image = new Image("file:photo.jpg",false);
                myCircle.setFill(new ImagePattern(image));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.close(connection,prs,rs);
        }
    }
    // end of init
}
