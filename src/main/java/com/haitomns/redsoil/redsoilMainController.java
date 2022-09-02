package com.haitomns.redsoil;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

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

    public void showDatabaseConnectivityAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database Connection Error");
        alert.setContentText("Could not connect to database.");
        alert.showAndWait();
    }
}
