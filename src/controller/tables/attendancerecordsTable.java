package controller.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class attendancerecordsTable {
    private IntegerProperty attendaceID;
    private IntegerProperty student_key;
    private StringProperty date;
    private StringProperty timein;
    private StringProperty timeout;
    private StringProperty loginremarks;
    private StringProperty logoutremarks;

    public attendancerecordsTable(int attendaceID,int student_key,String date,String timein,String timeout,String loginremarks,String logoutremarks){
        this.attendaceID = new SimpleIntegerProperty(attendaceID);
        this.student_key = new SimpleIntegerProperty(student_key);
        this.date = new SimpleStringProperty(date);
        this.timein = new SimpleStringProperty(timein);
        this.timeout = new SimpleStringProperty(timeout);
        this.loginremarks = new SimpleStringProperty(loginremarks);
        this.logoutremarks = new SimpleStringProperty(logoutremarks);
    }

    public int getAttendaceID() {
        return attendaceID.get();
    }

    public IntegerProperty attendaceIDProperty() {
        return attendaceID;
    }

    public int getStudent_key() {
        return student_key.get();
    }

    public IntegerProperty student_keyProperty() {
        return student_key;
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getTimein() {
        return timein.get();
    }

    public StringProperty timeinProperty() {
        return timein;
    }

    public String getTimeout() {
        return timeout.get();
    }

    public StringProperty timeoutProperty() {
        return timeout;
    }

    public String getLoginremarks() {
        return loginremarks.get();
    }

    public StringProperty loginremarksProperty() {
        return loginremarks;
    }

    public String getLogoutremarks() {
        return logoutremarks.get();
    }

    public StringProperty logoutremarksProperty() {
        return logoutremarks;
    }
}
