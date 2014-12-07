<%-- 
    Document   : LibAddRecord.jsp
    Created on : 13 Μαϊ 2011, 10:56:14 πμ
    Author     : Dimitris Baklesis
--%>

<%@ page import="libutil.*"%>
<%@ page import="java.sql.*"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/Lib-Add-Record.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/libScripts.js"></script>
</head>

<%
	State lib = (State) session.getAttribute("state");
	if (lib == null) {
		lib = new State();
		session.setAttribute("state", lib);
	}

	Statement statement = lib.getConnection().createStatement();
	String query;
	ResultSet resultSet;
	boolean touched = false;
	libUtilities util = new libUtilities();
    String mode = request.getParameter("mode");
    rsBean rsb = (rsBean)session.getAttribute("rsb");
    mtBean mt;
    int rsSize=0;
    Integer cursor=0;
    if (mode.equals("add")) {
    	
    	if (rsb != null) {
        	rsb.collapseRs();
        	session.setAttribute("rsb", null);
    	}
        
    	query = "SELECT * FROM lib_table_main ORDER BY COUNTER ASC";
    	int count = util.loadResultSet(query, lib, request, "rsb");
    	rsb = (rsBean)session.getAttribute("rsb");
    	
    	mt = new mtBean();
    	mt.setCounter((int)util.libGetLastId(statement) + 1);
    	rsb.addRsBean(mt);
    	cursor = rsb.getRsSize();
    	rsSize = rsb.getRsSize();
    	session.setAttribute("rsb", rsb);
    	session.setAttribute("cursor", cursor);
    	
    } else if (mode.equals("add_while_editing")) {
    	if ((rsb.getRsBean(rsb.getRsSize()).isEmpty()) == false) {
    		mt = new mtBean();
    		mt.setCounter((int)util.libGetLastId(statement) + 1);
    		rsb.addRsBean(mt);
    	}
    	else
    		mt = rsb.getRsBean(rsb.getRsSize());
    	cursor = rsb.getRsSize();
    	rsSize = rsb.getRsSize();
    	session.setAttribute("rsb", rsb);
    	session.setAttribute("cursor", cursor);
    	
    } else	if (mode.equals("deleted")){
    	
    	rsb = (rsBean)session.getAttribute("rsb");
    	cursor = (Integer)session.getAttribute("cursor");
    	rsSize = rsb.getRsSize();
    	mt = rsb.getRsBean(cursor);
    	mode="edit";
    	
    } else if (mode.equals("edit") || (mode.equals("browse"))) {
  
    	cursor = Integer.parseInt(request.getParameter("cursor"));
    	rsSize = rsb.getRsSize();
    	mt = rsb.getRsBean(cursor);
    	session.setAttribute("cursor", cursor);
    	
    } else {
    	
    	mt = new mtBean();
    	mt.setTitle("ERROR");
    	mt.setNotes("ERROR");
    	mt.setAuthor("ERROR");
    	mt.setSubjectKeywords("ERROR");
    	mt.setPubRef("ERROR");
    }
  
%>
<br>
<% if (lib.isAdmin()) { %>
<title>Library - Προσθήκη/Επεξεργασία</title>
<h1>Library - </h1><h2>Προσθήκη/Επεξεργασία</h2>
<br>
<br>
<a href="LibAdmin.jsp" style="color: #ffffff">Αρχική</a>
<br>
<%} else {%>
<title>Library - Προβολή</title>
<h1>Library - </h1><h2>Προβολή</h2>
<br>
<%} %>
<br>
<a href="LibMainSearch.jsp" style="color: #ffffff">Αναζήτηση</a>

<%--<body onLoad="selectedDropDowns('addRecordForm','<%=mt.getSector()%>', '<%=mt.getTypeofDoc()%>', '<%=mt.getCategory()%>', '<%=mt.getFolder()%>');fillTextAreas('addRecordForm','<%=mt.getTitle()%>','<%=mt.getSubjectKeywords()%>','<%=mt.getAuthor()%>','<%=mt.getPubRef()%>','<%=mt.getNotes()%>')">--%>
<body onLoad="selectedDropDowns('addRecordForm','<%=mt.getSector()%>', '<%=mt.getTypeofDoc()%>', '<%=mt.getCategory()%>', '<%=mt.getFolder()%>')">
<%--<form name="addRecordForm" method="POST">--%>

