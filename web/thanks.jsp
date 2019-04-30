<%-- 
    Document   : thanks
    Created on : Apr 29, 2019, 4:50:51 PM
    Author     : Nicholas Hutton
--%>
<%@page contentType="text/html" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Thanks!</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>    
</head>

<body>
    <h1>Thanks for shopping with us</h1>

    <p>Here is the information that you entered:</p>

    <label>First Name:</label>
    <span>${user.firstName}</span><br>
    <label>Last Name:</label>
    <span>${user.lastName}</span><br>
    <label>Email:</label>
    <span>${user.email}</span><br>
    <label>Phone Number:</label>
    <span>${user.phoneNumber}</span><br><br>
    <label>Quantity Purchased:</label>
    <span>${number}</span><br>

    <p>Click return to return to purchase page</p>

    <form action="contactList" method="get">
        <input type="hidden" name="action" value="buy">
        <input type="submit" value="Return">
    </form>

</body>
</html>