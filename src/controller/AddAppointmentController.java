package controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customers;
import utils.*;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * controller for AddAppointment.fxm
 * @author Erik Scovin
 */
public class AddAppointmentController implements Initializable {

    Stage stage;
    Parent scene;



    @FXML
    private Label errorEndTimeLab;

    /**
     * start time error label
     */
    @FXML
    private Label errorStartTimeLab;

    /**
     * start date error label
     */
    @FXML
    private Label errorStartDateLab;


    /**
     * appointment overlap error label
     */
    @FXML
    private Label appOverlapLab;

    /**
     * appointment title text field
     */
    @FXML
    private TextField appTitleTxt;

    /**
     * appointment description text field
     */
    @FXML
    private TextField appDescriptionTxt;

    /**
     * appointment location text field
     */
    @FXML
    private TextField appLocationTxt;

    /**
     * user ID text field
     */
    @FXML
    private TextField userIdTxt;

    /**
     * appointment contact text field
     */
    @FXML
    private TextField appTypeTxt;

    /**
     * contact combo box
     */
    @FXML
    public ComboBox<Contacts> contactComboBox;


    /**
     * customer id text field
     */
    @FXML
    private TextField customerIdTxt;


    /**
     * title error label
     */
    @FXML
    private Label errorTitleLab;

    /**
     * description error label
     */
    @FXML
    private Label errorDescriptionLab;

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
     * location error label
     */
    @FXML
    private Label errorLocationLab;

    /**
     * contact error label
     */
    @FXML
    private Label errorContactLab;


    /**
     * type error lab
     */
    @FXML
    private Label errorTypeLab;

    /**
     * customer id error label
     */
    @FXML
    private Label errorCustomerIdLab;

    /**
     * user id error label
     */
    @FXML
    private Label errorUserIdLab;

    /**
     * adds appointment to database
     *
     * @param event add app button action
     */
    @FXML
    public void addAppButtonAction(ActionEvent event) throws SQLException, IOException {


        Timestamp start;
        Timestamp end;

        // clears error fields
        errorDescriptionLab.setText("");
        errorLocationLab.setText("");
        errorTypeLab.setText("");
        errorContactLab.setText("");
        errorTitleLab.setText("");
        errorCustomerIdLab.setText("");
        errorUserIdLab.setText("");
        errorStartDateLab.setText("");
        errorEndTimeLab.setText("");
        errorStartTimeLab.setText("");

        ObservableList<Customers> customerList = DBCustomers.getAllCustomers();
        if (appTitleTxt.getText().isEmpty()) {
            errorTitleLab.setText("Invalid title entry.");

        } else if (appDescriptionTxt.getText().isEmpty()) {
            errorDescriptionLab.setText("Invalid description entry.");

        } else if (appLocationTxt.getText().isEmpty()) {
            errorLocationLab.setText("Invalid location entry.");

        } else if (appTypeTxt.getText().isEmpty()) {
            errorTypeLab.setText("Invalid type entry.");

        } else if (appTypeTxt.getText().isEmpty()) {
            errorTypeLab.setText("Invalid type entry.");

        } else if (userIdTxt.getText().isEmpty()) {
            errorUserIdLab.setText("Invalid user ID entry.");

        } else if (customerIdTxt.getText().isEmpty()) {
            errorCustomerIdLab.setText("Invalid customer id.");

        }else if (contactComboBox.getSelectionModel().isEmpty()) {
            errorContactLab.setText("Invalid contact selection.");

        }else if (startDateComboBox.getSelectionModel().isEmpty()) {
            errorStartDateLab.setText("Invalid start date selection.");

        }else if (startTimeComboBox.getSelectionModel().isEmpty()) {
            errorStartTimeLab.setText("Invalid start time selection.");


        }else if (endTimeComboBox.getSelectionModel().isEmpty()) {
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
                int appId = rs.getInt("Appointment_ID");
                int errorCount = 0;

                if (start.toLocalDateTime().isAfter(custStart.minusSeconds(1)) && start.toLocalDateTime().isBefore(custEnd.minusSeconds(1))) {
                    appOverlapLab.setText("Appointment overlaps existing appointment!");
                    errorCount++;
                    return;
                } else if (end.toLocalDateTime().isAfter(custStart) && start.toLocalDateTime().isBefore(custEnd)) {
                    appOverlapLab.setText("Appointment overlaps existing appointment!");
                    errorCount++;
                    return;
                } else if (start.toLocalDateTime().isBefore(custStart) && end.toLocalDateTime().isAfter(custEnd)) {
                    appOverlapLab.setText("Appointment overlaps existing appointment!");
                    errorCount++;
                    return;
                } else if (errorCount > 0) {
                    return;
                }

            }


        } catch (SQLException e) {
            return;

        }
        try {
            /**
             * MYSQL statement to ADD appointment
             */
            // ObservableList<Customers> customerList = DBCustomers.getAllCustomers();
            Connection conn = DBConnection.getConnection();
            String insertStatement = "INSERT INTO appointments(Title, Description, Location, Contact_ID, Type, Start, End, Customer_ID, User_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            DBQuery.setPreparedStatement(conn, insertStatement); // Create prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();


            String title;
            String description;
            String location;
            String type;


            int customerId;
            int contact;
            int userId;
            int selectedContact = contactComboBox.getValue().getContactId();
            LocalDateTime createDate = LocalDateTime.now();
            title = appTitleTxt.getText();
            description = appDescriptionTxt.getText();
            location = appLocationTxt.getText();
            type = appTypeTxt.getText();
            LocalDate swapDate = startDateComboBox.getValue();
            LocalTime dateSwapTime = LocalTime.of(22, 00);
            ZoneId swapTimeZone = ZoneId.of("UTC");
            ZonedDateTime swapZoneId = ZonedDateTime.of(swapDate, dateSwapTime, swapTimeZone);
            Instant swapInstant = swapZoneId.toInstant();


            start = Timestamp.valueOf(LocalDateTime.of(startDateComboBox.getValue(), startTimeComboBox.getValue()));
            end = Timestamp.valueOf(LocalDateTime.of(startDateComboBox.getValue(), endTimeComboBox.getValue()));
            customerId = Integer.parseInt(customerIdTxt.getText());
            userId = Integer.parseInt(userIdTxt.getText());
            contact = selectedContact;

            if (endTimeComboBox.getValue().isAfter(LocalTime.ofInstant(swapInstant, swapTimeZone))) {
                end = Timestamp.valueOf(LocalDateTime.of(startDateComboBox.getValue().plusDays(1), endTimeComboBox.getValue()));

            }

            /**
             * key mapping values
             */
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setInt(4, contact);
            ps.setString(5, type);
            ps.setTimestamp(6, start);
            ps.setTimestamp(7, end);
            ps.setInt(8, customerId);
            ps.setInt(9, userId);

            ps.execute(); // Execute Prepared Statement


            if (ps.getUpdateCount() > 0) {
                System.out.println("Appointment added!");
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else
                System.out.println("Appointment not added.");


        } catch (Exception e) {
            errorUserIdLab.setText("Invalid user and/or customer id.");

            return;
        }
    }

