<%-- 
    Document   : newjsp
    Created on : Aug 31, 2014, 3:26:12 PM
    Author     : Mohannad Lababidy (m.lababidy@gmail.com)
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<jsp:include page="..." />

For static resource, you should use:

<%@ include file="filename" %>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="header.html" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
<%@ include file="footer.html" %>