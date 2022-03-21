package com.example.banktellerfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;


public class BankTellerController {

    protected AccountDatabase db = new AccountDatabase();
    protected Date today = new Date();

    @FXML
    private TextArea myTextArea, userArea, accountArea;
    @FXML
    private RadioButton oButton, cButton, dButton, wButton, ccButton, mmButton, checkButton, sButton;
    @FXML
    private Button pButton, ptButton, ubButton, piButton, userButton, accountButton;
    @FXML
    private VBox userBox;
    @FXML
    private Pane accountBox;
    @FXML
    private TextField loyalty_campus, fName, lName, moneyField;
    @FXML
    private DatePicker myDatePicker;
    @FXML
    private AnchorPane commandPane;


    public void getCommand() {
        userBox.setDisable(false);
        commandPane.setDisable(true);
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
    }

    public void userInfo() {
        String fname;
        String lname;
        Date dob;

        if (fName.getText().trim().isEmpty() || lName.getText().trim().isEmpty() || myDatePicker.getValue() == null) {
            userArea.setText("Data missing!");
            return;
        }
        String dobS = myDatePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        dob = new Date(dobS);

        if (!dob.isValid() || dob.compareTo(today) >= 0) {
            userArea.setText("Date of birth is invalid!");
            return;
        }
        fname = fName.getText();
        lname = lName.getText();

        Profile prof = new Profile(fname, lname, dob);
        userArea.setText("Success! Continue below");
        accountBox.setDisable(false);
        userBox.setDisable(true);
        loyalty_campus.setDisable(true);
    }

    public void getAccType() {
        if (ccButton.isSelected()) {
            loyalty_campus.setDisable(false);
        }
        else if (checkButton.isSelected()) {
        }
        else if (sButton.isSelected()) {
            loyalty_campus.setDisable(false);
        }
        else if (mmButton.isSelected()) {
        }
        ccButton.setDisable(true); mmButton.setDisable(true); checkButton.setDisable(true); sButton.setDisable(true);
    }

    public void accountInfo() {

        if (moneyField.getText().trim().isEmpty()) {
            accountArea.setText("Enter a valid amount!");
            return;
        }
        if ((sButton.isSelected() || ccButton.isSelected()) && loyalty_campus.getText().isEmpty()) {
            accountArea.setText("Enter a valid loyalty or campus code!");
            return;
        }
        double money = 0;
        int code = -1;
        try {
            money = Double.parseDouble(moneyField.getText());
        }
        catch (NumberFormatException e) {
            accountArea.setText("Enter only numbers!");
            return;
        }

        if (mmButton.isSelected() && oButton.isSelected() && money < 2500.0) {
            accountArea.setText("Must open a MM account with >= $2500");
            return;
        }

        if (money <= 0) {
            accountArea.setText("Must enter an amount greater than 0!");
        }

    }



}