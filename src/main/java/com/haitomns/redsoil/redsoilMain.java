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
    static boolean database_connection;
    public static void main(String[] args) throws IOException {
        launch();
    }
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
        connectionThread mainConnection = new connectionThread();
        mainConnection.start();

        try {
            mainConnection.join();

            if(!database_connection){
                redsoilMainController controller = fxmlLoader.getController();
                controller.showDatabaseConnectivityAlert();
                stage.close();
            }

            //waiting for 5 seconds before closing splash screen
            PauseTransition delay = new PauseTransition(Duration.seconds(5));
            delay.setOnFinished( event -> stage.close() );
            delay.play();

            //TODO: Start the Login Screen

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class connectionThread extends Thread{
    @Override
    public void run() {
        redsoilMain.database_connection = mysqlFunction.mysqlDatabaseConnection();
    }
}