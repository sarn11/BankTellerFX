package com.example.banktellerfx;

/**
 * A subclass of Savings, defining the Money Market account.
 * @author Aum Pathak, Tyler Sarno
 */
public class MoneyMarket extends Savings{
    protected int withdrawals;
    /**
     * Constructor method for a MoneyMarket account.
     * @param holder the owner of the account.
     * @param deposit the initial deposit for the account.
     */
    public MoneyMarket (Profile holder, Double deposit) {
        this.holder = holder;
        this.balance = deposit;
        this.closed = false;
        this.withdrawals = 0;
        this.loyal = 1;
    }

    /**
     * Convert account to a string with all its info.
     * @return returns the string
     */
    @Override
    public String toString() {
        return super.toString() + "::" + "withdrawals:" + this.withdrawals;
    }

    /**
     * withdraw function specific for MoneyMarket.
     * @param amount the amount you want to withdraw.
     */
    @Override
    public void withdraw(double amount) {
        if (this.closed || this.balance < amount) return; //make sure to not increment withdraws if the withdrawal is invalid
        super.withdraw(amount);
        withdrawals++;
        if (this.balance < 2500 || this.withdrawals > 3) this.setLoyal(0);
    }

    /**
     * Calculate the monthly interest you will receive this month with the current balance.
     * @return the interest as a double.
     */
    public double monthlyInterest() {
        if (this.closed) return 0; //closed account
        if (this.balance < 0) return 0;
        if (this.balance < 2500) return this.balance * (.008/12);
        return this.balance * (.0095/12);
    }

    /**
     * Check to see if you will receive a service fee (based on your current balance).
     * @return return the monthly fee.
     */
    public double fee() {
        if (this.closed) return 0; //closed account
        if (this.balance < 2500) return 10.0;
        else if (this.withdrawals > 3) return 10.0;
        else return 0;
    }

    /**
     * Check what type of bank account you have.
     * @return the bank account type as a string.
     */
    public String getType() {
        return "Money Market Savings";
    }

}
