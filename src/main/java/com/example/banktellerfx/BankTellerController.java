package com.example.banktellerfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * The controller class of the GUI that performs actions by communicating with the model classes.
 *
 * @author Tyler Sarno, Aum Pathak.
 */
public class BankTellerController {

    protected AccountDatabase db = new AccountDatabase();
    protected Date today = new Date();
    Profile prof;

    /**
     * TextArea constructors to display messages.
     */
    @FXML
    private TextArea myTextArea, userArea, accountArea, displayScreen;
    /**
     * RadioButtons that perform various actions such as commands and account types.
     */
    @FXML
    private RadioButton oButton, cButton, dButton, wButton, ccButton, mmButton, checkButton,
            sButton, nbButton, newarkButton, camButton, loyalButton;
    /**
     * VBox that holds various nodes.
     */
    @FXML
    private VBox userBox, accountBox2;
    /**
     * Pane that holds various nodes.
     */
    @FXML
    private Pane accountBox;
    /**
     * TextFields that take in various data from the user.
     */
    @FXML
    private TextField fName, lName, moneyField, dobField;
    /**
     * HBox that holds various nodes.
     */
    @FXML
    private HBox commandPane;
    /**
     * AnchorPane that holds various nodes.
     */
    @FXML
    private AnchorPane optionPane;
    /**
     * Button that updates balances of all accounts.
     */
    @FXML
    private Button ubButton;


    /**
     * Method to get the command to be performed.
     */
    public void getCommand() {
        userBox.setDisable(false);
        commandPane.setDisable(true);
        if (oButton.isSelected()) {
            myTextArea.setText("Enter info below to open your account!");
        } else if (cButton.isSelected()) {
            myTextArea.setText("Enter info below to close your account.");
            moneyField.setDisable(true);
        } else if (dButton.isSelected()) {
            myTextArea.setText("Enter info below to make a deposit.");
        } else if (wButton.isSelected()) {
            myTextArea.setText("Enter info below to make a withdrawal.");
        }
        if (!oButton.isSelected()) {
            optionPane.setDisable(true);
        }
        if (!cButton.isSelected()) moneyField.setDisable(false);
    }

    /**
     * Method to get the information of the account holder.
     */
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

    /**
     * Method to get the type of account.
     */
    public void getAccType() {
        if (ccButton.isSelected()) {
            if (oButton.isSelected()) {
                optionPane.setDisable(false);
                loyalButton.setDisable(true);
            }
        } else if (sButton.isSelected()) {
            if (oButton.isSelected()) {
                optionPane.setDisable(false);
                nbButton.setDisable(true);
                newarkButton.setDisable(true);
                camButton.setDisable(true);
                loyalButton.setDisable(false);
            }
        }
        accountBox.setDisable(true);
        accountBox2.setDisable(false);
    }

    /**
     * Method to gather the information about the account.
     */
    public void accountInfo() {
        if (cButton.isSelected()) {
            createAcc(0.0, 0);
            resetData();
            return;
        }
        if (moneyField.getText().trim().isEmpty()) {
            accountArea.setText("Enter a valid amount!");
            return;
        }
        double money;
        int code = 0;
        if (sButton.isSelected() && oButton.isSelected() && loyalButton.isSelected()) {
            code = 1;
        }
        if ((ccButton.isSelected()) && oButton.isSelected()) {
            if (!nbButton.isSelected() && !camButton.isSelected() && !newarkButton.isSelected()) {
                accountArea.setText("Enter a campus!");
                return;
            } else if (nbButton.isSelected()) code = 0;
            else if (newarkButton.isSelected()) code = 1;
            else if (camButton.isSelected()) code = 2;
        }
        try {
            money = Double.parseDouble(moneyField.getText());
        } catch (NumberFormatException e) {
            accountArea.setText("Enter only numbers!");
            return;
        }
        if (mmButton.isSelected() && oButton.isSelected() && money < 2500.0) {
            accountArea.setText("Must open a MM account with >= $2500");
            return;
        }

        if (money <= 0 && !cButton.isSelected()) {
            accountArea.setText("Enter an amount greater than 0!");
            return;
        }
        createAcc(money, code);
        resetData();
    }

    /**
     * method that creates the account.
     *
     * @param money amount of deposit or withdrawal.
     * @param code  loyalty or campus code if applicable.
     */
    public void createAcc(Double money, int code) {
        Account acc = null;
        if (checkButton.isSelected()) {
            acc = new Checking(prof, money);
            checkButton.setSelected(false);
        } else if (ccButton.isSelected()) {
            acc = new CollegeChecking(prof, money, code);
            ccButton.setSelected(false);
        } else if (sButton.isSelected()) {
            acc = new Savings(prof, money, code);
            sButton.setSelected(false);
        } else if (mmButton.isSelected()) {
            acc = new MoneyMarket(prof, money);
            mmButton.setSelected(false);
        }
        if (acc != null && oButton.isSelected()) {
            openAcc(acc);
            oButton.setSelected(false);
        } else if (acc != null && cButton.isSelected()) {
            closeAcc(acc);
            cButton.setSelected(false);
        } else if (acc != null && dButton.isSelected()) {
            depositAcc(acc);
            dButton.setSelected(false);
        } else if (acc != null && wButton.isSelected()) {
            withdrawAcc(acc);
            wButton.setSelected(false);
        }
    }

