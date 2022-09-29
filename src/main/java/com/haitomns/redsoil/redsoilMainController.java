package com.haitomns.redsoil;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class redsoilMainController implements Initializable {

    @FXML
    private ProgressBar splashProgressBar;
    @FXML
    public Label splashProgressLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        splashProgressBar.setStyle("-fx-accent:  #C93F3E;");
        splashProgressLabel.setText("Connecting to database...");
    }

    public void startLoginOrSignup(String userStatus) {
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("loginAndSignup-view.fxml"));
        try {
            loginLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent parent = loginLoader.getRoot();
        Stage loginStage = new Stage();
        loginStage.setScene(new Scene(parent));
        loginStage.initStyle(StageStyle.DECORATED);
        loginStage.setTitle("RedSoil Login & Signup");
        loginStage.setResizable(false);
        Image icon = new Image("file:redSoilLogoRed.png");
        loginStage.getIcons().add(icon);
        loginStage.show();

        if(userStatus.equals("login")){
            loginAndSignupController loginController = loginLoader.getController();
            loginController.setLoginHbox();
        }
        else if(userStatus.equals("signup")){
            loginAndSignupController loginController = loginLoader.getController();
            loginController.setRegisterHbox();
        }
    }

    public static String checkUser() throws FileNotFoundException {
        File userFile = new File("redSoilUser.rdfs");
        if(userFile.exists()){
            Scanner scanner = new Scanner(userFile);
            return scanner.nextLine();
        }
        else{
            return null;
        }
    }
}
