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
import model.Countries;
import model.Customers;
import model.Divisions;
import utils.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * controller for ModifyCustomer.fxml
 * @author Erik Scovin
 */
public class ModifyCustomerController implements Initializable {

    Stage stage;
    Parent scene;

    ObservableList<Countries> countryList = DBCountries.getAllCountries();
    ObservableList<Divisions> divisionsList = DBDivisions.getDivisions();

    private ObservableList<Customers> customersList = FXCollections.observableArrayList();
    /**
     * customer name text field
     */
    @FXML
    private TextField nameTxt;

    /**
     * deletion confirmation label
     */
    @FXML
    private Label deleteConfirmLab;

    /**
     * customer ID text field
     */
    @FXML
    private TextField idTxt;

    /**
     * customer address text field
     */
    @FXML
    private TextField addressTxt;

    @FXML
    private Label errorSelDeleteLab;

    /**
     * customer postal code text field
     */
    @FXML
    private TextField postalCodeTxt;

    /**
     * customer phone number text field
     */
    @FXML
    private TextField phoneNumberTxt;

    /**
     * modify customer phone number label
     */
    @FXML
    private Label modifyCustomerPhoneNumberLab;

    /**
     * modify customer country label
     */
    @FXML
    private Label modifyCustomerCountryLab;

    /**
     * modify customer division label
     */
    @FXML
    private Label modifyCustomerDivLab;

    /**
     * name error label
     */
    @FXML
    private Label errorModNameLab;

    /**
     * division id variable
     */
    private int divisionId;
    private int countryId;

    /**
     * country error label
     */
    @FXML
    private Label errorCountryLab;

    /**
     * address error label
     */
    @FXML
    private Label errorModAddressLab;

    /**
     * postal code error label
     */
    @FXML
    private Label errorModPostalCodeLab;

    /**
     * phone number error label
     */
    @FXML
    private Label errorModPhoneLab;

    /**
     * customer id label
     */
    @FXML
    private Label modifyCustomerIdLab;

    /**
     * customer postal code label
     */
    @FXML
    private Label modifyCustomerPostalCodeLab;

    /**
     * customer address label
     */
    @FXML
    private Label modifyCustomerAddressLab;

    /**
     * customer name label
     */
    @FXML
    private Label modifyCustomerNameLab;

    /**
     * page title label
     */
    @FXML
    private Label modifyTitleLab;

    /**
     * division combo box
     */
    @FXML
    public ComboBox<Divisions> divComboBox;

    /**
     * country combo box
     */
    @FXML
    public ComboBox<Countries> countryComboBox;

    /**
     * save modify customer button
     */
    @FXML
    private Button saveModifyCustomerButton;

    /**
     * cancel customer modify button
     */
    @FXML
    private Button cancelModifyButton;

    /**
     * delete customer button
     */
    @FXML
    private Button deleteCustomerButton;

    /**
     * select modify button
     */
    @FXML
    private Button selectModifyButton;

    //Table data
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableColumn addressCol;
    public TableColumn postalCodeCol;
    public TableColumn phoneCol;
    public TableColumn divCol;

    public TableView<Customers> customerTableView;

    /**
     * filters divComboBox when country is selected
     * @param event
     */
    public void countryComboBoxAction(ActionEvent event) {

        errorCountryLab.setText("");

        ResourceBundle rb = ResourceBundle.getBundle("utils/Lang_fr", Locale.getDefault());

            int selectedCountry;
            ObservableList<Countries> countryList = DBCountries.getAllCountries();

            /**
             * filters division combo box options when selecting country.
             */
            selectedCountry = countryComboBox.getValue().getId();
            if (selectedCountry == 3) {
                ObservableList<Divisions> divisionList = DBCADivisions.getCADivisions();
                divComboBox.setItems(divisionList);
            } else if (selectedCountry == 2) {
                ObservableList<Divisions> divisionList = DBUKDivisions.getUKDivisions();
                divComboBox.setItems(divisionList);
            } else if (selectedCountry == 1) {
                ObservableList<Divisions> divisionList = DBUSDivisions.getUSDivisions();
                divComboBox.setItems(divisionList);

            } else {
                ObservableList<Divisions> divisionList = DBUSDivisions.getUSDivisions();
                divComboBox.setItems(null);
            }
        if ((countryComboBox.getValue().getId() != 3)
                && (countryComboBox.getValue().getId() != 2)
                && (countryComboBox.getValue().getId() != 1)
                && (Locale.getDefault().getLanguage().equals("fr"))) {
            errorCountryLab.setText(rb.getString("errorCountryLab"));
        } else if ((countryComboBox.getValue().getId() != 3)
                && (countryComboBox.getValue().getId() != 2)
                && (countryComboBox.getValue().getId() != 1)) {
            errorCountryLab.setText("Invalid country/division selection.");

        }
    }