    /**
     * method to withdraw money from an account.
     *
     * @param acc the account to be withdrawn from.
     */
    private void withdrawAcc(Account acc) {
        boolean b = db.withdraw(acc);
        switch (isReopen(acc)) {
            case "Duplicate" -> {
                if (!b) {
                    myTextArea.setText("Withdrawal - insufficient funds.");
                } else myTextArea.setText("Withdrawal - balance updated.");
            }
            case "Reopen" -> myTextArea.setText("Cannot withdraw from a closed account.");
            case "Different checking types", "No duplicate" -> myTextArea.setText(prof.toString() + " " + acc.getType()
                    + " is not in the database.");
        }
    }

    /**
     * Method to deposit money into an account.
     *
     * @param acc the account to be deposited into.
     */
    private void depositAcc(Account acc) {
        switch (isReopen(acc)) {
            case "Duplicate" -> {
                db.deposit(acc);
                myTextArea.setText("Deposit - balance updated.");
            }
            case "Reopen" -> myTextArea.setText("Cannot deposit into a closed account.");
            case "Different checking types", "No duplicate" -> myTextArea.setText(prof.toString() + " " + acc.getType()
                    + " is not in the database.");
        }
    }

    /**
     * resets all the data and input on the GUI for functionality!
     */
    public void resetData() {
        commandPane.setDisable(false);
        accountBox.setDisable(true);
        accountBox2.setDisable(true);
        optionPane.setDisable(true);
        userArea.setText("Enter user info below:");
        fName.clear();
        lName.clear();
        dobField.clear();
        accountArea.setText("Enter account info below:");
        moneyField.clear();
        nbButton.setSelected(false);
        camButton.setSelected(false);
        newarkButton.setSelected(false);
        loyalButton.setSelected(false);
        oButton.setSelected(false);
        cButton.setSelected(false);
        dButton.setSelected(false);
        wButton.setSelected(false);
        checkButton.setSelected(false);
        ccButton.setSelected(false);
        sButton.setSelected(false);
        mmButton.setSelected(false);
        nbButton.setDisable(false);
        newarkButton.setDisable(false);
        camButton.setDisable(false);
        loyalButton.setDisable(false);
        ubButton.setDisable(false);
    }

    /**
     * method to open an account.
     *
     * @param acc the account to be opened.
     */
    private void openAcc(Account acc) {
        switch (isReopen(acc)) {
            case "No duplicate" -> {
                db.open(acc);
                myTextArea.setText("Account opened!");
            }
            case "Reopen" -> {
                db.open(acc);
                myTextArea.setText("Account reopened!");
            }
            case "Different checking types" -> myTextArea.setText(prof.toString() + " already has a checking account.");
            case "Duplicate" -> myTextArea.setText(prof.toString() + " already has a " + acc.getType() + " account!");
        }
    }

    /**
     * method to close an account.
     *
     * @param acc the account to be closed.
     */
    private void closeAcc(Account acc) {
        switch (isReopen(acc)) {
            case "No duplicate", "Different checking types" -> myTextArea.setText(prof.toString() + " "
                    + acc.getType() + " is not in the database!");
            case "Reopen" -> myTextArea.setText("Account is already closed!");
            case "Duplicate" -> {
                myTextArea.setText("Account closed!");
                db.close(acc);
            }
        }
    }


    /**
     * method to check if there are duplicate accounts in the database already.
     *
     * @param account the account being checked
     * @return return a string value indicating the status.
     */
    private String isReopen(Account account) {
        int numAcct = db.getNumAcct();
        if (numAcct == 0) return "No duplicate";
        Account[] accounts = db.getAccounts();
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].equals(account) && accounts[i].closed &&
                    accounts[i].getType().equals(account.getType())) return "Reopen";
            if (accounts[i].equals(account) && !accounts[i].getType().equals(account.getType()))
                return "Different checking types";
            if (accounts[i].equals(account) && accounts[i].getType().equals(account.getType())) return "Duplicate";
        }
        return "No duplicate";
    }

    /**
     * method to display all the accounts in the database.
     */
    public void displayAccounts() {
        StringBuilder accDisplay = db.print();
        displayScreen.setText(String.valueOf(accDisplay));
    }

    /**
     * method to display the accounts in the database by type.
     */
    public void displayByType() {
        StringBuilder accDisplay = db.printByAccountType();
        displayScreen.setText(String.valueOf(accDisplay));
    }

    /**
     * method to display the updated balances in the database.
     */
    public void updateBalances() {
        StringBuilder accDisplay = db.printUpdatedBalances();
        displayScreen.setText(String.valueOf(accDisplay));
        ubButton.setDisable(true);
    }

    /**
     * method displaying all the fees and interest along with account information.
     */
    public void displayFees() {
        StringBuilder accDisplay = db.printFeeAndInterest();
        displayScreen.setText(String.valueOf(accDisplay));
    }
}