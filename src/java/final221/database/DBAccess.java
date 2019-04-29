package final221.database;

import final221.database.CustomerBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

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
            
            in = ClassLoader.getSystemResourceAsStream("db.properties");
            
            resources = new PropertyResourceBundle(in);
            
            in.close();
            
            driver = resources.getString("jdbc.driver");
            url = resources.getString("jdbc.url");
            System.out.println(url);
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
            connection = DriverManager.getConnection(url,user,password);  
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit( 1 );
        } // end catch
    } // end DBAccessClass constructor
     public int addPerson( 
      String fname, String lname, String email, String num )
   {
      int result = 0;
      
      // set parameters, then execute insertNewPerson
      try 
      {
         insertNewPerson.setString( 1, fname );
         insertNewPerson.setString( 2, lname );
         insertNewPerson.setString( 3, email );
         insertNewPerson.setString( 4, num );

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
               resultSet.getString( "newFirst" ),
               resultSet.getString( "newLast" ),
               resultSet.getString( "newPhone" ),
               resultSet.getString( "newEmail" ) ) );
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
               resultSet.getString( "newFirst" ),
               resultSet.getString( "newLast" ),
               resultSet.getString( "newPhone" ),
               resultSet.getString( "newEmail" ) ) );
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
   
        
    public List< ProductBean > getAllProduct()
   {
      List< ProductBean > results = null;
      ResultSet resultSet = null;
      
      try 
      {
         // executeQuery returns ResultSet containing matching entries
         resultSet = selectAllPeople.executeQuery(); 
         results = new ArrayList<  ProductBean  >();
         
         while ( resultSet.next() )
         {
               results.add( new ProductBean(
               resultSet.getString( "name" ),
               resultSet.getDouble( "price" ),
               resultSet.getInt( "Inv" )));

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

