package controller.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentOffenseSearchOffenseTable {
    private final StringProperty offeseDescription;
    private final StringProperty offeseSeverity;
    private final StringProperty deptName;
    private final IntegerProperty offenseId;

    public StudentOffenseSearchOffenseTable(String offenseDescription,String offenseSeverity,String deptName,int offenseId){
        this.offeseDescription = new SimpleStringProperty(offenseDescription);
        this.offeseSeverity = new SimpleStringProperty(offenseSeverity);
        this.deptName = new SimpleStringProperty(deptName);
        this.offenseId = new SimpleIntegerProperty(offenseId);
    }

    public StringProperty offeseDescriptionProperty() {
        return offeseDescription;
    }

    public StringProperty offeseSeverityProperty() {
        return offeseSeverity;
    }

    public StringProperty deptNameProperty() {
        return deptName;
    }
    public IntegerProperty OffenseIdProperty(){
        return offenseId;
    }
}
