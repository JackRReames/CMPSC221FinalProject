/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final221.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Nicholas
 */
public class NewClass {
    public static void main( String[] args ) {
        TableAccess DBAccess = new TableAccess();
        ResultSet resultSet = DBAccess.getProductList();
        try {
            while(resultSet.next()) {
                System.out.print(resultSet.getString(2));
            }
        }
        catch(SQLException e) {
            e.getMessage();
            System.exit(1);
        }
        
    }
}
