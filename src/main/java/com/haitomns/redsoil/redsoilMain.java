package com.haitomns.redsoil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class redsoilMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(redsoilMain.class.getResource("redsoilMain-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 440, 250);
        stage.setScene(scene);
        stage.alwaysOnTopProperty();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        //sets splash screen to center of screen
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - stage.getWidth()) / 2);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - stage.getHeight()) / 2);
    }

    public static void main(String[] args) {

        boolean database_connection = mysqlFunction.mysqlDatabaseConnection();

        if (database_connection) {
            System.out.println("Database connection successful");
        } else {
            System.out.println("Database connection failed");
        }

        launch();
    }
}