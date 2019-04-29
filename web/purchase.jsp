<%-- 
    Document   : newjsp
    Created on : Mar 28, 2019, 9:35:35 AM
    Author     : tzjsl
--%>

<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="final221.database.DBAccessClass"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Purchase page</title>
    </head>
    <body>
        <h1>product information:</h1>
        
        <%
            String driver = null;
            String url = null;
            String user = null;
            String password = null;
            DBAccessClass product = new DBAccessClass();
            product.DBAccessClass();
            
            try
            {
                Connection con = DriverManager.getConnection(url,user,password);  
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCTS ORDER BY ID");

                ResultSetMetaData rsmd = rs.getMetaData();

                int numberOfColumns = rsmd.getColumnCount();

                while (rs.next())
                {
                for (int i = 1; i <= numberOfColumns; i++)
                    {
                    System.out.print(rs.getString(i) + " ");
                    }


                System.out.println("");
               
                }

            stmt.close();

            con.close();
        }
        catch (Exception e) 
        {
            System.out.println(e);
        }



        %>
        
        <form action="contactList" method="post">
            <input type="hidden" name="action" value="purchase">
            <input type="submit" value="Submit">
        </form>


    </body>
</html>

