package com.haitomns.redsoil;

import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.haitomns.redsoil.mysqlFunction.checkDonorID;

public class dashboardController implements Initializable {
    String previousBloodDonatedStatus = "1";
    String diseaseList = "";
    @FXML
    private BorderPane dashboard_borderpane;
    @FXML
    private ScrollPane dashboardScrollPane, findBloodScrollPane, userAccountScrollPane;
    @FXML
    private HBox bloodDonationHbox;
    @FXML
    private Button userAccountButton, bloodDonationButton, dashboardButton, findBloodButton;
    @FXML
    private TextField companyNameField, companyAddressField, companyPhoneField, companyUsernameField, companyPasswordField;
    @FXML
    private TextField donationOrganizationField, donorNameField, donorAgeField, donorPhoneField, donorOccupationField, donorAddressField, donorEmailField;
    @FXML
    private  ChoiceBox<String> donorGenderField = new ChoiceBox<>();
    @FXML
    private DatePicker previouslyDonatedDate;
    @FXML
    private RadioButton donatedNoRadio, donatedYesRadio;
    @FXML
    private TextField patientNameField, donorIdField;
    @FXML
    private TextField weight, bp, hb, other,  unit;
    @FXML
    private JFXToggleButton respSys, cvs, giSystem, fit;
    @FXML
    private ChoiceBox<String> aboField, rhField = new ChoiceBox<>();
    @FXML
    private JFXToggleButton hiv, hbsag, hcv, vdrl;
    @FXML
    private CheckBox  malaria, leprosy, highBloodPressure, lotusPitta, diabetes, preSurgery, tuberculosis, pregnancy, drugAbuse, heartDisease, pneumonia, jaundice, kidneyDisease, aids, faintingSpells, cutaneousDisease, std, menstruation, foreignVisit, others;
    @FXML
    private DatePicker bloodExpiryDateField;
    @FXML
    private TableView<bloodFindTableModel> findBloodTable;
    @FXML
    private TableColumn<bloodFindTableModel, String> donorIdColumn;
    @FXML
    private TableColumn<bloodFindTableModel, String> donorNameColumn;
    @FXML
    private TableColumn<bloodFindTableModel, String> donorPhoneColumn;
    @FXML
    private TableColumn<bloodFindTableModel, String> aboColumn;
    @FXML
    private TableColumn<bloodFindTableModel, String> rhColumn;
    @FXML
    private TableColumn<bloodFindTableModel, String> unitColumn;
    @FXML
    private TableColumn<bloodFindTableModel, String> creationDateColumn;
    @FXML
    private TableColumn<bloodFindTableModel, String> expiryDateColumn;
    @FXML
    private TableView<bloodFindTableModel> dashboardBloodTable;
    @FXML
    private TableColumn<bloodFindTableModel, String> donorIdColumnDB;
    @FXML
    private TableColumn<bloodFindTableModel, String> donorNameColumnDB;
    @FXML
    private TableColumn<bloodFindTableModel, String> donorPhoneColumnDB;
    @FXML
    private TableColumn<bloodFindTableModel, String> aboColumnDB;
    @FXML
    private TableColumn<bloodFindTableModel, String> rhColumnDB;
    @FXML
    private TableColumn<bloodFindTableModel, String> unitColumnDB;
    @FXML
    private TableColumn<bloodFindTableModel, String> creationDateColumnDB;
    @FXML
    private TableColumn<bloodFindTableModel, String> expiryDateColumnDB;
    @FXML
    private TableView<bloodDonationTableModel> donorDataTable;
    @FXML
    private TableColumn<bloodDonationTableModel, String> donorIdColumnAdd;
    @FXML
    private TableColumn<bloodDonationTableModel, String> donorNameColumnAdd;
    @FXML
    private TableColumn<bloodDonationTableModel, String> donorGenderColumnAdd;
    @FXML
    private TableColumn<bloodDonationTableModel, String> donorPhoneColumnAdd;
    @FXML
    private TableColumn<bloodDonationTableModel, String> aboColumnAdd;
    @FXML
    private TableColumn<bloodDonationTableModel, String> rhColumnAdd;
    @FXML
    private TableColumn<bloodDonationTableModel, String> unitColumnAdd;
    @FXML
    private TableColumn<bloodFindTableModel, String> viewButtonColumn;
    @FXML
    private TableColumn<bloodFindTableModel, String> removeButtonColumn;
    @FXML
    private TableColumn<bloodFindTableModel, String> viewButtonDBColumn;
    @FXML
    private TableColumn<bloodFindTableModel, String> removeButtonDBColumn;
    @FXML
    private Label a_plus_number, a_minus_number, b_plus_number, b_minus_number, ab_plus_number, ab_minus_number, o_plus_number, o_minus_number;
    @FXML
    private Label total_donor_number, active_blood_number, expired_blood_number, total_unit_number;
    @FXML
    private TextField findDonorIdInput;
    @FXML
    private Pane a_plus_pane, a_minus_pane, b_plus_pane, b_minus_pane, ab_plus_pane, ab_minus_pane, o_plus_pane, o_minus_pane;
    @FXML
    private Pane total_donor_pane, active_pane, expired_pane;
    @FXML
    private ImageView dashboardCloseButton, dashboardMinimizeButton, dashboardMaximizeButton;

