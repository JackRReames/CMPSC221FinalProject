package final221.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.*;
import java.util.*;

/**
 * CMPSC 221 Final Project
 * DBAccessClass.java
 * Purpose: This class handles connecting to the DB and closing that connection.
 * 
 * @author Nicholas Hutton
 * @version 1.0 4/30/2019
 */
public class DBAccessClass {
    // declare our connection object
    private Connection connection = null; 
    /**
     * Pulls information from a db.properties file and then uses it to connect to the database
     */
    public DBAccessClass()
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
