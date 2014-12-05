<%-- 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    Document   : LibAddTableRec.jsp
    Created on : 15 Ιουν 2011, 11:39:49 πμ
    Author     : Dimitris
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<script type="text/javascript" src="libScripts.js"></script>
<script type="text/javascript" src="jquery.js"></script>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="LibTables.css">
    <title>Library - Πίνακες - Προσθήκη</title>
  </head>
  <br>
  <h1>Library - </h1><h2>Πίνακες - Προσθήκη</h2>
	<br>
	<br>
  	<a href="LibAdmin.jsp" style="color:#ffffff">Αρχική</a>
  	<br>
  	<br>
  	<a href="LibTablesAdmin.jsp" style="color:#ffffff">Μενού Πινάκων</a>
    <br>
    <br>
  	<%--<h2>Πίνακες</h2>--%>
  	
  <body>
    
    <%--<h3>Προσθήκη στοιχείου</h3>--%>
    
    <form name="tableAdd" action="/Library/AddTableRec" onsubmit="return validateAddTableInput()" method=POST>
    <label>Επιλογή Πίνακα:</label>
      <select id="tblid" name="tblname" onchange="renameElement('tableAdd', 'tblid', 'Υπάρχουσα στοχεία: ' )">
        <option></option>
        <option>Είδος Εγγράφου</option>
        <option>Κατηγορία</option>
        <option>Φάκελος</option>
        <option>Κλάδος</option>
      </select>
      <label>Στοχείο:</label>
      <input type="text" name="tblNewRec" size="40"/>
      <br>
      <br>
      <input type="submit" value="Συνέχεια" name="tblAddNext"/>
      <br>
      <br>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 	  <div style="display: inline" id="poptbl"></div>
     
    </form>
  </body>
</html>
