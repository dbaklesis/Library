<%-- 
    Document   : LibMainSearch
    Created on : 13 Μαϊ 2011, 10:56:14 πμ
    Author     : Dimitris
--%>
<!DOCTYPE html>

<%@page import="libutil.*"%>
<%@page import="java.sql.*"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
	//Get state
	State lib = (State) session.getAttribute("state");
	if (lib == null)
		lib = new State();

	String admin = request.getParameter("admin");
	if (admin != null)
		if (admin.equals("true"))
			lib.setAdmin(true);
		else if (admin.equals("false"))
			lib.setAdmin(false);

	session.setAttribute("state", lib);

	Statement statement = lib.getConnection().createStatement();
	String query;
	ResultSet resultSet;
	libUtilities util = new libUtilities();
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="LibMainSearch.css">
	<script type="text/javascript" src="jquery.js"></script>
	<script type="text/javascript" src="libScripts.js"></script>
	
<style TYPE="text/css">
 
h1 {
   font-family: Georgia, "Times New Roman", Times, serif;
   font-size: 25px;
   font-weight: bold;
   color: #CC0099;
   display: inline; 
}

h2 {
   font-family: Georgia, "Times New Roman", Times, serif;
   font-size: 17px;
   /*font-weight: bold;*/
   /*color: #FFFFFF;*/
   color: #FFFF00;
   display: inline;
}

td {
	border-bottom: navy solid thin;
	border-top: navy solid thin;
	border-left: none;
	border-right: none;
	font-size: small;
	background-color: #B3B3B3;
}

body {
   font-family: Georgia, "Times New Roman", Times, serif;
   font-size: 14px;
   color: #333333;
   /*background-color: #F9F9F9;*/
   /*background-color: #6666CC;*/
   /*background-color: #2A4747;*/
   background-color: #426E6E
}

.clear { /* generic container (i.e. div) for floating buttons */
    overflow: hidden;
    width: 100%;
}

a.button {
    background: transparent url('bg_button_a.gif') no-repeat scroll top right;
    color: #444;
    display: block;
    float: left;
    font: normal 12px arial, sans-serif;
    height: 24px;
    margin-right: 6px;
    padding-right: 18px; /* sliding doors padding */
    margin-left: 15px;
    text-decoration: none;
}

a.button span {
    background: transparent url('bg_button_span.gif') no-repeat;
    display: block;
    line-height: 14px;
    padding: 5px 0 5px 18px;
}

a.button:active {
    background-position: bottom right;
    color: #000;
    outline: none; /* hide dotted outline in Firefox */
}

a.button:active span {
    background-position: bottom left;
    padding: 6px 0 4px 18px; /* push text down 1px */
}

.suggestionsBox {
position: relative;
top: -200px;
left: 120px;
margin: 0px 0px 0px 0px;
width: 200px;
background-color: #7845DD;
-moz-border-radius: 7px;
-webkit-border-radius: 7px;
border: 2px solid #000;
color: #fff;
}
.suggestionList {
margin: 0px;
padding: 0px;
}

.suggestionList li {
margin: 0px 0px 3px 0px;
padding: 3px;
cursor: pointer;
}

.suggestionList li:hover {
background-color: #DD45CD;
}

</style>
<title>Library - Αναζήτηση</title>
</head>
<br>
<h1>Library - </h1><h2>Αναζήτηση</h2>
<br>
<br>
<%
	if (lib.isAdmin()) {
%>
<a href="LibAdmin.jsp" style="color:#ffffff">Αρχική</a>
<br>
<br>
<% } %>