    /**
     * Selects customer record to modify
     * @param event
     * @throws SQLException
     */
    public void selectModifyButtonAction(ActionEvent event) throws SQLException {




        /**
         * Clears error labels if they exists.
         */
        deleteConfirmLab.setText("");
        errorSelDeleteLab.setText("");


        try {
        Customers selectedCustomer;
        selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        ObservableList<Countries> countryList = DBCountries.getAllCountries();
        ObservableList<Divisions> divisionsList = DBDivisions.getDivisions();
        divComboBox.setItems(divisionsList);
        countryComboBox.setItems(countryList);


        /**
         * Populates text fields with customer data
         */
        idTxt.setText(String.valueOf(selectedCustomer.getCustomerId()));
        nameTxt.setText(selectedCustomer.getCustomerName());
        addressTxt.setText(selectedCustomer.getCustomerAddress());
        postalCodeTxt.setText(selectedCustomer.getCustomerPostalCode());
        phoneNumberTxt.setText(selectedCustomer.getCustomerPhoneNumber());

        /**
         * sets combo boxes and filters divComboBox options
         */
        divisionId = selectedCustomer.getDivisionId();
        for (Divisions d : divComboBox.getItems()) {
            if (divisionId == d.getDivId()) {
                countryId = d.getCountryId();
                if (countryId == 38) {
                    divComboBox.setItems(DBCADivisions.getCADivisions());
                    divComboBox.setValue(d); }
                if (countryId == 230) {
                    divComboBox.setItems(DBUKDivisions.getUKDivisions());
                    divComboBox.setValue(d); }
                if (countryId == 231) {
                    divComboBox.setItems(DBUSDivisions.getUSDivisions());
                    divComboBox.setValue(d);}
                    for (Countries c : countryComboBox.getItems()) {
                    if (countryId == c.getId()) {
                        countryComboBox.setValue(c);
                    }
                }
            }
        }
        for (Divisions d : divComboBox.getItems()) {
            if (divisionId == d.getDivId()) {
                divComboBox.setValue(d);
            }
            }

        } catch (Exception e) {
        errorSelDeleteLab.setText("Please select a customer record to modify.");
        }
        }

