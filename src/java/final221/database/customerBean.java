package final221.database;

/**
 * CMPSC 221 Final Program
 * customerBean.java
 * Purpose: Contains attributes and get/set methods associated with a customer.
 * 
 * @author Nicholas Hutton
 * @version 1.0 4/30/2019
 */
public class customerBean {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    /**
     * Default constructor for this bean
     */
    public customerBean() {
        firstName = null;
        lastName = null;
        phoneNumber = null;
        email = null;
    }
    /**
     * Regular constructor for this bean
     * @param newFirst A first name
     * @param newLast A last name
     * @param newPhone A phone #
     * @param newEmail An Email address
     */
    public customerBean(String newFirst, String newLast, String newPhone, String newEmail) {
        firstName = newFirst;
        lastName = newLast;
        phoneNumber = newPhone; //who dis??
        email = newEmail;
    }
    
    // set methods
    /**
     * Assigns a first name to the object
     * @param newFirst A first name
     */
    public void setFirstName(String newFirst) {
        firstName = newFirst;
    }
    /**
     * Assigns a last name to the object
     * @param newLast A last name
     */
    public void setLastName(String newLast) {
        lastName = newLast;
    }
    /**
     * Assigns a phone number to the object
     * @param newPhone A phone number
     */
    public void setPhoneNumber(String newPhone) {
        phoneNumber = newPhone;
    }
    /**
     * Assigns an Email address to the object
     * @param newEmail An Email address
     */
    public void setEmail(String newEmail) {
        email = newEmail;
    }
    
    // get methods
    /**
     * Retrieves a first name
     * @return A String containing a first name
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Retrieves a last name
     * @return A String containing a last name
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Retrieves a phone number
     * @return A String containing a phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * Retrieves an Email address
     * @return A String containing an Email address
     */
    public String getEmail() {
        return email;
    }
    
}
