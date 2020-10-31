/**
 * 
 */
package TMSPackage;

/**
 * This class defines a savings account and its unique features.
 * It implements the abstract methods from Account, adding the savings account
 * options along with its monthly fee and interest rate. * 
 * @author Sandeep Alankar, Graham Deubner
 */
public class Savings extends Account{
    private boolean isLoyal;

    /**
     * Parameterized constructor that calls the constructor from the superclass
     * and adds a fourth parameter, isLoyal, which is unique to a savings account.     
     * @param holder name of person owning account
     * @param balance  money amount in account
     * @param dateOpen  date that account was opened
     * @param isLoyal boolean that is true if customer is loyal, false otherwise
     * 
     */
    public Savings(Profile holder, double balance, Date dateOpen, boolean isLoyal) {
        super(holder, balance, dateOpen);
        this.isLoyal = isLoyal;
    }

    /**
     * This method returns the monthly interest amount, which is different depending on whether
     * or not the customer is loyal or not.
     * 
     * @return monthly interest amount, monthly interest rate times account balance
     */
    @Override
    public double monthlyInterest() {
        double monthInterest = 0.0025; //annual interest rate of 0.25%
        double isLoyalInterest = 0.0035; //loyal customer interest rate of 0.35%
        if (isLoyal) {
            return isLoyalInterest/Month.TOTALMONTHS * super.getBalance();
        }
        else {
            return monthInterest/Month.TOTALMONTHS * super.getBalance();
        }
    }

    /**
     * This method returns the monthly fee, which depends on the current balance in the 
     * account.
     * 
     * @return monthly fee that holder pays
     */
    @Override
    public double monthlyFee() {
        if (super.getBalance() >= 300) {
            return 0;
        }
        else {
            return 5;
        }       
    }
    
    /**
     * toString that calls superclass toString method and adds on account type and special savings feature
     * if necessary.
     * 
     * @return String representation of account type, superclass toString, and special savings
     */
    @Override
    public String toString() {
        return "*Savings*" + super.toString() + getSpecialString();
    }    
    
    /**
     * This method checks if the customer is loyal, and if so, marks the acocunt as special savings.
     * 
     * @return special savings account if the holder is loyal, empty string otherwise
     */
    @Override
    public String getSpecialString() {
        if (isLoyal) {
            return "*special Savings account*";
        }
        else {
            return "";
        }
    }
    
    /**
     * returns 's' indicating this Account is a savings account
     * 
     * @return returns the char 's'
     */
    @Override
    public char getAccountType() {
        return 'S';
    }    
    
    @Override
    public String getSpecialStringValue() {
        return String.valueOf(isLoyal);
    }
    
}
