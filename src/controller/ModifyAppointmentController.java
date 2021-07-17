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
import model.*;
import utils.*;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * appointment id text field
     */
    @FXML
    private TextField appIdTxt;

    /**
     * customer id error label
     */
    @FXML
    private Label errorCustomerIdLab;

    /**
     * start date error label
     */
    @FXML
    private Label errorStartDateLab;

    /**
     * appointment id error label
     */
    @FXML
    private Label errorAppIdLab;

    /**
     * title error label
     */
    @FXML
    private Label errorTitleLab;

    /**
     * end time error label
     */
    @FXML
    private Label errorEndTimeLab;

    /**
     * description error label
     */
    @FXML
    private Label errorDescriptionLab;

    /**
     * location error label
     */
    @FXML
    private Label errorLocationLab;

    /**
     * type error label
     */
    @FXML
    private Label errorTypeLab;

    /**
     * appointment overlap error label
     */
    @FXML
    private Label appOverlapLab;


    /**
     * title text field
     */
    @FXML
    private TextField titleTxt;

    /**
     * description text field
     */
    @FXML
    private TextField descriptionTxt;

    /**
     * location text field
     */
    @FXML
    private TextField locationTxt;

    /**
     * type text field
     */
    @FXML
    private TextField typeTxt;

    /**
     * start time error label
     */
    @FXML
    private Label errorStartTimeLab;

    /**
     * user ID text field
     */
    @FXML
    private TextField userIdTxt;

    /**
     * cutomers ID text field
     */
    @FXML
    private TextField customerIdTxt;

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
     * appointment cancellation confirmation label
     */
    @FXML
    private Label deleteConfirmLab;

    /**
     * user id error label
     */
    @FXML
    private Label errorUserIdLab;


    /**
     * appointment not selected error label
     */
    @FXML
    private Label errorSelDeleteLab;

    /**
     * contact error label
     */
    @FXML
    private Label errorContactLab;

    /**
     * cancel modify appointment button
     */
    @FXML
    private Button cancelModAppButton;

    /**
     * contact combo box
     */
    @FXML
    public ComboBox<Contacts> contactComboBox;

    /**
     * start time combo box
     */
    @FXML
    public ComboBox<LocalTime> startTimeComboBox;

    /**
     * end time combo box
     */
    @FXML
    public ComboBox<LocalTime> endTimeComboBox;

    /**
     * start date combo box
     */
    @FXML
    public ComboBox<LocalDate> startDateComboBox;

    /**
     * table view
     */
    public TableView<Appointments> appTableView;

    /**
     * populates the startTimeComboBox with hour selections
     *
     * @param event start date selection
     */
    public void startDateComboBoxAction(ActionEvent event) {

        LocalDate date = LocalDate.now();
        LocalTime startTime1 = LocalTime.of(8, 0, 0);
        LocalDate closingDate = startDateComboBox.getValue();
        LocalTime closingTime = LocalTime.of(22, 00);
        ZoneId closingTimeZone = ZoneId.of("America/New_York");
        ZonedDateTime closingZoneId = ZonedDateTime.of(closingDate, closingTime, closingTimeZone);
        Instant closingInstant = closingZoneId.toInstant();

        /**
         * clears combo boxes
         */
        startTimeComboBox.getItems().clear();
        endTimeComboBox.getItems().clear();


        while (startTime1.isBefore(LocalTime.ofInstant(closingInstant, closingTimeZone))) {
            startTimeComboBox.getItems().add(startTime1);
            startTime1 = startTime1.plusHours(1);
        }

    }

    /**
     * deletes selected appointment
     *
     * @param event cancel appointment button action
     * @throws SQLException FROM SQL
     */
    public void cancelAppButtonAction(ActionEvent event) throws SQLException {

        /**
         * Clears error labels if they exists.
         */
        deleteConfirmLab.setText("");
        errorSelDeleteLab.setText("");


        try {
            int appointmentId;

            Appointments appToDelete = appTableView.getSelectionModel().getSelectedItem();
            appointmentId = appToDelete.getAppointmentId();

            /**
             * SQL statement to delete customer appointments
             * SQL statement to delete customer appointments
             */
            Connection conn = DBConnection.getConnection();
            String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";
            DBQuery.setPreparedStatement(conn, deleteStatement); // Create prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();

            /**
             * key mapping value
             */
            ps.setInt(1, appointmentId);

            /**
             * executes prepared statement
             */
            ps.execute();

            /**
             * reloads appointment table
             */
            ObservableList<Appointments> appointmentList = DBAppointments.getAppointments();
            appTableView.setItems(appointmentList);
            appIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            String type = appToDelete.getType();


            if (ps.getUpdateCount() > 0) {
                deleteConfirmLab.setText("Appointment " + appointmentId + " " + type + " cancelled!");
            }

        } catch (Exception e) {

            errorSelDeleteLab.setText("Please select an appointment to cancel.");

        }
    }

    /**
     * populates endTimeComboBox
     *
     * @param event start time selected
     */
    public void startTimeComboBoxAction(ActionEvent event) {

        LocalDate closingDate = startDateComboBox.getValue();
        LocalTime selectedTime = startTimeComboBox.getValue();
        LocalTime closingTime = LocalTime.of(22, 01);
        ZoneId closingTimeZone = ZoneId.of("America/New_York");
        ZonedDateTime closingZoneId = ZonedDateTime.of(closingDate, closingTime, closingTimeZone);
        Instant closingInstant = closingZoneId.toInstant();

        /**
         * clears endTimeComboBox
         */
        endTimeComboBox.getItems().clear();

        if ((selectedTime.isBefore(LocalTime.now())) && (closingDate.equals(LocalDate.now()))) {
            System.out.println("Please selection a valid start time.");
            startTimeComboBox.getSelectionModel().clearSelection();
        }

        /**
         * builds list of hours until closing for selection
         */
        while (selectedTime.isBefore(LocalTime.ofInstant(closingInstant, closingTimeZone))) {
            endTimeComboBox.getItems().add(selectedTime);
            selectedTime = selectedTime.plusHours(1);
        }

    }

    public void endTimeComboBoxAction(ActionEvent event) {

        if (startTimeComboBox.getSelectionModel().isEmpty()) {
            endTimeComboBox.getItems().clear();
            System.out.println("Please select a valid start time");
        }


    }

    /**
     * populates fields with selected appointment data
     *
     * @param event select mod app button action
     */
    public void selectModAppButtonAction(ActionEvent event) {


        contactComboBox.getItems().clear();
        endTimeComboBox.getItems().clear();
        startTimeComboBox.getItems().clear();
        startDateComboBox.getItems().clear();
        appIdTxt.setText("");
        titleTxt.setText("");
        descriptionTxt.setText("");
        locationTxt.setText("");
        typeTxt.setText("");
        userIdTxt.setText("");
        customerIdTxt.setText("");
        errorAppIdLab.setText("");

        /**
         * populates start date combo box
         */
        LocalDate startDate1 = LocalDate.now();
        while (startDate1.isBefore(LocalDate.ofYearDay(2021, 364))) {
            startDateComboBox.getItems().add(startDate1);
            startDate1 = startDate1.plusDays(1);

        }

        /**
         * Clears error labels if they exists.
         */
        deleteConfirmLab.setText("");
        errorSelDeleteLab.setText("");

        try {
            Appointments selectedAppointment;
            selectedAppointment = appTableView.getSelectionModel().getSelectedItem();
            ObservableList<Appointments> appointmentList = DBAppointments.getAppointments();
            ObservableList<Contacts> contactList = DBContacts.getContacts();
            contactComboBox.setItems(contactList);

            int selectedContact = selectedAppointment.getContact();

            // gathers start and end datetime info
            LocalDateTime selectedDateTime = (selectedAppointment.getStartDateTime().toLocalDateTime());
            LocalDate selectedDate = selectedDateTime.toLocalDate();
            LocalTime selectedTime = selectedDateTime.toLocalTime();
            LocalDateTime selectedEndDateTime = selectedAppointment.getEndDateTime().toLocalDateTime();
            LocalTime selectedEndTime = selectedEndDateTime.toLocalTime();


            /**
             * Populates text fields and combo boxes with appointment data
             */
            startTimeComboBox.getItems().clear();
            appIdTxt.setText(String.valueOf(selectedAppointment.getAppointmentId()));
            titleTxt.setText(selectedAppointment.getTitle());
            descriptionTxt.setText(selectedAppointment.getDescription());
            locationTxt.setText(selectedAppointment.getLocation());
            typeTxt.setText(selectedAppointment.getType());
            userIdTxt.setText(String.valueOf(selectedAppointment.getUserId()));
            customerIdTxt.setText(String.valueOf(selectedAppointment.getCustomerId()));
            startDateComboBox.setValue(selectedDate);
            startTimeComboBox.setValue(selectedTime);
            endTimeComboBox.setValue(selectedEndTime);

            if (selectedContact == 3) {
                contactComboBox.setValue(contactList.get(2));
            } else if (selectedContact == 2) {
                contactComboBox.setValue(contactList.get(1));
            } else if (selectedContact == 1) {
                contactComboBox.setValue(contactList.get(0));
            } else if (selectedContact == 4) {
                contactComboBox.setValue(contactList.get(3));
            } else if (selectedContact == 5) {
                contactComboBox.setValue(contactList.get(4));
            }


        } catch (Exception e) {
            errorSelDeleteLab.setText("Please select a an appointment to modify.");
        }

        /**
         * start time combo box variables
         */

        LocalTime selectedTime = startTimeComboBox.getValue();
        LocalTime startTime1 = LocalTime.of(8, 0, 0);
        LocalDate closingDate = startDateComboBox.getValue();
        LocalTime closingTime = LocalTime.of(22, 00);
        ZoneId closingTimeZone = ZoneId.of("America/New_York");
        ZonedDateTime closingZoneId = ZonedDateTime.of(closingDate, closingTime, closingTimeZone);
        Instant closingInstant = closingZoneId.toInstant();


        /**
         * builds list of hours until closing for selection
         */
        /*        while (selectedTime.isBefore(LocalTime.ofInstant(closingInstant, closingTimeZone))) {
            endTimeComboBox.getItems().add(selectedTime);
            selectedTime = selectedTime.plusHours(1);
        }
        while (startTime1.isBefore(LocalTime.ofInstant(closingInstant, closingTimeZone))) {
            startTimeComboBox.getItems().add(startTime1);
            startTime1 = startTime1.plusHours(1);


         */
    }

