<%-- 
    Document   : logout
    Created on : 23-Oct-2014, 20:04:05
    Author     : Plamen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Logout</title>
    </head>
    <body>
        <%if (session != null) {
                session.invalidate();
                response.sendRedirect("/mapStax/index.jsp");
            }%>        
    </body>
</html>
