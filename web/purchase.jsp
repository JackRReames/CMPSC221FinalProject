<%-- 
    Document   : newjsp
    Created on : Mar 28, 2019, 9:35:35 AM
    Author     : tzjsl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Purchase page</title>
    </head>
    <body>
        <h1>product information:</h1>
        
        <label>Product Name:</label>
        <span>${name}</span>&nbsp;
        <label>Price:</label>
        <span>${price}</span>&nbsp;
        <label>Inv:</label>
        <span>${Inv}</span>&nbsp;

        
        
        <form action="contactList" method="post">
            <input type="hidden" name="action" value="purchase">
            <input type="submit" value="Submit">
        </form>


    </body>
</html>

