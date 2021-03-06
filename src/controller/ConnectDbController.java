package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import model.ConnectionHandler;

public class ConnectDbController implements Initializable {

    @FXML
    private JFXButton closeBtn;

    @FXML
    private JFXButton minimizeBtn;

    @FXML
    private JFXTextField passwordTxt;

    @FXML
    private JFXTextField serverTxt;

    @FXML
    private JFXTextField databaseTxt;

    @FXML
    private JFXTextField portTxt;

    @FXML
    private JFXTextField usernameTxt;

    @FXML
    private JFXButton connectBtn;

    @FXML
    private JFXButton cancelBtn;

    // Declare var below ;
    private MainController mc;
    private ConnectionHandler connector;
    private Connection connection;
    private String server,port,database,username,password;
    private static ConnectDbController instance;
    private _pushNotification _pushNotif;
    private DatabaseAccessObject dao = new DatabaseAccessObject();

    // create instance

    public ConnectDbController(){
        this.instance = this;
    }

    public static ConnectDbController getConnectDbController(){
        return instance;
    }

    // end of instance

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mc = new MainController();
        _pushNotif = new _pushNotification();
        connector = new ConnectionHandler();
        serverTxt.setText("localhost");
        portTxt.setText("3306");
        databaseTxt.setText("omsdb");
        usernameTxt.setText("root");
        passwordTxt.setText("");

        connectBtn.setOnAction(event -> { // event btn connectBtn
            try {
                connection = connector.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
                _pushNotification.get_PushNotification().failed("Error While Connecting To Database","Err "+e);
            } finally {
                _pushNotif.success("Database Connection Established","Connection Success, Connected to: "+connection);
                System.out.println(connection);
            }
        });
        cancelBtn.setOnAction(event -> {
            this.cancelBtn.getScene().getWindow().hide();
            try {
                mc.createPage(null,"/views/MainFxml.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public HashMap<String,String> getDatabaseInfo(){
       server = serverTxt.getText();
       port = portTxt.getText();
       database = databaseTxt.getText();
       username = usernameTxt.getText();
       password = passwordTxt.getText();

       HashMap<String, String> hashMap = new HashMap<String, String>();
       hashMap.put("server", server);
       hashMap.put("port", port);
       hashMap.put("database", database);
       hashMap.put("username", username);
        hashMap.put("password", password);
       return hashMap;
    }



}
