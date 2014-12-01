<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.mapStax.stores.*" %>
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
            <li><a href="/mapStax/index.jsp">Home</a></li>
            <li><a href="/mapStax/upload.jsp">Upload</a></li>                
            <li><a href="/mapStax/profile.jsp">Profile</a></li>
            <li><a href="/mapStax/MapServ">Maps</a></li>
        </ul>

        <%
            LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
            if (lg != null) {
            } else {
                String redirectURL = "index.jsp";
                response.sendRedirect(redirectURL);
            }

            if (lg.getlogedin()) {
        %>
        <h3>Creating a new mind map</h3>

        <form method="POST" action="NewMap"> 
            <p>Please enter a map name: <input type = "text" name="mapName"></p> 
            <input type="submit" value="Create">
        </form>
        <%}
        %>
    </body>
</html>