<%--<form id="addRecordForm" onkeypress="enableAddBtn('addRecordForm', true)" onload="enableAddBtn('addRecordForm', true)" method="POST">--%>
<form id="addRecordForm"  method="POST">
	<div style="text-align: center">
	<input type="hidden" name="buttonpressed">
	<%if (cursor == 1) {%>
	<input class="steer" type="button" disabled="disabled" value="<--" name="beforeButton">
	<%} else { %>
	<input class="steer" type="button" value="<--" onclick="return validateAddRecordInput('Προηγούμενο', '<%=mode%>', '<%=cursor%>')" name="beforeButton">
	<%} %>
	<%if (cursor == rsSize) { %>
	<input class="steer" type="button" disabled="disabled" value="-->" name="nextButton">
	<% } else {%>
	<input class="steer" type="button" value="-->" onclick="validateAddRecordInput('Επόμενο', '<%=mode%>', <%=cursor%>)" name="nextButton">
	<%} %>
	</div>
		<%--<table cellpadding=4 cellspacing=2 border=4>--%>
		<table>
			<tbody>

				<tr bgcolor="#CCCCFF">
					<td valign=top>
					<%--<b>ΚΩΔΙΚΟΣ</b>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp--%>
					<%--<b>ΗΜΕΡ/ΝΙΑ ΕΓΓΡΑΦΗΣ</b>--%>
					<b>ΚΩΔΙΚΟΣ</b>
					<br> 
					<% if (lib.isAdmin() == true) {%>
						<input type="text" name="code" value="<%=mt.getCode()%>" onchange="return validateCode()" size=1 maxlength=1> 
					<%} else { %>
						<input type="text" name="code" readonly="readonly" value="<%=mt.getCode()%>" size=1 maxlength=1>
					<%} %>
					<input type="text" name="counter" readonly="readonly" value="<%=mt.getCounter().toString()%>" size=10 maxlength=20>
					
					
					<%--<input type="text" name="dateofentry" readonly="readonly" value="<%=mt.getDateofEntry()%>" size=10>--%>
					<br>
					<br>
					<b>ΕΙΔΟΣ ΕΓΓΡΑΦΟΥ</b> 
					<br>
					<% if (lib.isAdmin() == true){ %>
						<select  style="DISPLAY: block" id="ddLTypeofDoc" name="tod">
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
					<%} else { %>
						<input	type="text" name="tod" readonly="readonly" value="<%=util.nulltoblank(mt.getTypeofDoc()) %>" size="25">
					<%} %>
					<br>
					<td valign=top>
					<b>ΚΑΤΗΓΟΡΙΑ</b> 
					<br> 
					<% if (lib.isAdmin() == true) { %>
					<select style="DISPLAY: block" id="ddLCategory" name="category">
							<%
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
					<%} else { %>
						<input	type="text" name="category" readonly="readonly" value="<%=util.nulltoblank(mt.getCategory()) %>" size="25">
						<br>
					<%} %>
					<br>
					<b>ΚΛΑΔΟΣ</b>
					<br>
					<% if (lib.isAdmin() == true) { %>
					<select style="DISPLAY: block;" id="ddLSector" name="sector" >
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
					<%} else { %>
						<input	type="text" name="sector" readonly="readonly" value="<%=util.nulltoblank(mt.getSector())%>" size="25">
					<%} %>
					<td valign=top>
					<b>ΑΝΑΦΟΡΑ ΕΚΔΟΣΗΣ</b>
				    <br>
				    <% if (lib.isAdmin() == true) {%>
				    	<textarea name="pub_ref" cols="40" rows="4"><%=mt.getPubRef()%></textarea>
				    <%} else {%>
				    	<textarea name="pub_ref" readonly="readonly" cols="40" rows="4"><%=util.nulltoblank(mt.getPubRef())%></textarea>
				    <%} %>
				</tr>
				<tr bgcolor="#CCCCFF">
					<td valign=top>
					<b>ΤΙΤΛΟΣ</b>
					<br>
					<% if (lib.isAdmin() == true) { %>
						<textarea name="title" cols="40" rows="6"><%=mt.getTitle()%></textarea>
					<% } else {  %>
						<textarea name="title" readonly="readonly" cols="40" rows="6"><%=util.nulltoblank(mt.getTitle())%></textarea>
					<%} %>
					<td valign=top>
					<b>ΛΕΞΕΙΣ ΚΛΕΙΔΙΑ</b>
					<br>
					<% if (lib.isAdmin() == true) { %>
						<textarea name="subject_key_words" cols="40" rows="6"><%=mt.getSubjectKeywords()%></textarea>
					<%} else { %>
						<textarea name="subject_key_words" readonly="readonly" cols="40" rows="6"><%=util.nulltoblank(mt.getSubjectKeywords())%></textarea>
					<%} %>
					<td valign=top>
					<b>ΦΑΚΕΛΟΣ</b>
					<br>
					<%if (lib.isAdmin() == true) { %>
					<select style="DISPLAY: block" id="ddLFolder" name="folder">
							<%
								query = util.constructSelectString("FOLDER");
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
					<%} else { %>
						<input type="text" name="folder" readonly="readonly" value="<%=util.nulltoblank(mt.getFolder())%>" size="25">
						<br>
					<%} %>
					<br>
					<b>ΘΕΣΗ ΑΡΧΕΙΟΘΕΤΗΣΗΣ</b> 
					<br>
					<% if (lib.isAdmin() == true) { %>
						<input	type="text" name="file_location" value="<%=mt.getFileLocation(15) %>" size="25">
					<%} else { %>
						<input type="text" name="file_location" readonly="readonly" value="<%=util.nulltoblank(mt.getFileLocation())%>" size="25">
					<%} %>
				</tr>
				<tr bgcolor="#CCCCFF">
				<td valign=top>
				<b>ΣΥΓΓΡΑΦΕΑΣ</b>
				<br>
				<%if (lib.isAdmin() == true) { %>
					<textarea name="author" id="inputString" onkeyup="lookup(this.value)" cols="40" rows="6"><%=mt.getAuthor()%></textarea>
				<%} else { %>
					<%--<textarea name="author" readonly="readonly" id="inputString" onkeyup="lookup(this.value)" cols="40" rows="6"><%=mt.getAuthor()%></textarea>--%>
					<textarea name="author" readonly="readonly" id="inputString" cols="40" rows="6"><%=util.nulltoblank(mt.getAuthor())%></textarea>
				<%} %>
				<td valign="top" colspan="2">
				<b>ΣΗΜΕΙΩΣΕΙΣ</b>
				<br>
				<% if (lib.isAdmin() == true) { %>
					<textarea name="notes" cols="84" rows="6"><%=mt.getNotes()%></textarea>
				<%} else { %>
					<textarea name="notes" readonly="readonly" cols="84" rows="6"><%=util.nulltoblank(mt.getNotes())%></textarea>
				<%} %>
				</tr>
			</tbody>
		</table>
		<% //if (cursor == rsb.getRsSize()) {
		//if (util.libIsCommited(mt.getCounter(), statement) == true) {
		if (lib.isAdmin() == true) {%>
			<%--<input class="" type="button" value="Προσθήκη" onclick="return validateAddRecordInput('Προσθήκη', '<%=mode%>', '<%=cursor%>')" name="aButton">--%>
			<input class="steer" type="button" value="Προσθήκη" onclick="return validateAddRecordInput('Προσθήκη/Επεξεργασία', '<%=mode%>', '<%=cursor%>')" name="aButton">
	 		<%if (cursor == rsb.getRsSize()) {
				if (util.libIsCommited(mt.getCounter(), statement) == true) {%>
					<input class="steer" type="button" value="Διαγραφή" onclick="return validateAddRecordInput('Διαγραφή', '<%=mode%>', '<%=cursor%>')" name="dButton">
			<% } else {%>
					<input class="steer" type="button" value="Διαγραφή" disabled="disabled"  name="dButton">
			<% }%>
			<%} else { %>
				<input class="steer" type="button" value="Διαγραφή" onclick="return validateAddRecordInput('Διαγραφή', '<%=mode%>', '<%=cursor%>')" name="dButton">
			<%}%>
			<div class="suggestionsBox" id="suggestions" style="display: none;">
			<div class="suggestionList" id="autoSuggestionsList">
			</div>
			</div> 	
		<% }%> 
	</form>
</body>
</html>