    /**
     * cancels appointment add and navigates to MainScreen.fxml
     *
     * @param event cancel add button action
     * @throws IOException from FXMLLoader
     */
    public void cancelAddAppButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * populates the startTimeComboBox with hour selections
     * @param event start date selection
     */
    public void startDateComboBoxAction(ActionEvent event) {


        LocalTime startTime1 = LocalTime.of(8,0,0);
        LocalDate closingDate = startDateComboBox.getValue();
        LocalTime closingTime = LocalTime.of(22, 00);
        ZoneId closingTimeZone = ZoneId.of("America/New_York");
        ZonedDateTime closingZoneId = ZonedDateTime.of(closingDate, closingTime, closingTimeZone);
        Instant closingInstant = closingZoneId.toInstant();

        /**
         * clears combo boxes
         */
        endTimeComboBox.getItems().clear();
        startTimeComboBox.getSelectionModel().clearSelection();



        while (startTime1.isBefore(LocalTime.ofInstant(closingInstant, closingTimeZone))) {
            startTimeComboBox.getItems().add(startTime1);
            startTime1 = startTime1.plusHours(1);
        }


    }

    /**
     * populates endTimeComboBox
     * @param event start time selected
     */
    public void startTimeComboBoxAction(ActionEvent event) {

        LocalDate date = LocalDate.now();
        LocalTime startTime1 = LocalTime.of(8,0,0);
        LocalDate closingDate = startDateComboBox.getValue();
        LocalTime selectedTime = startTimeComboBox.getValue();
        LocalTime closingTime = LocalTime.of(22, 01);
        ZoneId closingTimeZone = ZoneId.of("America/New_York");
        ZonedDateTime closingZoneId = ZonedDateTime.of(closingDate, closingTime, closingTimeZone);
        Instant closingInstant = closingZoneId.toInstant();
        LocalTime clearBox = startTimeComboBox.getValue();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LocalDate startDate1 = LocalDate.now();
        //populates contact combo box
        ObservableList<Contacts> contactList = DBContacts.getContacts();
        contactComboBox.setItems(contactList);

        /**
         * populates start date combo box
         */
        while (startDate1.isBefore(LocalDate.ofYearDay(2021, 364))) {
            startDateComboBox.getItems().add(startDate1);
            startDate1 = startDate1.plusDays(1);
        }
    }


}