    ObservableList<bloodFindTableModel> bloodDonorList = FXCollections.observableArrayList();
    ObservableList<bloodFindTableModel> dashboardTableData = FXCollections.observableArrayList();
    ObservableList<bloodDonationTableModel> bloodDonationAddData = FXCollections.observableArrayList();
    String getFindDonorID;

    @FXML
    private void dashboardNavigation(ActionEvent event) {
        if (event.getSource() == userAccountButton) {
            userAccountScrollPane.toFront();
            bloodDonationHbox.toBack();
            dashboardScrollPane.toBack();
            findBloodScrollPane.toBack();
            showCompanyDetails();
        } else if (event.getSource() == bloodDonationButton) {
            userAccountScrollPane.toBack();
            bloodDonationHbox.toFront();
            dashboardScrollPane.toBack();
            findBloodScrollPane.toBack();
            addDataToDonationTable();
        } else if (event.getSource() == dashboardButton) {
            userAccountScrollPane.toBack();
            bloodDonationHbox.toBack();
            dashboardScrollPane.toFront();
            findBloodScrollPane.toBack();
            initializeDashboardTable();
            initializeDashboardDataView();
        } else if (event.getSource() == findBloodButton) {
            userAccountScrollPane.toBack();
            bloodDonationHbox.toBack();
            dashboardScrollPane.toBack();
            findBloodScrollPane.toFront();
            initializeBloodFindTable(false);
        }
    }

    @FXML
    private void companyUpdateButtonAction() throws NoSuchAlgorithmException {
        String companyName = companyNameField.getText();
        String companyAddress = companyAddressField.getText();
        String companyPhone = companyPhoneField.getText();
        String companyUsername = companyUsernameField.getText();
        String companyPassword = companyPasswordField.getText();

        mysqlFunction.mysqlDatabaseConnection();

        if(validateCompanyDetails(companyName, companyAddress, companyPhone, companyUsername, companyPassword)) {
            if (mysqlFunction.companyUpdate(companyName, companyAddress, companyPhone, companyUsername, encryptString(companyPassword))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("RedSoil Dashboard");
                alert.setContentText("Company Details Updated :)");
                alert.showAndWait();
            } else {
                showError("Error", "RedSoil Dashboard", "Company Details Update Failed :(");
            }
        }
    }

    public static String encryptString(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger bigInt = new BigInteger(1,messageDigest);
        return bigInt.toString(16);
    }

