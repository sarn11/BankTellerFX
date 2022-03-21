package com.example.banktellerfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;



public class BankTellerController {

    protected AccountDatabase db = new AccountDatabase();
    protected Date today = new Date();
    Profile prof;

    @FXML
    private TextArea myTextArea, userArea, accountArea, displayScreen;
    @FXML
    private RadioButton oButton, cButton, dButton, wButton, ccButton, mmButton, checkButton, sButton;
    @FXML
    private VBox userBox, accountBox2;
    @FXML
    private Pane accountBox;
    @FXML
    private TextField loyalty_campus, fName, lName, moneyField, dobField;
    @FXML
    private HBox commandPane;


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

        if (fName.getText().trim().isEmpty() || lName.getText().trim().isEmpty() || dobField.getText().trim().isEmpty()) {
            userArea.setText("Data missing!");
            return;
        }
        String dobS = dobField.getText();
        if (!Date.validFormat(dobS)) {
            userArea.setText("Enter dob as mm/dd/yyyy!");
            return;
        }
        dob = new Date(dobS);
        if (!dob.isValid() || dob.compareTo(today) >= 0) {
            userArea.setText("Date of birth is invalid!");
            return;
        }
        fname = fName.getText();
        lname = lName.getText();

        prof = new Profile(fname, lname, dob);
        userArea.setText("Success! Continue below");
        accountBox.setDisable(false);
        userBox.setDisable(true);
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
        accountBox.setDisable(true);
        accountBox2.setDisable(false);
    }

    public void accountInfo() {

        if (moneyField.getText().trim().isEmpty()) {
            accountArea.setText("Enter a valid amount!");
            return;
        }
        if ((sButton.isSelected() || ccButton.isSelected()) && loyalty_campus.getText().isEmpty()) {
            accountArea.setText("Enter a loyalty/campus code!");
            return;
        }
        double money;
        int code = -1;
        try {
            money = Double.parseDouble(moneyField.getText());
            if (sButton.isSelected() || ccButton.isSelected()) {
                code = Integer.parseInt(loyalty_campus.getText());
            }
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
            accountArea.setText("Enter an amount greater than 0!");
            return;
        }
        if (ccButton.isSelected() && (code > 3 || code < 0)) {
            accountArea.setText("Invalid campus code!");
            return;
        }
        if (sButton.isSelected() && (code < 0 || code > 1)) {
            accountArea.setText("Invalid loyalty code!");
            return;
        }
        createAcc(money, code);
        resetData();
        //accountArea.setText("Success!");
    }

    public void createAcc(Double money, int code) {
        Account acc = null;
        if (checkButton.isSelected()) {
            acc = new Checking(prof, money);
            checkButton.setSelected(false);
        }
        else if (ccButton.isSelected()) {
            acc = new CollegeChecking(prof, money, code);
            ccButton.setSelected(false);
        }
        else if (sButton.isSelected()) {
            acc = new Savings(prof, money, code);
            sButton.setSelected(false);

        }
        else if (mmButton.isSelected()) {
            acc = new MoneyMarket(prof, money);
            mmButton.setSelected(false);
        }
        if (acc != null && oButton.isSelected()) {
            openAcc(acc);
            oButton.setSelected(false);
        }
    }

    private void resetData() {
        commandPane.setDisable(false);
        accountBox.setDisable(true);
        accountBox2.setDisable(true);
        userArea.setText("Enter user info here:");
        fName.clear();
        lName.clear();
        dobField.clear();
        accountArea.setText("Enter account info here:");
        moneyField.clear();
        loyalty_campus.clear();
    }
    private void openAcc(Account acc) {
        switch (isReopen(acc)) {
            case "No duplicate":
                db.open(acc);
                myTextArea.setText("Account opened!");
                break;
            case "Reopen":
                db.open(acc);
                myTextArea.setText("Account reopened!");
            case "Different checking types":
                myTextArea.setText(prof.toString() + " already has a checking account.");
                break;
            case "Duplicate":
                myTextArea.setText(prof.toString() + " already has a " + acc.getType() + " account!");
                break;
        }
    }


    private String isReopen(Account account) {
        int numAcct = db.getNumAcct();
        if (numAcct == 0) return "No duplicate";
        Account[] accounts = db.getAccounts();
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].equals(account) && accounts[i].closed &&
                    accounts[i].getType().equals(account.getType())) return "Reopen";
            if (accounts[i].equals(account) && !accounts[i].getType().equals(account.getType())) return "Different checking types";
            if (accounts[i].equals(account) && accounts[i].getType().equals(account.getType())) return "Duplicate";
        }
        return "No duplicate";
    }
    // -2 if accounts are EQUAL and its closed -reopen if called/can't deposit/can't withdraw.
    // -3 if accounts are both checking type, but not same variation.
    // -4 if accounts are EQUAL, and it is not closed.

    public void displayAccounts() {
        StringBuilder accDisplay = db.print();
        displayScreen.setText(String.valueOf(accDisplay));
    }


}