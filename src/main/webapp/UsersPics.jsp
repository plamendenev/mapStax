<%-- 
    Document   : UsersPics
    Created on : Sep 24, 2014, 2:52:48 PM
    Author     : Administrator
--%>

<%@page import="mapstax.models.User"%>
<%@page import="mapstax.servlets.Profile"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="mapstax.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>mapStax</title>
        <link rel="stylesheet" type="text/css" href="/mapStax/Styles.css" />
    </head>
    <body>
        <header>

            <h1>mapStax ! </h1>
            <h2>Your world in Black and White</h2>
        </header>       
        <ul>
            <li><a href="/mapStax/index.jsp">Home</a></li>
            <li><a href="/mapStax/upload.jsp">Upload</a></li>                
            <li><a href="/mapStax/profile.jsp">Profile</a></li>
        </ul>
        <article>
            <h1>Your Pics</h1>
            
            <%
                LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
                if (lsPics == null) {
            %>
            <p>No Pictures found</p>
            <%
            } else {
                Iterator<Pic> iterator;
                iterator = lsPics.iterator();
                while (iterator.hasNext()) {
                    Pic p = (Pic) iterator.next();

            %>
            <a href="/mapStax/Image/<%=p.getSUUID()%>" ><img src="/mapStax/Thumb/<%=p.getSUUID()%>"></a>

            <form method="POST"  action="/mapStax/Profile">  
                <input type="text" name="picid" value="<%=p.getSUUID()%>" hidden>
                <input type="text" name="user" value="<%=lg.getUser().getUsername()%>" hidden>
                <input type="submit" value="Make profile pic">
            </form>
            <%
                    }
                }
            %>            
        </article>        
    </body>
</html>
