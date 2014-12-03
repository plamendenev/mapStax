<%-- 
    Document   : index
    Created on : Sep 28, 2014, 7:01:44 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="mapstax.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>mapStax</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <header>
            <h1>mapStax !</h1>
            <h2>Your mind mapping world</h2>
        </header>        
        <ul>
            <%
                LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                if (lg != null) {
                    if (lg.getlogedin()) {
            %>
            <h2>Hello, <%=lg.getUser().getName()%>!</h2>
            <li><a href="/mapStax/MapServ">Maps</a></li>
            <li><a href="profile.jsp">Profile</a></li>
            <li><a href="/mapStax/Images/<%=lg.getUser().getUsername()%>">Images</a></li>
            <li><a href="logout.jsp">Logout</a></li>
                <%}
                } else {
                %>
            <li><a href="register.jsp">Register</a></li>
            <li><a href="login.jsp">Login</a></li>
                <%
                    }%>
        </ul>        
        <footer>
            <ul>
                <li>&COPY; Stackers</li>
            </ul>
        </footer>
    </body>
</html>