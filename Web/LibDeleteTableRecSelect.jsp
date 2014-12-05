<%-- 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    Document   : LibDeleteTableRecSelect
    Created on : 15 Ιουν 2011, 11:39:49 πμ
    Author     : Dimitris
--%>
<!DOCTYPE html>

<%@page import="libutil.*"%>
<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("UTF-8");
	State lib = (State) session.getAttribute("state");
	if (lib == null) {
		lib = new State();
		session.setAttribute("state", lib);
	}

	libUtilities util = new libUtilities();
	Statement statement = lib.getConnection().createStatement();
	String query;
	ResultSet resultSet; 
%>

<html>
<script src="libScripts.js"></script>
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="LibTables.css">
    <title>Library - Πίνακες - Διαγραφή</title>
  </head>
  <body>
  <br>
    <h1>Library - </h1><h2>Πίνακες - Διαγραφή</h2>
    <br>
    <br>
    <a href="LibAdmin.jsp" style="color:#ffffff">Αρχική</a>
    <br>
    <br>
    <%--<h2>Πίνακες</h2>--%>
  
    <%--<h3>Επιλογή Πίνακα</h3>--%>
    
    <form name="tableDeleteTableRec" action="/Library/DelTableRec" onsubmit="return validateDeleteTableInput()" method="POST">
    <label>Επιλογή στοιχείου γιά διαγραφή:</label>
      <select style="DISPLAY: block" id="tblSelect" name="tblentry">
      	<% 
      	 	String tblname = request.getParameter("tbl");
      		String tbl[] = util.mapTable(tblname);
      		query = util.constructSelectString(tbl[2]);
      	 	session.setAttribute("tbl", tblname);
      		resultSet = statement.executeQuery(query);
      	%>
      		<option></option>
      	<%
      		while (resultSet.next()) {
      	%>
                <option><%= resultSet.getString(1)%></option>
		<%	}%>
      </select>
      <br>
      <br>
      <input type="submit" value="Διαγραφή" name="tblDeleteTableRec"/>
      <br>
      <br>
    </form>
  </body>
</html>
