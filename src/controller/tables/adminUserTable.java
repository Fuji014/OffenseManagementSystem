package controller.tables;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class adminUserTable {
    private final IntegerProperty adminId;
    private final StringProperty firstname;
    private final StringProperty lastname;
    private final StringProperty mi;
    private final StringProperty contact;
    private final StringProperty username;
    private final StringProperty datecreated;

    public adminUserTable(int adminId, String firstname, String lastname, String mi, String contact, String username, String  datecreated){
        this.adminId = new SimpleIntegerProperty(adminId);
        this.firstname = new SimpleStringProperty(firstname);
        this.lastname = new SimpleStringProperty(lastname);
        this.mi = new SimpleStringProperty(mi);
        this.contact = new SimpleStringProperty(contact);
        this.username = new SimpleStringProperty(username);
        this.datecreated = new SimpleStringProperty(datecreated);
    }

    public IntegerProperty getAdminId(){ return adminId; }
    public StringProperty getFirstname(){ return firstname; }
    public StringProperty getLastname(){ return lastname; }
    public StringProperty getMi(){ return mi; }
    public StringProperty getContact(){ return contact; }
    public StringProperty getUsername(){ return username; }
    public StringProperty getDatecreated(){ return datecreated; }

}
