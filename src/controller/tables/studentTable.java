package controller.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class studentTable {
    private final IntegerProperty id;
    private final StringProperty studentRfid;
    private final IntegerProperty studentId;
    private final StringProperty studentName;
    private final StringProperty studentYear;
    private final StringProperty studentSection;
    private final StringProperty studentCourse;
    private final StringProperty studentStrand;
    private final StringProperty studentDepartment;
    private final StringProperty parentName;
    private final StringProperty parentContact;
    private final StringProperty studentContact;
    private final StringProperty parentAddress;

    public studentTable(int id, String studentRfid, int studentId, String studentName, String studentYear, String studentSection, String studentCourse, String studentStrand, String studentDepartment, String parentName, String parentContact, String studentContact, String parentAddress){
        this.id = new SimpleIntegerProperty(id);
        this.studentRfid = new SimpleStringProperty(studentRfid);
        this.studentId = new SimpleIntegerProperty(studentId);
        this.studentName = new SimpleStringProperty(studentName);
        this.studentYear = new SimpleStringProperty(studentYear);
        this.studentSection = new SimpleStringProperty(studentSection);
        this.studentCourse = new SimpleStringProperty(studentCourse);
        this.studentStrand = new SimpleStringProperty(studentStrand);
        this.studentDepartment = new SimpleStringProperty(studentDepartment);
        this.parentName = new SimpleStringProperty(parentName);
        this.parentContact = new SimpleStringProperty(parentContact);
        this.studentContact = new SimpleStringProperty(studentContact);
        this.parentAddress = new SimpleStringProperty(parentAddress);
    }


    public IntegerProperty idProperty() {
        return id;
    }


    public StringProperty studentRfidProperty() {
        return studentRfid;
    }


    public IntegerProperty studentIdProperty() {
        return studentId;
    }


    public StringProperty studentNameProperty() {
        return studentName;
    }


    public StringProperty studentYearProperty() {
        return studentYear;
    }

    public StringProperty studentSectionProperty() {
        return studentSection;
    }

    public StringProperty studentCourseProperty() {
        return studentCourse;
    }

    public StringProperty studentStrandProperty() {
        return studentStrand;
    }

    public StringProperty studentDepartmentProperty() {
        return studentDepartment;
    }

    public StringProperty parentNameProperty() {
        return parentName;
    }

    public StringProperty parentContactProperty() {
        return parentContact;
    }

    public StringProperty studentContactProperty() {
        return studentContact;
    }

    public StringProperty parentAdressProperty() {
        return parentAddress;
    }


}
