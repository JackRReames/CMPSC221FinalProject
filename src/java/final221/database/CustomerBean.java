
package final221.database;

/**
 * CMPSC 221 Final Program
 * CustomerBean.java
 * Purpose: Contains attributes and get/set methods associated with a customer.
 * 
 * @author Nicholas Hutton
 * @author Jack R. Reames
 * @version 1.0 4/30/2019
 */
public class CustomerBean implements java.io.Serializable {
    private String firstName;
    // customer's last name
    private String lastName;
    // customer's phone number
    private String phoneNumber;
    // customer's email address
    private String email;
    // customer id number
    private int id;
    
    /**
     * Default constructor for this bean
     */
    public CustomerBean() {
        id = 0;
        firstName = null;
        lastName = null;
        phoneNumber = null;
        email = null;
    }
    
    /**
     * Regular constructor for this bean
     * @param id
     * @param newFirst A first name
     * @param newLast A last name
     * @param newPhone A phone #
     * @param newEmail An Email address
     */
    public CustomerBean(int id, String newFirst, String newLast, String newPhone, String newEmail) {
        this.id = id;
        firstName = newFirst;
        lastName = newLast;
        phoneNumber = newPhone; //who dis??
        email = newEmail;
        id = 0;
    }
    
    // set methods
    
    /**
     * Assigns an ID to the object
     * 
     * @param id - the new ID number
     */
    public void setID(int id) {
        this.id = id;
    }
    
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
    
    public int getID() {
        return id;
    }
    
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
