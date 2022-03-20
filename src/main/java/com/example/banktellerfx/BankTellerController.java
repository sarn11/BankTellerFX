package com.example.banktellerfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class BankTellerController {

    @FXML
    private TextArea myTextArea, userArea, accountArea;
    @FXML
    private RadioButton oButton, cButton, dButton, wButton, pButton, ptButton, ubButton, piButton;
    @FXML
    private VBox userBox;
    @FXML
    private Pane accountBox;


    public void getCommand(ActionEvent e) {
        userBox.setDisable(false);
        accountBox.setDisable(false);

        if (oButton.isSelected()) {
            myTextArea.setText("Enter info below to open your account!");
        }
        else if (cButton.isSelected()) {
            myTextArea.setText("Enter info below to close your account.");
        }
        else if (dButton.isSelected()) {
            myTextArea.setText("Enter info below to make a deposit.");
        }
        else if (wButton.isSelected()) {
            myTextArea.setText("Enter info below to make a withdrawal.");
        }
        else {
            userBox.setDisable(true);
            accountBox.setDisable(true);
        }
        if (pButton.isSelected()) {
            myTextArea.setText("Displaying all accounts!");
        }
        else if (ptButton.isSelected()) {
            myTextArea.setText("Displaying all accounts by type!");
        }
        else if (ubButton.isSelected()) {
            myTextArea.setText("Updating balance of all accounts!");
        }
        else if (piButton.isSelected()) {
            myTextArea.setText("Displaying all accounts with fees and interest!");
        }
    }
}