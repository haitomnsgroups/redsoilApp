package com.haitomns.redsoil;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class redsoilMainController implements Initializable {

    @FXML
    private ProgressBar splashProgressBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        splashProgressBar.setStyle("-fx-accent:  #C93F3E;");
    }
}
