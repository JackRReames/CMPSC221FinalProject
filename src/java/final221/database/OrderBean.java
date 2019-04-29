
package final221.database;

/**
 * CMPSC 221 Final Project
 * OrderBean.java
 * Purpose: Contains attributes and get/set methods associated with an order. 
 * 
 * @author Jack R. Reames
 * @version 1.0 4/30/2019
 */
public class OrderBean {
    private int id;                 //The ID number of the order
    private long time;              //The time the order was placed
    private CustomerBean customer;  //The customer of placing the order
    private ProductBean product;    //The product being ordered
    private int amount;             //The amount of the product that was ordered
    
    /**
     * Constructor for OrderBean
     * 
     * @param id - the ID number of the order
     * @param time - the time 
     * @param customer - a CustomerBean
     * @param product - a ProductBean
     * @param amount - the amount of product purchased
     */
    public OrderBean (int id, long time, CustomerBean customer, ProductBean product, int amount) {
        this.id = id;
        this.time = time;
        this.customer = customer;
        this.product = product;
        this.amount = amount;
    }
    
    /**
     * Default constructor
     */
    public OrderBean() {
        this(0, 0, null, null, 0);
    }
    
    /**
     * Setter method for ID
     * 
     * @param id - the ID to be assigned
     */
    public void setID(int id) {
        this.id = id;
    }
    
    /**
     * Setter method for time
     * 
     * @param time - the time to be assigned
     */
    public void setTime(long time) {
        this.time = time;
    }
    
    /**
     * Setter method for customer
     * 
     * @param customer - the customer to be assigned
     */
    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }
    
    /**
     * Setter method for product
     * 
     * @param product - the product to be assigned
     */
    public void setProduct(ProductBean product) {
        this.product = product;
    }
    
    /**
     * Setter method for amount
     * 
     * @param amount - the amount to be assigned
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    /**
     * Getter method for ID
     * 
     * @return - the ID
     */
    public int getID() {
        return id;
    }
    
    /**
     * Getter method for time
     * 
     * @return - the time
     */
    public long getTime() {
        return time;
    }
    
    /**
     * Getter method for customer
     * 
     * @return - the customer
     */
    public CustomerBean getCustomer() {
        return customer;
    }
    
    /**
     * Getter method for product
     * 
     * @return - the product
     */
    public ProductBean getProduct() {
        return product;
    }
    
    /**
     * Getter method for amount
     * 
     * @return - the amount
     */
    public int getAmount() {
        return amount;
    }
}
