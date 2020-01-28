package controller.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentOffenseSearchStudentTable {
    private final IntegerProperty studId;
    private final StringProperty studName;

    public StudentOffenseSearchStudentTable(int studId, String studName){
        this.studId = new SimpleIntegerProperty(studId);
        this.studName = new SimpleStringProperty(studName);
    }

    public IntegerProperty studIdProperty() {
        return studId;
    }

    public StringProperty studNameProperty() {
        return studName;
    }
}
