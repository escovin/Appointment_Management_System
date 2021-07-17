package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * dbcontacts class
 * @author Erik Scovin
 */
public class DBContacts {

    public static ObservableList<Contacts> getContacts() {

        ObservableList<Contacts> contactList = FXCollections.observableArrayList();



        try {
            String sql = "SELECT * from contacts";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            //Loops through RS
            while(rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contacts Cont = new Contacts(contactId, contactName, email);
                contactList.add(Cont);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactList;


    }

    public static void checkDateConversion() {
        System.out.println("CREATE DATE TEST");
        String sql = "SELECT Create_Date from customers";
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
