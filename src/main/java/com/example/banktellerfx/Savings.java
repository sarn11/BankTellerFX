package com.example.banktellerfx;

/**
 * A subclass of Account, this class defines the Savings account.
 * @author Aum Pathak, Tyler Sarno
 */
public class Savings extends Account {
    protected int loyal;

    /**
     * Constructor method for a Savings account.
     * @param holder the owner of the account.
     * @param deposit the initial deposit for the account.
     */
    public Savings (Profile holder, Double deposit, int loyal) {
        this.holder = holder;
        this.balance = deposit;
        this.closed = false;
        this.loyal = loyal;
    }

    /**
     * Convert account to a string with all its info.
     * @return returns the string
     */
    @Override
    public String toString() {
        if (this.loyal == 1){
            return super.toString() + "::" + this.getLoyal();
        }
        return super.toString();
    }

    /**
     * setter method for loyalty status.
     * @param loyal the loyalty code.
     */
    public void setLoyal(int loyal) {
        this.loyal = loyal;
    }

    /**
     * getter method for the loyalty status.
     * @return returns the loyalty status.
     */
    public String getLoyal() {
        if(this.loyal == 1) return "loyal";
        return "";
    }

    /**
     * parameterless constructor for Savings.
     */
    public Savings() {
    }

    /**
     * Calculate the monthly interest you will receive this month with the current balance.
     * @return the interest as a double.
     */
    public double monthlyInterest() {

        if (this.closed) return 0; //closed account
        if (loyal == 0) return this.balance * (.003/12);
        return (this.balance * (.0045/12));
    }

    /**
     * Check to see if you will receive a service fee (based on your current balance).
     * @return return the monthly fee.
     */
    public double fee() {
        if (this.closed) return 0; //closed account
        if (this.balance < 0) return 0;
        if (this.balance < 300) return 6.0;
        else return 0;
    }

    /**
     * Check what type of bank account you have.
     * @return the bank account type as a string.
     */
    public String getType() {
        return "Savings";
    }

}
