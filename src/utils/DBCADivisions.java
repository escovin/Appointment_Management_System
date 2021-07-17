package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Divisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * dbcadivisions class
 * @author Erik Scovin
 */
public class DBCADivisions {

    public static ObservableList<Divisions> getCADivisions() {

        ObservableList<Divisions> divisionList = FXCollections.observableArrayList();


        try {
            String sql = "SELECT * from  first_level_divisions WHERE COUNTRY_ID = 3";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            //Loops through RS
            while(rs.next()) {
                int divId = rs.getInt("Division_ID");
                String divName = rs.getString("Division");
                int countryId = rs.getInt("COUNTRY_ID");
                Divisions D = new Divisions(divId, divName, countryId);
                divisionList.add(D);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divisionList;


    }

    public static void checkDateConversion() {
        System.out.println("CREATE DATE TEST");
        String sql = "SELECT Create_Date from first_level_divisions";
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
