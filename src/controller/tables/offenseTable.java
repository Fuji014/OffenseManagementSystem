package controller.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class offenseTable {
    private final IntegerProperty offenseId;
    private final StringProperty offenseDesc;
    private final StringProperty offenseSeverity;
    private final StringProperty deptName;

    public offenseTable(int offenseId, String offenseDesc, String offenseSeverity, String deptName){
        this.offenseId = new SimpleIntegerProperty(offenseId);
        this.offenseDesc = new SimpleStringProperty(offenseDesc);
        this.offenseSeverity = new SimpleStringProperty(offenseSeverity);
        this.deptName = new SimpleStringProperty(deptName);
    }

    public IntegerProperty offenseIdProperty() {
        return offenseId;
    }


    public StringProperty offenseDescProperty() {
        return offenseDesc;
    }

    public StringProperty offenseSeverityProperty() {
        return offenseSeverity;
    }


    public StringProperty deptNameProperty() {
        return deptName;
    }
}
