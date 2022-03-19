package com.example.banktellerfx;

/**
 * A subclass of Checking, defining the college checking account.
 * @author Aum Pathak, Tyler Sarno
 */
public class CollegeChecking extends Checking {
    protected String campus;

    /**
     * getter method for the campus code
     * @return returns the campus as a string.
     */
    public String getCampus() {
        return campus;
    }

    /**
     * Constructor for CollegeChecking.
     * @param holder the owner of the account.
     * @param deposit the initial deposit.
     * @param campus the rutgers campus student is located on.
     */
    public CollegeChecking(Profile holder, Double deposit, int campus){
        this.holder = holder;
        this.balance = deposit;
        this.closed = false;
        if (campus == 0) this.campus = "New Brunswick";
        else if(campus == 1) this.campus = "Newark";
        else if(campus == 2) this.campus = "Camden";
    }

    /**
     * Convert account to a string with all its info.
     * @return returns the string
     */
    @Override
    public String toString() {
        return super.toString() + "::" + this.getCampus();
    }

    /**
     * Calculate the monthly interest you will receive this month with the current balance.
     * @return the interest as a double.
     */
    public double monthlyInterest() {
        if (this.closed) return 0; //closed account
        if (this.balance < 0) return 0;
        return this.balance * .0025/12;
    }

    /**
     * Check to see if you will receive a service fee.
     * @return return the monthly fee.
     */
    public double fee() {
        return 0;
    }

    /**
     * Check what type of bank account you have.
     * @return the bank account type as a string.
     */
    public String getType() {
        return "College Checking";
    }

}
