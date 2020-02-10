package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static void main(String[] args){
        Application.launch(Main.class, new String[0]);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/MainFxml.fxml"));
        Scene sc = new Scene(root);
        primaryStage.setScene(sc);
            primaryStage.setTitle("Offense Management System");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }
}
