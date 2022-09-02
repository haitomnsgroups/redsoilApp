package com.haitomns.redsoil;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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

        //start the connection thread
        connectionThread mainConneciton = new connectionThread();
        mainConneciton.start();

        try {
            mainConneciton.join();
            //waiting for 5 secons before closing splash screen
            PauseTransition delay = new PauseTransition(Duration.seconds(5));
            delay.setOnFinished( event -> stage.close() );
            delay.play();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        launch();
    }
}

class connectionThread extends Thread{
    @Override
    public void run() {
        //connects to database
        boolean database_connection = mysqlFunction.mysqlDatabaseConnection();
    }
}