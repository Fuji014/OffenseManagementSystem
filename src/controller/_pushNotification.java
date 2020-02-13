package controller;

import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;



public class _pushNotification {
    private TrayNotification trayNotification;
    private AnimationType animationType;
    private static _pushNotification instance;
    // init itself
    public _pushNotification(){
        this.instance = this;
    }
    public static _pushNotification get_PushNotification(){return instance;}
    // end init itself
    public void success(String title,String message){
        trayNotification = new TrayNotification();
        animationType =  AnimationType.POPUP;

        trayNotification.setAnimationType(animationType);
        trayNotification.setTitle(title);
        trayNotification.setMessage(message);
        trayNotification.setNotificationType(NotificationType.SUCCESS);
        trayNotification.showAndDismiss(Duration.millis(3000));
    }
    public void failed(String title, String message){
        trayNotification = new TrayNotification();
        animationType =  AnimationType.POPUP;

        trayNotification.setAnimationType(animationType);
        trayNotification.setTitle(title);
        trayNotification.setMessage(message);
        trayNotification.setNotificationType(NotificationType.ERROR);
        trayNotification.showAndDismiss(Duration.millis(3000));
    }
    public void information(String title, String message){
        trayNotification = new TrayNotification();
        animationType =  AnimationType.POPUP;

        trayNotification.setAnimationType(animationType);
        trayNotification.setTitle(title);
        trayNotification.setMessage(message);
        trayNotification.setNotificationType(NotificationType.INFORMATION);
        trayNotification.showAndDismiss(Duration.millis(3000));
    }
}
