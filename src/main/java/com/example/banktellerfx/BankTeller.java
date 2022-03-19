package com.example.banktellerfx;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Bank Teller program to take info from the client through the console.
 *
 * @author Tyler Sarno, Aum Pathak
 */
public class BankTeller {
    private Date today = new Date();
    private AccountDatabase db = new AccountDatabase();

    /**
     * Check if you are reopening an account.
     *
     * @param account the account you are searching for.
     * @return returns -1 if no duplicate has been found, returns the index of the dupe account otherwise.
     */
    private int isReopen(Account account) {
        int numAcct = db.getNumAcct();
        if (numAcct == 0) return -1;
        Account[] accounts = db.getAccounts();
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].equals(account) && accounts[i].closed &&
                    accounts[i].getType().equals(account.getType())) return -2;
            if (accounts[i].equals(account) && !accounts[i].getType().equals(account.getType())) return -3;
            if (accounts[i].equals(account) && accounts[i].getType().equals(account.getType())) return -4;
        }
        return -1;
    }
    // -2 if accounts are EQUAL and its closed -reopen if called/can't deposit/can't withdraw.
    // -3 if accounts are both checking type, but not same variation.
    // -4 if accounts are EQUAL, and it is not closed.

    /**
     * creates a money market savings account.
     *
     * @param command the specific command.
     * @param holder  the account holder.
     * @param bal     the account balance/deposit/withdrawal.
     */
    private void createMM(String command, Profile holder, double bal) {

        if (command.equals("C")) {
            MoneyMarket acc = new MoneyMarket(holder, 0.0);
            if (isReopen(acc) == -2) System.out.println("Account is closed already.");
            if (isReopen(acc) == -1) System.out.println(holder + " Money Market is not in the database.");
            if (isReopen(acc) == -4) {
                System.out.println("Account closed.");
                db.close(acc);
            }
        }
        if (command.equals("D")) {
            MoneyMarket acc = new MoneyMarket(holder, bal);
            if (isReopen(acc) == -2) {
                System.out.println("Cannot deposit into a closed account.");
                return;
            }
            if (isReopen(acc) == -1) {
                System.out.println(holder + " Money Market is not in the database.");
                return;
            }
            db.deposit(acc);
            System.out.println("Deposit - balance updated.");
        }
        if (command.equals("W")) {
            MoneyMarket acc = new MoneyMarket(holder, bal);
            boolean b = db.withdraw(acc);
            if (isReopen(acc) == -1 || isReopen(acc) == -2) {
                System.out.println(holder + " Money Market is not in the database.");
                return;
            }
            if (!b) {
                System.out.println("Withdraw - insufficient funds.");
                return;
            }
            System.out.println("Withdraw - balance updated.");
        }

        if (command.equals("O")) {
            if (bal < 2500) {
                System.out.println("Minimum of $2500 to open a MoneyMarket account.");
            } else {
                MoneyMarket acc = new MoneyMarket(holder, bal);
                if (isReopen(acc) == -2) {
                    System.out.println("Account reopened.");
                } else if (isReopen(acc) == -4) {
                    System.out.println(holder + " same account(type) is in the database.");
                    return;
                } else System.out.println("Account opened");
                db.open(acc);
            }
        }
    }

    /**
     * creates a checking account.
     *
     * @param command the specific command.
     * @param holder  the account holder.
     * @param bal     the account balance/deposit/withdrawal.
     */
    private void createC(String command, Profile holder, double bal) {
        if (command.equals("C")) {
            Checking acc = new Checking(holder, 0.0);
            if (isReopen(acc) == -2) System.out.println("Account is closed already.");
            if (isReopen(acc) == -1) System.out.println(holder + " Checking is not in the database.");
            if (isReopen(acc) == -4) {
                System.out.println("Account closed.");
                db.close(acc);
            }
        }
        Checking acc = new Checking(holder, bal);
        if (command.equals("D")) {
            //Checking acc = new Checking(holder, bal);
            if (isReopen(acc) == -2) {
                System.out.println("Cannot deposit into a closed account.");
                return;
            }
            if (isReopen(acc) == -1 || isReopen(acc) == -3) {
                System.out.println(holder + " Checking is not in the database.");
                return;
            }
            db.deposit(acc);
            System.out.println("Deposit - balance updated.");
        }
        if (command.equals("W")) {
            boolean b = db.withdraw(acc);
            if (isReopen(acc) == -1 || isReopen(acc) == -3 || isReopen(acc) == -2) {
                System.out.println(holder + " Checking is not in the database.");
                return;
            }
            if (!b) {
                System.out.println("Withdraw - insufficient funds.");
                return;
            }
            System.out.println("Withdraw - balance updated.");
        }

        if (command.equals("O")) {
            if (isReopen(acc) == -2) {
                System.out.println("Account reopened.");
            } else if (isReopen(acc) == -4 || isReopen(acc) == -3) {
                System.out.println(holder + " same account(type) is in the database.");
                return;
            } else System.out.println("Account opened");
            db.open(acc);
        }
    }

    /**
     * creates a college checking account.
     *
     * @param campus  the campus code.
     * @param command the specific command.
     * @param holder  the account holder
     * @param bal     the account balance/deposit/withdrawal.
     */
    private void createCC(int campus, String command, Profile holder, double bal) {
        if (command.equals("C")) {
            CollegeChecking acc = new CollegeChecking(holder, 0.0, 0);
            if (isReopen(acc) == -2) System.out.println("Account is closed already.");
            if (isReopen(acc) == -1) System.out.println(holder + " Checking is not in the database.");
            if (isReopen(acc) == -4) {
                System.out.println("Account closed.");
                db.close(acc);
            }
        }
        CollegeChecking acc = new CollegeChecking(holder, bal, campus);
        if (command.equals("D")) {
            if (isReopen(acc) == -2) {
                System.out.println("Cannot deposit into a closed account.");
                return;
            }
            if (isReopen(acc) == -1 || isReopen(acc) == -3) {
                System.out.println(holder + " College Checking is not in the database.");
                return;
            }
            db.deposit(acc);
            System.out.println("Deposit - balance updated.");
        }
        if (command.equals("W")) {
            boolean b = db.withdraw(acc);
            if (isReopen(acc) == -1 || isReopen(acc) == -3 || isReopen(acc) == -2) {
                System.out.println(holder + " College Checking is not in the database.");
                return;
            }
            if (!b) {
                System.out.println("Withdraw - insufficient funds.");
                return;
            }
            System.out.println("Withdraw - balance updated.");
        }

        if (command.equals("O")) {
            if (isReopen(acc) == -2) {
                System.out.println("Account reopened.");
            } else if (isReopen(acc) == -4 || isReopen(acc) == -3) {
                System.out.println(holder + " same account(type) is in the database.");
                return;
            } else System.out.println("Account opened");
            db.open(acc);
        }
    }

    /**
     * method to create a savings account
     *
     * @param loyal   loyalty code.
     * @param command specific command.
     * @param holder  holder of the account.
     * @param bal     account balance/deposit/withdrawal.
     */
    private void createS(int loyal, String command, Profile holder, double bal) {
        if (command.equals("C")) {
            Savings acc = new Savings(holder, 0.0, 0);
            if (isReopen(acc) == -2) System.out.println("Account is closed already.");
            if (isReopen(acc) == -1) System.out.println(holder + " Savings is not in the database.");
            if (isReopen(acc) == -4) {
                System.out.println("Account closed.");
                db.close(acc);
            }
        }
        Savings acc = new Savings(holder, bal, loyal);
        if (command.equals("D")) {
            if (isReopen(acc) == -2) {
                System.out.println("Cannot deposit into a closed account.");
                return;
            }
            if (isReopen(acc) == -1) {
                System.out.println(holder + " Checking is not in the database.");
                return;
            }
            db.deposit(acc);
            System.out.println("Deposit - balance updated.");
        }
        if (command.equals("W")) {
            boolean b = db.withdraw(acc);
            if (isReopen(acc) == -1 || isReopen(acc) == -2) {
                System.out.println(holder + " Savings is not in the database.");
                return;
            }
            if (!b) {
                System.out.println("Withdraw - insufficient funds.");
                return;
            }
            System.out.println("Withdraw - balance updated.");
        }

        if (command.equals("O")) {
            if (isReopen(acc) == -2) {
                System.out.println("Account reopened.");
            } else if (isReopen(acc) == -4) {
                System.out.println(holder + " same account(type) is in the database.");
                return;
            } else System.out.println("Account opened");
            db.open(acc);
        }
    }

    /**
     * Method that gathers all the necessary data and calls the specific method to create an account.
     *
     * @param input   the input from the user
     * @param command the command for what action to take.
     */
    private void createAccount(StringTokenizer input, String command) {
        if (command.equals("C") && input.countTokens() < 4) {
            System.out.println("Missing data for closing an account.");
            return;
        } else if (!command.equals("C") && input.countTokens() < 5) {
            System.out.println("Missing data for opening an account.");
            return;
        }
        String type = input.nextToken();
        String fname = input.nextToken();
        String lname = input.nextToken();
        String birth = input.nextToken();
        double bal = 0;

        if (!command.equals("C")) {
            try {
                bal = Double.parseDouble(input.nextToken());
            } catch (NumberFormatException e) {
                System.out.println("Invalid balance.");
                return;
            }
        }

        if (!Date.validFormat(birth)) {
            System.out.println("Date of birth invalid.");
            return;
        }
        Date dob = new Date(birth);
        if (!dob.isValid() || dob.compareTo(today) >= 0) {
            System.out.println("Date of birth invalid.");
            return;
        }
        if (bal <= 0 && command.equals("O")) {
            System.out.println("Initial deposit cannot be 0 or negative.");
            return;
        }
        if (bal <= 0 && command.equals("D")) {
            System.out.println("Deposit - amount cannot be 0 or negative.");
            return;
        }
        if (bal <= 0 && command.equals("W")) {
            System.out.println("Withdraw - amount cannot be 0 or negative.");
            return;
        }
        Profile holder = new Profile(fname, lname, dob);

        if (type.equals("MM")) createMM(command, holder, bal);
        if (type.equals("C")) createC(command, holder, bal);
        int campus = -1;
        int loyal = -1;
        if (type.equals("CC")) {
            if (input.hasMoreTokens()) {
                try {
                    campus = Integer.parseInt(input.nextToken());
                } catch (NumberFormatException ignored) {
                }
            }
            if ((campus < 0 || campus > 2) && command.equals("O")) {
                System.out.println("Invalid campus code.");
                return;
            }
        }
        if (type.equals("CC")) createCC(campus, command, holder, bal);
        if (type.equals("S")) {
            if (input.hasMoreTokens()) {
                try {
                    loyal = Integer.parseInt(input.nextToken());
                } catch (NumberFormatException ignored) {
                }
            }
            if ((loyal < 0 || loyal > 1) && command.equals("O")) {
                System.out.println("Invalid loyalty code.");
                return;
            }
            createS(loyal, command, holder, bal);
        }
    }

    /**
     * run method that prompts the console to start taking input using scanner and string tokenizer.
     */
    protected void run() {
        System.out.println("Bank Teller is running.");
        Scanner sc = new Scanner(System.in);
        while (true) {
            String read;
            try {
                read = sc.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Invalid command!");
                break;
            }

            StringTokenizer input = new StringTokenizer(read, "\t ");
            //handles no such element
            if (!input.hasMoreTokens()) {
                System.out.println("Invalid Command!");
                continue;
            }
            String command = input.nextToken();

            if (command.equals("Q")) break;
            else if (command.equals("P")) db.print();
            else if (command.equals("PT")) db.printByAccountType();
            else if (command.equals("PI")) db.printFeeAndInterest();
            else if (command.equals("UB")) db.printUpdatedBalances();
            else if (!command.equals("O") && !command.equals("C") && !command.equals("D") && !command.equals("W")) {
                System.out.println("Invalid Command!");
            } else createAccount(input, command);

        }
        System.out.println("Bank Teller is terminated.");
    }
}
