package model;



import java.sql.Timestamp;

/**
 * Appointments class
 *  * @author Erik Scovin
 */
public class Appointments {

    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private int contact;
    private String type;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private int customerId;
    private int userId;

    public Appointments(int appointmentId, String title, String description,
                     String location, int contact, String type, Timestamp startDateTime, Timestamp endDateTime, int customerId, int userId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerId = customerId;
        this.userId = userId;

    }

    public int getAppointmentId() { return appointmentId; }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public String getLocation() { return location; }

    public int getContact() { return contact; }

    public String getType() { return type; }

    public Timestamp getStartDateTime() { return startDateTime; }

    public Timestamp getEndDateTime() { return endDateTime; }

    public  int getCustomerId() { return customerId; }

    public  int getUserId() { return userId; }

    /**
     * Corrects combo box display by overriding toString
     */
    @Override
    public String toString() {
        return (type);
    }


}
