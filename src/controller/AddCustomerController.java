package controller;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Countries;
import model.Divisions;
import utils.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * country/division error label
     */
    @FXML
    private Label errorCountryLab;

    /**
     * name error label
     */
    @FXML
    private Label errorNameLab;

    /**
     * address error label
     */
    @FXML
    private Label errorAddressLab;

    /**
     * postal code error label
     */
    @FXML
    private Label errorPostalCodeLab;

    /**
     * phone error label
     */
    @FXML
    private Label errorPhoneLab;

    /**
     * Add customer button
     */
    @FXML
    private Button addCustomerRecordButton;

    /**
     * Cancel add button
     */
    @FXML
    private Button cancelAddButton;

    /**
     * Title label
     */
    @FXML
    private Label addTitleLab;

    /**
     * Customer name Label
     */
    @FXML
    private Label addCustomerNameLab;

    /**
     * Customer address label
     */
    @FXML
    private Label addCustomerAddressLab;

    /**
     * Customer postal code label
     */
    @FXML
    private Label addCustomerPostalCodeLab;

    /**
     * Customer phone number label
     */
    @FXML
    private Label addCustomerPhoneNumberLab;

    /**
     * Customer country label
     */
    @FXML
    private Label addCustomerCountryLab;

    /**
     * Customer division label;
     */
    @FXML
    private Label addCustomerDivLab;

    /**
     * Customer name text field
     */
    @FXML
    private TextField nameTxt;

    /**
     * Customer address text field
     */
    @FXML
    private TextField addressTxt;

    /**
     * Customer postal code text field
     */
    @FXML
    private TextField postalCodeTxt;

    /**
     * Customer phone number text field
     */
    @FXML
    private TextField phoneNumberTxt;



    /**
     * Cancels customer entry and navigates to MainScreen.fxml
     *
     * @param event Cancel button action
     * @throws IOException from FXMLLoader
     */
    public void cancelAddButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * combo box for division selection
     */
    @FXML
    public ComboBox<Divisions> divComboBox;


    /**
     * combo box for country selection
     */
    @FXML
    public ComboBox<Countries> countryComboBox;
    ObservableList<Countries> countryList = DBCountries.getAllCountries();

    /**
     * @param event countryComboBoxAction filters division combo box
     * @throws SQLException f
     */
    public void countryComboBoxAction(ActionEvent event) throws SQLException {
        int selectedCountry = countryComboBox.getValue().getId();
        System.out.println(selectedCountry);

        if (selectedCountry == 3) {
            ObservableList<Divisions> divisionList = DBCADivisions.getCADivisions();
            divComboBox.setItems(divisionList);
        }
        else if (selectedCountry == 2) {
            ObservableList<Divisions> divisionList = DBUKDivisions.getUKDivisions();
            divComboBox.setItems(divisionList);
        }
        else if (selectedCountry == 1) {
            ObservableList<Divisions> divisionList = DBUSDivisions.getUSDivisions();
            divComboBox.setItems(divisionList);

        }

    }

        /**
         * add customer button action
         * @param event customer button action
         * @throws SQLException from FXMLLoader
         */
        @FXML
        void addCustomerButtonAction (ActionEvent event) throws SQLException {

            // clears error fields
            errorCountryLab.setText("");
            errorPhoneLab.setText("");
            errorAddressLab.setText("");
            errorPostalCodeLab.setText("");
            errorNameLab.setText("");

            try {
                Connection conn = DBConnection.getConnection();
                String insertStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
                DBQuery.setPreparedStatement(conn, insertStatement); // Create prepared statement
                PreparedStatement ps = DBQuery.getPreparedStatement();

                String customer_Name;
                String address;
                String postal_Code;
                String phone;
                int division_id;
                int selectedDivision = divComboBox.getValue().getDivId();
                customer_Name = nameTxt.getText();
                address = addressTxt.getText();
                postal_Code = postalCodeTxt.getText();
                phone = phoneNumberTxt.getText();
                division_id = selectedDivision;

                /**
                 * checks for valid field entries and displays invalid entries in default language
                 */

                ps.setString(1, customer_Name);
                ps.setString(2, address);
                ps.setString(3, postal_Code);
                ps.setString(4, phone);
                ps.setInt(5, division_id);

                ps.execute(); // Execute Prepared Statement

                /**
                 * confirms customer addition in terminal and returns to main screen
                 */
                if (ps.getUpdateCount() > 0) {
                    System.out.println("Customer added!");
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                } else
                    System.out.println("Customer not added.");

            } catch (Exception e) {

                ResourceBundle rb = ResourceBundle.getBundle("utils/Lang_fr", Locale.getDefault());


                if ((nameTxt.getText().isEmpty()) && (Locale.getDefault().getLanguage().equals("fr"))) {
                    errorNameLab.setText(rb.getString("errorNameLab"));
                } else if (nameTxt.getText().isEmpty()) {
                    errorNameLab.setText("Invalid customer name.");

                } else if ((addressTxt.getText().isEmpty()) && (Locale.getDefault().getLanguage().equals("fr"))) {
                    errorAddressLab.setText(rb.getString("errorAddressLab"));
                } else if (addressTxt.getText().isEmpty()) {
                    errorAddressLab.setText("Invalid customer address.");

                } else if ((postalCodeTxt.getText().isEmpty()) && (Locale.getDefault().getLanguage().equals("fr"))) {
                    errorPostalCodeLab.setText(rb.getString("errorPostalCodeLab"));
                } else if (postalCodeTxt.getText().isEmpty()) {
                    errorPostalCodeLab.setText("Invalid customer postal code.");

                } else if ((phoneNumberTxt.getText().isEmpty()) && (Locale.getDefault().getLanguage().equals("fr"))) {
                    errorPhoneLab.setText(rb.getString("errorPhoneLab"));
                } else if (phoneNumberTxt.getText().isEmpty()) {
                    errorPhoneLab.setText("Invalid customer phone number.");

                } else if (countryComboBox.getValue() == null) {
                    errorCountryLab.setText("Please select a country.");

                    } else errorCountryLab.setText("Please select a division.");
                }
            }

    /**
     * Initializes controller
     * @param url url
     * @param resourceBundle rb
     */
    @Override
        public void initialize (URL url, ResourceBundle resourceBundle) {

            //DBConnection.startConnection();
            Connection conn = DBConnection.startConnection();

        /**
         * populates country combo box
         */
        countryComboBox.setItems(countryList);
           // divComboBox.setItems(divisionList);

        /**
         * Sets text displays to french
         */
            ResourceBundle rb = ResourceBundle.getBundle("utils/Lang_fr", Locale.getDefault());
            if (Locale.getDefault().getLanguage().equals("fr")) {
                addCustomerNameLab.setText(rb.getString("addCustomerNameLab"));
                addCustomerAddressLab.setText(rb.getString("addCustomerAddressLab"));
                addCustomerPostalCodeLab.setText(rb.getString("addCustomerPostalCodeLab"));
                addCustomerPhoneNumberLab.setText(rb.getString("addCustomerPhoneNumberLab"));
                addTitleLab.setText(rb.getString("addTitleLab"));
                addCustomerRecordButton.setText(rb.getString("addCustomerRecordButton"));
                cancelAddButton.setText(rb.getString("cancelAddButton"));
                addCustomerCountryLab.setText(rb.getString("addCustomerCountryLab"));
                addCustomerDivLab.setText(rb.getString("addCustomerDivLab"));

            }

        }

    }