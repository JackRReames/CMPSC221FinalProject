package final221.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.List;
import java.util.ArrayList;

/**
 * CMPSC 221 Final Program
 * DBAccessClass.java
 * Purpose: This class handles connecting to the DB and closing that connection.
 * 
 * @author Nicholas Hutton
 * @version 1.0 4/30/2019
 */
public class DBAccess {
    // declare our connection object
    private Connection connection = null; 
   private PreparedStatement selectAllPeople = null; 
   private PreparedStatement selectPeopleByLastName = null; 
   private PreparedStatement insertNewPerson = null; 
   private PreparedStatement deletePerson = null; 
   private Connection connection2 = null; 
     private PreparedStatement selectAllProducts = null; 
   private PreparedStatement selectProductsByName = null; 
   private PreparedStatement insertNewProducts = null; 
   private PreparedStatement deleteProducts = null; 
    /**
     * Pulls information from a db.properties file and then uses it to connect to the database
     */
    public DBAccess()
    {
        // variables to hold the required db connection info
        String driver = null;
        String url = null;
        String user = null;
        String password = null;
        // attempt to grab all of the above info from the db.properties file
        try {
            ResourceBundle resources;
            InputStream in = null;
            ResourceBundle newResources;
            
            in = getClass().getResourceAsStream("db.properties");
            
            resources = new PropertyResourceBundle(in);
            
            in.close();
            
            //driver = resources.getString("jdbc.driver");
            url = resources.getString("jdbc.url");
            //System.out.println(url);
            user = resources.getString("jdbc.user");
            password = resources.getString("jdbc.password");
   
        } // end try
        catch (Exception exp) {
            System.out.println("Couldn't load resources." + exp);
            System.exit(-1);
        } // end catch
        // attempt to load the driver
        try {
            Class.forName(driver);
        }
        catch (Exception e) {
            System.out.println("Failed to load driver.");
            return;
        }
        // attempt to connect to the database
        try {
            connection = DriverManager.getConnection(url, user, password);  
            
                   selectAllPeople = 
            connection.prepareStatement( "SELECT * FROM CUSTOMERS" );
             
                selectPeopleByLastName = connection.prepareStatement( 
            "SELECT * FROM APP.CUSTOMERS WHERE LNAME = ?" );
                
                   insertNewPerson = connection.prepareStatement( 
            "INSERT INTO APP.CUSTOMERS " + 
            "( LNAME,FNAME,PHONE, EMAIL ) " + 
            "VALUES ( ?, ?, ?, ? )" );
                    deletePerson = connection.prepareStatement(
                "DELETE FROM APP.CUSTOMERS WHERE ID = ?");
                    
                    connection2 = DriverManager.getConnection(url,user,password);  
            
                   selectAllProducts = 
            connection2.prepareStatement( "SELECT * FROM PRODUCTS" );
             
                selectProductsByName = connection.prepareStatement( 
            "SELECT * FROM APP.PRODUCTS WHERE PRODNAME = ?" );
                
                   insertNewProducts = connection.prepareStatement( 
            "INSERT INTO APP.PRODUCTS " + 
            "( PRODNAME,PRICE,INVENTORY ) " + 
            "VALUES ( ?, ?, ? )" );
                    deleteProducts = connection.prepareStatement(
                "DELETE FROM APP.PRODUCTS WHERE ID = ?");
                    
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit( 1 );
        } // end catch
    } // end DBAccessClass constructor
   
         public int deletePerson(  String addrID ) {
       int result = 0;
       
       try {
           deletePerson.setString(1, addrID);
           
           result = deletePerson.executeUpdate();
       }
       catch (SQLException sqlException) {
           sqlException.printStackTrace();
           close();
       } // end catch
       
       return result;
   }
    
