package com.example.banktellerfx;

import java.text.DecimalFormat;
/**
 * A class that holds and organizes all the bank accounts in the database.
 * @author Tyler Sarno, Aum Pathak.
 */
public class AccountDatabase {
    private Account [] accounts;
    private int numAcct;

    /**
     * Constructor for AccountDatabase.
     */
    public AccountDatabase(){
        accounts = new Account[4];
        numAcct = 0;
    }

    /**
     * Searches the database to see if there is a duplicate account.
     * @param account the account you are searching for.
     * @return returns -1 if no duplicate has been found, returns the index of the dupe account otherwise.
     */
    private int find(Account account) {
        if(numAcct == 0) return -1;

        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].equals(account)) return i;
        }
        return -1;
    }

    /**
     * getter method for numAcct
     * @return int (the number of accounts).
     */
    public int getNumAcct() {
        return this.numAcct;
    }

    /**
     * getter method for accounts.
     * @return the accounts array.
     */
    public Account[] getAccounts() {
        return this.accounts;
    }

    /**
     * if the database array has reached capacity, expand it by 4 slots.
     */
    private void grow() {

        Account[] temp = new Account[numAcct+4];
        for (int i = 0; i < numAcct; i++) {
            temp[i] = accounts[i];
        }
        accounts = temp;
    }

    /**
     * open an account and add it to the database.
     * @param account the account you want to add.
     * @return return true if acc opening successful, false otherwise.
     */
    public boolean open(Account account) {
        int accIndex = find(account);

        if (accIndex >= 0 && !accounts[accIndex].closed) return false; //acc already exits and is not closed.
        if (accIndex >= 0 && accounts[accIndex].closed) {
            accounts[accIndex].closed = false;
            accounts[accIndex].balance = accounts[accIndex].balance + account.balance;
            if (accounts[accIndex].getType().equals("Money Market Savings") && accounts[accIndex].balance >= 2500){
                ((MoneyMarket)accounts[accIndex]).setLoyal(1);
            }
            if (accounts[accIndex].getType().equals("College Checking")) {
                ((CollegeChecking)accounts[accIndex]).campus =((CollegeChecking)account).campus;
            }
            return true; //acc needs to be reopened (handle in the console).
        }

        if (accounts.length == numAcct) this.grow();
        accounts[numAcct] = account; //numAcct SHOULD be the right index every time.
        numAcct++;
        return true;
    }

    /**
     * Method to close an account.
     * @param  account the account that needs to be closed
     * @return false if no account present. Else, true once the account is closed
     */
    public boolean close(Account account) {
        int index = find(account);
        if (index == -1) return false;

        // If account is already closed.
        if (accounts[index].closed) return false;

        accounts[index].closed = true;
        accounts[index].balance = 0;

        if(accounts[index].getType().equals("Savings")){
            ((Savings)accounts[index]).setLoyal(0);
        }
        if (accounts[index].getType().equals("Money Market Savings")) {
            ((MoneyMarket)accounts[index]).setLoyal(0);
            ((MoneyMarket)accounts[index]).withdrawals = 0;
        }
        return true;
    }

    /**
     * deposit money into an account.
     * @param account the account you want to deposit money into.
     */
    public void deposit(Account account) {
        int index = find(account);

        accounts[index].deposit(account.balance);
        if (accounts[index].getType().equals("Money Market Savings")) {
            ((MoneyMarket) accounts[index]).setLoyal(1);
        }
    }

    /**
     * withdraw money from an account.
     * @param account the account you are withdrawing from.
     * @return return true if the withdrawal was successful, false otherwise.
     */
    public boolean withdraw(Account account) {
        int index = find(account);
        if (index == -1) {
            return false;
        }
        if (index >= 0 && accounts[index].closed) {
            return false;
        }

        if (account.balance > accounts[index].balance) return false;
        if (!account.getType().equals(accounts[index].getType())) return false;
        accounts[index].withdraw(account.balance);

        return true;
    }

    /**
     * display all the accounts in the database.
     */
    public StringBuilder print() {
        StringBuilder accDisplay = new StringBuilder();
        if(numAcct == 0) {
            accDisplay.append("Account Database is empty!");
            return accDisplay;
        }
        accDisplay.append("*list of accounts in the database*");
        accDisplay.append('\n');
        for (int i = 0; i < numAcct; i++){
            accDisplay.append(accounts[i].toString());
            accDisplay.append('\n');
        }
        accDisplay.append("*end of list*");
        accDisplay.append('\n');
        return accDisplay;
    }

    /**
     * method that sorts the database for printing (uses insertion sort).
     */
    private void sortDatabase() {

        for (int i = 1; i < numAcct; i++) {
            Account temp = accounts[i];
            int j = i - 1;
            while (j >= 0 && accounts[j].getType().compareTo(temp.getType()) > 0) {
                accounts[j + 1] = accounts[j];
                j--;
            }
            accounts[j + 1] = temp;
        }
    }

    /**
     * display all the accounts in the database organized by the type.
     */
    public StringBuilder printByAccountType() {
        StringBuilder accDisplay = new StringBuilder();
        if(numAcct == 0) {
            accDisplay.append("Account Database is empty!");
            return accDisplay;
        }
        accDisplay.append("*list of accounts by account type*\n");
        sortDatabase();
        for (int i = 0; i < numAcct; i++){
            accDisplay.append(accounts[i].toString() + '\n');
        }
        accDisplay.append("*end of list*" + '\n');
        return accDisplay;
    }

    /**
     * displays all accounts along with their fees and monthly interest.
     */
    public StringBuilder printFeeAndInterest() {
        StringBuilder accDisplay = new StringBuilder();
        if(numAcct == 0) {
           accDisplay.append("Account Database is empty!");
           return accDisplay;
        }
        accDisplay.append("*list of accounts with fees and monthly interest*" + '\n');
        DecimalFormat fmt = new DecimalFormat("###,##0.00");
        String fee;
        String monthlyInt;

        for (int i = 0; i < numAcct; i++){
            fee = fmt.format(accounts[i].fee());
            monthlyInt = fmt.format((accounts[i].monthlyInterest()));
            accDisplay.append(accounts[i].toString() + "::" + "fee $" + fee + "::" + "monthly interest $" + monthlyInt + '\n');
        }
        accDisplay.append("*end of list*" + '\n');
        return accDisplay;
    }

    /**
     * Update the balance of all the accounts in the database (that aren't closed).
     */
    public StringBuilder printUpdatedBalances() {
        StringBuilder accDisplay = new StringBuilder();
        if (numAcct == 0) {
            accDisplay.append("Account Database is empty!");
            return accDisplay;
        }
        accDisplay.append("*list of accounts with updated balance*" + '\n');
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].closed) continue;
            accounts[i].deposit(accounts[i].monthlyInterest());
            accounts[i].balance = accounts[i].balance - accounts[i].fee();
            if (accounts[i].getType().equals("Money Market Savings") && accounts[i].balance < 2500) {
                ((MoneyMarket)accounts[i]).setLoyal(0);
            }
        }
        for (int i = 0; i < numAcct; i++){
            accDisplay.append(accounts[i].toString() + '\n');
        }
        accDisplay.append("*end of list*" + '\n');
        accDisplay.append("You can only do this once per action!");
        return accDisplay;
    }
}