//    }

    /**
     * cancels modifications and navigates to MainScreen.fxml
     *
     * @param event cancel mod app button action
     * @throws IOException from FXMLLoader
     */
    public void cancelModAppButtonAction(ActionEvent event) throws IOException {

        stage = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    public void saveModAppButtonAction(ActionEvent event) throws SQLException {

        Timestamp start;
        Timestamp end;

        /**
         * Clears deletion confirmation if it exists
         */
        deleteConfirmLab.setText("");

        /**
         * clears error fields
         */

        errorAppIdLab.setText("");
        errorTitleLab.setText("");
        errorDescriptionLab.setText("");
        errorLocationLab.setText("");
        appOverlapLab.setText("");
        errorTypeLab.setText("");
        errorUserIdLab.setText("");
        errorCustomerIdLab.setText("");
        errorContactLab.setText("");
        errorStartDateLab.setText("");
        errorStartTimeLab.setText("");
        errorEndTimeLab.setText("");
        errorAppIdLab.setText("");

/**
 * input validation
 */
        ObservableList<Customers> customerList = DBCustomers.getAllCustomers();

        /**
         * checks for selection
         */
        if (appIdTxt.getText().isEmpty()) {
            errorAppIdLab.setText("Select an appointment to modify.");

        } else if (titleTxt.getText().isEmpty()) {
            errorTitleLab.setText("Invalid title entry.");

        } else if (descriptionTxt.getText().isEmpty()) {
            errorDescriptionLab.setText("Invalid description entry.");

        } else if (locationTxt.getText().isEmpty()) {
            errorLocationLab.setText("Invalid location entry.");

        } else if (typeTxt.getText().isEmpty()) {
            errorTypeLab.setText("Invalid type entry.");

        } else if (userIdTxt.getText().isEmpty()) {
            errorUserIdLab.setText("Invalid user ID entry.");

        } else if (customerIdTxt.getText().isEmpty()) {
            errorCustomerIdLab.setText("Invalid customer id.");

        } else if (contactComboBox.getSelectionModel().isEmpty()) {
            errorContactLab.setText("Invalid contact selection.");

        } else if (startDateComboBox.getSelectionModel().isEmpty()) {
            errorStartDateLab.setText("Invalid start date selection.");

        } else if (startTimeComboBox.getSelectionModel().isEmpty()) {
            errorStartTimeLab.setText("Invalid start time selection.");


        } else if (endTimeComboBox.getSelectionModel().isEmpty()) {
            errorEndTimeLab.setText("Invalid end time selection.");


        }

        try {



            String sql = "SELECT * from appointments WHERE Customer_ID = ?;";

            PreparedStatement ps3 = DBConnection.getConnection().prepareStatement(sql);

            int custId = Integer.parseInt(customerIdTxt.getText());
            start = Timestamp.valueOf(LocalDateTime.of(startDateComboBox.getValue(), startTimeComboBox.getValue()));
            end = Timestamp.valueOf(LocalDateTime.of(startDateComboBox.getValue(), endTimeComboBox.getValue()));

            ps3.setInt(1, custId);

            ps3.execute();

            ResultSet rs = ps3.executeQuery();

            while (rs.next()) {


                Timestamp ts = rs.getTimestamp("Start");
                Timestamp ts2 = rs.getTimestamp("End");
                LocalDateTime custStart = ts.toLocalDateTime();
                LocalDateTime custEnd = ts2.toLocalDateTime();
                LocalDateTime currTime = LocalDateTime.now();
                int appId1 = rs.getInt("Appointment_ID");
                int errorCount = 0;
                String appId = String.valueOf(appId1);


                if (start.toLocalDateTime().isAfter(custStart.minusSeconds(1)) && start.toLocalDateTime().isBefore(custEnd.minusSeconds(1)) && (!(appId.equals(appIdTxt.getText())))) {
                    appOverlapLab.setText("Appointment overlaps existing appointment!");
                    errorCount++;
                    return;
                } else if (end.toLocalDateTime().isAfter(custStart) && start.toLocalDateTime().isBefore(custEnd) && (!(appId.equals(appIdTxt.getText())))) {
                    appOverlapLab.setText("Appointment overlaps existing appointment!");
                    errorCount++;
                    return;
                } else if (start.toLocalDateTime().isBefore(custStart) && end.toLocalDateTime().isAfter(custEnd) && (!(appId.equals(appIdTxt.getText())))) {
                    appOverlapLab.setText("Appointment overlaps existing appointment!");
                    errorCount++;
                    return;
                } else if (errorCount > 0) {
                    return;
                }

            }


        } catch (SQLException e) {

        }
            /**
             * MYSQL statement to update appointment
             */
            try {
                Connection conn = DBConnection.getConnection();
                String updateStatement = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
                DBQuery.setPreparedStatement(conn, updateStatement); // Create prepared statement
                PreparedStatement ps = DBQuery.getPreparedStatement();

                String title, description, location, type;
                int contactId, appointmentId, userId, customerId;


                title = titleTxt.getText();
                description = descriptionTxt.getText();
                location = locationTxt.getText();
                type = typeTxt.getText();
                Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());
                contactId = (contactComboBox.getValue().getContactId());
                start = Timestamp.valueOf(LocalDateTime.of(startDateComboBox.getValue(), startTimeComboBox.getValue()));
                end = Timestamp.valueOf(LocalDateTime.of(startDateComboBox.getValue(), endTimeComboBox.getValue()));
                customerId = Integer.parseInt(customerIdTxt.getText());
                userId = Integer.parseInt(userIdTxt.getText());
                appointmentId = Integer.parseInt(appIdTxt.getText());


                if (titleTxt.getText().isEmpty()) {
                    errorTitleLab.setText("Invalid appointment title entry.");

                } else if (descriptionTxt.getText().isEmpty()) {
                    errorDescriptionLab.setText("Invalid appointment description entry.");

                } else if (locationTxt.getText().isEmpty()) {
                    errorLocationLab.setText("Invalid appointment location entry.");

                } else if (typeTxt.getText().isEmpty()) {
                    errorTypeLab.setText("Invalid appointment type entry.");

                } else {

                    /**
                     * key mapping
                     */
                    ps.setString(1, title);
                    ps.setString(2, description);
                    ps.setString(3, location);
                    ps.setString(4, type);
                    ps.setTimestamp(5, start);
                    ps.setTimestamp(6, end);
                    ps.setTimestamp(7, lastUpdate);
                    ps.setInt(8, (customerId));
                    ps.setInt(9, (userId));
                    ps.setInt(10, contactId);
                    ps.setInt(11, appointmentId);

                    /**
                     * executes prepared statement
                     */
                    ps.execute();

                    /**
                     * Reloads table view
                     */
                    ObservableList<Appointments> appointmentList = DBAppointments.getAppointments();
                    appTableView.setItems(appointmentList);
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
            } catch (Exception e) {


            }
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         * populates tableview
         */
        ObservableList<Appointments> appointmentList = DBAppointments.getAppointments();
        appTableView.setItems(appointmentList);
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

        }
