package controller.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class studentOffenseTable {
    private final IntegerProperty main_id;
    private final IntegerProperty student_key;
    private final IntegerProperty offense_key;
    private final StringProperty severity;
    private final StringProperty duration;
    private final StringProperty completed;
    private final StringProperty status;
    private final StringProperty count;
    private final StringProperty remarks;
    private final StringProperty date;

    public studentOffenseTable(int main__id,int student_key, int offense_key, String severity, String duration, String completed, String status, String count, String remarks, String date){
        this.main_id = new SimpleIntegerProperty(main__id);
        this.student_key = new SimpleIntegerProperty(student_key);
        this.offense_key = new SimpleIntegerProperty(offense_key);
        this.severity = new SimpleStringProperty(severity);
        this.duration = new SimpleStringProperty(duration);
        this.completed = new SimpleStringProperty(completed);
        this.status = new SimpleStringProperty(status);
        this.count = new SimpleStringProperty(count);
        this.remarks = new SimpleStringProperty(remarks);
        this.date = new SimpleStringProperty(date);
    }

    // getters

    public int getMain_id() {
        return main_id.get();
    }

    public IntegerProperty main_idProperty() {
        return main_id;
    }

    public int getStudent_key() {
        return student_key.get();
    }

    public IntegerProperty student_keyProperty() {
        return student_key;
    }

    public int getOffense_key() {
        return offense_key.get();
    }

    public IntegerProperty offense_keyProperty() {
        return offense_key;
    }

    public String getSeverity() {
        return severity.get();
    }

    public StringProperty severityProperty() {
        return severity;
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

    public String getCount() {
        return count.get();
    }

    public StringProperty countProperty() {
        return count;
    }

    public String getRemarks() {
        return remarks.get();
    }

    public StringProperty remarksProperty() {
        return remarks;
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }
}
