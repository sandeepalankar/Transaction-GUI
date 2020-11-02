package TMSPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.StringTokenizer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * This class is the controller for the Transaction Manager GUI. It manages the
 * interactions between the front end and the back end of the program.
 * 
 * @author Graham Deubner, Sandeep Alankar
 *
 */
public class MainController {

    private static AccountDatabase database = null;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField strBalance;

    @FXML
    private RadioButton btnSavings;

    @FXML
    private RadioButton btnChecking;

    @FXML
    private RadioButton btnMoneyMarket;

    @FXML
    private CheckBox loyalCustomer;

    @FXML
    private CheckBox directDeposit;

    @FXML
    private Button openAccount;

    @FXML
    private Button closeAccount;

    @FXML
    private TextField date;

    @FXML
    private RadioButton depositeRadioButton;

    @FXML
    private ToggleGroup depositeWithdrawToggleGroup;

    @FXML
    private RadioButton withdrawRadioButton;

    @FXML
    private RadioButton checkingDepositeWithdrawRadioButton;

    @FXML
    private ToggleGroup accountTypeDepositeWithdrawToggleGroup;

    @FXML
    private RadioButton savingsDepositeWithdrawRadioButton;

    @FXML
    private RadioButton moneyMarketDepositeWithdrawRadioButton;

    @FXML
    private TextField fnameDepositeWithdrawTextField;

    @FXML
    private TextField lnameDepositeWithdrawTextField;

    @FXML
    private TextField sumDepositeWithdrawTextField;

    @FXML
    private Button depositeWithdrawButton;

    @FXML
    private RadioButton noOrderRadioButton;

    @FXML
    private ToggleGroup displayToggleGroup;

    @FXML
    private RadioButton dateOpenedRadioButton;

    @FXML
    private RadioButton lastNameRadioButton;

    @FXML
    private Button displayAccountsButton;

    @FXML
    private RadioButton importRadioButton;

    @FXML
    private ToggleGroup importExportToggleGroup;

    @FXML
    private RadioButton exportRadioButton;

    @FXML
    private TextField filePathTextField;

    @FXML
    private Button browseButton;

    @FXML
    private Button importExportButton;

    @FXML
    private TextArea textArea;

    @FXML
    private Button clearButton;

    /**
     * This method handles all the button requirements for the Savings, Checking,
     * and MoneyMarket buttons.
     * 
     * @param event of any of the account type RadioButtons being clicked
     */
    @FXML
    private void handleButtons(ActionEvent event) {
        if (btnSavings.isSelected()) {
            loyalCustomer.setSelected(false);
            directDeposit.setSelected(false);
            loyalCustomer.setDisable(false);
            directDeposit.setDisable(true);
        } else if (btnChecking.isSelected()) {
            loyalCustomer.setSelected(false);
            directDeposit.setSelected(false);
            directDeposit.setDisable(false);
            loyalCustomer.setDisable(true);
        } else if (btnMoneyMarket.isSelected()) {
            loyalCustomer.setSelected(false);
            directDeposit.setSelected(false);
            directDeposit.setDisable(true);
            loyalCustomer.setDisable(true);
        }
    }

    /**
     * This method makes sure that only one of the check boxes can be selected at a
     * time.
     */
    @FXML
    private void handleLoyalCustomer() {
        if (loyalCustomer.isSelected()) {
            directDeposit.setSelected(false);
        }
    }

    /**
     * This method makes sure that only one of the checkboxes can be selected at a
     * time.
     */
    @FXML
    private void handleDirectDeposit() {
        if (directDeposit.isSelected()) {
            loyalCustomer.setSelected(false);
        }
    }

