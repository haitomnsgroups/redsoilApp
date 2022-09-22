package com.haitomns.redsoil;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class editDataController implements Initializable {
    @FXML
    private TextField donationOrganizationField, donorNameField, donorAgeField, donorPhoneField, donorOccupationField, donorAddressField, donorEmailField;
    @FXML
    private ChoiceBox<String> donorGenderField = new ChoiceBox<>();
    @FXML
    private DatePicker previouslyDonatedDate;
    @FXML
    private RadioButton donatedNoRadio, donatedYesRadio;
    @FXML
    private TextField patientNameField, donorIdField;
    @FXML
    private TextField weight, bp, hb, respSys, cvs, giSystem, other, fit, unit;
    @FXML
    private ChoiceBox<String> aboField, rhField = new ChoiceBox<>();
    @FXML
    private TextField  hiv, hbsag, hcv, vdrl;
    @FXML
    private CheckBox malaria, leprosy, highBloodPressure, lotusPitta, diabetes, preSurgery, tuberculosis, pregnancy, drugAbuse, heartDisease, pneumonia, jaundice, kidneyDisease, aids, faintingSpells, cutaneousDisease, std, menstruation, foreignVisit, others;
    @FXML
    private DatePicker bloodExpiryDateField;

    public void updateView(String donorId){
        try{
            Parent updateParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("editData-view.fxml")));
            Scene updateScene = new Scene(updateParent);
            Stage updateStage = new Stage();
            updateStage.setScene(updateScene);
            updateStage.setTitle("RedSoil Dashboard");
            updateStage.setResizable(false);
            updateStage.initModality(Modality.APPLICATION_MODAL);
            updateStage.showAndWait();

            initializeUpdatePage(donorId);

        } catch (IOException e) {
            Logger.getLogger(editDataController.class.getName()).log(Level.SEVERE, null, e);
        }

    }
    public void initializeUpdatePage(String donorId) {
        mysqlFunction.mysqlDatabaseConnection();
        List<String> updateData = mysqlFunction.fetchUpdateData(donorId);

        donationOrganizationField.setText(updateData.get(1));
        donorNameField.setText(updateData.get(2));
        donorGenderField.setValue(updateData.get(3));
        donorAgeField.setText(updateData.get(4));
        donorOccupationField.setText(updateData.get(5));
        donorAddressField.setText(updateData.get(6));
        donorPhoneField.setText(updateData.get(7));
        donorEmailField.setText(updateData.get(8));
        patientNameField.setText(updateData.get(9));
        donorIdField.setText(updateData.get(10));
        if (updateData.get(11).equals("Yes")) {
            donatedYesRadio.setSelected(true);
        } else {
            donatedNoRadio.setSelected(true);
        }
        previouslyDonatedDate.setValue(java.sql.Date.valueOf(updateData.get(12)).toLocalDate());

        weight.setText(updateData.get(14));
        bp.setText(updateData.get(15));
        hb.setText(updateData.get(16));
        respSys.setText(updateData.get(17));
        cvs.setText(updateData.get(18));
        giSystem.setText(updateData.get(19));
        other.setText(updateData.get(20));
        fit.setText(updateData.get(21));
        unit.setText(updateData.get(22));
        aboField.setValue(updateData.get(23));
        rhField.setValue(updateData.get(24));
        hiv.setText(updateData.get(25));
        hbsag.setText(updateData.get(26));
        hcv.setText(updateData.get(27));
        vdrl.setText(updateData.get(28));
        bloodExpiryDateField.setValue(java.sql.Date.valueOf(updateData.get(29)).toLocalDate());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