    @FXML
    private void bloodDonationSubmitButtonAction(){
        String donationOrganization = donationOrganizationField.getText();
        String donorName = donorNameField.getText();
        String donorGender = donorGenderField.getValue();
        String donorAge = donorAgeField.getText();
        String donorPhone = donorPhoneField.getText();
        String donorOccupation = donorOccupationField.getText();
        String donorAddress = donorAddressField.getText();
        String donorEmail = donorEmailField.getText();

        String patientName = patientNameField.getText();
        String donorId = donorIdField.getText();

        getSelectedDisease();
        if(diseaseList.isEmpty()){
            diseaseList = "None";
        }

        String lastDonatedDate;
        if(previousBloodDonatedStatus.equals("0")){
            lastDonatedDate = getDateOfLastDonation();
        }else {
            lastDonatedDate = "0000-00-00";
        }

        String weight = this.weight.getText();
        String bp = this.bp.getText();
        String hb = this.hb.getText();
        String respSys;
        if(this.respSys.isSelected()){
            respSys = "Yes";
        }else {
            respSys = "No";
        }
        String cvs;
        if(this.cvs.isSelected()){
            cvs = "Yes";
        }else {
            cvs = "No";
        }
        String giSystem;
        if(this.giSystem.isSelected()){
            giSystem = "Yes";
        }else {
            giSystem = "No";
        }
        String fit;
        if(this.fit.isSelected()){
            fit = "Yes";
        }else {
            fit = "No";
        }
        String other = this.other.getText();
        String unit = this.unit.getText();
        String abo = this.aboField.getValue();
        String rh = this.rhField.getValue();
        String hiv;
        if(this.hiv.isSelected()){
            hiv = "Yes";
        }else {
            hiv = "No";
        }
        String hcv;
        if(this.hcv.isSelected()){
            hcv = "Yes";
        }else {
            hcv = "No";
        }
        String hbsag;
        if(this.hbsag.isSelected()){
            hbsag = "Yes";
        }else {
            hbsag = "No";
        }
        String vdrl;
        if(this.vdrl.isSelected()){
            vdrl = "Yes";
        }else {
            vdrl = "No";
        }
        String bloodExpiryDate = getDateOfBloodExpiry();

        mysqlFunction.mysqlDatabaseConnection();

        if(validateDonorAndTesting(donorId, donorName, donorGender, donorAge, donorPhone, donorEmail, patientName, abo, rh, unit, bloodExpiryDate)) {
            if (mysqlFunction.bloodAddDonorAndBlood(donationOrganization, donorName, donorGender, donorAge, donorPhone, donorOccupation, donorAddress, donorEmail, patientName, donorId, diseaseList, previousBloodDonatedStatus, lastDonatedDate, weight, bp, hb, respSys, cvs, giSystem, other, fit, unit, abo, rh, hiv, hcv, hbsag, vdrl, bloodExpiryDate)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("RedSoil Dashboard");
                alert.setContentText("Donor Details Inserted :)");
                alert.showAndWait();
                clear_button_click();
                addDataToDonationTable();
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
            previousBloodDonatedStatus = "0";
        } else if(donatedNoRadio.isSelected()){
            previouslyDonatedDate.setDisable(true);
            previousBloodDonatedStatus = "1";
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
        else if(companyPassword.contains("*")){
            showError("Error", "RedSoil Dashboard", "Provide different a Company Password");
            return false;
        }
        else if(companyPassword.length() < 5 || companyPassword.length() > 20){
            showError("Error", "RedSoil Dashboard", "Company Password must be between 5 and 20 characters");
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

    public static boolean validateDonorAndTesting(String donorId, String  donorName, String  donorGender, String  donorAge, String  donorPhone, String donorEmail, String patientName, String abo, String rh, String unit, String bloodExpiryDate){
        if(donorId.isEmpty() || donorName.isEmpty() || patientName.isEmpty()){
            showError("Error", "RedSoil Dashboard", "Patient Name, Donor Name and Donor ID are required");
            return false;
        } else if(checkDonorID(donorId)){
            showError("Error", "RedSoil Dashboard", "Donor ID is already in use.");
            return false;
        }else if (donorGender.equals("Select")) {
            showError("Error", "RedSoil Dashboard", "Select Donor Gender");
            return false;
        } else if(!donorPhone.equals("")) {
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
        } else if (unit.isEmpty() || Integer.parseInt(unit) < 1 || Integer.parseInt(unit) > 5) {
                showError("Error", "RedSoil Dashboard", "Unit is Not Valid");
                return false;
        } else if (bloodExpiryDate == null || bloodExpiryDate.isEmpty()) {
                showError("Error", "RedSoil Dashboard", "Blood Expiry Date is Not Valid");
                return false;
        } else {
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
            companyPasswordField.setText("********");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("RedSoil Dashboard");
            alert.setContentText("Company Details Fetch Failed :(");
            alert.showAndWait();
        }
    }

    public String getDateOfLastDonation(){
        LocalDate lastDonationDate = previouslyDonatedDate.getValue();
        if(lastDonationDate != null) {
            return lastDonationDate.toString();
        }
        else{
            return null;
        }
    }

    public String getDateOfBloodExpiry(){
        LocalDate bloodExpiry = bloodExpiryDateField.getValue();
        if(bloodExpiry != null) {
            return bloodExpiry.toString();
        }
        else{
            return null;
        }
    }

    public void getSelectedDisease(){
        if(malaria.isSelected()){
            diseaseList = diseaseList + "Malaria, ";
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
    public void initializeBloodFindTable(Boolean findCondition){
        mysqlFunction.mysqlDatabaseConnection();
        if(findCondition) {
            bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID where blooddonationuserdata.Donor_ID = '"+getFindDonorID+"';");
        }else{
            bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID where discard_blood = \"No\"  AND Expiry_date > current_date();;");
        }
        if(bloodDonorList!=null){
            donorIdColumn.setCellValueFactory(new PropertyValueFactory<>("donorId"));
            donorNameColumn.setCellValueFactory(new PropertyValueFactory<>("donorName"));
            donorPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("donorPhone"));
            aboColumn.setCellValueFactory(new PropertyValueFactory<>("abo"));
            rhColumn.setCellValueFactory(new PropertyValueFactory<>("rh"));
            unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
            creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfCreation"));
            expiryDateColumn.setCellValueFactory(new PropertyValueFactory<>("bloodExpiryDate"));
            viewButtonColumn.setVisible(true);
            removeButtonColumn.setVisible(true);

            findBloodTable.setItems(bloodDonorList);
        }
    }

    public void initializeDashboardTable(){
        mysqlFunction.mysqlDatabaseConnection();
        dashboardTableData = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID where discard_blood = \"No\" AND Expiry_date > current_date();");

        if(dashboardTableData!=null){
            addDataToTable(dashboardTableData);
        }
    }

    public void a_plus_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID where discard_blood = \"No\" AND abo = \"A\" AND rh = \"+\"  AND Expiry_date > current_date(); ;");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void pane_enter_a_plus(){
        a_plus_pane.setStyle("-fx-background-color: #8d2c2b; -fx-background-radius: 10px;");
    }

    public void pane_exit_a_plus(){
        a_plus_pane.setStyle("-fx-background-color: #C93F3E; -fx-background-radius: 10px");
    }

    public void a_minus_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID where discard_blood = \"No\" AND abo = \"A\" AND rh = \"-\"  AND Expiry_date > current_date();;");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void pane_enter_a_minus(){
        a_minus_pane.setStyle("-fx-background-color: #8d2c2b; -fx-background-radius: 10px;");
    }

    public void pane_exit_a_minus(){
        a_minus_pane.setStyle("-fx-background-color: #C93F3E; -fx-background-radius: 10px");
    }

    public void b_plus_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID where discard_blood = \"No\" AND abo = \"B\" AND rh = \"+\"  AND Expiry_date > current_date();;");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void pane_enter_b_plus(){
        b_plus_pane.setStyle("-fx-background-color: #8d2c2b; -fx-background-radius: 10px;");
    }