    /**
     * This method opens a new account given a String[] of inputs containing the
     * account type, holder's first and last name, starting account balance
     * 
     * @param event of openAccount button being clicked
     */
    @FXML
    private void openAccount(ActionEvent event) {
        atActionStart(textArea);
        String[] input = new String[6];
        if (btnSavings.isSelected()) {
            input[0] = "S";
        } else if (btnChecking.isSelected()) {
            input[0] = "C";
        } else if (btnMoneyMarket.isSelected()) {
            input[0] = "M";
        } else {
            textArea.appendText("Please select an account type.\n");
            return;
        }
        if (firstName.getText().equals("")) {
            textArea.appendText("Please enter a first name\n");
            return;
        }
        if (lastName.getText().equals("")) {
            textArea.appendText("Please enter a last name\n");
            return;
        }
        if (strBalance.getText().equals("")) {
            textArea.appendText("Please enter a balance\n");
            return;
        }
        if (date.getText().equals("")) {
            textArea.appendText("Please enter a date\n");
            return;
        }
        input[1] = firstName.getText();
        input[2] = lastName.getText();
        input[3] = strBalance.getText();
        input[4] = date.getText();
        if (input[0].equals("M")) {
            input[5] = "0";
        } else {
            if (loyalCustomer.isSelected() || directDeposit.isSelected()) {
                input[5] = "true";
            } else {
                input[5] = "false";
            }
        }
        Account newAccount = null;
        newAccount = createAccount(input, textArea);
        if (newAccount != null) {
            if (database == null) {
                database = new AccountDatabase();
            }
            if (database.add(newAccount))
                textArea.appendText("Account opened and added to the database.\n");
            else
                textArea.appendText("Account is already in the database.\n");
        }
    }

    /**
     * This method closes an account given a String[] of inputs containing the
     * account type, holder's first and last name, and account balance.
     * 
     * @param event of closeAccount button being clicked
     */
    @FXML
    private void closeAccount(ActionEvent event) {
        atActionStart(textArea);
        if (database == null) {
            textArea.appendText("The account database is empty\n");
            return;
        }
        String[] input = new String[3];
        if (btnSavings.isSelected()) {
            input[0] = "S";
        } else if (btnChecking.isSelected()) {
            input[0] = "C";
        } else if (btnMoneyMarket.isSelected()) {
            input[0] = "M";
        } else {
            textArea.appendText("Please select an account type.\n");
            return;
        }
        if (firstName.getText().equals("")) {
            textArea.appendText("Please enter a first name\n");
            return;
        }
        if (lastName.getText().equals("")) {
            textArea.appendText("Please enter a last name\n");
            return;
        }
        input[1] = firstName.getText();
        input[2] = lastName.getText();
        if (database.remove(createWrapperAccount(input)))
            textArea.appendText("Account removed from the database.\n");
        else
            textArea.appendText("Account not found in database.\n");
    }

