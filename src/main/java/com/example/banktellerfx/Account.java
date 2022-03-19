package com.example.banktellerfx;

import java.text.DecimalFormat;
/**
 * The superclass of all types of bank accounts.
 * @author Aum Pathak, Tyler Sarno
 */
public abstract class Account {
    protected Profile holder;
    protected boolean closed;
    protected double balance;

    /**
     * Check if two accounts are equal.
     * @param obj takes in a generic obj type
     * @return return true if the two accounts are the same.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account) {
            String s1 = this.holder.toString() + this.getType();
            String s2 = ((Account) obj).holder.toString() + ((Account) obj).getType();
            return s1.equals(s2);
        }
        return false;
    }

    /**
     * Convert account to a string with all its info.
     * @return returns the string
     */
    @Override
    public String toString() {
        DecimalFormat fmt = new DecimalFormat("###,##0.00");
        String balance = fmt.format(this.getBalance());
        return this.getType() + "::" + this.holder.toString() + "::" + "Balance $" + balance
                + getClosed();
    }

    /**
     * a method to take money out of an account
     * @param amount the amount you want to withdraw.
     */
    public void withdraw(double amount) {
        if (this.closed || this.balance < amount) return; //error trapping(no funds or closed account)
        this.balance = this.balance - amount;
    }

    /**
     * a method to deposit money into an account.
     * @param amount the amount to be deposited.
     */
    public void deposit(double amount) {
        if (this.closed) return; //error trapping for closed account
        this.balance = this.balance + amount;
    }

    /**
     * getter method for balance.
     * @return the balance of the account.
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * get closed status of account.
     * @return string stating if the account is closed.
     */
    public String getClosed(){
        if (this.closed) return "::CLOSED";
        return "";
    }

    /**
     * abstract method for monthly interest
     * @return the interest as a double.
     */
    public abstract double monthlyInterest(); //return the monthly interest

    /**
     * abstract method for monthly fee.
     * @return the fee for this month.
     */
    public abstract double fee(); //return the monthly fee

    /**
     * abstract method for getting the type of account.
     * @return the account type as a string.
     */
    public abstract String getType(); //return the account type (class name)

}