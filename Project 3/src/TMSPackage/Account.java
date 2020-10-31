/**
 * 
 */
package TMSPackage;

import java.text.DecimalFormat;

/**
 * This class defines the basic features of an account, regardless of what type it is.
 * It contains getter methods to access its private instance variables, and two abstract
 * methods that are implemented in each of its child classes.  
 * @author Sandeep Alankar, Graham Deubner
 */
public abstract class Account {    
    private Profile holder;
    private double balance;
    private Date dateOpen;

    /**
     * Parameterized constructor that initializes instance variables, allows 
     * use in other classes.
     * @param holder an object containing the first and last name of the account holder
     * @param balance the balance of the new account
     * @param dateOpen an object containing the date the new account was opened. 
     * 
     */
    public Account (Profile holder, double balance, Date dateOpen) {
        this.holder = holder;
        this.balance = balance;
        this.dateOpen = dateOpen;
    }

    /**
     * Getter method that returns holder.
     * 
     * @return holder of account
     */
    public Profile getHolder() {
        return holder;
    }

    /**
     * Getter method that returns balance.
     * 
     * @return balance of account
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Getter method that returns dateOpen.
     * 
     * @return dateOpen, date that account was opened
     */
    public Date getDateOpen() {
        return dateOpen;
    }

    /**
     * Decreases the balance by amount. 
     * @param amount an amount to be withdrawn from this account
     */
    public void debit(double amount) { 
        balance -= amount;
    } 

    /**
     * Increases the balance by amount. 
     * @param amount the amount by which the balance will be increased;
     */
    public void credit(double amount) { 
        balance += amount;
    } 

    /**
     * returns holder, balance, and dateOpen separated by *. This method is called in each of the account
     * subclasses.
     * 
     * @return String representation of holder, balance, and dateOpen
     */@Override
    public String toString() {
         DecimalFormat decimalFormat = new DecimalFormat(",##0.00");
        return holder.toString() + "* $" + decimalFormat.format(balance) + "*" + dateOpen.toString();
    }    

    /**
     * Empty abstract method that is implemented in each of child classes to return monthly
     * interest on account.
     * 
     * @return monthly interest amount
     */
    public abstract double monthlyInterest();    

    /**
     * Empty abstract method that is implemented in each of child classes to return monthly 
     * fee.
     * 
     * @return monthly fee owed
     */
    public abstract double monthlyFee();   

    /**
     * Empty abstract method that is implemented in subclasses and returns 
     * appropriate strings.
     * 
     * @return string representation of account options for each account
     */
    public abstract String getSpecialString();  
    
    /**
     * returns a char representing the type of account. c = Checking
     * s = Savings, m = MoneyMarket
     * 
     * @return returns a char
     */
    public abstract char getAccountType();
    
    /**
     * This method withdraws the given amount from the account's balance.
     * @param amount the amount to be withdrawn.
     * 
     */
    public void withdraw(double amount) {
        balance -= amount;
    }
    
    /**
     * this method allows the balance to be reset.
     * @param newBalance the double the balance will be set to.
     */
    public void setBalance(double newBalance) {
        balance = newBalance;
    }
    
    
    /**
     * This method returns a string boolean for checking and savings, and a string integer for money market accounts.
     * @return either a boolean or integer in String form
     */
    public abstract String getSpecialStringValue();
    
}
