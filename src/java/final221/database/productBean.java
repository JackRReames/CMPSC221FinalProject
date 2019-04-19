package final221.database;

/**
 * CMPSC 221 Final Program
 * productBean.java
 * Purpose: Contains attributes and get/set methods associated with a product.
 * 
 * @author Nicholas Hutton
 * @version 1.0 4/30/2019
 */
public class productBean {
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
    public productBean() {
        prodName = null;
        prodPrice = 0;
        prodInventory = 0;
    }
    
    public productBean(String name, double price, int inv) {
        prodName = name;
        prodPrice = price;
        prodInventory = inv;
    }
    
    
    public void setProdName(String name) {
        prodName = name;
    }
    public void setProdPrice(double price) {
        prodPrice = price;
    }
    public void setProdInventory(int inv) {
        prodInventory = inv;
    }
    
    
    public String getProdName() {
        return prodName;
    }
    public double getProdPrice() {
        return prodPrice;
    }
    public int getProdInventory() {
        return prodInventory;
    }
}
