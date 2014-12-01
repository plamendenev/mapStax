<%-- 
    Document   : userMaps
    Created on : 27-Nov-2014, 12:46:49
    Author     : plamendenev
--%>

<%@page import="java.util.Iterator"%>
<%@page import="mapstax.stores.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>mapStax</title>
        <link rel="stylesheet" type="text/css" href="/mapStax/Styles.css" />
    </head>
    <body>
        <ul>
            <li><a href="/mapStax/index.jsp">Home</a></li>
            <li><a href="/mapStax/upload.jsp">Upload</a></li>                
            <li><a href="/mapStax/profile.jsp">Profile</a></li>
        </ul>
        <article>
            <h1>Your maps</h1>
            <h3><a href="newMap.jsp">Create a new map</a></h3>
            <%
                java.util.LinkedList<Map> mapsList = (java.util.LinkedList<Map>) request.getAttribute("maps");
                if (mapsList == null) {
            %>
            <p>No maps found</p>
            <%
            } else {
                /*Iterator<Map> iterator;
                 iterator = mapsList.iterator();
                 while (iterator.hasNext()) {
                 Map map = (Map) iterator.next();*/
            %><ul><%
                        for (int i = 0; i < mapsList.size(); i++) {
                %>
                <li><a href="DisplayMap/<%=mapsList.get(i).getUUID()%>"><% out.println(mapsList.get(i).getMapName());%></a></li>           
                    <%
                        }
                    %></ul><%
                                }

                %>            
        </article>    
    </body>
</html>
