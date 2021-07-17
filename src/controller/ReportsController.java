package controller;

import javafx.collections.FXCollections;
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
import model.Contacts;
import model.Customers;
import sample.countAppsInterface;
import utils.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * controller for Reports.dxml
 * @author Erik Scovin
 */
public class ReportsController implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * count label
     */
    @FXML
    private Label countLab;

    /**
     * contact combo box
     */
    @FXML
    public ComboBox<Contacts> contactComboBox;

    /**
     * contact combo box
     */
    @FXML
    public ComboBox<Customers> customerComboBox;

    /**
     * month combo box
     */
    @FXML
    public ComboBox<String> monthComboBox;

    /**
     * type combo box
     */
    @FXML
    public ComboBox<String> typeComboBox;

    /**
     * table view
     */
    public TableView<Appointments> appTableView;

    /**
     * table view
     */
    public TableView<Appointments> customerAppTableView;

    /**
     * customerAppTableView columns
     */
    public TableColumn appIdCol2;
    public TableColumn titleCol2;
    public TableColumn descriptionCol2;
    public TableColumn typeCol2;
    public TableColumn startCol2;
    public TableColumn endCol2;
    public TableColumn contactIdCol;

    /**
     * appTableView columns
     */
    public TableColumn appCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn typeCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn customerIdCol;


    /**
     * navigates to MainScreen.fxml
     * @param event back button action
     */
    public void backButtonAction(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * populates the type combo box with filtered resutls
     * @param event month selected in combo box
     */
    public void monthComboBoxAction(ActionEvent event) throws SQLException {

        /**
         * sets type combo box based on date selected
         */
        String selectedMonth = monthComboBox.getValue();
        typeComboBox.getItems().clear();
        String month = "";
        Connection conn = DBConnection.getConnection();

        if (selectedMonth == "January") { //January selected
            month = "2021-01-00";
            try {
                String selectStatement = "SELECT * FROM appointments WHERE EXTRACT(MONTH FROM '" + month + "') = MONTH(Start);";
                DBQuery.setPreparedStatement(conn, selectStatement); // Create prepared statement
                PreparedStatement ps = DBQuery.getPreparedStatement();
                ps.execute();
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String appType = rs.getString("Type");
                    if (!typeComboBox.getItems().contains(appType)) {
                        typeComboBox.getItems().add(appType);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else  if (selectedMonth == "February") {   //February selected
            month = "2021-02-00";
            try {
                String selectStatement = "SELECT * FROM appointments WHERE EXTRACT(MONTH FROM '" + month + "') = MONTH(Start);";
                DBQuery.setPreparedStatement(conn, selectStatement); // Create prepared statement
                PreparedStatement ps = DBQuery.getPreparedStatement();
                ps.execute();
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String appType = rs.getString("Type");
                    if (!typeComboBox.getItems().contains(appType)) {
                        typeComboBox.getItems().add(appType);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } else  if (selectedMonth == "March") {    //March selected
            month = "2021-03-00";
            try {
                String selectStatement = "SELECT * FROM appointments WHERE EXTRACT(MONTH FROM '" + month + "') = MONTH(Start);";
                DBQuery.setPreparedStatement(conn, selectStatement); // Create prepared statement
                PreparedStatement ps = DBQuery.getPreparedStatement();
                ps.execute();
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String appType = rs.getString("Type");
                    if (!typeComboBox.getItems().contains(appType)) {
                        typeComboBox.getItems().add(appType);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } else if (selectedMonth == "April") {  //April selected
            month = "2021-04-00";
            try {
                String selectStatement = "SELECT * FROM appointments WHERE EXTRACT(MONTH FROM '" + month + "') = MONTH(Start);";
                DBQuery.setPreparedStatement(conn, selectStatement); // Create prepared statement
                PreparedStatement ps = DBQuery.getPreparedStatement();
                ps.execute();
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String appType = rs.getString("Type");
                    if (!typeComboBox.getItems().contains(appType)) {
                        typeComboBox.getItems().add(appType);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } else  if (selectedMonth == "May") {     //May selected
            month = "2021-05-00";
            try {
                String selectStatement = "SELECT * FROM appointments WHERE EXTRACT(MONTH FROM '" + month + "') = MONTH(Start);";
                DBQuery.setPreparedStatement(conn, selectStatement); // Create prepared statement
                PreparedStatement ps = DBQuery.getPreparedStatement();
                ps.execute();
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String appType = rs.getString("Type");
                    if (!typeComboBox.getItems().contains(appType)) {
                        typeComboBox.getItems().add(appType);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } else if (selectedMonth == "June") {   //June selected
            month = "2021-06-00";
            try {
                String selectStatement = "SELECT * FROM appointments WHERE EXTRACT(MONTH FROM '" + month + "') = MONTH(Start);";
                DBQuery.setPreparedStatement(conn, selectStatement); // Create prepared statement
                PreparedStatement ps = DBQuery.getPreparedStatement();
                ps.execute();
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String appType = rs.getString("Type");
                    if (!typeComboBox.getItems().contains(appType)) {
                        typeComboBox.getItems().add(appType);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } else if (selectedMonth == "July") { //July selected
            month = "2021-07-00";
            try {
                String selectStatement = "SELECT * FROM appointments WHERE EXTRACT(MONTH FROM '" + month + "') = MONTH(Start);";
                DBQuery.setPreparedStatement(conn, selectStatement); // Create prepared statement
                PreparedStatement ps = DBQuery.getPreparedStatement();
                ps.execute();
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String appType = rs.getString("Type");
                    if (!typeComboBox.getItems().contains(appType)) {
                        typeComboBox.getItems().add(appType);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } else if (selectedMonth == "August") { //August selected
            month = "2021-08-00";
            try {
                String selectStatement = "SELECT * FROM appointments WHERE EXTRACT(MONTH FROM '" + month + "') = MONTH(Start);";
                DBQuery.setPreparedStatement(conn, selectStatement); // Create prepared statement
                PreparedStatement ps = DBQuery.getPreparedStatement();
                ps.execute();
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String appType = rs.getString("Type");
                    if (!typeComboBox.getItems().contains(appType)) {
                        typeComboBox.getItems().add(appType);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } else if (selectedMonth == "September") { //September selected
            month = "2021-09-00";
            try {
                String selectStatement = "SELECT * FROM appointments WHERE EXTRACT(MONTH FROM '" + month + "') = MONTH(Start);";
                DBQuery.setPreparedStatement(conn, selectStatement); // Create prepared statement
                PreparedStatement ps = DBQuery.getPreparedStatement();
                ps.execute();
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String appType = rs.getString("Type");
                    if (!typeComboBox.getItems().contains(appType)) {
                        typeComboBox.getItems().add(appType);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } else if (selectedMonth == "October") { //October selected
            month = "2021-10-00";
            try {
                String selectStatement = "SELECT * FROM appointments WHERE EXTRACT(MONTH FROM '" + month + "') = MONTH(Start);";
                DBQuery.setPreparedStatement(conn, selectStatement); // Create prepared statement
                PreparedStatement ps = DBQuery.getPreparedStatement();
                ps.execute();
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String appType = rs.getString("Type");
                    if (!typeComboBox.getItems().contains(appType)) {
                        typeComboBox.getItems().add(appType);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } else if (selectedMonth == "November") { //November selected
            month = "2021-11-00";
            try {
                String selectStatement = "SELECT * FROM appointments WHERE EXTRACT(MONTH FROM '" + month + "') = MONTH(Start);";
                DBQuery.setPreparedStatement(conn, selectStatement); // Create prepared statement
                PreparedStatement ps = DBQuery.getPreparedStatement();
                ps.execute();
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String appType = rs.getString("Type");
                    if (!typeComboBox.getItems().contains(appType)) {
                        typeComboBox.getItems().add(appType);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } else if (selectedMonth == "December") { //december selected
            month = "2021-12-00";
            try {
                String selectStatement = "SELECT * FROM appointments WHERE EXTRACT(MONTH FROM '" + month + "') = MONTH(Start);";
                DBQuery.setPreparedStatement(conn, selectStatement); // Create prepared statement
                PreparedStatement ps = DBQuery.getPreparedStatement();
                ps.execute();
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String appType = rs.getString("Type");
                    if (!typeComboBox.getItems().contains(appType)) {
                        typeComboBox.getItems().add(appType);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    /**
     * counts instances of type in selected month
     * @param event type combobox action
     */
    public void typeComboBoxAction(ActionEvent event) throws SQLException {
        String selectedType = typeComboBox.getSelectionModel().getSelectedItem();
        Connection conn = DBConnection.getConnection();
        int count = 0;


        try {
            String type = selectedType;
            String selectStatement = "SELECT * FROM appointments WHERE EXTRACT(MONTH FROM '2021-05-00') = MONTH(Start) && ('" + type + "' = Type)";
            DBQuery.setPreparedStatement(conn, selectStatement); // Create prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                /**
                 * Lambda Expression #1
                 * counts the number of like-type appointments in a selected month
                 */
                countAppsInterface counts = n -> n + 1;
                count = counts.countApps(count);
            }
            countLab.setText(String.valueOf(count));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * combo box action sets appTableView
     * @param event
     */
    public void contactComboBoxAction(ActionEvent event) {

        int selectedContact = contactComboBox.getSelectionModel().getSelectedItem().getContactId();
        Connection conn = DBConnection.getConnection();
        ObservableList<Appointments> appList = FXCollections.observableArrayList();

        try {
            String selectStatement = "SELECT * FROM appointments WHERE Contact_ID = " + selectedContact +";";
            DBQuery.setPreparedStatement(conn, selectStatement); // Create prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
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
                    appList.add(Ap);


            }
            /**
             * populates table view
             */
            appTableView.setItems(appList);
            appCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));




        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public void customerComboBoxAction(ActionEvent event) {

        int selectedCustomer = customerComboBox.getSelectionModel().getSelectedItem().getCustomerId();
        Connection conn = DBConnection.getConnection();
        ObservableList<Appointments> customerAppList = FXCollections.observableArrayList();

        try {
            String selectStatement = "SELECT * FROM appointments WHERE Customer_ID = " + selectedCustomer + ";";
            DBQuery.setPreparedStatement(conn, selectStatement); // Create prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ResultSet rs = ps.executeQuery();
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
                customerAppList.add(Ap);

            }

            /**
             * populates table view
             */
            customerAppTableView.setItems(customerAppList);
            appIdCol2.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titleCol2.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol2.setCellValueFactory(new PropertyValueFactory<>("description"));
            typeCol2.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol2.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
            endCol2.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
            contactIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         * creates list of months for use in monthComboBox
         */
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("January");
        list.add("February");
        list.add("March");
        list.add("April");
        list.add("May");
        list.add("June");
        list.add("July");
        list.add("August");
        list.add("September");
        list.add("October");
        list.add("November");
        list.add("December");

        /**
         * sets month combo box items
         */
        monthComboBox.setItems(list);

        /**
         * sets contact combo box
         */
        ObservableList<Contacts> contactList = DBContacts.getContacts();
        contactComboBox.setItems(contactList);

        /**
         * sets contact combo box for customer list
         */
        ObservableList<Customers> customerList = DBCustomers.getAllCustomers();
        customerComboBox.setItems(customerList);



    }
}