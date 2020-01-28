package controller.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class studentOffenseTable {
    private final IntegerProperty studOffId;
    private final StringProperty studId;
    private final StringProperty studOff;
    private final StringProperty studCount;
    private final StringProperty studDate;
    private final StringProperty studRemarks;
    private final StringProperty studSeverity;

    public studentOffenseTable(int studOffId, String studId, String studOff,String studSeverity,String studCount, String studDate, String studRemarks){
        this.studOffId = new SimpleIntegerProperty(studOffId);
        this.studId = new SimpleStringProperty(studId);
        this.studOff = new SimpleStringProperty(studOff);
        this.studSeverity = new SimpleStringProperty(studSeverity);
        this.studCount = new SimpleStringProperty(studCount);
        this.studDate = new SimpleStringProperty(studDate);
        this.studRemarks = new SimpleStringProperty(studRemarks);
    }

    public IntegerProperty studOffIdProperty() {
        return studOffId;
    }

    public StringProperty studIdProperty() {
        return studId;
    }

    public StringProperty studOffProperty() {
        return studOff;
    }

    public StringProperty studSeverityProperty() {
        return studSeverity;
    }

    public StringProperty studCountProperty() {
        return studCount;
    }

    public StringProperty studDateProperty() {
        return studDate;
    }

    public StringProperty studRemarksProperty() {
        return studRemarks;
    }
}
