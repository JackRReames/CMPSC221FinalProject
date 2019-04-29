<%-- 
    Document   : productListPage
    Created on : Apr 29, 2019, 4:08:50 PM
    Author     : Nicholas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome!</title>
    </head>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse
        }
    </style>
    <body>
        <h1>Welcome to Dunder Mifflin Altoona's Web Store</h1>
        <table style="width:100%">
            <tr>
                <th>Product Name</th>
                <th>Product ID</th>
                <th>Price</th>
            </tr>
            <%
            
            
                while(resultSet.next()) {
            %>
            <tr>
                <td> <%= resultSet.getString(2) %> </td>
                <td> <%= resultSet.getString(3) %> </td>
                <td> <%= resultSet.getString(1) %> </td>
            </tr>
            <%
               }
            %>    
        </table>
        <form action="contactList" method ="post">
            Product ID: 
            <input type="number" name="prodID">
            Quantity:
            <input type="number" name="quantity">
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
