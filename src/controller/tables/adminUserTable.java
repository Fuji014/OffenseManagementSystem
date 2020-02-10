package controller.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class adminUserTable {
    private final IntegerProperty adminId;
    private final StringProperty name;
    private final StringProperty contact;
    private final StringProperty deptname;
    private final StringProperty username;
    private final StringProperty datecreated;

    public adminUserTable(int adminId, String name, String contact,String deptname, String username, String  datecreated){
        this.adminId = new SimpleIntegerProperty(adminId);
        this.name = new SimpleStringProperty(name);
        this.deptname = new SimpleStringProperty(deptname);
        this.contact = new SimpleStringProperty(contact);
        this.username = new SimpleStringProperty(username);
        this.datecreated = new SimpleStringProperty(datecreated);
    }

    public IntegerProperty getAdminId(){ return adminId; }
    public StringProperty getName(){ return name; }
    public StringProperty getContact(){ return contact; }
    public StringProperty getDeptname(){ return deptname; }
    public StringProperty getUsername(){ return username; }
    public StringProperty getDatecreated(){ return datecreated; }



}
