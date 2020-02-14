package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class _alert {
    private static _alert instance;
    public _alert(){
        this.instance = this;
    }
    public static _alert get_alert(){
        return instance;
    }
    public boolean alertConfirmation(String headerText, String content){
        boolean isConfirm = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(headerText);
        alert.setContentText(content);
        Optional<ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK){
            isConfirm = true;
        }else{
            isConfirm = false;
        }
        return isConfirm;
    }
}
