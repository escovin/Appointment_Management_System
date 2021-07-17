package model;

/**
 * Contacts class
 * @author Erik Scovin
 */
public class Contacts {


    private int contactId;
    private String contactName;
    private String email;

    public Contacts( int contactId, String contactName, String email) {

        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;

    }

    public int getContactId() { return contactId; }

    public String getContactName() { return contactName; }

    public String getEmail() { return email; }



    //Corrects combo box display by overriding toString
    @Override
    public String toString() {
        return (contactName);
    }
}
