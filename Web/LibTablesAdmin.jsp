<%-- 
    Document   : LibTablesAdmin
    Created on : 13 Ιουν 2011, 2:27:36 μμ
    Author     : Δημήτρης
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/LibTables.css"> 
  </head>
  
  <title>Library - Πίνακες - Διαχείρηση</title>
  <br>
  <h1>Library - </h1><h2>Πίνακες - Διαχείρηση</h2>
   <br>
   <br>
  <a href="LibAdmin.jsp" style="color:#ffffff">Αρχική</a>
  <br>
  <br>
  <body>
 	 <a class="button" href="LibAddTableRec.jsp"><span>Προσθήκη στοιχείου</span></a>
    <br>
    <a class="button" href="LibTableRename.jsp"><span>Μετονομασία στοιχείου</span></a>
    <br>
    <a class="button" href="LibDeleteTableRec.jsp"><span>Διαγραφή στοιχείου</span></a>    
  </body>
</html>