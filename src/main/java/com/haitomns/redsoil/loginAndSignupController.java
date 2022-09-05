package com.haitomns.redsoil;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class loginAndSignupController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public void loginButtonClicked(){
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(usernameAndPasswordValidate(username, password)){
            mysqlFunction.mysqlDatabaseConnection();

            if(mysqlFunction.loginCheck(username, password)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("RedSoil Login");
                alert.setContentText("Login Successful");
                alert.showAndWait();
            }
            else{
                showError("Error", "RedSoil Login", "Login Failed :(");
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
        else if(password.length() < 5 || password.length() > 20){
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

    public static void showError(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
