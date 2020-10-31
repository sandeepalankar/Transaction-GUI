/**
 * 
 */
package TMSPackage;

//Check if this import is okay!!!!!!!!!!!!!!!!!!!!!!!!!

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * This class manages the transactions that the user would like to perform on an account and
 * displays the appropriate results on the console. The user can perform different operations on
 * an account and for each operation, the run() method checks to see if the input is valid and
 * then calls the right method to perform said operation. The user will be asked for inputs until 
 * he/she enters "Q", which will end the transaction processing.
 * @author Graham Deubner, Sandeep Alankar
 */
public class TransactionManager {
    
    private static AccountDatabase db;
    private static Scanner scan;
    
    /**
     * This method continually scans for user input until the user enters "Q". The method takes in user input,
     * compares the first input of each command, and then calls the corresponding method an account in the 
     * database.
     */
    public void run() {
        db = new AccountDatabase();
        scan = new Scanner(System.in);

        while (true) {
            String[] input = scan.nextLine().split("\\s");
            if (input[0].equals("OC") || input[0].equals("OS") || input[0].equals("OM")) {
                open(input);
            } else if(input[0].equals("CC") || input[0].equals("CS") || input[0].equals("CM")){
                close(input);
            } else if(input[0].equals("DC") || input[0].equals("DS") || input[0].equals("DM")){
                deposit(input);
            }else if(input[0].equals("WC") || input[0].equals("WS") || input[0].equals("WM")){
                withdraw(input);
            }else if(input[0].equals("PA") || input[0].equals("PD") || input[0].equals("PN")){
                print(input);
            }else if(input[0].equals("Q")){
                quit();
                break;
            } else {
                System.out.println("Command " + input[0] + " not supported!");
            }
        }
        scan.close();
    }
    
    /**
     * Helper method which makes certain that the user gives the program at least the correct
     * number of inputs. It takes in the input already read as a String[], creates another String[] with
     * the correct size to fit the requested number of inputs, and asks the user for input until the 
     * array is filled.
     * @param input user provided input from the command line
     * @param requiredInputNum the number of parameters required
     * @return a String array containing the 3 required parameters forGroceryItem
     */
    private static String[] getInputs(String[] input, int requiredInputNum) {
        int inputTokenCounter = 0; // counter for parameters entered by user
        String[] commands = new String[requiredInputNum];
        while (inputTokenCounter < requiredInputNum) {
            for (String s : input) {
                commands[inputTokenCounter] = s;
                inputTokenCounter++;
            }
            if (inputTokenCounter < requiredInputNum)
                input = scan.nextLine().split("\\s");
        }
        return commands;
    }
    
    /**
     * This method creates a Date object from the given string.
     * @param date a String version of the date to be converted.
     * @return returns a Date object containing the converted date.
     * @throws Exception "[date] is not a valid date." thrown if the date cannot be converted.
     */
    private static Date createDate(String date) throws Exception{
        int numDateValues = 3; //for the 3 values that makes up a date 
        StringTokenizer tokenizer = new StringTokenizer(date, "/", false);
        if(tokenizer.countTokens() != numDateValues) {
            throw new Exception(date + " is not a valid date.");
        }
        int[] dateValues = new int[numDateValues];
        for(int i = 0; i < dateValues.length; i++) {
            try {
                dateValues[i] = Integer.parseInt(tokenizer.nextToken());
            } catch(Exception e) {
                throw new Exception(date + " is not a valid date.");
            }
        }
        return new Date(dateValues[0], dateValues[1], dateValues[2]);
    }
    
    /**
     * Converts a string value into a double.
     * @param strBalance takes a String containing the amount to be converted to a double
     * @return returns a double containing the amount
     * @throws Exception "Invalid amount entered." Thrown if given amount cannot be converted.
     */
    private static double convertAmount(String strBalance) throws Exception{
        double balance;
        try {
            balance = Double.parseDouble(strBalance);
        } catch (Exception e){
            throw new Exception("Invalid amount entered.");
        }
        return balance;
    }
    
    /**
     * This method takes a string and converts it into the corresponding boolean.
     * @param bool the string to be converted into a boolean
     * @return returns true of false
     * @throws Exception "Invalid input, expected boolean." Thrown if the string does not convert into a boolean
     */
    private static boolean convertBoolean(String bool) throws Exception {
        if(bool.equalsIgnoreCase("true"))///////////may need to change the "ignoreCase" part to be case sensitive
            return true;
        else if(bool.equalsIgnoreCase("false"))
            return false;
        else
            throw new Exception("Invalid input, expected boolean.");
    }
    
