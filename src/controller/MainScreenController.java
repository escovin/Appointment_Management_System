package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import sample.appointmentAlertInterface;
import sample.countAppsInterface;
import utils.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

/**
 * Controller for MainScreen.fxml
 * @author Erik Scovin
 */
public class MainScreenController implements Initializable {


    Stage stage;
    Parent scene;


    /**
     * appointment alert label
     */
    @FXML
    private Label appAlert;


    /**
     * table columns
     */
    public TableColumn appIdCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn locationCol;
    public TableColumn contactCol;
    public TableColumn typeCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn customerIdCol;

    /**
     * table view
     */
    public TableView<Appointments> appointmentsTableView;

    /**
     * Exits application and closes database connection
     *
     * @param event exit button action
     */
    @FXML
    public void exitButtonAction(ActionEvent event) {

        DBConnection.closeConnection();
        System.exit(0);

    }

    /**
     * navigates to modify customer page
     *
     * @param event modify customer button action
     * @throws IOException from FXMLLoader
     */
    public void modifyCustomerButtonAction(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/ModifyCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * navigates to reports.fxml
     * @param event reports button pushed
     * @throws IOException from FXMLLoader
     */
    public void reportsButtonAction(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/Reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * navigates to add customer page
     *
     * @param event add customer button action
     * @throws IOException from FXMLLoader
     */
    public void addCustomerButtonAction(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * navigates to add appointment page
     *
     * @param event add appointment button action
     * @throws IOException from FXMLLoder
     */
    public void addAppointmentButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Navigates to ModifyAppointment.fxml
     *
     * @param event
     * @throws IOException
     */
    public void modifyAppointmentButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/ModifyAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * populates tableview with current week's appointment
     * @param event
     * @throws SQLException
     */
    public void weekViewRadioButtonAction(ActionEvent event) throws SQLException {


        appointmentsTableView.getItems().clear();
        ObservableList<Appointments> appointmentList = DBWeekAppointments.getAppointments();
        appointmentsTableView.setItems(appointmentList);
        appIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));


    }

    /**
     * populates tableview with current month's appointments
     * @param event monthViewRadioButton action
     * @throws SQLException SQL
     */
    public void monthViewRadioButtonAction(ActionEvent event) throws SQLException {


        appointmentsTableView.getItems().clear();
        ObservableList<Appointments> appointmentList = DBMonthAppointments.getAppointments();
        appointmentsTableView.setItems(appointmentList);
        appIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Appointments> userAppointmentList = DBUserAppointments.getUserAppointments();
        ObservableList<Appointments> appointmentList = DBAppointments.getAppointments();
        appointmentsTableView.setItems(appointmentList);
        appIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));


        try {
            String sql = "SELECT * from appointments WHERE User_ID = 1;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.execute();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {


                Timestamp ts = rs.getTimestamp("Start");
                LocalDateTime start = ts.toLocalDateTime();
                LocalDateTime currTime = LocalDateTime.now();
                int appId = rs.getInt("Appointment_ID");
                long timeDifference = ChronoUnit.MINUTES.between(start, currTime);
                long interval = (timeDifference + -1) * -1;
                int count = 0;

                if (interval > 0 && interval <= 15) {
                    /**
                     * Lambda Expression #2
                     * creates alert message if appointment is within 15 minutes and populates alert label on GUI.
                     */
                    appointmentAlertInterface alerts = n -> "You have appointment " + appId + " at " + n + ".";
                    appAlert.setText(alerts.alert(String.valueOf(start)));
                    JOptionPane.showMessageDialog(null, alerts.alert(String.valueOf(start)));
                    count ++;

                }
                if (count == 0) {

                        appAlert.setText("You have no upcoming appointments.");
                }

            }

        } catch (SQLException e) {

        }

        }
    }

