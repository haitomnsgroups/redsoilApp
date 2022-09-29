package com.haitomns.redsoil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class editDataController{
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
    @FXML
    public Button updateBloodButton;

    String donorIdToUpdate;

    public void initializeUpdatePage(String donorId) {
        mysqlFunction.mysqlDatabaseConnection();
        List<String> updateData = mysqlFunction.fetchUpdateData(donorId);

        if(updateData!=null) {
            donationOrganizationField.setText(updateData.get(1));
            donorNameField.setText(updateData.get(2));

            ObservableList<String> genderChoiceBoxValues = FXCollections.observableArrayList("Select","Male","Female", "Other");
            donorGenderField.setItems(genderChoiceBoxValues);
            donorGenderField.setValue(updateData.get(3));

            donorAgeField.setText(updateData.get(4));
            donorOccupationField.setText(updateData.get(5));
            donorAddressField.setText(updateData.get(6));
            donorPhoneField.setText(updateData.get(7));
            donorEmailField.setText(updateData.get(8));
            patientNameField.setText(updateData.get(9));
            donorIdField.setText(updateData.get(10));
            donorIdToUpdate = updateData.get(10);

            if (updateData.get(11).equals("0")) {
                donatedYesRadio.setSelected(true);
                donatedNoRadio.setSelected(false);
                previouslyDonatedDate.setValue(java.sql.Date.valueOf(updateData.get(12)).toLocalDate());
                previouslyDonatedDate.setDisable(false);
            } else {
                donatedNoRadio.setSelected(true);
                donatedYesRadio.setSelected(false);
            }

            if(updateData.get(13).contains("Malaria,")){
                malaria.setSelected(true);
            }
            if(updateData.get(13).contains("Leprosy,")){
                leprosy.setSelected(true);
            }
            if(updateData.get(13).contains("High Blood Pressure,")){
                highBloodPressure.setSelected(true);
            }
            if(updateData.get(13).contains("Lotus Pitta,")){
                lotusPitta.setSelected(true);
            }
            if(updateData.get(13).contains("Diabetes,")){
                diabetes.setSelected(true);
            }
            if(updateData.get(13).contains("Pre-Surgery,")){
                preSurgery.setSelected(true);
            }
            if(updateData.get(13).contains("Tuberculosis,")){
                tuberculosis.setSelected(true);
            }
            if(updateData.get(13).contains("Pregnancy,")){
                pregnancy.setSelected(true);
            }
            if(updateData.get(13).contains("Drug Abuse,")){
                drugAbuse.setSelected(true);
            }
            if(updateData.get(13).contains("Heart Disease,")){
                heartDisease.setSelected(true);
            }
            if(updateData.get(13).contains("Pneumonia,")){
                pneumonia.setSelected(true);
            }
            if(updateData.get(13).contains("Jaundice,")){
                jaundice.setSelected(true);
            }
            if(updateData.get(13).contains("Kidney Disease,")){
                kidneyDisease.setSelected(true);
            }
            if(updateData.get(13).contains("AIDS,")){
                aids.setSelected(true);
            }
            if(updateData.get(13).contains("Fainting Spells,")){
                faintingSpells.setSelected(true);
            }
            if(updateData.get(13).contains("Cutaneous Disease,")){
                cutaneousDisease.setSelected(true);
            }
            if(updateData.get(13).contains("STD,")){
                std.setSelected(true);
            }
            if(updateData.get(13).contains("Menstruation,")){
                menstruation.setSelected(true);
            }
            if(updateData.get(13).contains("Foreign Visit,")){
                foreignVisit.setSelected(true);
            }
            if(updateData.get(13).contains("Others,")){
                others.setSelected(true);
            }

            weight.setText(updateData.get(14));
            bp.setText(updateData.get(15));
            hb.setText(updateData.get(16));
            respSys.setText(updateData.get(17));
            cvs.setText(updateData.get(18));
            giSystem.setText(updateData.get(19));
            other.setText(updateData.get(20));
            fit.setText(updateData.get(21));
            unit.setText(updateData.get(22));

            ObservableList<String> aboChoiceBoxValues = FXCollections.observableArrayList("Select","A","B","AB","O");
            aboField.setItems(aboChoiceBoxValues);
            aboField.setValue(updateData.get(23));

            ObservableList<String> rhChoiceBoxValues = FXCollections.observableArrayList("Select","+","-");
            rhField.setItems(rhChoiceBoxValues);
            rhField.setValue(updateData.get(24));

            hiv.setText(updateData.get(25));
            hbsag.setText(updateData.get(26));
            hcv.setText(updateData.get(27));
            vdrl.setText(updateData.get(28));
            bloodExpiryDateField.setValue(java.sql.Date.valueOf(updateData.get(29)).toLocalDate());
        }
    }

    public void updateBloodDetails(){
        String donationOrganization = donationOrganizationField.getText();
        String donorName = donorNameField.getText();
        String donorGender = donorGenderField.getValue();
        String donorAge = donorAgeField.getText();
        String donorOccupation = donorOccupationField.getText();
        String donorAddress = donorAddressField.getText();
        String donorPhone = donorPhoneField.getText();
        String donorEmail = donorEmailField.getText();
        String patientName = patientNameField.getText();
        String donorId = donorIdField.getText();

        String donated;
        String previouslyDonatedDate;
        if(donatedYesRadio.isSelected()){
            donated = "0";
            previouslyDonatedDate = this.previouslyDonatedDate.getValue().toString();
        }
        else {
            donated = "1";
            previouslyDonatedDate = "0000-00-00";
        }

        String disease = "";
        if(malaria.isSelected()){
            disease += "Malaria,";
        }
        if(leprosy.isSelected()){
            disease += "Leprosy,";
        }
        if(highBloodPressure.isSelected()){
            disease += "High Blood Pressure,";
        }
        if(lotusPitta.isSelected()){
            disease += "Lotus Pitta,";
        }
        if(diabetes.isSelected()){
            disease += "Diabetes,";
        }
        if(preSurgery.isSelected()){
            disease += "Pre-Surgery,";
        }
        if(tuberculosis.isSelected()){
            disease += "Tuberculosis,";
        }
        if(pregnancy.isSelected()){
            disease += "Pregnancy,";
        }
        if(drugAbuse.isSelected()){
            disease += "Drug Abuse,";
        }
        if(heartDisease.isSelected()){
            disease += "Heart Disease,";
        }
        if(pneumonia.isSelected()){
            disease += "Pneumonia,";
        }
        if(jaundice.isSelected()){
            disease += "Jaundice,";
        }
        if(kidneyDisease.isSelected()){
            disease += "Kidney Disease,";
        }
        if(aids.isSelected()){
            disease += "AIDS,";
        }
        if(faintingSpells.isSelected()){
            disease += "Fainting Spells,";
        }
        if(cutaneousDisease.isSelected()){
            disease += "Cutaneous Disease,";
        }
        if(std.isSelected()){
            disease += "STD,";
        }
        if(menstruation.isSelected()){
            disease += "Menstruation,";
        }
        if(foreignVisit.isSelected()) {
            disease += "Foreign Visit,";
        }
        if(others.isSelected()){
            disease += "Others,";
        }
        if(disease.isEmpty()){
            disease = "None";
        }

        String weight = this.weight.getText();
        String bp = this.bp.getText();
        String hb = this.hb.getText();
        String respSys = this.respSys.getText();
        String cvs = this.cvs.getText();
        String giSystem = this.giSystem.getText();
        String other = this.other.getText();
        String fit = this.fit.getText();
        String unit = this.unit.getText();
        String abo = aboField.getValue();
        String rh = rhField.getValue();
        String hiv = this.hiv.getText();
        String hbsag = this.hbsag.getText();
        String hcv = this.hcv.getText();
        String vdrl = this.vdrl.getText();
        String bloodExpiryDate = bloodExpiryDateField.getValue().toString();

        mysqlFunction.mysqlDatabaseConnection();

        if(validateDonorAndTesting(donorId, donorName, donorGender, donorAge, donorPhone, donorEmail, patientName, abo, rh)) {
            if (mysqlFunction.updateDonor(donorIdToUpdate, donationOrganization, donorName, donorGender, donorAge, donorOccupation, donorAddress, donorPhone, donorEmail, patientName, donorId, donated, previouslyDonatedDate, disease, weight, bp, hb, respSys, cvs, giSystem, other, fit, unit, abo, rh, hiv, hcv, hbsag, vdrl, bloodExpiryDate)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("RedSoil Dashboard");
                alert.setContentText("Donor Details Updated. Refresh the table to view changes :)");
                alert.showAndWait();

                Stage stage = (Stage) updateBloodButton.getScene().getWindow();
                stage.close();

                dashboardController dashboardRefresh = new dashboardController();
                dashboardRefresh.initializeDashboardTable();
                dashboardRefresh.initializeDashboardDataView();
            } else {
                showError("Error", "RedSoil Dashboard", "Donor Details Update Failed :(");
            }
        }
    }

    public static boolean validateDonorAndTesting(String donorId, String  donorName, String  donorGender, String  donorAge, String  donorPhone, String donorEmail, String patientName, String abo, String rh){
        if(donorId.isEmpty() || donorName.isEmpty() || patientName.isEmpty()){
            showError("Error", "RedSoil Dashboard", "All fields are required");
            return false;
        } else if (donorGender.equals("Select")) {
            showError("Error", "RedSoil Dashboard", "Select Donor Gender");
            return false;
        }  else if(!donorPhone.equals("")) {
            if (donorPhone.length() != 10) {
                showError("Error", "RedSoil Dashboard", "Phone number must be 10 digits");
                return false;
            }
            return true;
        } else if(!donorEmail.equals("")){
            if (!donorEmail.contains("@") || !donorEmail.contains(".")) {
                showError("Error", "RedSoil Dashboard", "Invalid Email Address");
                return false;
            }
            return true;
        } else if (donorAge.isEmpty() || Integer.parseInt(donorAge) < 18 || Integer.parseInt(donorAge) > 55) {
            showError("Error", "RedSoil Dashboard", "Donor Age is Not Valid");
            return false;
        } else if(abo.equals("Select") || rh.equals("Select")){
            showError("Error", "RedSoil Dashboard", "Select ABO and RH");
            return false;
        } else {
            return true;
        }
    }

    @FXML
    private void donatedPreviouslySelection(){
        if(donatedYesRadio.isSelected()){
            previouslyDonatedDate.setDisable(false);
        } else if(donatedNoRadio.isSelected()){
            previouslyDonatedDate.setDisable(true);
        }
    }

    public static void showError(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
