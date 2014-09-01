<%-- 
    Document   : main_template
    Created on : Aug 31, 2014, 3:31:21 PM
    Author     : Mohannad Lababidy (m.lababidy@gmail.com)
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here:
<%@attribute name="message"%>

 any content can be specified here e.g.: 
<h2>${message}</h2> --%>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<html>
  <body>
    <div id="pageheader">
<jsp:invoke fragment="header"/>
    </div>
    <div id="body">
<jsp:doBody/>
    </div>
    <div id="pagefooter">
<jsp:invoke fragment="footer"/>
    </div>
  </body>
</html>