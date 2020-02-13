package controller.tables;

import com.sun.org.apache.xml.internal.utils.StringBufferPool;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class notificationmanageTable {
    private IntegerProperty notifID;
    private IntegerProperty notifStudID;
    private StringProperty notifDescription;
    private StringProperty notifStatus;

    public notificationmanageTable(int notifID, int notifStudID, String notifDescription, String notifStatus){
        this.notifID = new SimpleIntegerProperty(notifID);
        this.notifStudID = new SimpleIntegerProperty(notifStudID);
        this.notifDescription = new SimpleStringProperty(notifDescription);
        this.notifStatus = new SimpleStringProperty(notifStatus);
    }

    public int getNotifID() {
        return notifID.get();
    }

    public IntegerProperty notifIDProperty() {
        return notifID;
    }

    public int getNotifStudID() {
        return notifStudID.get();
    }

    public IntegerProperty notifStudIDProperty() {
        return notifStudID;
    }

    public String getNotifDescription() {
        return notifDescription.get();
    }

    public StringProperty notifDescriptionProperty() {
        return notifDescription;
    }

    public String getNotifStatus() {
        return notifStatus.get();
    }

    public StringProperty notifStatusProperty() {
        return notifStatus;
    }
}
