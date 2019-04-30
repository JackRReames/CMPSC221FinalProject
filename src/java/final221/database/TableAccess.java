
package final221.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * CMPSC 221 Final Project
 * TableAccess.java
 * Purpose: Contains methods for accessing the tables within the database
 * 
 * @author Jack R. Reames
 */
public class TableAccess {
    private String url;
    private String user;
    private String password;
    private Connection con;
    private PreparedStatement insert;
    private PreparedStatement delete;
    private PreparedStatement select;
    private ResultSet resultSet;
    private int orderCurrentID;
    private int customerCurrentID;
    private int productCurrentID;
    
    public TableAccess () {
        try {
            ResourceBundle properties;
            InputStream inStrm;
            
            inStrm = getClass().getResourceAsStream("db.properties");
            properties = new PropertyResourceBundle(inStrm);
            inStrm.close();
            
            url = properties.getString("jdbc.url");
            user = properties.getString("jdbc.user");
            password = properties.getString("jdbc.password");
        }
        catch(Exception e) {
            System.out.println("Couldn't load resources:\t" + e);
            System.exit(-1);
        }
        
        orderCurrentID = orderMaxID() + 1;
        customerCurrentID = customerMaxID() + 1;
        productCurrentID = productMaxID() + 1;
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="Methods for Orders Table">
    
    /**
     * Finds the last used ID number in Orders
     * @return - an integer, the ID
     */
    public int orderMaxID() {
        int id = 0;
        
        try {
            con = DriverManager.getConnection(url, user, password);
            
            select = con.prepareStatement("SELECT * FROM ORDERS");
            
            resultSet = select.executeQuery();
        } catch(SQLException e) {
            e.getMessage();
            System.out.println(e);
            System.exit(1);
        }
        
        try {
            while(resultSet.next()) {
                id = resultSet.getInt("ID");
            }
        } catch (SQLException e) {
            e.getMessage();
            System.out.println(e);
            System.exit(1);
        } finally {
            try {
                select.close();
                con.close();
                resultSet.close();
            } catch (SQLException e) {
                e.getMessage();
                System.out.println(e);
                System.exit(1);
            }
        }
        
        return id;
    }
    
    /**
     * Searches the Orders table by id
     * @param id - the ID of the order being searched for
     * @return - an OrderBean object containing the order in question
     */
    public OrderBean orderSearch(int id) {
        OrderBean order = null;
        int prodID, custID;
        
        try {
            con = DriverManager.getConnection(url, user, password);
            
            select = con.prepareStatement("SELECT * FROM ORDERS");
            
            resultSet = select.executeQuery();
        } catch(SQLException e) {
            e.getMessage();
            System.out.println(e);
            System.exit(1);
        } finally {
            try {
                select.close();
                con.close();
            } catch(SQLException e) {
                e.getMessage();
                System.out.println(e);
                System.exit(1);
            }
        }
        
        try {
            while(resultSet.next()) {
                custID = resultSet.getInt("CUSTID");
                prodID = resultSet.getInt("PRODID");
                
                if (resultSet.getInt("ID") == id) {
                    order = new OrderBean(
                            resultSet.getInt("ID"),
                            resultSet.getLong("ORDERTIME"),
                            customerSearch(custID),
                            productSearch(prodID),
                            resultSet.getInt("AMOUNT"));
                }
            }
        } catch (SQLException e) {
            e.getMessage();
            System.out.println(e);
            System.exit(1);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.getMessage();
                System.out.println(e);
                System.exit(1);
            }
        }
        
        return order;
    }
    
