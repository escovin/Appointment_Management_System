package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DBCountries {

    public static ObservableList<Countries> getAllCountries() {

        ObservableList<Countries> countryList = FXCollections.observableArrayList();



        try {
            String sql = "SELECT * from countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            //Loops through RS
            while(rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries C = new Countries(countryId, countryName);
                countryList.add(C);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countryList;


    }

    public static void checkDateConversion() {
        System.out.println("CREATE DATE TEST");
        String sql = "SELECT Create_Date from countries";
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
