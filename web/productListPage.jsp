<%-- 
    Document   : productListPage
    Created on : Apr 29, 2019, 4:08:50 PM
    Author     : Nicholas Hutton
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
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
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
        <p>Please enter your contact information:</p>
        <form action="contactList" method="post">
            <input type="hidden" name="action" value="purchase">        
            <label class="pad_top">First Name:</label>
            <input type="text" name="firstName" required><br>
            <label class="pad_top">Last Name:</label>
            <input type="text" name="lastName" required><br>
            <label class="pad_top">Email:</label>
            <input type="email" name="email" required><br>
            <label class="pad_top">Phone Number:</label>
            <input type="text" name="phoneNumber" required><br>
            <input type="hidden" name="action" value="purchase">
            <label class="pad_top">Product ID:</label> 
            <input type="number" min="1" name="prodID" required><br>
            <label class="pad_top">Quantity:</label>
            <input type="number" min="1" name="quantity" required><br>
            <label>&nbsp;</label>
            <input type="submit" value="Submit" action="purchase">
        </form>
    </body>
</html>
