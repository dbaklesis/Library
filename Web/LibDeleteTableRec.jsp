<%-- 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    Document   : LibDeleteTableRec.jsp
    Created on : 15 Ιουν 2011, 11:39:49 πμ
    Author     : Dimitris
--%>
<!DOCTYPE html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
<script type="text/javascript" src="libScripts.js"></script>
<script type="text/javascript" src="jquery.js"></script>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="LibTables.css">
    <title>Library - Πίνακες - Διαγραφή</title>
  </head>
  
  <br>
  <h1>Library - </h1><h2>Πίνακες - Διαγραφή</h2>
  <br>
  <br>
    <a href="LibAdmin.jsp" style="color:#ffffff">Αρχική</a> 
    <br>
	<br>
    <a href="LibTablesAdmin.jsp" style="color:#ffffff">Μενού Πινάκων</a>
     <br>
     <br>
    
  <body>
    <form name="tableDelete" action="/Library/DelTableRec" onsubmit="return validateDelTableInput()" method="POST" >
    	<label>Επιλογή Πίνακα:</label>
      	<select style="display: inline" id="tblid" name="tblname" onchange="renameElement('tableDelete', 'tblid','Στοιχείο: ')">
        	<option></option>
        	<option>Είδος Εγγράφου</option>
        	<option>Κατηγορία</option>
        	<option>Φάκελος</option>
        	<option>Κλάδος</option>
      	</select>
      <div style="display: inline" id="poptbl"></div>
      <br>
      <br>
      <input type="submit" value="Διαγραφή" name="tblDeleteTableRec"/>
    </form>
  </body>
</html>
