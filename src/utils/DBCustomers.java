package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * dbcustomers class
 * @author Erik Scovin
 */
public class DBCustomers {

    public static ObservableList<Customers> getAllCustomers() {

        ObservableList<Customers> customerList = FXCollections.observableArrayList();



        try {
            String sql = "SELECT * from customers";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            //Loops through RS
            while(rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhoneNumber = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                Customers Cu = new Customers(customerId, customerName, customerAddress, customerPostalCode, customerPhoneNumber, divisionId);
                customerList.add(Cu);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;


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
