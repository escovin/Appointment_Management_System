package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * dbuserappointments class
 * @author Erik Scovin
 */
public class DBUserAppointments {




    public static ObservableList<Appointments> getUserAppointments() {

        ObservableList<Appointments> userAppointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments WHERE User_ID = 1;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            //Loops through RS
            while(rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                Timestamp startDateTime = rs.getTimestamp("Start");
                Timestamp endDateTime = rs.getTimestamp("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Appointments Ap = new Appointments(appointmentId, title, description, location, contactId, type, startDateTime, endDateTime, customerId, userId);
                userAppointmentList.add(Ap);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userAppointmentList;


    }

    public static void checkDateConversion() {
        System.out.println("CREATE DATE TEST");
        String sql = "SELECT Create_Date from appointments";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("Create_Date");
                System.out.println("CD: " + ts.toLocalDateTime().toString());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