    public void pane_exit_b_plus(){
        b_plus_pane.setStyle("-fx-background-color: #C93F3E; -fx-background-radius: 10px");
    }

    public void b_minus_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID where discard_blood = \"No\" AND abo = \"B\" AND rh = \"-\"  AND Expiry_date > current_date();;");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void pane_enter_b_minus(){
        b_minus_pane.setStyle("-fx-background-color: #8d2c2b; -fx-background-radius: 10px;");
    }

    public void pane_exit_b_minus(){
        b_minus_pane.setStyle("-fx-background-color: #C93F3E; -fx-background-radius: 10px");
    }

    public void ab_plus_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID where discard_blood = \"No\" AND abo = \"AB\" AND rh = \"+\"  AND Expiry_date > current_date();;");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void pane_enter_ab_plus(){
        ab_plus_pane.setStyle("-fx-background-color: #8d2c2b; -fx-background-radius: 10px;");
    }

    public void pane_exit_ab_plus(){
        ab_plus_pane.setStyle("-fx-background-color: #C93F3E; -fx-background-radius: 10px");
    }

    public void ab_minus_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID where discard_blood = \"No\" AND abo = \"AB\" AND rh = \"-\"  AND Expiry_date > current_date();;");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void pane_enter_ab_minus(){
        ab_minus_pane.setStyle("-fx-background-color: #8d2c2b; -fx-background-radius: 10px;");
    }

    public void pane_exit_ab_minus(){
        ab_minus_pane.setStyle("-fx-background-color: #C93F3E; -fx-background-radius: 10px");
    }

    public void o_plus_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID where discard_blood = \"No\" AND abo = \"O\" AND rh = \"+\"  AND Expiry_date > current_date();;");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void pane_enter_o_plus(){
        o_plus_pane.setStyle("-fx-background-color: #8d2c2b; -fx-background-radius: 10px;");
    }

    public void pane_exit_o_plus(){
        o_plus_pane.setStyle("-fx-background-color: #C93F3E; -fx-background-radius: 10px");
    }

    public void o_minus_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID where discard_blood = \"No\" AND abo = \"O\" AND rh = \"-\"  AND Expiry_date > current_date();;");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void pane_enter_o_minus(){
        o_minus_pane.setStyle("-fx-background-color: #8d2c2b; -fx-background-radius: 10px;");
    }

    public void pane_exit_o_minus(){
        o_minus_pane.setStyle("-fx-background-color: #C93F3E; -fx-background-radius: 10px");
    }

    public void total_donor_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID;");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void pane_enter_total_donor(){
        total_donor_pane.setStyle("-fx-background-color: #126091; -fx-background-radius: 10px;");
    }

    public void pane_exit_total_donor(){
        total_donor_pane.setStyle("-fx-background-color:  #1982C4; -fx-background-radius: 10px");
    }

    public void total_active_donor_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID where blooddonationtestingdetails.Expiry_date > current_date() AND discard_blood = \"No\" ;");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void pane_enter_total_active_donor(){
        active_pane.setStyle("-fx-background-color: #126091; -fx-background-radius: 10px;");
    }

    public void pane_exit_total_active_donor(){
        active_pane.setStyle("-fx-background-color:  #1982C4; -fx-background-radius: 10px");
    }

    public void total_expired_donor_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID where blooddonationtestingdetails.Expiry_date < current_date();");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void pane_enter_total_expired_donor(){
        expired_pane.setStyle("-fx-background-color: #126091; -fx-background-radius: 10px;");
    }

    public void pane_exit_total_expired_donor(){
        expired_pane.setStyle("-fx-background-color:  #1982C4; -fx-background-radius: 10px");
    }

    public void addDataToTable(ObservableList<bloodFindTableModel> tableData){
        donorIdColumnDB.setCellValueFactory(new PropertyValueFactory<>("donorId"));
        donorNameColumnDB.setCellValueFactory(new PropertyValueFactory<>("donorName"));
        donorPhoneColumnDB.setCellValueFactory(new PropertyValueFactory<>("donorPhone"));
        aboColumnDB.setCellValueFactory(new PropertyValueFactory<>("abo"));
        rhColumnDB.setCellValueFactory(new PropertyValueFactory<>("rh"));
        unitColumnDB.setCellValueFactory(new PropertyValueFactory<>("unit"));
        creationDateColumnDB.setCellValueFactory(new PropertyValueFactory<>("dateOfCreation"));
        expiryDateColumnDB.setCellValueFactory(new PropertyValueFactory<>("bloodExpiryDate"));

        Callback<TableColumn<bloodFindTableModel, String>, TableCell<bloodFindTableModel, String>> cellFactory = new Callback<>() {
            @Override
            public TableCell call(final TableColumn<bloodFindTableModel, String> param) {
                return new TableCell<bloodFindTableModel, String>() {
                    final Button btn = new Button("View");
                    {
                        btn.setStyle("-fx-background-color: #C93F3E; -fx-text-fill: white; -fx-background-radius: 10px;");
                    }

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {

                                FXMLLoader updateViewLoader = new FXMLLoader ();
                                updateViewLoader.setLocation(getClass().getResource("editData-view.fxml"));
                                try {
                                    updateViewLoader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                editDataController editDataController = updateViewLoader.getController();
                                editDataController.initializeUpdatePage(getTableView().getItems().get(getIndex()).getDonorId());

                                Parent parent = updateViewLoader.getRoot();
                                Stage stage = new Stage();
                                stage.setScene(new Scene(parent));
                                stage.initStyle(StageStyle.UTILITY);
                                stage.setTitle("RedSoil Update");
                                stage.show();
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
            }
        };
        viewButtonColumn.setCellFactory(cellFactory);
        viewButtonDBColumn.setCellFactory(cellFactory);
        Callback<TableColumn<bloodFindTableModel, String>, TableCell<bloodFindTableModel, String>> cellFactoryDelete = new Callback<>() {
            @Override
            public TableCell call(final TableColumn<bloodFindTableModel, String> param) {
                return new TableCell<bloodFindTableModel, String>() {
                    final Button btn = new Button("Remove");
                    {
                        btn.setStyle("-fx-background-color: #C93F3E; -fx-text-fill: white; -fx-background-radius: 10px;");
                    }

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {

                                FXMLLoader removalLoader = new FXMLLoader();
                                removalLoader.setLocation(getClass().getResource("bloodComponent-view.fxml"));
                                try {
                                    removalLoader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                bloodComponent bloodComponentController = removalLoader.getController();
                                bloodComponentController.initializeBloodComponentPage(getTableView().getItems().get(getIndex()).getDonorId(), getTableView().getItems().get(getIndex()).getAbo(), getTableView().getItems().get(getIndex()).getRh(), getTableView().getItems().get(getIndex()).getUnit());

                                Parent parent = removalLoader.getRoot();
                                Stage removalStage = new Stage();
                                removalStage.setScene(new Scene(parent));
                                removalStage.initStyle(StageStyle.UTILITY);
                                removalStage.setResizable(false);
                                removalStage.setTitle("Blood Component");
                                removalStage.show();
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
            }
        };
        removeButtonColumn.setCellFactory(cellFactoryDelete);
        removeButtonDBColumn.setCellFactory(cellFactoryDelete);

        dashboardBloodTable.setItems(tableData);
    }

    public void removed_view(){
        ObservableList<bloodFindTableModel> removeTableData = mysqlFunction.removeDataView();
        if(removeTableData!=null){
            donorIdColumn.setCellValueFactory(new PropertyValueFactory<>("donorId"));
            donorNameColumn.setCellValueFactory(new PropertyValueFactory<>("donorName"));
            donorPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("donorPhone"));
            aboColumn.setCellValueFactory(new PropertyValueFactory<>("abo"));
            rhColumn.setCellValueFactory(new PropertyValueFactory<>("rh"));
            unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
            creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfCreation"));
            expiryDateColumn.setCellValueFactory(new PropertyValueFactory<>("bloodExpiryDate"));
            viewButtonColumn.setVisible(false);
            removeButtonColumn.setVisible(false);

            findBloodTable.setItems(removeTableData);
        }
    }

    public void view_all_blood(){
        initializeBloodFindTable(false);
    }

    public void addDataToDonationTable(){
        bloodDonationAddData = mysqlFunction.bloodDonationAddTable();
        if(bloodDonationAddData!=null){
            donorIdColumnAdd.setCellValueFactory(new PropertyValueFactory<>("donorId"));
            donorNameColumnAdd.setCellValueFactory(new PropertyValueFactory<>("donorName"));
            donorGenderColumnAdd.setCellValueFactory(new PropertyValueFactory<>("donorGender"));
            donorPhoneColumnAdd.setCellValueFactory(new PropertyValueFactory<>("donorPhone"));
            aboColumnAdd.setCellValueFactory(new PropertyValueFactory<>("abo"));
            rhColumnAdd.setCellValueFactory(new PropertyValueFactory<>("rh"));
            unitColumnAdd.setCellValueFactory(new PropertyValueFactory<>("unit"));

            donorDataTable.setItems(bloodDonationAddData);
        }
    }
    public void clear_button_click(){
        donationOrganizationField.setText("");
        donorNameField.setText("");
        donorPhoneField.setText("");
        donorAgeField.setText("");
        donorGenderField.setValue("Select");
        donorOccupationField.setText("");
        donorAddressField.setText("");
        donorEmailField.setText("");
        donatedYesRadio.setSelected(false);
        donatedNoRadio.setSelected(true);
        previouslyDonatedDate.setValue(null);
        malaria.setSelected(false);
        leprosy.setSelected(false);
        highBloodPressure.setSelected(false);
        lotusPitta.setSelected(false);
        diabetes.setSelected(false);
        preSurgery.setSelected(false);
        tuberculosis.setSelected(false);
        pregnancy.setSelected(false);
        drugAbuse.setSelected(false);
        heartDisease.setSelected(false);
        pneumonia.setSelected(false);
        jaundice.setSelected(false);
        kidneyDisease.setSelected(false);
        aids.setSelected(false);
        faintingSpells.setSelected(false);
        cutaneousDisease.setSelected(false);
        std.setSelected(false);
        menstruation.setSelected(false);
        foreignVisit.setSelected(false);
        others.setSelected(false);
        patientNameField.setText("");
        donorIdField.setText("");
        weight.setText("");
        bp.setText("");
        hb.setText("");
        respSys.setSelected(false);
        cvs.setSelected(false);
        giSystem.setSelected(false);
        fit.setSelected(false);
        other.setText("");
        unit.setText("");
        aboField.setValue("Select");
        rhField.setValue("Select");
        hiv.setSelected(false);
        hbsag.setSelected(false);
        hcv.setSelected(false);
        vdrl.setSelected(false);
        bloodExpiryDateField.setValue(null);
    }

    public void find_donor_button_click(){
        getFindDonorID = findDonorIdInput.getText();
        if(getFindDonorID.isEmpty()){
            showError("RedSoil Dashboard", "Find Donor", "Please enter donor ID!");
        }
        else {
           initializeBloodFindTable(true);
        }
    }

    public void initializeDashboardDataView(){
        mysqlFunction.mysqlDatabaseConnection();
        List<Integer> bloodTotalList = mysqlFunction.bloodDonationViewCount();
        if(bloodTotalList!=null){
            a_plus_number.setText(bloodTotalList.get(0).toString()+" Pouches");
            a_minus_number.setText(bloodTotalList.get(1).toString()+" Pouches");
            b_plus_number.setText(bloodTotalList.get(2).toString()+" Pouches");
            b_minus_number.setText(bloodTotalList.get(3).toString()+" Pouches");
            ab_plus_number.setText(bloodTotalList.get(4).toString()+" Pouches");
            ab_minus_number.setText(bloodTotalList.get(5).toString()+" Pouches");
            o_plus_number.setText(bloodTotalList.get(6).toString()+" Pouches");
            o_minus_number.setText(bloodTotalList.get(7).toString()+" Pouches");
        }

        List<Integer> bloodStatusTotal = mysqlFunction.bloodStatusCount();
        if(bloodStatusTotal!=null){
            total_donor_number.setText(bloodStatusTotal.get(0).toString());
            active_blood_number.setText(bloodStatusTotal.get(1).toString());
            expired_blood_number.setText(bloodStatusTotal.get(2).toString());
            total_unit_number.setText(bloodStatusTotal.get(3).toString());
        }
    }

    public void backupButtonClick(){
        File backupFolder = new File("redsoilBackup");
        if(!backupFolder.exists()){
            backupFolder.mkdir();
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Backup");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("SQL File", "*.sql")
        );

        DateTimeFormatter tf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();

        fileChooser.setInitialFileName("redsoilBackup_"+tf.format(now)+".sql");

        File file = fileChooser.showSaveDialog(null);

        if(file!=null){
            String path = file.getAbsolutePath();
            if(!path.endsWith(".sql")){
                path = path + ".sql";
            }

            mysqlFunction.mysqlDatabaseConnection();
            boolean backupStatus = mysqlFunction.backupDatabase(path);
            if(backupStatus){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("RedSoil Dashboard");
                alert.setHeaderText("Backup");
                alert.setContentText("Backup Successful!");
                alert.showAndWait();
            }
            else {
                showError("RedSoil Dashboard", "Backup", "Backup failed!");
            }
        }
    }

    public void restoreButtonClick(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Restore Backup");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("SQL File", "*.sql")
        );
        File file = fileChooser.showOpenDialog(null);

        if(file!=null){
            String path = file.getAbsolutePath();
            if(!path.endsWith(".sql")){
                path = path + ".sql";
            }

            mysqlFunction.mysqlDatabaseConnection();
            boolean restoreStatus = mysqlFunction.restoredb(path);

            if(restoreStatus){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("RedSoil Dashboard");
                alert.setHeaderText("Restore");
                alert.setContentText("Restore Successful!");
                alert.showAndWait();
            }
            else {
                showError("RedSoil Dashboard", "Restore", "Restore failed!");
            }
        }
    }

    public static void showError(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void dashboard_close_button(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("RedSoil Dashboard");
        alert.setHeaderText("Close Dashboard");
        alert.setContentText("Are you sure you want to close the dashboard?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Stage stage = (Stage) dashboardCloseButton.getScene().getWindow();
            stage.close();
            mysqlFunction.closeDatabase();
        }
    }

    public void dashboard_minimize_button(){
        Stage stage = (Stage) dashboardMinimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    public void dashboard_maximize_button(){
        Stage stage = (Stage) dashboardMaximizeButton.getScene().getWindow();
        if(stage.isMaximized()){
            stage.setMaximized(false);
        }
        else {
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(primaryScreenBounds.getMinX());
            stage.setY(primaryScreenBounds.getMinY());

            stage.setMaxWidth(primaryScreenBounds.getWidth());
            stage.setMinWidth(primaryScreenBounds.getWidth());

            stage.setMaxHeight(primaryScreenBounds.getHeight());
            stage.setMinHeight(primaryScreenBounds.getHeight());
        }
    }

    int xx, yy;
    public void title_mouse_pressed(MouseEvent event){
        xx = (int) event.getSceneX();
        yy = (int) event.getSceneY();
    }

    public void title_mouse_dragged(MouseEvent event){
        Stage stage = (Stage) dashboard_borderpane.getScene().getWindow();
        stage.setX(event.getScreenX() - xx);
        stage.setY(event.getScreenY() - yy);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> genderChoiceBoxValues = FXCollections.observableArrayList("Select","Male","Female", "Other");
        donorGenderField.setItems(genderChoiceBoxValues);
        donorGenderField.setValue("Select");

        ObservableList<String> aboChoiceBoxValues = FXCollections.observableArrayList("Select","A","B","AB","O");
        aboField.setItems(aboChoiceBoxValues);
        aboField.setValue("Select");

        ObservableList<String> rhChoiceBoxValues = FXCollections.observableArrayList("Select","+","-");
        rhField.setItems(rhChoiceBoxValues);
        rhField.setValue("Select");

        initializeDashboardTable();
        initializeDashboardDataView();
    }
}