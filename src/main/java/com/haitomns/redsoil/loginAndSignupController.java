package com.haitomns.redsoil;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class loginAndSignupController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField resetPhoneNumber, resetUsername, resetPassword;
    @FXML
    private HBox registerationHbox;
    @FXML
    private HBox loginHbox;
    @FXML
    private HBox resetPasswordHbox;
    @FXML
    private TextField companyNameField, companyAddressField, companyPhoneField, usernameRegisterField, passwordRegisterField;

    public void loginButtonClicked() throws NoSuchAlgorithmException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(usernameAndPasswordValidate(username, password)){
            mysqlFunction.mysqlDatabaseConnection();

            if(mysqlFunction.loginCheck(username, encryptString(password))){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("RedSoil Login");
                alert.setContentText("Login Successful");
                alert.showAndWait();

                Stage loginStage = (Stage) loginHbox.getScene().getWindow();
                loginStage.close();

                FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("dashboard-view.fxml"));
                try {
                    dashboardLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent parent = dashboardLoader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setTitle("RedSoil Dashboard");
                Image icon = new Image("file:redSoilLogoRed.png");
                stage.getIcons().add(icon);
                stage.show();
            }
            else{
                showError("Error", "RedSoil Login", "Login Failed :(");
                passwordField.setText("");
                usernameField.setText("");
            }
        }
    }

    public static boolean usernameAndPasswordValidate(String username, String password){
        if(username.isEmpty() || password.isEmpty()){
            showError("Error", "RedSoil Login", "Username and Password cannot be empty");
            return false;
        }
        else if(username.length() < 5 || username.length() > 20){
            showError("Error", "RedSoil Login", "Username must be between 5 and 20 characters");
            return false;
        }
        else if(password.length() < 5 || password.length() > 50){
            showError("Error", "RedSoil Login", "Password must be between 5 and 30 characters");
            return false;
        }
        else if(username.contains(" ") || password.contains(" ")){
            showError("Error", "RedSoil Login", "Username and Password cannot contain spaces");
            return false;
        }
        else{
            return true;
        }
    }

    public void registrationSubmitButton() throws NoSuchAlgorithmException, IOException {
        String companyName = companyNameField.getText();
        String companyAddress = companyAddressField.getText();
        String companyPhone = companyPhoneField.getText();
        String username = usernameRegisterField.getText();
        String password = passwordRegisterField.getText();

        if(companyValidate(companyName, companyAddress, companyPhone, username, password)){
            mysqlFunction.mysqlDatabaseConnection();
            if(mysqlFunction.companyUpdate(companyName, companyAddress, companyPhone, username, encryptString(password))){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("RedSoil Registration");
                alert.setContentText("Registration Successful");
                alert.showAndWait();

                FileWriter fileWriter = new FileWriter("redSoilUser.rdfs");
                fileWriter.write("true");
                fileWriter.close();

                loginHbox.toFront();
            }
            else{
                showError("Error", "RedSoil Registration", "Registration Failed :(");
            }
        }
    }

    public static boolean companyValidate(String companyName, String companyAddress, String companyPhone, String username, String password){
        if(companyName.isEmpty() || companyAddress.isEmpty() || companyPhone.isEmpty() || username.isEmpty() || password.isEmpty()){
            showError("Error", "RedSoil Registration", "All fields are required");
            return false;
        }
        else if(companyName.length() < 2 || companyName.length() > 1024){
            showError("Error", "RedSoil Registration", "Company Name must be between 5 and 20 characters");
            return false;
        }
        else if(companyAddress.length() < 2 || companyAddress.length() > 2048){
            showError("Error", "RedSoil Registration", "Company Address must be between 5 and 50 characters");
            return false;
        }
        else if(companyPhone.length() != 10){
            showError("Error", "RedSoil Registration", "Company Phone must be 10 digits");
            return false;
        }
        else if(username.length() < 5 || username.length() > 20){
            showError("Error", "RedSoil Registration", "Username must be between 5 and 20 characters");
            return false;
        }
        else if(password.length() < 5 || password.length() > 50){
            showError("Error", "RedSoil Registration", "Password must be between 5 and 30 characters");
            return false;
        }
        else if(username.contains(" ") || password.contains(" ")){
            showError("Error", "RedSoil Registration", "Username and Password cannot contain spaces");
            return false;
        }
        else{
            return true;
        }
    }

    public void resetButtonClick() throws NoSuchAlgorithmException {
        String username = resetUsername.getText();
        String password = resetPassword.getText();
        String phoneNumber = resetPhoneNumber.getText();

        if(username.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()){
            showError("Error", "RedSoil Reset", "All fields are required");
        }
        else if(username.length() < 5 || username.length() > 20){
            showError("Error", "RedSoil Reset", "Username must be between 5 and 20 characters");
        }
        else if(password.length() < 5 || password.length() > 50){
            showError("Error", "RedSoil Reset", "Password must be between 5 and 30 characters");
        }
        else if(username.contains(" ") || password.contains(" ")){
            showError("Error", "RedSoil Reset", "Username and Password cannot contain spaces");
        }
        else if(phoneNumber.length() != 10){
            showError("Error", "RedSoil Reset", "Phone Number must be 10 digits");
        }
        else{
            mysqlFunction.mysqlDatabaseConnection();
            if(mysqlFunction.resetPassword(phoneNumber, username, encryptString(password))){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("RedSoil Reset");
                alert.setContentText("Password Reset Successful");
                alert.showAndWait();

                loginHbox.toFront();
            }
            else{
                showError("Error", "RedSoil Reset", "Password Reset Failed :(");
            }
        }
    }

    public void resetPageOpen(){
        resetPasswordHbox.toFront();
    }

    public void setLoginHbox(){
        loginHbox.toFront();
    }

    public void setRegisterHbox(){
        registerationHbox.toFront();
    }

    public static void showError(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static String encryptString(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger bigInt = new BigInteger(1,messageDigest);
        return bigInt.toString(16);
    }
}
