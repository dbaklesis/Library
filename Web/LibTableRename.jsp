<%-- 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    Document   : LibTableRename.jsp
    Created on : 15 Ιουν 2011, 11:39:49 πμ
    Author     : Dimitris
--%>

<%@ page import="java.sql.*"%>
<%@ page import="libutil.*"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/libScripts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.js"></script>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/LibTables.css">
    <title>Library - Πίνακες - Μετονομασία</title>
  </head>
  
    <br>
    <h1>Library - </h1><h2>Πίνακες - Μετονομασία</h2>
    <br>
    <br>
    <a href="LibAdmin.jsp" style="color:#ffffff">Αρχική</a> 
    <br>
    <br>
    <a href="LibTablesAdmin.jsp" style="color:#ffffff">Μενού Πινάκων</a>
    <br>
    <br>
    <%--<h2>Πίνακες</h2>--%>
  
    <%--<h3>Μετονομασία Στοιχείου Πίνακα </h3>--%>
    
  <body>

<%
	request.setCharacterEncoding("UTF-8");
	
	State lib = (State) session.getAttribute("state");
	if (lib == null) {
		lib = new State();
		session.setAttribute("state", lib);
	}

	Statement statement = lib.getConnection().createStatement();
	String query;
	ResultSet resultSet;
	libUtilities util = new libUtilities();
%>
    <form name="tableRename" action="/Library/RenameTableRec" onsubmit="return validateRenameTableInput()" method="POST">
    <label>Επιλογή Πίνακα:</label>
      	<select id="tblid" name="tblname" onchange="renameElement('tableRename', 'tblid','Από: ')">
        <option></option>
        <option>Είδος Εγγράφου</option>
        <option>Κατηγορία</option>
        <option>Φάκελος</option>
        <option>Κλάδος</option>
      </select>
      <div style="display: inline" id="poptbl"></div>
      <label>Σέ:</label>
      <input type="text" id="tblto" name="tblto"/>
      <br>
      <br>
      <input type="submit" value="Μετονομασία" name="tblRenExe"/>
      <br>
    </form>
  </body>
</html>