    /**
     * This helper method creates a specified account type with a specified owner's name,
     * a specified balance, a specified opening date. For checking 
     * and savings, an additional boolean is needed from the user to determine if
     * the account has direct deposit or is loyal, respectively. 
     * @param input a String[] containing user input
     * @return returns the newly created account
     */
    private static Account createAccount(String[] input) {
        Account newAccount;
        Profile profile;
        double balance;
        Date date;
        boolean specialValue;  //directDeposit for Checking class or isLoyal for Savings class
        profile = new Profile(input[1], input[2]);
        try {
            balance = convertAmount(input[3]);
            date = createDate(input[4]);
            if(!date.isValid()) {
                System.out.println(date.toString() + " is not a valid date.");
                return null;
            }
                
            if(input[0].equals("OM")) {
                newAccount =new MoneyMarket(profile, balance, date);
                return newAccount;
            }
            specialValue = convertBoolean(input[5]);
            if(input[0].equals("OC")) {
                newAccount = new Checking(profile, balance, date, specialValue);
            } else if(input[0].equals("OS")) {
                newAccount = new Savings(profile, balance, date, specialValue);
            } else   {
                System.out.println("Account type error.");
                return null;
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return newAccount;
    }
    
    /**
     * This helper method creates a profile object from the given input and wraps it in a given account type.
     * The purpose of this method is to give other methods a way of passing some account information, without
     * having to make a whole new account.
     * @param input a String[] containing the input from the user
     * @return returns an Account object, either of type Checking, Savings, or MoneyMarket
     */
    private static Account createWrapperAccount(String[] input) {
        Account newAccount = null;
        Profile profile = new Profile(input[1], input[2]);
        String accountType = input[0].substring(1, 2);
        switch (accountType) {
        case "C":
            newAccount = new Checking(profile, -1.0, null, false);
            break;
        case "S":
            newAccount = new Savings(profile, -1.0, null, false);
            break;
        case "M":
            newAccount = new MoneyMarket(profile, -1.0, null);
            break;
        }
        return newAccount;
    }
    
    /**
     * This method will receive an array of user input and creates a new account,
     * and adds it to the database.
     *  On success, a success message is printed. On failure, a failure
     * message is printed. 
     * @param input a String[] of the users input
     * 
     */
    private static void open(String[] input) {
        Account newAccount = null;
        int checkingSavingInputNum = 6;
        int MoneyMarkInputNum = 5;
        switch (input[0]) {
        case "OC":
            input = getInputs(input, checkingSavingInputNum);
            newAccount = createAccount(input);
            break;
        case "OS":
            input = getInputs(input, checkingSavingInputNum);
            newAccount = createAccount(input);
            break;
        case "OM":
            input = getInputs(input, MoneyMarkInputNum);
            newAccount = createAccount(input);
            break;
        }
        if(newAccount != null) {
            if(db.add(newAccount))
                System.out.println("Account opened and added to the database.");
            else
                System.out.println("Account is already in the database.");
        }
    }

    /**
     * This method will receive an array of user input and then removes a given account
     * from the database. On success, a success message is printed. On failure, a failure
     * message is printed. 
     * @param input a String[] of the users input
     * 
     */
    private static void close(String[] input) {
        int closeInputNum = 3;
        input = getInputs(input, closeInputNum);
        if(db.remove(createWrapperAccount(input)))
            System.out.println("Account removed from the database.");
        else
        System.out.println("Account not found in database.");
    }

    /**
     * This method will receive an array of user input and then deposit a given
     * amount into a given account. on success, a success message is printed. On 
     * failure due to the account not existing, an error message is printed.
     * @param input a String[] of the users input
     * 
     */
    private static void deposit(String[] input) {
        double amount = -1;
        Account newAccount;
        int depositInputNum = 4;
        input = getInputs(input, depositInputNum);
        newAccount = createWrapperAccount(input);
        try {
            amount = convertAmount(input[3]);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }
        if(db.deposit(newAccount, amount))
            System.out.println(amount + " deposited into account.");
        else
            System.out.println("Account does not exist.");
    }
    
    /**
     * This method will receive an array of user input and withdraw a given sum 
     * from a given account. On success, the a success message is printed to standard output.
     * If the account does not exist or there are insufficient funds for the withdrawal, 
     * an error message is printed. 
     * @param input a String[] of the users input
     * 
     */
    private static void withdraw(String[] input) {
        double amount = -1;
        Account newAccount;
        int withdrawInputNum = 4;
        input = getInputs(input, withdrawInputNum);
        newAccount = createWrapperAccount(input);
        try {
            amount = convertAmount(input[3]);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return;
        }     
        int result = db.withdrawal(newAccount, amount);
        if( result == 0) //success
            System.out.println(amount + " withdrawn from account.");
        else if(result == -1)
            System.out.println("Account does not exist.");
        else
            System.out.println("Insufficient funds");
    }
    
    /**
     * This method will print the contents of the account, based on the input parameter.
     * "PA" will print a list of the accounts in the database
     * "PD" will print account statements ordered by the date they were opened.
     * "PN" will print account statements ordered by the last name of the account holder. 
     * @param input either "PA", "PD", or "PN", dictating the type of print that should occur. 
     * 
     */
    private static void print(String[] input) {
        switch (input[0]){
        case "PA":
            db.printAccounts();
            break;
        case "PD":
            db.printByDateOpen();
            break;
        case "PN":
            db.printByLastName();
            break;
        }
    }
    
    /**
     * Method for quitting the program. 
     */
    private static void quit() {
        System.out.println("Transaction processing complete.");
    }  
}
