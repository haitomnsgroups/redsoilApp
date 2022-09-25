package com.haitomns.redsoil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
        stage.show();

        stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - stage.getWidth()) / 2);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - stage.getHeight()) / 2);

        redsoilMain.database_connection = mysqlFunction.mysqlDatabaseConnection();
        if(!database_connection){

            mysql_connection = mysqlFunction.mysqlConnectionCheck();
            if(!mysql_connection){
                openDBConfigurator();
            }
            else{
                boolean restoreStatus = mysqlFunction.restoreDatabase();
                if(restoreStatus){
                    redsoilMainController restoreSignup = new redsoilMainController();
                    restoreSignup.startLoginOrSignup("signup");
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Database Restore Failed");
                    alert.setContentText("Contact the RedSoil Support Team.");
                    alert.showAndWait();

                    openDBConfigurator();
                }
            }
            stage.close();
        }else{
            stage.close();
            redsoilMainController startLoginOrSignup = new redsoilMainController();
            String userStatus = redsoilMainController.checkUser();
            if (userStatus != null) {
                if(userStatus.equals("true")) {
                    startLoginOrSignup.startLoginOrSignup("login");
                }
                else{
                    startLoginOrSignup.startLoginOrSignup("signup");
                }
            }
            else{
                FileWriter fileWriter = new FileWriter("redSoilUser.rdfs");
                fileWriter.write("false");
                fileWriter.close();
                startLoginOrSignup.startLoginOrSignup("signup");
            }
        }
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
        dbConfStage.initStyle(StageStyle.UTILITY);
        dbConfStage.setTitle("RedSoil DB Configurator");
        dbConfStage.show();
    }
}