    /**
     * Searches the Orders table by a person's last name, and a product name
     * @param lName - the last name of a customer
     * @param prodName - the name of a product
     * @return - an OrderBean object
     */
    public OrderBean orderSearch(String lName, String prodName) {
        OrderBean order = null;
        int custID, prodID;
        try {
            con = DriverManager.getConnection(url, user, password);
            
            select = con.prepareStatement("SELECT * FROM ORDERS");
            
            resultSet = select.executeQuery();
        } catch(SQLException e) {
            e.getMessage();
            System.out.println(e);
            System.exit(1);
        } finally {
            try {
                select.close();
                con.close();
            } catch(SQLException e) {
                e.getMessage();
                System.out.println(e);
                System.exit(1);
            }
        }
        
        try {
            while(resultSet.next()) {
                custID = resultSet.getInt("CUSTID");
                prodID = resultSet.getInt("PRODID");
                
                if (custID == customerIDSearch(lName) && prodID == productIDSearch(prodName)) {
                    order = new OrderBean(
                            resultSet.getInt("ID"),
                            resultSet.getLong("ORDERTIME"),
                            customerSearch(custID),
                            productSearch(prodID),
                            resultSet.getInt("AMOUNT"));
                }
            }
        } catch (SQLException e) {
            e.getMessage();
            System.out.println(e);
            System.exit(1);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.getMessage();
                System.out.println(e);
                System.exit(1);
            }
        }
        
