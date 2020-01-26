package controller.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class departmentTable {
    private final IntegerProperty id;
    private final StringProperty deptName;
    private final StringProperty deptDescription;

    public departmentTable(int id, String deptName, String deptDescription){
        this.id = new SimpleIntegerProperty(id);
        this.deptName = new SimpleStringProperty(deptName);
        this.deptDescription = new SimpleStringProperty(deptDescription);
    }


    public IntegerProperty idProperty() {
        return id;
    }


    public StringProperty deptNameProperty() {
        return deptName;
    }



    public StringProperty deptDescriptionProperty() {
        return deptDescription;
    }
}
