/**
 * 
 */
package TMSPackage;

import java.text.DecimalFormat;

/**
 * This class represents a date, used to record when an account was opened.
 * The format of the date is mm/dd/yyyy.
 * It implements the comparable interface so that date objects may be compared,
 * and an isVald method that decides if the date object contains a possible date.
 * @author Graham Deubner, Sandeep Alankar
 */
public class Date implements Comparable<Date>{

    private int year;
    private int month;
    private int day;
    
    /**
     * parameterized constructor method which assigns the year, month, and day to the passed values.
     * @param year the year of the new date object
     * @param month the month of the new date object
     * @param day the day of the new year object
     * 
     */
    public Date(int month, int day, int year) {
        this.year = year;
        this.month = month;
        this.day = day;
    } 
    
    /**
     *Method which compares this Date object object to the Date Object passed as a parameter.
     *Implements the Comparable interface.
     *@param date the date object which this date object will be compared to
     *@return returns 0 if equivalent, 1 if this Date comes after the passed Date Object, 
     *and -1 if this Date Object comes before the passed Date object.
     */
    public int compareTo(Date date) {
        if (this.year > date.year)
            return 1;
        else if (this.year < date.year)
            return -1;
        else {
            if (this.month > date.month)
                return 1;
            else if (this.month < date.month)
                return -1;
            else {
                if (this.day > date.day)
                    return 1;
                else if (this.day < date.day)
                    return -1;
                else
                    return 0;
            }
        }

    }
    
    /**
     *Method returns the date held by this object in string format: mm/dd/yyyy
     *
     *@return returns the string literal of the date
     */
    public String toString() {
        DecimalFormat df2char = new DecimalFormat("00");
        DecimalFormat df4char = new DecimalFormat("00");
        String str = df2char.format(month) + "/" + 
                df2char.format(day) + "/" + df4char.format(year);
        return str;
    }

    /**
     * method checks to see the objects day, month and year can be a real date.
     * 
     * @return returns true if the date object contains a real date, false otherwise.
     */
    public boolean isValid() {
        int[] daysInEachMonth = {Month.JAN, Month.FEB, Month.MAR, Month.APR, Month.MAY, 
                Month.JUN, Month.JUL, Month.AUG, Month.SEP, Month.OCT, Month.NOV, Month.DEC};
        if(day < 1 || month < 1 || year < 1)
            return false;
        if(month>daysInEachMonth.length)
            return false;
        if (month == Month.FEBRUARY) {// deals with leap years
            int febDays = Month.FEB;
            if (year % Month.QUADRENNIAL == 0) {
                if (year % Month.CENTENNIAL == 0) {
                    if (year % Month.QUADRACENTENNIAL == 0) {
                        febDays++; //it's a leap year
                    }

                } else {
                    febDays++;// it's a leap year
                }
            }
            if (day > febDays)
                return false;
        } else if (day > daysInEachMonth[month-1])
            return false;
        return true;
    }    
}
