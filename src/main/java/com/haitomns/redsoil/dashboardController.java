package com.haitomns.redsoil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {
    String previousBloodDonatedStatus = "1";
    String diseaseList = "";
    @FXML
    private ScrollPane dashboardScrollPane, findBloodScrollPane, userAccountScrollPane;
    @FXML
    private SplitPane  bloodDonationSplitPane;
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
    private TextField weight, bp, hb, respSys, cvs, giSystem, other, fit, unit;
    @FXML
    private ChoiceBox<String> aboField, rhField = new ChoiceBox<>();
    @FXML
    private TextField  hiv, hbsag, hcv, vdrl;
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

    ObservableList<bloodFindTableModel> bloodDonorList = FXCollections.observableArrayList();
    ObservableList<bloodFindTableModel> dashboardTableData = FXCollections.observableArrayList();
    ObservableList<bloodDonationTableModel> bloodDonationAddData = FXCollections.observableArrayList();

    public dashboardController() {
    }

    @FXML
    private void dashboardNavigation(ActionEvent event) {
        if (event.getSource() == userAccountButton) {
            userAccountScrollPane.toFront();
            bloodDonationSplitPane.toBack();
            dashboardScrollPane.toBack();
            findBloodScrollPane.toBack();
            showCompanyDetails();
        } else if (event.getSource() == bloodDonationButton) {
            userAccountScrollPane.toBack();
            bloodDonationSplitPane.toFront();
            dashboardScrollPane.toBack();
            findBloodScrollPane.toBack();
            addDataToDonationTable();
        } else if (event.getSource() == dashboardButton) {
            userAccountScrollPane.toBack();
            bloodDonationSplitPane.toBack();
            dashboardScrollPane.toFront();
            findBloodScrollPane.toBack();
            initializeDashboardTable();
            initializeDashboardDataView();
        } else if (event.getSource() == findBloodButton) {
            userAccountScrollPane.toBack();
            bloodDonationSplitPane.toBack();
            dashboardScrollPane.toBack();
            findBloodScrollPane.toFront();
            initializeBloodFindTable();
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
        String respSys = this.respSys.getText();
        String cvs = this.cvs.getText();
        String giSystem = this.giSystem.getText();
        String other = this.other.getText();
        String fit = this.fit.getText();
        String unit = this.unit.getText();
        String abo = this.aboField.getValue();
        String rh = this.rhField.getValue();
        String hiv = this.hiv.getText();
        String hcb = this.hcv.getText();
        String hbsag = this.hbsag.getText();
        String vdrl = this.vdrl.getText();

        String bloodExpiryDate = getDateOfBloodExpiry();

        mysqlFunction.mysqlDatabaseConnection();

        if(validateDonorAndTesting(donorId, donorName, donorGender, donorAge, donorPhone, donorEmail, patientName, abo, rh)) {
            if (mysqlFunction.bloodAddDonorAndBlood(donationOrganization, donorName, donorGender, donorAge, donorPhone, donorOccupation, donorAddress, donorEmail, patientName, donorId, diseaseList, previousBloodDonatedStatus, lastDonatedDate, weight, bp, hb, respSys, cvs, giSystem, other, fit, unit, abo, rh, hiv, hcb, hbsag, vdrl, bloodExpiryDate)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("RedSoil Dashboard");
                alert.setContentText("Donor Details Inserted :)");
                alert.showAndWait();
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

    public static boolean validateDonorAndTesting(String donorId, String  donorName, String  donorGender, String  donorAge, String  donorPhone, String donorEmail, String patientName, String abo, String rh){
        if(donorId.isEmpty() || donorName.isEmpty() || patientName.isEmpty()){
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
        return bloodExpiry.toString();
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

    public void initializeBloodFindTable(){
        mysqlFunction.mysqlDatabaseConnection();
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID;");

        if(bloodDonorList!=null){
            donorIdColumn.setCellValueFactory(new PropertyValueFactory<>("donorId"));
            donorNameColumn.setCellValueFactory(new PropertyValueFactory<>("donorName"));
            donorPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("donorPhone"));
            aboColumn.setCellValueFactory(new PropertyValueFactory<>("abo"));
            rhColumn.setCellValueFactory(new PropertyValueFactory<>("rh"));
            unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
            creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfCreation"));
            expiryDateColumn.setCellValueFactory(new PropertyValueFactory<>("bloodExpiryDate"));

            findBloodTable.setItems(bloodDonorList);
        }
    }
    
    public void initializeDashboardTable(){
        mysqlFunction.mysqlDatabaseConnection();
        dashboardTableData = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID;");

        if(dashboardTableData!=null){
            addDataToTable(dashboardTableData);
        }
    }

    public void a_plus_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID where abo = \"A\" AND rh = \"+\";");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void a_minus_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID where abo = \"A\" AND rh = \"-\";");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void b_plus_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID where abo = \"B\" AND rh = \"+\";");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void b_minus_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID where abo = \"B\" AND rh = \"-\";");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void ab_plus_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID where abo = \"AB\" AND rh = \"+\";");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void ab_minus_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID where abo = \"AB\" AND rh = \"-\";");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void o_plus_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID where abo = \"O\" AND rh = \"+\";");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void o_minus_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID where abo = \"O\" AND rh = \"-\";");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void total_donor_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID;");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void total_active_donor_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID where blooddonationtestingdetails.Expiry_date > current_date(); ;");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
    }

    public void total_expired_donor_panel_clicked(){
        bloodDonorList = mysqlFunction.bloodDonationView("SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID where blooddonationtestingdetails.Expiry_date < current_date(); ;");

        if(bloodDonorList!=null){
            addDataToTable(bloodDonorList);
        }
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

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                bloodFindTableModel bloodFindTableModel = getTableView().getItems().get(getIndex());
                                editDataController editDataController = new editDataController();
                                editDataController.updateView(bloodFindTableModel.getDonorId());
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

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                bloodFindTableModel bloodFindTableModel = getTableView().getItems().get(getIndex());
                                String idToDelete = bloodFindTableModel.getDonorId();

                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("RedSoil Dashboard");
                                alert.setHeaderText("Remove Blood Donation");
                                alert.setContentText("Are you sure you want to remove this blood donation?");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK){
                                    boolean removeSuccess = mysqlFunction.removeBloodDonation(idToDelete);
                                    if(removeSuccess){
                                        Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
                                        alertSuccess.setTitle("RedSoil Dashboard");
                                        alertSuccess.setHeaderText("Remove Blood Donation");
                                        alertSuccess.setContentText("Blood donation removed successfully!");
                                        alertSuccess.showAndWait();
                                        initializeDashboardTable();
                                        initializeBloodFindTable();
                                    }
                                    else{
                                        showError("RedSoil Dashboard", "Remove Blood Donation", "Blood donation removal failed!");
                                    }
                                }
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
        respSys.setText("");
        cvs.setText("");
        giSystem.setText("");
        other.setText("");
        fit.setText("");
        unit.setText("");
        aboField.setValue("Select");
        rhField.setValue("Select");
        hiv.setText("");
        hbsag.setText("");
        hcv.setText("");
        vdrl.setText("");
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

    public static void showError(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO: SEND this to the visible panel function of bloodAdditionPage
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