        return order;
    }
    
    /**
     * Inserts a new order into the table
     * @param order - the order to be inserted
     * @return - a boolean indicating success or failure
     */
    public boolean orderInsert(OrderBean order) {
        if (orderSearch(order.getID()) != null) {
            return false;
        }
        
        int id = orderCurrentID;
        long time = order.getTime();
        int amount = order.getAmount();
        int prodID = order.getProduct().getID();
        int custID = order.getCustomer().getID();
        
        try {
            con = DriverManager.getConnection(url, user, password);
            
            insert = con.prepareStatement("INSERT INTO ORDERS (ID, ORDERTIME, AMOUNT, PRODID, CUSTID)" +
                    "VALUES (?, ?, ?, ?, ?)");
            
            insert.setInt(1, id);
            insert.setLong(2, time);
            insert.setInt(3, amount);
            insert.setInt(4, prodID);
            insert.setInt(5, custID);
            
            insert.execute();
            
            orderCurrentID++;
            return true;
        } catch(SQLException e) {
            e.getMessage();
            System.out.println(e);
            return false;
        } finally {
            try {
                insert.close();
                con.close();
            } catch(SQLException e) {
                e.getMessage();
                System.out.println(e);
                System.exit(1);
            }
        }
    }
    
    /**
     * Deletes an order from the table
     * @param order - the order to be deleted
     * @return - a boolean indicating success or failure
     */
    public boolean orderDelete(OrderBean order) {
        if (orderSearch(order.getID()) == null) {
            return false;
        }
        
        int id = order.getID();
        
        try {
            con = DriverManager.getConnection(url, user, password);
            
            delete = con.prepareStatement("DELETE FROM ORDERS" +
                    "WHERE ID=?");
            
            delete.setInt(1, id);
            
            delete.execute();
            
            return true;
        } catch(SQLException e) {
            e.getMessage();
            System.out.println(e);
            return false;
        } finally {
            try {
                delete.close();
                con.close();
            } catch(SQLException e) {
                e.getMessage();
                System.out.println(e);
                System.exit(1);
            }
        }
    }
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Methods for Customer Table">
    
    /**
     * Finds the last user ID in Customers
     * @return - an integer, the ID
     */
    public int customerMaxID() {
        int id = 0;
        
        try {
            con = DriverManager.getConnection(url, user, password);
            
            select = con.prepareStatement("SELECT * FROM CUSTOMERS");
            
            resultSet = select.executeQuery();
        } catch(SQLException e) {
            e.getMessage();
            System.exit(1);
        }
        
        try {
            while(resultSet.next()) {
                id = resultSet.getInt("ID");
            }
        } catch (SQLException e) {
            e.getMessage();
            System.exit(1);
        } finally {
            try {
                resultSet.close();
                select.close();
                con.close();
            } catch (SQLException e) {
                e.getMessage();
                System.exit(1);
            }
        }
        
        return id;
    }
    
    /**
     * Searches the Customer table by Customer ID
     * @param id - the ID of the customer
     * @return - a CustomerBean object containing the customer's information
     */
    public CustomerBean customerSearch(int id) {
        CustomerBean customer = null;
        
        try {
            con = DriverManager.getConnection(url, user, password);
            
            select = con.prepareStatement("SELECT * FROM CUSTOMERS");
            
            resultSet = select.executeQuery();
        } catch(SQLException e) {
            e.getMessage();
            System.exit(1);
        }
        
        try {
            while(resultSet.next()) {
                if (resultSet.getInt("ID") == id) {
                    customer = new CustomerBean(
                            resultSet.getInt("ID"),
                            resultSet.getString("FNAME"),
                            resultSet.getString("LNAME"),
                            resultSet.getString("PHONE"),
                            resultSet.getString("EMAIL"));
                }
            }
        } catch (SQLException e) {
            e.getMessage();
            System.exit(1);
        } finally {
            try {
                resultSet.close();
                select.close();
                con.close();
            } catch (SQLException e) {
                e.getMessage();
                System.exit(1);
            }
        }
        
        return customer;
    }
    
    /**
     * Searches the Customer table by Last Name
     * @param lName - the Last Name of the customer
     * @return - the ID of the customer
     */
    public int customerIDSearch(String lName) {
        int id = 0;
        
        try {
            con = DriverManager.getConnection(url, user, password);
            
            select = con.prepareStatement("SELECT * FROM CUSTOMERS");
            
            resultSet = select.executeQuery();
        } catch(SQLException e) {
            e.getMessage();
            System.exit(1);
        }
        
        try {
            while(resultSet.next()) {
                if (resultSet.getString("LNAME").equals(lName)) {
                    id = resultSet.getInt("ID");
                }
            }
        } catch (SQLException e) {
            e.getMessage();
            System.exit(1);
        } finally {
            try {
                resultSet.close();
                select.close();
                con.close();
            } catch (SQLException e) {
                e.getMessage();
                System.exit(1);
            }
        }
        
        return id;
    }
    
    /**
     * Searches the Customer table by Last Name
     * @param lName - the LAst Name of the customer
     * @return - the ID of the customer
     */
    public CustomerBean customerSearch(String lName) {
        CustomerBean customer = null;
        
        try {
            con = DriverManager.getConnection(url, user, password);
            
            select = con.prepareStatement("SELECT * FROM CUSTOMERS");
            
            resultSet = select.executeQuery();
        } catch(SQLException e) {
            e.getMessage();
            System.exit(1);
        }
        
        try {
            while(resultSet.next()) {
                if (resultSet.getString("LNAME").equals(lName)) {
                    customer = new CustomerBean(
                            resultSet.getInt("ID"),
                            resultSet.getString("FNAME"),
                            resultSet.getString("LNAME"),
                            resultSet.getString("PHONE"),
                            resultSet.getString("EMAIL"));
                }
            }
        } catch (SQLException e) {
            e.getMessage();
            System.exit(1);
        } finally {
            try {
                resultSet.close();
                select.close();
                con.close();
            } catch (SQLException e) {
                e.getMessage();
                System.exit(1);
            }
        }
        
        return customer;
    }
    
    /**
     * Inserts a new customer into the Customers table
     * @param customer - a CustomerBean object to be inserted
     * @return - a boolean indicating success or failure
     */
    public boolean customerInsert(CustomerBean customer) {
        if (customerSearch(customer.getID()) != null) {
            return false;
        }
        
        int id = customerCurrentID;
        String lName = customer.getLastName();
        String fName = customer.getFirstName();
        String phone = customer.getPhoneNumber();
        String email = customer.getEmail();
        
        try {
            con = DriverManager.getConnection(url, user, password);
            
            insert = con.prepareStatement("INSERT INTO CUSTOMERS (ID, LNAME, FNAME, PHONE, EMAIL)" +
                                          "VALUES (?, ?, ?, ?, ?)");
            
            insert.setInt(1, id);
            insert.setString(2, lName);
            insert.setString(3, fName);
            insert.setString(4, phone);
            insert.setString(5, email);
            
            insert.execute();
            
            customerCurrentID++;
            return true;
        } catch(SQLException e){
            e.getMessage();
            return false;
        } finally{
            try{
                insert.close();
                con.close();
            } catch(SQLException e){
                e.getMessage();
                System.exit(-1);
            }
        }
    }
    
    /**
     * Deletes a customer from the Customers table
     * @param customer - a CustomerBean object to be removed
     * @return - a boolean indicating success or failure
     */
    public boolean customerDelete(CustomerBean customer) {
        if (customerSearch(customer.getID()) == null) {
            return false;
        }
        
        int id = customer.getID();
        
        try {
            con = DriverManager.getConnection(url, user, password);
            
            delete = con.prepareStatement("DELETE FROM CUSTOMERS" +
                    "WHERE ID=?");
            
            delete.setInt(1, id);
            
            delete.execute();
            
            return true;
        } catch(SQLException e) {
            e.getMessage();
            return false;
        } finally {
            try {
                delete.close();
                con.close();
            } catch(SQLException e) {
                e.getMessage();
                System.exit(-1);
            }
        }
    }
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Methods for Product Table">
    
    /**
     * Finds the last ID in the Products table
     * @return - an integer, the ID
     */
    public int productMaxID() {
        int id = 0;
        
        try {
            con = DriverManager.getConnection(url, user, password);
            
            select = con.prepareStatement("SELECT * FROM PRODUCTS");
            
            resultSet = select.executeQuery();
        } catch(SQLException e) {
            e.getMessage();
            System.exit(1);
        }
        
        try {
            while(resultSet.next()) {
                id = resultSet.getInt("ID");
            }
        } catch (SQLException e) {
            e.getMessage();
            System.exit(1);
        } finally {
            try {
                resultSet.close();
                select.close();
                con.close();
            } catch (SQLException e) {
                e.getMessage();
                System.exit(1);
            }
        }
        
        return id + 1;
    }
    
    /**
     * Searches the Products table by Product ID
     * @param id - the ID of the Product
     * @return - a ProductBean containing the product's information
     */
    public ProductBean productSearch(int id) {
        ProductBean product = null;
        
        try {
            con = DriverManager.getConnection(url, user, password);
            
            select = con.prepareStatement("SELECT * FROM PRODUCTS");
            
            resultSet = select.executeQuery();
        } catch(SQLException e) {
            e.getMessage();
            System.exit(1);
        }
        
        try {
            while(resultSet.next()) {
                if (resultSet.getInt("ID") == id) {
                    product = new ProductBean(
                            resultSet.getInt("ID"),
                            resultSet.getString("PRODNAME"),
                            resultSet.getDouble("PRICE"),
                            resultSet.getInt("INVENTORY"));
                }
            }
        } catch (SQLException e) {
            e.getMessage();
            System.exit(1);
        } finally {
            try {
                resultSet.close();
                select.close();
                con.close();
            } catch (SQLException e) {
                e.getMessage();
                System.exit(1);
            }
        }
        
        return product;
    }
    
    /**
     * Searches the Products table by Product Name
     * @param prodName - the Name of the Product
     * @return - an integer, the ID of the product
     */
    public int productIDSearch(String prodName) {
        int id = 0;
        
        try {
            con = DriverManager.getConnection(url, user, password);
            
            select = con.prepareStatement("SELECT * FROM PRODUCTS");
            
            resultSet = select.executeQuery();
        } catch(SQLException e) {
            e.getMessage();
            System.exit(1);
        }
        
        try {
            while(resultSet.next()) {
                if (resultSet.getString("PRODNAME").equals(prodName)) {
                    id = resultSet.getInt("ID");
                }
            }
        } catch (SQLException e) {
            e.getMessage();
            System.exit(1);
        } finally {
            try {
                resultSet.close();
                select.close();
                con.close();
            } catch (SQLException e) {
                e.getMessage();
                System.exit(1);
            }
        }
        
        return id;
    }
    
    /**
     * Searches the Products table by Product Name
     * @param prodName - the Name of the Product
     * @return - a ProductBean object containing the product's information
     */
    public ProductBean productSearch(String prodName) {
        ProductBean product = null;
        
        try {
            con = DriverManager.getConnection(url, user, password);
            
            select = con.prepareStatement("SELECT * FROM PRODUCTS");
            
            resultSet = select.executeQuery();
        } catch(SQLException e) {
            e.getMessage();
            System.exit(1);
        }
        
        try {
            while(resultSet.next()) {
                if (resultSet.getString("PRODNAME").equals(prodName)) {
                    product = new ProductBean(
                            resultSet.getInt("ID"),
                            resultSet.getString("PRODNAME"),
                            resultSet.getDouble("PRICE"),
                            resultSet.getInt("INVENTORY"));
                }
            }
        } catch (SQLException e) {
            e.getMessage();
            System.exit(1);
        } finally {
            try {
                resultSet.close();
                select.close();
                con.close();
            } catch (SQLException e) {
                e.getMessage();
                System.exit(1);
            }
        }
        
        return product;
    }
    
    /**
     * Returns a list of all products
     * @return - an ArrayList, containing every product
     */
    public ArrayList<ProductBean> getProductList() {
        ArrayList<ProductBean> results = new ArrayList();
        
        try {
            con = DriverManager.getConnection(url, user, password);
            
            select = con.prepareStatement("SELECT * FROM PRODUCTS");
            
            resultSet = select.executeQuery();
        } catch(SQLException e) {
            e.getMessage();
            System.out.println(e);
            return null;
        }
        
        try {
            while(resultSet.next()) {
                results.add(new ProductBean(
                    resultSet.getInt("ID"),
                    resultSet.getString("PRODNAME"),
                    resultSet.getDouble("PRICE"),
                    resultSet.getInt("INVENTORY")));
            }
        } catch (SQLException e) {
            e.getMessage();
            System.out.println(e);
            System.exit(1);
        } finally {
            try {
                resultSet.close();
                select.close();
                con.close();
            } catch(SQLException e) {
                e.getMessage();
                System.out.println(e);
                System.exit(1);
            }
        }
        
        return results;
    }
    
    /**
     * Inserts a new Product into the Products table
     * @param product - the product to be added
     * @return - a boolean indicating success or failure
     */
    public boolean productInsert(ProductBean product) {
        if (productSearch(product.getID()) != null) {
            return false;
        }
        
        int id = productCurrentID;
        String prodName = product.getProdName();
        double price = product.getProdPrice();
        int inventory = product.getProdInventory();
        
        try {
            con = DriverManager.getConnection(url, user, password);
            
            insert = con.prepareStatement("INSERT INTO PRODUCTS (ID, PRODNAME, PRICE, INVENTORY)" +
                                          "VALUES (?, ?, ?, ?)");
            
            insert.setInt(1, id);
            insert.setString(2, prodName);
            insert.setDouble(3, price);
            insert.setInt(4, inventory);
            
            insert.execute();
            
            productCurrentID++;
            return true;
        } catch(SQLException e) {
            e.getMessage();
            return false;
        } finally {
            try {
                insert.close();
                con.close();
            } catch(SQLException e) {
                e.getMessage();
                System.exit(-1);
            }
        }
    }
    
    /**
     * Deletes a Product from the Products table
     * @param product - the product to be deleted
     * @return - a boolean indicating success or failure
     */
    public boolean productDelete(ProductBean product) {
        if (productSearch(product.getID()) == null) {
            return false;
        }
        
        int id = product.getID();
        
        try {
            con = DriverManager.getConnection(url, user, password);
            
            delete = con.prepareStatement("DELETE FROM PRODUCTS" +
                    "WHERE ID=?");
            
            delete.setInt(1, id);
            
            delete.execute();
            
            return true;
        } catch(SQLException e) {
            e.getMessage();
            return false;
        } finally {
            try {
                delete.close();
                con.close();
            } catch(SQLException e) {
                e.getMessage();
                System.exit(-1);
            }
        }
    }
    
    /**
     * Reduces the amount of a product in inventory by the given amount
     * @param amountSold - the amount sold
     * @param id - the ID of the Product
     * @return - a boolean indicating success or failure
     */
    public boolean productSale(int amountSold, int id) {
        ProductBean prod = productSearch(id);
        
        if (prod == null) {
            return false;
        }
        
        try {
            con = DriverManager.getConnection(url, user, password);
            
            insert = con.prepareStatement("UPDATE PRODUCTS" +
                    "SET AMOUNT=?" +
                    "WHERE ID=?");
            
            insert.setInt(1, prod.getProdInventory() - amountSold);
            insert.setInt(2, id);
            
            insert.execute();
            
            return true;
        } catch(SQLException e) {
            e.getMessage();
            return false;
        } finally {
            try {
                insert.close();
                con.close();
            } catch(SQLException e) {
                e.getMessage();
                System.exit(-1);
            }
        }
    }
    
    //</editor-fold>
}