    /**
     * This method opens the File Explorer and allows the user to select a text file
     * to import for testing of the program.
     * 
     * @param event of Browse button being clicked
     */
    @FXML
    void chooseFile(ActionEvent event) {
        if(!importRadioButton.isSelected() && !exportRadioButton.isSelected()) {
            atActionStart(textArea);
            textArea.appendText("Please select \"import\" or \"export\"\n");
            return;
        }
        Node source = (Node) event.getSource();
        Window theStage = source.getScene().getWindow();
        FileChooser chooser = new FileChooser();
        
        chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
                new ExtensionFilter("All Files", "*.*"));
        File file = new File(filePathTextField.getText());
        if (file.exists()) {
            file = new File(file.getParent());
            chooser.setInitialDirectory(file);
            chooser.setInitialFileName(filePathTextField.getText());
        }
        File sourceFile;
        if(importRadioButton.isSelected()) {
            chooser.setTitle("Select file to import");
            sourceFile = chooser.showOpenDialog(theStage);
        }else {
            chooser.setTitle("Select export file location");
            sourceFile = chooser.showSaveDialog(theStage);
        }
        try {
            filePathTextField.setText(sourceFile.toString());
        } catch (Exception e) {
            // exception caught. now I will do NOTHING!
        }
    }

    /**
     * Depending on whether the Import RadioButton is clicked or the Export
     * RadioButton is clicked, this method will either call the import or
     * exportDatabase methods.
     * 
     * @param event of Import/Export button being clicked
     */
    @FXML
    void importExportDatabase(ActionEvent event) {
        atActionStart(textArea);
        if (importRadioButton.isSelected()) {
            importDatabase(filePathTextField.getText(), textArea);
        } else if (exportRadioButton.isSelected())
            exportDatabase(filePathTextField.getText(), textArea);
        else
            textArea.appendText("Please select \"Import\" or \"Export\"\n");
    }

    /**
     * This method imports a text file filled with different account operations that
     * can be used to test the Transaction Manager GUI. The text file is appended to
     * the TextArea line by line and the file path is checked to make sure it is
     * valid.
     * 
     * @param path     to the chosen text file from File Explorer
     * @param textArea where the text will be appended
     */
    private static void importDatabase(String path, TextArea textArea) {
        File file = new File(path);
        if (!file.isFile() || !file.toString().endsWith(".txt")) {
            textArea.appendText("Invald file path\n");
            return;
        }
        Scanner scan = null;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            textArea.appendText("Unable to create Scanner Object\n");
            return;
        }
        if (database == null)
            database = new AccountDatabase();
        while (scan.hasNextLine()) {
            String[] input = new String[6];
            Scanner scan2 = new Scanner(scan.nextLine()).useDelimiter(",");
            for (int i = 0; i < 6; i++) {
                input[i] = scan2.next();
            }
            open(input, textArea);
        }
        scan.close();
    }

    /**
     * This method calls the createAccount method given an array of input Strings
     * that represent each of the necessary elements to open a new account. The
     * method also checks if the account in question is already in the account
     * database, in which case it is not opened again.
     * 
     * @param input    array of Strings representing first and last name, date
     *                 opened, balance, and account options
     * @param textArea where necessary output text will be appended
     */
    private static void open(String[] input, TextArea textArea) {
        Account newAccount = null;
        newAccount = createAccount(input, textArea);
        if (newAccount != null) {
            if (database.add(newAccount))
                textArea.appendText("Account opened and added to the database.\n");
            else
                textArea.appendText("Account is already in the database.\n");
        }
    }

    /**
     * This helper method creates a specified account type with a specified owner's
     * name, a specified balance, a specified opening date. For checking and
     * savings, an additional boolean is needed from the user to determine if the
     * account has direct deposit or is loyal, respectively.
     * 
     * @param input a String[] containing user input
     * @return returns the newly created account
     */
    private static Account createAccount(String[] input, TextArea textArea) {
        Account newAccount;
        Profile profile;
        double balance;
        Date date;
        boolean specialValue; // directDeposit for Checking class or isLoyal for Savings class
        profile = new Profile(input[1], input[2]);
        try {
            balance = convertAmount(input[3]);
            date = createDate(input[4]);
            if (!date.isValid()) {
                textArea.appendText(date.toString() + " is not a valid date.\n");
                return null;
            }
            if (input[0].equals("M")) {
                newAccount = new MoneyMarket(profile, balance, date, convertInteger(input[5]));
                return newAccount;
            }
            specialValue = convertBoolean(input[5]);
            if (input[0].equals("C")) {
                newAccount = new Checking(profile, balance, date, specialValue);
            } else if (input[0].equals("S")) {
                newAccount = new Savings(profile, balance, date, specialValue);
            } else {
                textArea.appendText("Account type error.\n");
                return null;
            }
        } catch (Exception e) {
            textArea.appendText(e.getMessage() + "\n");
            return null;
        }
        return newAccount;
    }

    /**
     * This method creates a Date object from the given string.
     * 
     * @param date a String version of the date to be converted.
     * @return returns a Date object containing the converted date.
     * @throws Exception "[date] is not a valid date." thrown if the date cannot be
     *                   converted.
     */
    private static Date createDate(String date) throws Exception {
        int numDateValues = 3; // for the 3 values that makes up a date
        StringTokenizer tokenizer = new StringTokenizer(date, "/", false);
        if (tokenizer.countTokens() != numDateValues) {
            throw new Exception(date + " is not a valid date.");
        }
        int[] dateValues = new int[numDateValues];
        for (int i = 0; i < dateValues.length; i++) {
            try {
                dateValues[i] = Integer.parseInt(tokenizer.nextToken());
            } catch (Exception e) {
                throw new Exception(date + " is not a valid date.");
            }
        }
        return new Date(dateValues[0], dateValues[1], dateValues[2]);
    }

    /**
     * This method converts a string value into a double.
     * 
     * @param strBalance takes a String containing the amount to be converted to a
     *                   double
     * @return returns a double containing the amount
     * @throws Exception "Invalid amount entered." Thrown if given amount cannot be
     *                   converted.
     */
    private static double convertAmount(String strBalance) throws Exception {
        double balance;
        try {
            balance = Double.parseDouble(strBalance);
        } catch (Exception e) {
            throw new Exception("Invalid amount entered.");
        }
        if (balance < 0)
            throw new Exception("Invalid amount entered.");

        return balance;
    }

    /**
     * This method takes a String and converts it into the corresponding int.
     * 
     * @param number of withdrawals
     * @return an int representing the number of withdrawals
     * @throws Exception "Invalid input for number of withdrawals." Thrown if string
     *                   cannot be converted to int.
     */
    private static int convertInteger(String integer) throws Exception {
        try {
            return Integer.parseInt(integer);
        } catch (Exception e) {
            throw new Exception("Invalid input for number of withdraws\n");
        }
    }

    /**
     * This method takes a string and converts it into the corresponding boolean.
     * 
     * @param bool the string to be converted into a boolean
     * @return returns true of false
     * @throws Exception "Invalid input, expected boolean." Thrown if the string
     *                   does not convert into a boolean
     */
    private static boolean convertBoolean(String bool) throws Exception {
        if (bool.equalsIgnoreCase("true"))
            return true;
        else if (bool.equalsIgnoreCase("false"))
            return false;
        else
            throw new Exception("Invalid input, expected boolean.");
    }

    /**
     * This method is used to export a file and checks if the specified file is a
     * .txt file. If the operation is successful, the necessary text is appended to
     * the TextArea and if not, the exception is caught and the exception message is
     * printed to the TextArea.
     * 
     * @param path to a text file in File Explorer
     * @param textArea where output text is appended
     */
    private static void exportDatabase(String path, TextArea textArea) {
        if (path.equals("")) {
            textArea.appendText("Invalid file path.\n");
            return;
        }
        
        if (database == null) {
            textArea.appendText("The database is empty\n");
            return;
        }

        if (!path.endsWith(".txt"))
            path = path + ".txt";
        try {
            database.exportDatabase(path);
            textArea.appendText("Exported Database.\n");
        } catch (Exception e) {
            textArea.appendText(e.getMessage());
        }
    }

    /**
     * This method displays the accounts currently in the account database and
     * depending on which RadioButton is selected, the accounts will be appended to
     * the TextArea in a different order.
     * 
     * @param event of Display Accounts button being clicked.
     */
    @FXML
    void displayDatabase(ActionEvent event) {
        atActionStart(textArea);
        if (!lastNameRadioButton.isSelected() && !dateOpenedRadioButton.isSelected()
                && !noOrderRadioButton.isSelected()) {
            textArea.appendText("Please select an ordering method.\n");
            return;
        }
        if (database == null) {
            textArea.appendText("The database is empty.\n");
            return;
        }
        if (noOrderRadioButton.isSelected())
            textArea.appendText(database.printAccounts());
        else if (dateOpenedRadioButton.isSelected())
            textArea.appendText(database.printByDateOpen());
        else if (lastNameRadioButton.isSelected())
            textArea.appendText(database.printByLastName());

    }

    /**
     * This method calls either the withdraw or deposit method depending on whether
     * the deposit or withdraw RadioButton is selected. It creates an array of
     * Strings that contains the account type, holder first and last name, and
     * amount to be deposited/withdrawn.
     * 
     * @param event of Deposit/Withdraw button being clicked
     */
    @FXML
    void depositeWithdrawSum(ActionEvent event) {
        atActionStart(textArea);
        if (database == null) {
            textArea.appendText("The account database is empty\n");
            return;
        }
        String[] input = new String[4];
        if (checkingDepositeWithdrawRadioButton.isSelected())
            input[0] = "C";
        else if (savingsDepositeWithdrawRadioButton.isSelected())
            input[0] = "S";
        else if (moneyMarketDepositeWithdrawRadioButton.isSelected())
            input[0] = "M";
        else {
            textArea.appendText("Please select an account type.\n");
            return;
        }
        if (fnameDepositeWithdrawTextField.getText().equals("")) {
            textArea.appendText("Please enter a first name\n");
            return;
        }
        if (lnameDepositeWithdrawTextField.getText().equals("")) {
            textArea.appendText("Please enter a last name\n");
            return;
        }
        input[1] = fnameDepositeWithdrawTextField.getText();
        input[2] = lnameDepositeWithdrawTextField.getText();
        input[3] = sumDepositeWithdrawTextField.getText();
        if (depositeRadioButton.isSelected()) {
            textArea.appendText(deposit(input));
        } else if (withdrawRadioButton.isSelected()) {
            textArea.appendText(withdraw(input));
        } else {
            textArea.appendText("Please select \"Deposite\" or \"Withdraw\"\n");
        }
    }

    /**
     * This method is called to deposit an amount into an account. The amount is
     * properly formatted, and the resultant account is return in String format.
     * 
     * @param input array containing account balance
     * @return appropriate String representing amount deposited into account, if
     *         possible
     */
    private static String deposit(String[] input) {
        DecimalFormat format = new DecimalFormat(",##0.00");
        double amount = -1;
        Account newAccount;
        newAccount = createWrapperAccount(input);
        try {
            amount = convertAmount(input[3]);
        } catch (Exception e) {
            return e.getMessage();
        }
        if (database.deposit(newAccount, amount))
            return "$" + format.format(amount) + " deposited into " + newAccount.getHolder().getFName() + " "
                    + newAccount.getHolder().getLName() + "'s account.\n";
        else
            return "Account does not exist.\n";
    }

    /**
     * This method is called to withdraw a certain amount from an account. It
     * formats the withdrawal amount, and checks if the operation is possible, then
     * prints the necessary text.
     * 
     * @param input that contains String representation of account balance
     * @return appropriate String representing amount withdrawn from account, if it
     *         was possible
     */
    private static String withdraw(String[] input) {
        DecimalFormat format = new DecimalFormat(",##0.00");
        double amount = -1;
        Account newAccount;
        newAccount = createWrapperAccount(input);
        try {
            amount = convertAmount(input[3]);
        } catch (Exception e) {
            return e.getMessage();
        }
        int result = database.withdrawal(newAccount, amount);
        if (result == 0) // success
            return "$" + format.format(amount) + " withdrawn from" + newAccount.getHolder().getFName() + " "
                    + newAccount.getHolder().getLName() + "'s account.";
        else if (result == -1)
            return "Account does not exist.\n";
        else
            return "Insufficient funds\n";
    }

    /**
     * This helper method creates a profile object from the given input and wraps it
     * in a given account type. The purpose of this method is to give other methods
     * a way of passing some account information, without having to make a whole new
     * account.
     * 
     * @param input a String[] containing the input from the user
     * @return returns an Account object, either of type Checking, Savings, or
     *         MoneyMarket
     */
    private static Account createWrapperAccount(String[] input) {
        Account newAccount = null;
        Profile profile = new Profile(input[1], input[2]);
        String accountType = input[0].substring(0, 1);
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
     * This method adds a new line to the TextArea if it is empty at the time.
     * 
     * @param textArea where new line is added
     */
    private static void atActionStart(TextArea textArea) {
        if (!textArea.getText().equals(""))
            textArea.appendText("\n");
    }

    /**
     * This method clears all of the text in the TextArea.
     * 
     * @param event of Clear button being clicked
     */
    @FXML
    void clearTextArea(ActionEvent event) {
        textArea.setText("");
    }
}
