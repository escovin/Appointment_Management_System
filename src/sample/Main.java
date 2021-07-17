package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnection;

import java.sql.*;
import java.util.Locale;


/**
 *
 * @author Erik Scovin
 */
public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        primaryStage.setTitle("Appointment Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {

        // Test in French
        //Locale.setDefault(new Locale("fr"));

        //DBConnection.startConnection();
        Connection conn = DBConnection.startConnection();

        launch(args);

        DBConnection.closeConnection(); //Close DB Connection

        //Value returning lambda expression
      //  newInterface message = s ->

    }

}
