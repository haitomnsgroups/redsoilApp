package com.haitomns.redsoil;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class bloodComponent {
    @FXML
    private CheckBox prbcs, prbc, ffp, platelets, prp, cryoppt;
    @FXML
    private Label componentDonorId, componentBloodGroup, componentUnit, removeBloodDate;
    @FXML
    private Button removeCompButton, removeWholeBloodButton;

    String donorIDComponent;
    String prbcsCheck, prbcCheck, ffpCheck, plateletsCheck, prpCheck, cryopptCheck;

    @FXML
    public void bloodComponentRemove() {
        String prbcs, prbc, ffp, platelets, prp, cryoppt;

        String dateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        if (this.prbcs.isSelected() && prbcsCheck.equals("No")) {
            prbcs = dateNow;
        }
        else {
            prbcs = prbcsCheck;
        }
        if (this.prbc.isSelected() && prbcCheck.equals("No")) {
            prbc = dateNow;
        }
        else {
            prbc = prbcCheck;
        }
        if (this.ffp.isSelected() && ffpCheck.equals("No")) {
            ffp = dateNow;
        }
        else {
            ffp = ffpCheck;
        }
        if (this.platelets.isSelected() && plateletsCheck.equals("No")) {
            platelets = dateNow;
        }
        else {
            platelets = plateletsCheck;
        }
        if (this.prp.isSelected() && prpCheck.equals("No")) {
            prp = dateNow;
        }
        else {
            prp = prpCheck;
        }
        if (this.cryoppt.isSelected() && cryopptCheck.equals("No")) {
            cryoppt = dateNow;
        }
        else {
            cryoppt = cryopptCheck;
        }

        Alert alertRemove = new Alert(Alert.AlertType.CONFIRMATION);
        alertRemove.setTitle("RedSoil Dashboard");
        alertRemove.setHeaderText("Remove Blood Component");
        alertRemove.setContentText("Are you sure you want to remove this blood Component?");
        Optional<ButtonType> result = alertRemove.showAndWait();
        if (result.get() == ButtonType.OK) {
            mysqlFunction.mysqlDatabaseConnection();
            boolean componentRemovalStatus = mysqlFunction.bloodComponentAdd(donorIDComponent, prbcs, prbc, ffp, platelets, prp, cryoppt);

            Alert alert;
            if (componentRemovalStatus) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Blood Component");
                alert.setHeaderText("Blood Component");
                alert.setContentText("Blood Component Removed Successfully");
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Blood Component");
                alert.setHeaderText("Blood Component");
                alert.setContentText("Blood Component Removal Failed");
            }
            alert.showAndWait();

            Stage stage = (Stage) removeCompButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void removeWholeBlood(){
        String dateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        boolean componentRemovalStatus;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("RedSoil Dashboard");
        alert.setHeaderText("Remove Blood Component");
        alert.setContentText("Are you sure you want to remove this blood Component?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            mysqlFunction.mysqlDatabaseConnection();
            componentRemovalStatus = mysqlFunction.wholeBloodComponentAdd(donorIDComponent, dateNow);

            Alert alertConfirmation;
            if(componentRemovalStatus){
                alertConfirmation = new Alert(Alert.AlertType.INFORMATION);
                alertConfirmation.setTitle("Blood Component");
                alertConfirmation.setHeaderText("Blood Component");
                alertConfirmation.setContentText("Blood Component Removed Successfully");
            }else{
                alertConfirmation = new Alert(Alert.AlertType.ERROR);
                alertConfirmation.setTitle("Blood Component");
                alertConfirmation.setHeaderText("Blood Component");
                alertConfirmation.setContentText("Blood Component Removal Failed");
            }
            alertConfirmation.showAndWait();

            Stage stage = (Stage) removeWholeBloodButton.getScene().getWindow();
            stage.close();
        }
    }

    public void initializeBloodComponentPage(String donor_id, String abo, String rh, String unit){
        donorIDComponent = donor_id;
        componentDonorId.setText("Donor ID: "+donor_id);
        componentBloodGroup.setText("Blood Group: "+abo+" "+rh);
        componentUnit.setText("Unit: "+unit);

        mysqlFunction.mysqlDatabaseConnection();
        List<String> componentReadData = mysqlFunction.bloodComponentInitialize(donor_id);
        if(componentReadData !=  null){
            if(!componentReadData.get(0).equals("No")){
                prbcs.setSelected(true);
                prbcs.setDisable(true);
                prbcs.setText("PRBC With SAGM : ("+componentReadData.get(0)+")");
                prbcsCheck = componentReadData.get(0);
            }
            else {
                prbcsCheck = "No";
            }
            if(!componentReadData.get(1).equals("No")){
                prbc.setSelected(true);
                prbc.setDisable(true);
                prbc.setText("PRBC Without SAGM : ("+componentReadData.get(1)+")");
                prbcCheck = componentReadData.get(1);
            }
            else {
                prbcCheck = "No";
            }
            if(!componentReadData.get(2).equals("No")){
                ffp.setSelected(true);
                ffp.setDisable(true);
                ffp.setText("FFP : ("+componentReadData.get(2)+")");
                ffpCheck = componentReadData.get(2);
            }
            else {
                ffpCheck = "No";
            }
            if(!componentReadData.get(3).equals("No")){
                platelets.setSelected(true);
                platelets.setDisable(true);
                platelets.setText("Platelets : ("+componentReadData.get(3)+")");
                plateletsCheck = componentReadData.get(3);
            }
            else {
                plateletsCheck = "No";
            }
            if(!componentReadData.get(4).equals("No")){
                prp.setSelected(true);
                prp.setDisable(true);
                prp.setText("PRP : ("+componentReadData.get(4)+")");
                prpCheck = componentReadData.get(4);
            }
            else {
                prpCheck = "No";
            }
            if(!componentReadData.get(5).equals("No")){
                cryoppt.setSelected(true);
                cryoppt.setDisable(true);
                cryoppt.setText("Cryoprecipitate : ("+componentReadData.get(5)+")");
                cryopptCheck = componentReadData.get(5);
            }
            else {
                cryopptCheck = "No";
            }
            if(!componentReadData.get(6).equals("No")){
                removeBloodDate.setText("Whole Blood Removed On: "+componentReadData.get(6));
                removeCompButton.setDisable(true);
                removeWholeBloodButton.setDisable(true);
                prbcs.setDisable(true);
                prbc.setDisable(true);
                ffp.setDisable(true);
                platelets.setDisable(true);
                prp.setDisable(true);
                cryoppt.setDisable(true);
            }
        }
    }
}
