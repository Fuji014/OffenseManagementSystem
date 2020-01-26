package controller.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class scheduleTable {
    private final IntegerProperty schedId;
    private final StringProperty deptName;
    private final StringProperty schedMonday;
    private final StringProperty schedTuesday;
    private final StringProperty schedWednesday;
    private final StringProperty schedThursday;
    private final StringProperty schedFriday;

    public scheduleTable(int schedId, String deptName, String schedMonday, String schedTuesday, String schedWednesday, String schedThursday, String schedFriday){
        this.schedId = new SimpleIntegerProperty(schedId);
        this.deptName = new SimpleStringProperty(deptName);
        this.schedMonday = new SimpleStringProperty(schedMonday);
        this.schedTuesday = new SimpleStringProperty(schedTuesday);
        this.schedWednesday = new SimpleStringProperty(schedWednesday);
        this.schedThursday = new SimpleStringProperty(schedThursday);
        this.schedFriday = new SimpleStringProperty(schedFriday);
    }


    public IntegerProperty schedIdProperty() {
        return schedId;
    }
    public StringProperty deptNameProperty() {
        return deptName;
    }
    public StringProperty schedMondayProperty() {
        return schedMonday;
    }
    public StringProperty schedTuesdayProperty() {
        return schedTuesday;
    }
    public StringProperty schedWednesdayProperty() {
        return schedWednesday;
    }
    public StringProperty schedThursdayProperty() {
        return schedThursday;
    }
    public StringProperty schedFridayProperty() {
        return schedFriday;
    }
}
