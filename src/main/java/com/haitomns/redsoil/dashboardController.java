package com.haitomns.redsoil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {
    boolean previousBloodDonatedStatus = false;

    String diseaseList = "";
    @FXML
    private ScrollPane dashboardScrollPane;
    @FXML
    private SplitPane userAccountSplitPane, bloodDonationSplitPane;
    @FXML
    private Button userAccountButton, bloodDonationButton, dashboardButton;
    @FXML
    private TextField companyNameField, companyAddressField, companyPhoneField, companyUsernameField, companyPasswordField;
    @FXML
    private TextField donorIdField, donorNameField, donorAgeField, donorPhoneField, donorOccupationField, donorAddressField, donorEmailField;
    @FXML
    private  ChoiceBox<String> donorGenderField = new ChoiceBox<>();
    @FXML
    private DatePicker donorDobField, previouslyDonatedDate;
    @FXML
    private RadioButton donatedNoRadio, donatedYesRadio;
    @FXML
    private TextField weight, bp, hb, respSys, cvs, giSystem, other, fit, unit;
    @FXML
    private TextField abo, rh, hiv, hbsag, hcv, vdrl;
    @FXML
    private CheckBox bloodTestingEnableButton, malaria, leprosy, highBloodPressure, lotusPitta, diabetes, preSurgery, tuberculosis, pregnancy, drugAbuse, heartDisease, pneumonia, jaundice, kidneyDisease, aids, faintingSpells, cutaneousDisease, std, menstruation, foreignVisit, others;
    @FXML
    private void dashboardNavigation(ActionEvent event) {
        if (event.getSource() == userAccountButton) {
            userAccountSplitPane.toFront();
            bloodDonationSplitPane.toBack();
            dashboardScrollPane.toBack();
            showCompanyDetails();
        } else if (event.getSource() == bloodDonationButton) {
            userAccountSplitPane.toBack();
            bloodDonationSplitPane.toFront();
            dashboardScrollPane.toBack();
        } else if (event.getSource() == dashboardButton) {
            userAccountSplitPane.toBack();
            bloodDonationSplitPane.toBack();
            dashboardScrollPane.toFront();
        }
    }

    @FXML
    private void companyUpdateButtonAction() {
        String companyName = companyNameField.getText();
        String companyAddress = companyAddressField.getText();
        String companyPhone = companyPhoneField.getText();
        String companyUsername = companyUsernameField.getText();
        String companyPassword = companyPasswordField.getText();

        mysqlFunction.mysqlDatabaseConnection();

        if(validateCompanyDetails(companyName, companyAddress, companyPhone, companyUsername, companyPassword)) {
            if (mysqlFunction.companyUpdate(companyName, companyAddress, companyPhone, companyUsername, companyPassword)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("RedSoil Dashboard");
                alert.setContentText("Company Details Updated :)");
                alert.showAndWait();
            } else {
                showError("Error", "RedSoil Dashboard", "Company Details Update Failed :(");
            }
        }
    }

    @FXML
    private void bloodDonationSubmitButtonAction(){
        String donorId = donorIdField.getText();
        String donorName = donorNameField.getText();
        String donorGender = donorGenderField.getValue();
        String donorAge = donorAgeField.getText();
        String donorPhone = donorPhoneField.getText();
        String donorDob = getDOBValue();
        String donorOccupation = donorOccupationField.getText();
        String donorAddress = donorAddressField.getText();
        String donorEmail = donorEmailField.getText();

        getSelectedDisease();
        String lastDonatedDate = getDateOfLastDonation();

        String weight = this.weight.getText();
        String bp = this.bp.getText();
        String hb = this.hb.getText();
        String respSys = this.respSys.getText();
        String cvs = this.cvs.getText();
        String giSystem = this.giSystem.getText();
        String other = this.other.getText();
        String fit = this.fit.getText();
        String unit = this.unit.getText();
        String abo = this.abo.getText();
        String rh = this.rh.getText();
        String hiv = this.hiv.getText();
        String hcb = this.hcv.getText();
        String hbsag = this.hbsag.getText();
        String vdrl = this.vdrl.getText();

        mysqlFunction.mysqlDatabaseConnection();

        if(validateDonorAndTesting(donorId, donorName, donorGender, donorAge, donorPhone, donorDob, donorEmail)) {
            if (mysqlFunction.bloodAddDonorAndBlood(donorId, donorName, donorGender, donorAge, donorPhone, donorDob, donorOccupation, donorAddress, donorEmail, diseaseList, lastDonatedDate, weight, bp, hb, respSys, cvs, giSystem, other, fit, unit, abo, rh, hiv, hcb, hbsag, vdrl)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("RedSoil Dashboard");
                alert.setContentText("Donor Details Inserted :)");
                alert.showAndWait();
            } else {
                showError("Error", "RedSoil Dashboard", "Donor Details Insertion Failed :(");
            }
        }

    }


    @FXML
    private void ageNumberInput(){
        numericValidator(donorAgeField);
    }

    @FXML
    private void phoneNumberInput(){
        numericValidator(donorPhoneField);
    }

    @FXML
    private void donatedPreviouslySelection(){
        if(donatedYesRadio.isSelected()){
            previouslyDonatedDate.setDisable(false);
            previousBloodDonatedStatus = true;
        } else if(donatedNoRadio.isSelected()){
            previouslyDonatedDate.setDisable(true);
            previousBloodDonatedStatus = false;
        }
    }

    @FXML
    private void bloodTestingAction(){
        if(bloodTestingEnableButton.isSelected()) {
            abo.setDisable(false);
            rh.setDisable(false);
            hiv.setDisable(false);
            hbsag.setDisable(false);
            hcv.setDisable(false);
            vdrl.setDisable(false);
        }
        else {
            abo.setDisable(true);
            rh.setDisable(true);
            hiv.setDisable(true);
            hbsag.setDisable(true);
            hcv.setDisable(true);
            vdrl.setDisable(true);
        }
    }

    public void numericValidator(TextField numericTextField){
        numericTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numericTextField.setText(newValue.replaceAll("\\D", ""));
            }
        });
    }

    public static boolean validateCompanyDetails(String companyName, String companyAddress, String companyPhone, String companyUsername, String companyPassword){
        if(companyName.isEmpty() || companyAddress.isEmpty() || companyPhone.isEmpty() || companyUsername.isEmpty() || companyPassword.isEmpty()){
            showError("Error", "RedSoil Dashboard", "All fields are required");
            return false;
        }
        else if(companyPhone.length() != 10){
            showError("Error", "RedSoil Dashboard", "Phone number must be 10 digits");
            return false;
        }
        else if(companyUsername.length() < 5 || companyUsername.length() > 20){
            showError("Error", "RedSoil Dashboard", "Company Username must be between 5 and 20 characters");
            return false;
        }
        else if(companyPassword.length() < 5 || companyPassword.length() > 30){
            showError("Error", "RedSoil Dashboard", "Company Password must be between 5 and 30 characters");
            return false;
        }
        else if(companyPhone.contains(" ") || companyUsername.contains(" ") || companyPassword.contains(" ")){
            showError("Error", "RedSoil Dashboard", "Company Phone, Username and Password must not contain spaces");
            return false;
        }
        else{
            return true;
        }
    }

    public static boolean validateDonorAndTesting(String donorId, String  donorName, String  donorGender, String  donorAge, String  donorPhone, String donorDob, String donorEmail){
        if(donorId.isEmpty() || donorName.isEmpty() ||  donorPhone.isEmpty() || donorEmail.isEmpty() ){
            showError("Error", "RedSoil Dashboard", "All fields are required");
            return false;
        } else if (donorGender.equals("Select")) {
            showError("Error", "RedSoil Dashboard", "Select Donor Gender");
            return false;
        } else if(donorPhone.length() != 10){
            showError("Error", "RedSoil Dashboard", "Phone number must be 10 digits");
            return false;
        } else if(!donorEmail.contains("@") || !donorEmail.contains(".")){
            showError("Error", "RedSoil Dashboard", "Invalid Email Address");
            return false;
        } else if (donorAge.isEmpty()) {
            if(donorDob.isEmpty()){
                showError("Error", "RedSoil Dashboard", "Donor Age or Date of Birth is required");
                return false;
            }
            return true;
        } else{
            return true;
        }
    }

    private void showCompanyDetails(){
        mysqlFunction.mysqlDatabaseConnection();
        List<Object> companyDetails  = mysqlFunction.companyDetails();

        if(companyDetails!=null) {
            companyNameField.setText(companyDetails.get(0).toString());
            companyAddressField.setText(companyDetails.get(1).toString());
            companyPhoneField.setText(companyDetails.get(2).toString());
            companyUsernameField.setText(companyDetails.get(3).toString());
            companyPasswordField.setText(companyDetails.get(4).toString());
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("RedSoil Dashboard");
            alert.setContentText("Company Details Fetch Failed :(");
            alert.showAndWait();
        }
    }

    public String getDOBValue(){
        LocalDate date = donorDobField.getValue();
        return date.toString();
    }

    public String getDateOfLastDonation(){
        LocalDate date = previouslyDonatedDate.getValue();
        return date.toString();
    }

    public void getSelectedDisease(){
        if(malaria.isSelected()){
            diseaseList = diseaseList + "Maleria, ";
        }
        if(leprosy.isSelected()){
            diseaseList = diseaseList + "Leprosy, ";
        }
        if(highBloodPressure.isSelected()){
            diseaseList = diseaseList + "High Blood Pressure, ";
        }
        if(lotusPitta.isSelected()){
            diseaseList = diseaseList + "Lotus Pitta, ";
        }
        if(diabetes.isSelected()){
            diseaseList = diseaseList + "Diabetes, ";
        }
        if(preSurgery.isSelected()){
            diseaseList = diseaseList + "Pre Surgery, ";
        }
        if(tuberculosis.isSelected()){
            diseaseList = diseaseList + "Tuberculosis, ";
        }
        if(pregnancy.isSelected()){
            diseaseList = diseaseList + "Pregnancy, ";
        }
        if(drugAbuse.isSelected()){
            diseaseList = diseaseList + "Drug Abuse, ";
        }
        if(heartDisease.isSelected()){
            diseaseList = diseaseList + "Heart Disease, ";
        }
        if(pneumonia.isSelected()){
            diseaseList = diseaseList + "Pneumonia, ";
        }
        if(jaundice.isSelected()){
            diseaseList = diseaseList + "Jaundice, ";
        }
        if(kidneyDisease.isSelected()){
            diseaseList = diseaseList + "Kidney Disease, ";
        }
        if(aids.isSelected()){
            diseaseList = diseaseList + "AIDS, ";
        }
        if(faintingSpells.isSelected()){
            diseaseList = diseaseList + "Fainting Spells, ";
        }
        if(cutaneousDisease.isSelected()){
            diseaseList = diseaseList + "Cutaneous Disease, ";
        }
        if(std.isSelected()){
            diseaseList = diseaseList + "STD, ";
        }
        if(menstruation.isSelected()){
            diseaseList = diseaseList + "Menstruation, ";
        }
        if(foreignVisit.isSelected()){
            diseaseList = diseaseList + "Foreign Visit, ";
        }
        if(others.isSelected()){
            diseaseList = diseaseList + "Other, ";
        }
    }

    public static void showError(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> genderChoiceBoxValues= FXCollections.observableArrayList("Select","Male","Female", "Other");
        donorGenderField.setItems(genderChoiceBoxValues);
        donorGenderField.setValue("Select");
    }
}
