package final221.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * CMPSC 221 Final Project
 * DBAccessClass.java
 * Purpose: This class handles connecting to the DB and closing that connection.
 * 
 * @author Nicholas Hutton
 * @version 1.0 4/30/2019
 */
public class DBAccessClass {
    
    private static final String URL = "jdbc:derby://localhost:1527/SalaryTable";
    private static final String USERNAME = "app";
    private static final String PASSWORD = "app";
    private Connection connection = null; // manages connection

//    private PreparedStatement selectPeopleByLastName = null; 
//    private PreparedStatement insertNewPerson = null; 
    
    public DBAccessClass()
    {
        try {
            
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            // create query that selects entries with a specific last name
//            selectPeopleByLastName = connection.prepareStatement("SELECT * FROM saltbl WHERE LastName = ?");
         
            // create insert that adds a new entry into the database
//            insertNewPerson = connection.prepareStatement( 
//                "INSERT INTO SALTBL " + 
//                "(FirstName, LastName, YearlySal, MonthlySal) " + 
//                "VALUES (?, ?, ?, ?)");
        } // end try
        catch ( SQLException sqlException )
        {
            sqlException.printStackTrace();
            System.exit( 1 );
        } // end catch
    } // end PersonQueries constructor
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
    /**
    * Attempts to add a person to the database. This code is heavily modified from an earlier lab.
    * 
    * @param fName A first name
    * @param lName A last name
    * @param ySal A yearly salary
    * @param mSal A monthly salary
    * @return An int whose 3 states tell the main function how this attempt went. 1 == success, 2 == database issue, 3 == dupe entry
    */
//    public int addPerson(String fName, String lName, double ySal, double mSal) {
//        // result will be used in the main function to determine if the entry was successful
//        int result = 0;
//        // we make a resultSet to check if an entry is a dupe
//        ResultSet resultSet = null;
//        // attempt to do the entry
//        try {
//            selectPeopleByLastName.setString( 1, lName ); // specify last name
//
//            // executeQuery returns ResultSet containing matching entries
//            resultSet = selectPeopleByLastName.executeQuery(); 
//            // if this function returns true then there is a matching entry in the table
//            if(resultSet.next()) {
//                // -1 == Duplicate entry in table
//                result = -1;
//            } else {
//                // set parameters, then execute insertNewPerson
//                try {
//                    insertNewPerson.setString( 1, fName );
//                    insertNewPerson.setString( 2, lName );
//                    insertNewPerson.setDouble( 3, ySal );
//                    insertNewPerson.setDouble( 4, mSal );
//
//                 // insert the new entry; returns # of rows updated
//                    result = insertNewPerson.executeUpdate(); 
//                } // end try
//                catch ( SQLException sqlException ) { 
//                    sqlException.printStackTrace();
//                    close();
//                } // end catch
//            }
//        }
//        catch ( SQLException sqlException ) {
//            sqlException.printStackTrace();
//        } // end catch
//        finally {
//            try {
//                resultSet.close();
//            } // end try
//            catch ( SQLException sqlException ) {
//                sqlException.printStackTrace();         
//                close();
//            } // end catch
//        } // end finally
//
//        return result;
//    } // end method addPerson

}
