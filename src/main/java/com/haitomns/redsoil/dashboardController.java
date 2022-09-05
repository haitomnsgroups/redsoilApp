package com.haitomns.redsoil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class dashboardController {
    @FXML
    private ScrollPane dashboardScrollPane;
    @FXML
    private SplitPane userAccountSplitPane, bloodDonationSplitPane;
    @FXML
    private Button userAccountButton, bloodDonationButton, dashboardButton;
    @FXML
    private TextField companyNameField, companyAddressField, companyPhoneField, companyUsernameField, companyPasswordField;

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
    private void companyUpdateButtonAction(ActionEvent event) {
        String companyName = companyNameField.getText();
        String companyAddress = companyAddressField.getText();
        String companyPhone = companyPhoneField.getText();
        String companyUsername = companyUsernameField.getText();
        String companyPassword = companyPasswordField.getText();

        mysqlFunction.mysqlDatabaseConnection();

        if(mysqlFunction.companyUpdate(companyName, companyAddress, companyPhone, companyUsername, companyPassword)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("RedSoil Dashboard");
            alert.setContentText("Company Details Updated :)");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("RedSoil Dashboard");
            alert.setContentText("Company Details Update Failed :(");
            alert.showAndWait();
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
}
