package com.haitomns.redsoil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;

public class dashboardController {
    @FXML
    private ScrollPane dashboardScrollPane;
    @FXML
    private SplitPane userAccountSplitPane, bloodDonationSplitPane;
    @FXML
    private Button userAccountButton, bloodDonationButton, dashboardButton, companyUpdateButton;
    @FXML
    private TextField companyNameField, companyAddressField, companyPhoneField, companyUsernameField, companyPasswordField;

    @FXML
    private void dashboardNavigation(ActionEvent event) {
        if (event.getSource() == userAccountButton) {
            userAccountSplitPane.toFront();
            bloodDonationSplitPane.toBack();
            dashboardScrollPane.toBack();
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
            System.out.println("Company information updated successfully!");
        }else{
            System.out.println("Company information update failed!");
        }
    }
}