    public int addPerson( String lname, String fname, String num, String email )
    {
      int result = 0;
      
      // set parameters, then execute insertNewPerson
      try 
      {
         insertNewPerson.setString( 1, lname );
         insertNewPerson.setString( 2, fname );
         insertNewPerson.setString( 3, num);
         insertNewPerson.setString( 4, email );
     

         // insert the new entry; returns # of rows updated
         result = insertNewPerson.executeUpdate(); 
      } // end try
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();
         close();
      } // end catch
      
      return result;
   } // end method addPerson
     
        public List< CustomerBean > getAllCustomer()
   {
      List< CustomerBean > results = null;
      ResultSet resultSet = null;
      
      try 
      {
         // executeQuery returns ResultSet containing matching entries
         resultSet = selectAllPeople.executeQuery(); 
         results = new ArrayList<  CustomerBean  >();
         
         while ( resultSet.next() )
         {
            results.add( new CustomerBean (
                     resultSet.getInt( "ID" ),
               resultSet.getString( "LNAME" ),
               resultSet.getString( "FNAME" ),
               resultSet.getString( "PHONE" ),
               resultSet.getString( "EMAIL" ) 
             ) 
            );
         } // end while
      } // end try
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();         
      } // end catch
      finally
      {
         try 
         {
            resultSet.close();
         } // end try
         catch ( SQLException sqlException )
         {
            sqlException.printStackTrace();         
            close();
         } // end catch
      } // end finally
      
      return results;
   } // end method getAllPeople
           public int deleteProduct(  String addrID ) {
       int result = 0;
       
       try {
           deleteProducts.setString(1, addrID);
           
           result = deleteProducts.executeUpdate();
       }
       catch (SQLException sqlException) {
           sqlException.printStackTrace();
           close();
       } // end catch
       
       return result;
   }
           
               
        public List< CustomerBean > getPeopleByLastName( String name )
   {
      List< CustomerBean> results = null;
      ResultSet resultSet = null;

      try 
      {
         selectPeopleByLastName.setString( 1, name ); // specify last name

         // executeQuery returns ResultSet containing matching entries
         resultSet = selectPeopleByLastName.executeQuery(); 
		 resultSet.next();
         results = new ArrayList< CustomerBean >();

         while ( resultSet.next() )
         {
            results.add( new CustomerBean( 
                     resultSet.getInt( "ID" ),
               resultSet.getString( "LNAME" ),
               resultSet.getString( "FNAME" ),
               resultSet.getString( "PHONE" ),
               resultSet.getString( "EMAIL" ) ) );
         } // end while
      } // end try
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();
      } // end catch
      finally
      {
         try 
         {
            resultSet.close();
         } // end try
         catch ( SQLException sqlException )
         {
            sqlException.printStackTrace();         
            close();
         } // end catch
      } // end finally
      
      return results;
   } // end method getPeopleByName
        
          public List< ProductBean > getAllProducts()
   {
      List< ProductBean > results = null;
      ResultSet resultSet = null;
      
      try 
      {
         // executeQuery returns ResultSet containing matching entries
         resultSet = selectAllProducts.executeQuery(); 
         results = new ArrayList<  ProductBean  >();
         
         while ( resultSet.next() )
         {
            results.add( new ProductBean (
                     resultSet.getInt( "ID" ),
               resultSet.getString( "PRODNAME" ),
               resultSet.getDouble( "PRICE" ),
               resultSet.getInt( "INVENTORY" )
             ) 
            );
         } // end while
      } // end try
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();         
      } // end catch
      finally
      {
         try 
         {
            resultSet.close();
         } // end try
         catch ( SQLException sqlException )
         {
            sqlException.printStackTrace();         
            close();
         } // end catch
      } // end finally
      
      return results;
   } // end method getAllPeople
   
    /**
    * Attempts to close the connection to the database
    */
    public void close() {
        try {
            connection.close();
        } // end try
        catch ( SQLException sqlException ) {
            sqlException.printStackTrace();
        } // end catch
    } // end method close
}