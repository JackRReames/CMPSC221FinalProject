package final221.database;

/**
 * CMPSC 221 Final Program
 * ProductBean.java
 * Purpose: Contains attributes and get/set methods associated with a product.
 * 
 * @author Nicholas Hutton
 * @version 1.0 4/30/2019
 */
public class ProductBean implements java.io.Serializable {
    // declare attributes
    // prduct name
    private String prodName;
    // product price
    private double prodPrice;
    // number of product in inventory
    private int prodInventory;
    
    /**
     * Default constructor for this bean
     */
    public ProductBean() {
        prodName = null;
        prodPrice = 0;
        prodInventory = 0;
    }
    
    /**
     * Regular constructor for this bean
     * @param name A product name
     * @param price A product price
     * @param inv An amount of a product in the inventory
     */
    public ProductBean(String name, double price, int inv) {
        prodName = name;
        prodPrice = price;
        prodInventory = inv;
    }
    
    // set methods
    /**
     * Assigns the product name
     * @param name A product name
     */
    public void setProdName(String name) {
        prodName = name;
    }
    /**
     * Assigns the product's price
     * @param price A product price
     */
    public void setProdPrice(double price) {
        prodPrice = price;
    }
    /**
     * Assigns the amount of the product in the inventory
     * @param inv An amount of the product in the inventory
     */
    public void setProdInventory(int inv) {
        prodInventory = inv;
    }
    
    // get methods
    /**
     * Retrieves the product name
     * @return A String containing the product name
     */
    public String getProdName() {
        return prodName;
    }
    /**
     * Retrieves the product price
     * @return A double containing the product price
     */
    public double getProdPrice() {
        return prodPrice;
    }
    /**
     * Retrieves the amount of the product in the inventory
     * @return An int containing the amount of the product in the inventory
     */
    public int getProdInventory() {
        return prodInventory;
    }
}
