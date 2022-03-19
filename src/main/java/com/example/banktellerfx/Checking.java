package com.example.banktellerfx;

/**
 * A subclass of Account, this class defines the checking account.
 * @author Aum Pathak, Tyler Sarno
 */
public class Checking extends Account{

    /**
     * Constructor method for a checking account.
     * @param holder the owner of the account.
     * @param deposit the initial deposit for the account.
     */
    public Checking (Profile holder, Double deposit) {
        this.holder = holder;
        this.balance = deposit;
        this.closed = false;
    }

    /**
     * parameterless constructor for Checking.
     */
    public Checking() {
    }

    /**
     * Check if two checking accounts are equal (cannot have both a college checking and a normal checking).
     * @param obj takes in a generic obj type.
     * @return return true if the two accounts are the same.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account) {
            if (!((Account) obj).getType().equals("Checking") &&
                    !((Account) obj).getType().equals("College Checking")) return false;
            return this.holder.equals(((Account) obj).holder);
        }
        return false;
    }


    /**
     * Calculate the monthly interest you will receive this month with the current balance.
     * @return the interest as a double.
     */
    public double monthlyInterest() {
        if (this.closed) return 0; //closed account
        if (this.balance < 0) return 0;
        return this.balance * (.001/12);
    }

    /**
     * Check to see if you will receive a service fee (based on your current balance).
     * @return return the monthly fee.
     */
    public double fee() {
        if (this.closed) return 0; //closed account
        if (this.balance < 1000) return 25.0;
        else return 0;
    }

    /**
     * Check what type of bank account you have.
     * @return the bank account type as a string.
     */
    public String getType() {
        return "Checking";
    }
}
