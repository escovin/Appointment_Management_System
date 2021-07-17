package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * dbusers class
 * @author Erik Scovin
 */
public class DBUsers {

    public static ObservableList<Users> getUsers() {

        ObservableList<Users> userList = FXCollections.observableArrayList();



        try {
            String sql = "SELECT * from users";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            //Loops through RS
            while(rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                Users user = new Users(userId, userName, password);
                userList.add(user);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;


    }

    public static void checkDateConversion() {
        System.out.println("CREATE DATE TEST");
        String sql = "SELECT Create_Date from user";
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
