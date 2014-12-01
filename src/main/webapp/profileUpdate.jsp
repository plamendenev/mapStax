<%-- 
    Document   : profileUpdate
    Created on : 27-Oct-2014, 12:04:08
    Author     : plamendenev
--%>

<%@page import="mapstax.stores.LoggedIn"%>
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
            <h2>Your world in Black and White</h2>
        </header>
        <article>
            <h3>Update your details</h3>
            <form method="POST" action="ProfileUpdate">            
                <ul>
                    <%
                        if (request.getAttribute("updateSuccess") != null) {
                    %>
                    <font color ="green"><%out.println(request.getAttribute("updateSuccess"));%></font> 
                    <%}%>
                    <%
                        if (request.getAttribute("updateErrorMessage") != null) {
                    %>
                    <font color ="red"><%out.println(request.getAttribute("updateErrorMessage"));%></font> 
                    <%}%>
                    <li>First name <input type="text" name="name"></li>
                    <li>Surname <input type="text" name="surname"></li>
                    <li>Email <input type="email" name="email"></li>                   
                    <li>Password <input type="password" name="password"></li>
                    <li>Repeat password <input type="password" name="passwordConfirm"></li>                    
                </ul>
                <input type="submit" value="Update"> 
            </form>

        </article>
        <footer>
            <ul>
                <li class="footer"><a href="/mapStax/index.jsp">Home</a></li>
            </ul>
        </footer>
    </body>
</html>
