package controller.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sun.java2d.pipe.SpanShapeRenderer;

public class StudentOffenseSearchOffenseTable {
    private final StringProperty offeseDescription;
    private final StringProperty offeseSeverity;
    private final StringProperty deptName;
    private final IntegerProperty offenseId;
    private final StringProperty offenseSanction;

    public StudentOffenseSearchOffenseTable(String offenseDescription,String offenseSeverity,String deptName,int offenseId,String offenseSanction){
        this.offeseDescription = new SimpleStringProperty(offenseDescription);
        this.offeseSeverity = new SimpleStringProperty(offenseSeverity);
        this.deptName = new SimpleStringProperty(deptName);
        this.offenseId = new SimpleIntegerProperty(offenseId);
        this.offenseSanction = new SimpleStringProperty(offenseSanction);
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

    public StringProperty offenseSanctionProperty() {
        return offenseSanction;
    }
}
