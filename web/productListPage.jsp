<%-- 
    Document   : productListPage
    Created on : Apr 29, 2019, 4:08:50 PM
    Author     : Nicholas
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="final221.database.TableAccess"%>
<%@page import="final221.database.ProductBean"%>
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
        <h1>Welcome to Dundear Mifflin Altoona's Web Store</h1>
        <table style="width:100%">
            <tr>
                <th>Product Name</th>
                <th>Product ID</th>
                <th>Price</th>
            </tr>
            <%
                TableAccess DBAccess = new TableAccess();
                ArrayList<ProductBean> dbData = DBAccess.getProductList();
                for (int i = 0; i < dbData.size(); i++) {
            %>
            <tr>
                <td> <%= dbData.get(i).getProdName() %> </td>
                <td> <%= dbData.get(i).getID() %> </td>
                <td> <%= dbData.get(i).getProdPrice() %> </td>
            </tr>
            <%
               }
            %>    
        </table>
        <form action="contactList" method ="post">
            <input type="hidden" name="action" value="purchase">
            Product ID: 
            <input type="number" min="1" name="prodID" required>
            Quantity:
            <input type="number" min="1" name="quantity" required>
            <input type="submit" value="Submit" action="purchase">
        </form>
    </body>
</html>
