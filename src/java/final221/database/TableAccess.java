
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
        
        orderCurrentID = orderMaxID();
        customerCurrentID = customerMaxID();
        productCurrentID = productMaxID();
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="Methods for Orders Table">
    
    private int orderMaxID() {
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
    
    private OrderBean orderSearch(int id) {
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
    
    public boolean orderInsert(OrderBean order) {
        if (orderSearch(order.getID()) == null) {
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
    
    private int customerMaxID() {
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
    
    private CustomerBean customerSearch(int id) {
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
    
    private int customerIDSearch(String lName) {
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
    
    public boolean customerInsert(CustomerBean customer) {
        if (customerSearch(customer.getID()) == null) {
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
    
    private int productMaxID() {
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
        
        return id;
    }
    
    private ProductBean productSearch(int id) {
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
    
    private int productIDSearch(String prodName) {
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
    
    public boolean productInsert(ProductBean product) {
        if (productSearch(product.getID()) == null) {
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
    
    public boolean productDelete(ProductBean product) {
        if (customerSearch(product.getID()) == null) {
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
    
    //</editor-fold>
}
