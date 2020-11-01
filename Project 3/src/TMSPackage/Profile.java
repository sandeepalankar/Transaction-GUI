/**
 * 
 */
package TMSPackage;

/**
 * This class defines the basic features of a Profile and includes a toString method to print the 
 * first and last name of a holder as well as an equals method that returns true if two holders
 * are the same.
 * @author Sandeep Alankar, Graham Deubner
 */
public class Profile {

	private String fname;
	private String lname;

	/**
	 * Parameterized constructor which assigns fname and lname the names for the profile
	 * @param fname the first name for the profile
	 * @param lname the last name for the profile
	 * 
	 */
	public Profile(String fname, String lname) {
		this.fname = fname;
		this.lname = lname;
	}

	/**
	 * Method that returns the the last name for the Profile
	 * 
	 * @return the last name in a String
	 */
	public String getLName() {
		return lname;
	}

	/**
	 * Method that returns the the first name for the Profile
	 * 
	 * @return the first name in a String
	 */
	public String getFName() {
		return fname;
	}

	/**
	 * toString method that returns the first and last name of a holder separated by a space
	 * 
	 * @return String representation of first name, space, last name
	 */
    @Override
    public String toString() {
        return fname + " " + lname;
    }

    /**
     * This method checks if the first and last name of the holder is the same as
     * the passed in Profile object.
     * 
     * @param prof this parameter is contains the profile object that this profile
     *             object will be compared to
     * @return true if two Profile first and last names are the same, false otherwise.
     */
    //@Override
    public boolean equals(Profile prof) {
        if (fname.equals(prof.getFName()) && lname.equals(prof.getLName()))
            return true;
        return false;
    }
}
