<%-- 
    Document   : profile
    Created on : 14-Oct-2014, 17:56:31
    Author     : plamendenev
--%>

<%@page import="java.util.*"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile</title>
    </head>
    <body>
        <h1>mapStax!</h1>
        <%
            LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
            if (lg.getlogedin()) {
        %>
        <ul>
            <li><a href="/mapStax">Home</a></li>    
            <li><a href="/mapStax/Images/<%=lg.getUser().getUsername()%>">Images</a></li>
        </ul>
        <h2>Your profile</h2>
        <img src="/mapStax/Thumb/<%=lg.getUser().getProfilePic()%>">

        <%
            if (request.getAttribute("updateSuccess") != null) {
        %>
        <font color ="green"><%out.println(request.getAttribute("updateSuccess"));%></font> 
        <%}%>

        <h3>First name: <%=lg.getUser().getName()%></h3>
        <h3>Surname: <%=lg.getUser().getSurname()%></h3>
        <h3>Username: <%=lg.getUser().getUsername()%></h3>
        <h3>Email: <%=lg.getUser().getEmail()%></h3>        

        <a href="/mapStax/profileUpdate.jsp">Update details</a>   

        <%} else
                response.sendRedirect("/mapStax/login.jsp");
        %>

    </body>
</html>
