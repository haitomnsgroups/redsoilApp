package com.haitomns.redsoil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class loginAndSignup extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(redsoilMain.class.getResource("loginAndSignup-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        //sets splash screen to center of screen
        primaryStage.setX((Screen.getPrimary().getVisualBounds().getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((Screen.getPrimary().getVisualBounds().getHeight() - primaryStage.getHeight()) / 2);
    }
}
