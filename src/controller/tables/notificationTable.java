package controller.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class notificationTable {
    private final IntegerProperty notifId;
    private final StringProperty notifDescription;
    private final StringProperty notifDate;

    public notificationTable(int notifId, String notifDescription, String notifDate){
        this.notifId = new SimpleIntegerProperty(notifId);
        this.notifDescription = new SimpleStringProperty(notifDescription);
        this.notifDate = new SimpleStringProperty(notifDate);
    }

    public int getNotifId() {
        return notifId.get();
    }

    public IntegerProperty notifIdProperty() {
        return notifId;
    }

    public String getNotifDescription() {
        return notifDescription.get();
    }

    public StringProperty notifDescriptionProperty() {
        return notifDescription;
    }

    public String getNotifDate() {
        return notifDate.get();
    }

    public StringProperty notifDateProperty() {
        return notifDate;
    }
}
