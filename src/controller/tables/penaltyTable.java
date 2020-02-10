package controller.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class penaltyTable {
    private final IntegerProperty studId;
    private final StringProperty studName;
    private final StringProperty severity;
    private final StringProperty description;
    private final StringProperty duration;
    private final StringProperty completed;
    private final StringProperty status;
    private final StringProperty date;

    public penaltyTable(int studId, String studName, String severity, String description, String duration, String completed, String status, String date){
        this.studId = new SimpleIntegerProperty(studId);
        this.studName = new SimpleStringProperty(studName);
        this.severity = new SimpleStringProperty(severity);
        this.description = new SimpleStringProperty(description);
        this.duration = new SimpleStringProperty(duration);
        this.completed = new SimpleStringProperty(completed);
        this.status = new SimpleStringProperty(status);
        this.date = new SimpleStringProperty(date);

    }

    public int getStudId() {
        return studId.get();
    }

    public IntegerProperty studIdProperty() {
        return studId;
    }

    public String getStudName() {
        return studName.get();
    }

    public StringProperty studNameProperty() {
        return studName;
    }

    public String getSeverity() {
        return severity.get();
    }

    public StringProperty severityProperty() {
        return severity;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getDuration() {
        return duration.get();
    }

    public StringProperty durationProperty() {
        return duration;
    }

    public String getCompleted() {
        return completed.get();
    }

    public StringProperty completedProperty() {
        return completed;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }
}

