<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Murach's Java Servlets and JSP</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>    
</head>

<body>
    <h1>Thanks for shpping with us</h1>

    <p>Here is the information that you entered:</p>

    <label>First Name:</label>
    <span>${customerBean.firstName}</span><br>
    <label>Last Name:</label>
    <span>${customerBean.lastName}</span><br>
    <label>Email:</label>
    <span>${customerBean.email}</span><br>
    <label>PhoneNumber:</label>
    <span>${customerBean.phoneNumber}</span><br>

    <p>Click return to return to purchase page</p>

    <form action="contactList" method="get">
        <input type="hidden" name="action" value="join">
        <input type="submit" value="Return">
    </form>

</body>
</html>