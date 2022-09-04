package com.haitomns.redsoil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;

public class dashboardController {
    @FXML
    private ScrollPane dashboardScrollPane;
    @FXML
    private SplitPane userAccountSplitPane, bloodDonationSplitPane;
    @FXML
    private Button userAccountButton, bloodDonationButton, dashboardButton;

    @FXML
    private void dashboardNavigation(ActionEvent event) {
        if (event.getSource() == userAccountButton) {
            userAccountSplitPane.toFront();
            bloodDonationSplitPane.toBack();
            dashboardButton.toFront();
        } else if (event.getSource() == bloodDonationButton) {
            userAccountSplitPane.toBack();
            bloodDonationSplitPane.toFront();
            dashboardButton.toFront();
        } else if (event.getSource() == dashboardButton) {
            userAccountSplitPane.toBack();
            bloodDonationSplitPane.toBack();
            dashboardButton.toFront();
        }
    }
}