        public void saveModifyCustomerButtonAction(ActionEvent event) throws SQLException {

            ResourceBundle rb = ResourceBundle.getBundle("utils/Lang_fr", Locale.getDefault());

            // clears error fields
            errorCountryLab.setText("");
            errorModPhoneLab.setText("");
            errorModAddressLab.setText("");
            errorModPostalCodeLab.setText("");
            errorModNameLab.setText("");


            /**
             * Clears deletion confirmation if it exists
             */
            deleteConfirmLab.setText("");

            /**
             * MYSQL statement to update customer record
             */
            try {
            Connection conn = DBConnection.getConnection();
            String updateStatement = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Division_ID = ? WHERE Customer_ID = ?";
            DBQuery.setPreparedStatement(conn, updateStatement); // Create prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();

            String name, address, postalCode, phone;
            int divId, customerId;

            customerId = Integer.parseInt(idTxt.getText());
            name = nameTxt.getText();
            address = addressTxt.getText();
            postalCode = postalCodeTxt.getText();
            phone = phoneNumberTxt.getText();
            Timestamp lastUpdate = new Timestamp(System.currentTimeMillis());
            divId = divComboBox.getValue().getDivId();



            if ((nameTxt.getText().isEmpty()) && (Locale.getDefault().getLanguage().equals("fr"))) {
                errorModNameLab.setText(rb.getString("errorNameLab"));
            } else if (nameTxt.getText().isEmpty()) {
                errorModNameLab.setText("Invalid customer name.");

            } else if ((addressTxt.getText().isEmpty()) && (Locale.getDefault().getLanguage().equals("fr"))) {
                errorModAddressLab.setText(rb.getString("errorAddressLab"));
            } else if (addressTxt.getText().isEmpty()) {
                errorModAddressLab.setText("Invalid customer address.");

            } else if ((postalCodeTxt.getText().isEmpty()) && (Locale.getDefault().getLanguage().equals("fr"))) {
                errorModPostalCodeLab.setText(rb.getString("errorPostalCodeLab"));
            } else if (postalCodeTxt.getText().isEmpty()) {
                errorModPostalCodeLab.setText("Invalid customer postal code.");

            } else if ((phoneNumberTxt.getText().isEmpty()) && (Locale.getDefault().getLanguage().equals("fr"))) {
                errorModPhoneLab.setText(rb.getString("errorPhoneLab"));
            } else if (phoneNumberTxt.getText().isEmpty()) {
                errorModPhoneLab.setText("Invalid customer phone number.");

            } else if ((countryComboBox.getValue().getId() != 3)
                    && (countryComboBox.getValue().getId() != 2)
                    && (countryComboBox.getValue().getId() != 1)
                    && (Locale.getDefault().getLanguage().equals("fr"))) {
                errorCountryLab.setText(rb.getString("errorCountryLab"));
            } else if ((countryComboBox.getValue().getId() != 3)
                    && (countryComboBox.getValue().getId() != 2)
                    && (countryComboBox.getValue().getId() != 1)) {
                errorCountryLab.setText("Invalid country/division selection.");

            } else {

                /**
                 * key mapping
                 */
                ps.setString(1, name);
                ps.setString(2, address);
                ps.setString(3, postalCode);
                ps.setString(4, phone);
                ps.setTimestamp(5,lastUpdate);
                ps.setInt(6, divId);
                ps.setInt(7, customerId);


                /**
                 * executes prepared statement
                 */
                ps.execute();


                /**
                 * Reloads table view
                 */
                ObservableList<Customers> customerList = DBCustomers.getAllCustomers();
                customerTableView.setItems(customerList);
                idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
                nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
                addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
                postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
                phoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
                divCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));


            }
            } catch(Exception e) {
                /**
                 * checks for selection, outputs error if nothing is selected
                 */
                String selection;
                selection = idTxt.getText();
                if ((selection.isEmpty() && (Locale.getDefault().getLanguage().equals("fr")))) {
                    errorCountryLab.setText(rb.getString("selectError"));
                } else if (selection.isEmpty()){
                    errorCountryLab.setText("Select a customer record to modify.");

            } else if (Locale.getDefault().getLanguage().equals("fr")) {
                    errorCountryLab.setText(rb.getString("errorCountryLab"));
                } else errorCountryLab.setText("Invalid country/division selection.");
            }
        }


    /**
     * cancels customer modification and navigates to MainScreen.fxml
     * @param event cancel modify button action
     * @throws IOException from FXMLLoader
     */
    public void cancelModifyButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * deletes customer record and any associated appointments
     * @param event delete customer button action
     * @throws SQLException from SQL
     */
    public void deleteCustomerButtonAction(ActionEvent event) throws SQLException {

        errorSelDeleteLab.setText("");
        deleteConfirmLab.setText("");

        try {
            ResourceBundle rb = ResourceBundle.getBundle("utils/Lang_fr", Locale.getDefault());
            int customerId;

            Customers customerToDelete = customerTableView.getSelectionModel().getSelectedItem();
            customerId = customerToDelete.getCustomerId();

            /**
             * SQL statement to delete customer appointments
             */
            Connection conn = DBConnection.getConnection();
            String deleteStatement = "DELETE FROM appointments WHERE Customer_ID = ?";
            DBQuery.setPreparedStatement(conn, deleteStatement); // Create prepared statement
            PreparedStatement ps = DBQuery.getPreparedStatement();


            /**
             * key mapping value
             */
            ps.setInt(1, customerId);

            /**
             * executes prepared statement
             */
            ps.execute();

            /**
             * SQL statement to delete customer record
             */
            String deleteStatement2 = "DELETE FROM customers WHERE Customer_ID = ?";
            DBQuery.setPreparedStatement(conn, deleteStatement2);
            PreparedStatement ps2 = DBQuery.getPreparedStatement();

            ps2.setInt(1, customerId);

            /**
             * executes prepared statement 2
             */
            ps2.execute();

            /**
             * Reloads table after customer deletion
             */
            ObservableList<Customers> customerList = DBCustomers.getAllCustomers();
            customerTableView.setItems(customerList);
            idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            phoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
            divCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

            /**
             * checks for successful record deletion
             */

            if (ps2.getUpdateCount() > 0) {
                deleteConfirmLab.setText("Customer record successfully deleted!");

            }
            } catch(Exception e){

                errorSelDeleteLab.setText("Please select a customer record to delete.");

            }
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         * populates table view with customer information
         */
        Connection conn = DBConnection.getConnection();
        ObservableList<Customers> customerList = DBCustomers.getAllCustomers();
        customerTableView.setItems(customerList);
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        divCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        /**
         * populates country combo box
         */
         countryComboBox.setItems(countryList);
         divComboBox.setItems(divisionsList);


        }

    }