<body>
	<form action="/Library/InputSearch" method="POST">
		<table cellpadding=4 cellspacing=2 border=0>
			<tbody>
				<%--<tr bgcolor="#c8d8f8">--%>
				<tr>
					<td valign=top>
					<b>ΚΩΔΙΚΟΣ</b>
					<br> 
					<input type="text" name="counter" value="" size=10 maxlength=20>
					<br>
					<br>
					<b>ΕΙΔΟΣ ΕΓΓΡΑΦΟΥ</b>
					<br> 
					<select	style="DISPLAY: block" id="ddLTypeofDoc" name="tod">
							<%
								query = util.constructSelectString("TYPE_OF_DOC");
							%>
							<%
								resultSet = statement.executeQuery(query);
							%>
							<option></option>
							<%
								while (resultSet.next()) {
									Integer i = resultSet.getString(1).length();
									if (i > 40)
										i = 40;
							%>
							<option><%=resultSet.getString(1).substring(0,i)%></option>
							<%
								}
							%>
					</select>
					<td valign=top>
					<b>ΚΑΤΗΓΟΡΙΑ</b>
					<br>
					<select	style="DISPLAY: block" id="ddLCategory" name="category">
							<%
								//query = "SELECT category FROM lib_table_category ORDER BY category ASC";
								query = util.constructSelectString("CATEGORY");
							%>
							<%
								resultSet = statement.executeQuery(query);
							%>
							<option></option>
							<%
							while (resultSet.next()) {
								Integer i = resultSet.getString(1).length();
								if (i > 40)
									i = 40;
							%>
							<option><%=resultSet.getString(1).substring(0,i)%></option>
							<%
								}
							%>
					</select>
					<br>
					<b>ΚΛΑΔΟΣ</b> 
					<select style="DISPLAY: block" id="ddLSector" name="sector">
							<%
								query = util.constructSelectString("SECTOR");
							%>
							<%
								resultSet = statement.executeQuery(query);
							%>
							<option></option>
							<%
							while (resultSet.next()) { 
								Integer i = resultSet.getString(1).length();
								if (i > 40)
									i = 40;
							%>
							<option><%=resultSet.getString(1).substring(0,i)%></option>
							<%
								}
							%>
					</select>
					<td valign=top>
					<b>ΑΝΑΦΟΡΑ ΕΚΔΟΣΗΣ</b>
					<br> <textarea	name="pub_ref" cols="40" rows="4"></textarea>
				</tr>
			
				<tr bgcolor="#c8d8f8">
					<td valign=top>
					<b>ΤΙΤΛΟΣ</b>
					<br>
					<textarea name="title" cols="40" rows="6"></textarea>
					<td valign=top>
					<b>ΛΕΞΕΙΣ ΚΛΕΙΔΙΑ</b>
					<br>
					<textarea name="subject_key_words" cols="40" rows="6"></textarea>
					<td valign=top>
					<b>ΦΑΚΕΛΟΣ</b>
					<br>
					<select	style="DISPLAY: block" id="ddLFolder" name="folder">
							<%
								query = "select folder from lib_table_folder order by folder ASC";
							%>
							<%
								resultSet = statement.executeQuery(query);
							%>
							<option></option>
							<%
							while (resultSet.next()) {
								Integer i = resultSet.getString(1).length();
								if (i > 40)
									i = 40;
							%>
							<option><%=resultSet.getString(1).substring(0,i)%></option>
							<%
								}
							%>
					</select>
					<br>
					<b>ΘΕΣΗ ΑΡΧΕΙΟΘΕΤΗΣΗΣ</b>
					<br>
					<input type="text" name="file_location" value="" size="25">
				</tr>
				<tr bgcolor="#c8d8f8">
					<td valign=top>
					<b>ΣΥΓΓΡΑΦΕΑΣ</b>
					<br>
					<textarea name="author" id="inputString" onkeyup="lookup(this.value)" cols="40" rows="6"></textarea>
					<td valign="top" colspan="2">
					<b>ΣΗΜΕΙΩΣΕΙΣ</b>
					<br>
					<textarea name="notes" cols="84" rows="6"></textarea>
				</tr>
			</tbody>
		</table>
		<input type="submit" value="Αναζήτηση" name="sButton">
		<input type="reset" value="Εκκαθάριση"/>
		<div class="suggestionsBox" id="suggestions" style="display: none;">
		<div class="suggestionList" id="autoSuggestionsList">
		</div>
		</div>
	</form>
</body>
</html>
