<%-- 
    Document   : register.jsp
    Created on : Sep 28, 2014, 6:29:51 PM
    Author     : Administrator
--%>

<%@page import="uk.ac.dundee.computing.aec.mapStax.stores.LoggedIn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>mapStax</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
    </head>
    <body>
        <header>
            <h1>mapStax ! </h1>
            <h2>Your mind mapping world</h2>
        </header>
        <article>
            <h3>Register as user</h3>
            <form method="POST" action="Register">            
                <ul>                   
                    <%
                        if (request.getAttribute("registerErrorMessage") != null) {
                    %>
                    <font color ="red"><%out.println(request.getAttribute("registerErrorMessage"));%></font> 
                    <%}%>
                    <li>First name <input type="text" name="name"></li>
                    <li>Surname <input type="text" name="surname"></li>
                    <li>Email <input type="email" name="email"></li>
                    <li>User Name <input type="text" name="username"></li>                    
                    <li>Password <input type="password" name="password"></li>
                    <li>Repeat password <input type="password" name="passwordConfirm"></li>                    
                </ul>
                <input type="submit" value="Submit"> 
            </form>

        </article>
        <footer>
            <ul>
                <li class="footer"><a href="/mapStax/index.jsp">Home</a></li>
            </ul>
        </footer>
    </body>
</html>
