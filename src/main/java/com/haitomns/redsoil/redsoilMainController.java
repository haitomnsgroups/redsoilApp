package com.haitomns.redsoil;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class redsoilMainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}