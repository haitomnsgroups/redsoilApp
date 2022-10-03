package com.haitomns.redsoil;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class databaseConfigurator implements Initializable {
    @FXML
    private TextField mysqlPort, mysqlUsername, mysqlPassword, mysqlPath;
    @FXML
    private ProgressBar dbConfProgress;
    @FXML
    public void dbConfigSubmitClick(){
        String mysqlPortInput = mysqlPort.getText();
        String mysqlUsernameInput = mysqlUsername.getText();
        String mysqlPasswordInput = mysqlPassword.getText();
        String mysqlPathInput = mysqlPath.getText();

        dbConfProgress.setVisible(true);

        if(validateDatabaseConfig(mysqlPortInput, mysqlUsernameInput, mysqlPasswordInput, mysqlPathInput)) {
            try {
                if(mysqlPathInput.contains("\\")){
                    mysqlPathInput = mysqlPathInput.replace("\\", "/");
                }
                mysqlPathInput = mysqlPathInput + "/";

                FileWriter dbConfig = new FileWriter("redSoilDatabaseConnection.rdfs");
                dbConfig.write(mysqlPortInput+"\n");
                dbConfig.write(mysqlUsernameInput+"\n");
                dbConfig.write(mysqlPasswordInput+"\n");
                dbConfig.write(mysqlPathInput);
                dbConfig.close();

                boolean statusAfterWrite = mysqlFunction.mysqlDatabaseConnection();
                if(!statusAfterWrite){
                    boolean statusOfConnection = mysqlFunction.mysqlConnectionCheck();
                    if(!statusOfConnection){
                        dbConfProgress.setVisible(false);
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("RedSoil Database Configurator");
                        alert.setHeaderText("Database Connection");
                        alert.setContentText("Database Connection Failed. Provided Credentials are incorrect.");
                        alert.showAndWait();
                    }
                    else {
                        boolean restoreStatus = mysqlFunction.restoreDatabase();
                        dbConfProgress.setVisible(false);

                        if(restoreStatus){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("RedSoil Database Configurator");
                            alert.setHeaderText("Database Connection");
                            alert.setContentText("Database Connection Successful. Database Created.");
                            alert.showAndWait();

                            Stage stage = (Stage) mysqlPort.getScene().getWindow();
                            stage.close();

                            redsoilMainController restoreSignup = new redsoilMainController();
                            restoreSignup.startLoginOrSignup("signup");
                        }
                        else{
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("RedSoil Database Configurator");
                            alert.setHeaderText("Database Connection");
                            alert.setContentText("Database Connection Failed. Database Creation Failed.");
                            alert.showAndWait();
                        }
                    }
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("RedSoil Database Configurator");
                    alert.setHeaderText("Database Connection");
                    alert.setContentText("Database Connection Successful.");
                    alert.showAndWait();

                    Stage stage = (Stage) mysqlPort.getScene().getWindow();
                    stage.close();

                    redsoilMainController restoreSignup = new redsoilMainController();
                    restoreSignup.startLoginOrSignup("signup");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean validateDatabaseConfig(String mysqlPort, String mysqlUsername, String mysqlPassword, String mysqlPath){
        return !mysqlPort.isEmpty() && !mysqlUsername.isEmpty() && !mysqlPassword.isEmpty() && !mysqlPath.isEmpty();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConfProgress.setStyle("-fx-accent:  #C93F3E;");
    }
}
