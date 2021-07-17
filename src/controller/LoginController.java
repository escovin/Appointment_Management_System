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
import model.Users;
import utils.DBUsers;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * controller for Login.fxml
 */
public class LoginController implements Initializable {


    Stage stage;
    Parent scene;

    /**
     * Error display label
     */
    @FXML
    private Label errorLab;

    /**
     * Login button
     */
    @FXML
    private Button loginButton;

    /**
     * username label
     */
    @FXML
    private Label usernameLab;

    /**
     * password label
     */
    @FXML
    private Label passwordLab;

    /**
     * page title label
     */
    @FXML
    private Label titleLab;

    /**
     * Zone ID label
     */
    @FXML
    private Label zoneIdLab;

    /**
     * username text field
     */
    @FXML
    private TextField usernameTxt;

    /**
     * password text field
     */
    @FXML
    private PasswordField passwordTxt;


    private ZoneId localZoneId = ZoneId.systemDefault();


    /**
     * Authenticates supplied "test" username and password and loads the main screen.
     * If password/username is invalid, error is displayed in default system language.
     *
     * @param event loginButtonAction
     * @throws IOException from FXMLLoader
     */
    public void loginButtonAction(ActionEvent event) throws IOException {


        ResourceBundle rb = ResourceBundle.getBundle("utils/Lang_fr", Locale.getDefault());

        if (usernameTxt.getText().equals("test") && (!passwordTxt.getText().equals("test")) && (Locale.getDefault().getLanguage().equals("fr")))  {
            errorLab.setText(rb.getString("InvalidPass"));

        } else if ((usernameTxt.getText().equals("test") && (!passwordTxt.getText().equals("test")))) {
            errorLab.setText("Invalid password");


        } else if (!usernameTxt.getText().equals("test") &&  (Locale.getDefault().getLanguage().equals("fr"))) {
            errorLab.setText(rb.getString("InvalidUsername"));

        } else if (!usernameTxt.getText().equals("test")) {
            errorLab.setText("Invalid username.");

        } else if (usernameTxt.getText().equals("test") && passwordTxt.getText().equals("test")) {
            System.out.println("Login successful.");


            // Navigates to MainScreen.fxml
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

            } else if (Locale.getDefault().getLanguage().equals("fr")) {
            System.out.println("Invalid credentials.");
            errorLab.setText(rb.getString("errorLab"));

            } else {
                errorLab.setText("Invalid username and/or password.");

            }

        /**
         * Log for saving login attempts
         */
        //file and variable names
        String fileName = "login_activity.txt";
        LocalDateTime attemptTime = LocalDateTime.now();
        String outcome = "successful";
        String attempt = "Login attempt by " + usernameTxt.getText() + " at " + attemptTime + " was " + outcome + ".";

        //Create fileWriter object
        FileWriter fWriter = new FileWriter(fileName, true);

        //create and open file
        PrintWriter outputFile = new PrintWriter(fWriter);
        //Write to file
        if ((usernameTxt.getText().equals("test") && passwordTxt.getText().equals("test"))) {
            outputFile.println(attempt);
        } else {
            outcome = "not successful";
            String attemptFail = "Login attempt at " + attemptTime + " was " + outcome + ".";
            outputFile.println(attemptFail);

        }

        //close file
        outputFile.close();
        System.out.println("Login attempt saved.");


        }

    /**
     * Initializes the controller class
     *
     * @param url            url
     * @param resourceBundle rb
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        /**
         * Sets text displays to French depending on system language defaults.
         * Translation provided by Google Translate.
         */
        ResourceBundle rb = ResourceBundle.getBundle("utils/Lang_fr", Locale.getDefault());

        if (Locale.getDefault().getLanguage().equals("fr")) {
            usernameLab.setText(rb.getString("usernameLab"));
            passwordLab.setText(rb.getString("passwordLab"));
            loginButton.setText(rb.getString("loginButton"));
            titleLab.setText(rb.getString("titleLab"));

        }

    /**
     * Displays Zone ID on login page
     */
            zoneIdLab.setText(String.valueOf(localZoneId));


}
}