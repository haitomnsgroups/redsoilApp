package com.haitomns.redsoil;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class redsoilMain extends Application {
    static boolean database_connection;
    static boolean mysql_connection;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(redsoilMain.class.getResource("redsoilMain-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 440, 250);
        stage.setScene(scene);
        stage.alwaysOnTopProperty();
        stage.initStyle(StageStyle.UNDECORATED);

        Image icon = new Image("file:redSoilLogoRed.png");
        stage.getIcons().add(icon);

        stage.show();

        stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - stage.getWidth()) / 2);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - stage.getHeight()) / 2);

        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished( event -> {
            redsoilMain.database_connection = mysqlFunction.mysqlDatabaseConnection();
            if(!database_connection){

                mysql_connection = mysqlFunction.mysqlConnectionCheck();
                if(!mysql_connection){
                    stage.close();
                    openDBConfigurator();
                }
                else{
                    boolean restoreStatus = mysqlFunction.restoreDatabase();
                    if(restoreStatus){
                        stage.close();
                        redsoilMainController restoreSignup = new redsoilMainController();
                        restoreSignup.startLoginOrSignup("signup");
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Database Restore Failed");
                        alert.setContentText("Contact the RedSoil Support Team.");
                        alert.showAndWait();

                        stage.close();
                        openDBConfigurator();
                    }
                }
            }else{
                redsoilMainController startLoginOrSignup = new redsoilMainController();
                String userStatus;
                try {
                    userStatus = redsoilMainController.checkUser();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                if (userStatus != null) {
                    if(userStatus.equals("true")) {
                        stage.close();
                        startLoginOrSignup.startLoginOrSignup("login");
                    }
                    else{
                        stage.close();
                        startLoginOrSignup.startLoginOrSignup("signup");
                    }
                }
                else{
                    FileWriter fileWriter;
                    try {
                        fileWriter = new FileWriter("redSoilUser.rdfs");
                        fileWriter.write("false");
                        fileWriter.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stage.close();
                    startLoginOrSignup.startLoginOrSignup("signup");
                }
            }
        } );
        delay.play();
    }

    public void  openDBConfigurator(){
        FXMLLoader dbConfLoader = new FXMLLoader();
        dbConfLoader.setLocation(getClass().getResource("databaseConfigurator-view.fxml"));
        try {
            dbConfLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent parent = dbConfLoader.getRoot();
        Stage dbConfStage = new Stage();
        dbConfStage.setScene(new Scene(parent));
        dbConfStage.initStyle(StageStyle.DECORATED);
        dbConfStage.setResizable(false);
        dbConfStage.setTitle("RedSoil DB Configurator");
        Image icon = new Image("file:redSoilLogoRed.png");
        dbConfStage.getIcons().add(icon);
        dbConfStage.show();
    }
}