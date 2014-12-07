<%-- 
    Document   : LibEditRecord
    Created on : 13 Μαϊ 2011, 10:56:14 πμ
    Author     : Dimitris
--%>

<%@page import="libutil.*"%>
<%@page import="java.sql.*"%>

<%-- JSP Comment --%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
	//Get state
		State lib = (State) session.getAttribute("state");
	    if (lib == null) {
	    	lib = new State();
	    	session.setAttribute("state", lib);
	    }


	Statement statement = lib.getConnection().createStatement();
	String query;
	ResultSet resultSet;
	libUtilities util = new libUtilities();
	int error = 0;
	try {
    	error = Integer.parseInt(request.getParameter("error"));
  	} 	catch (NumberFormatException e) {
  }
%>


<!DOCTYPE html>
<html>

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="${pageContext.request.contextPath}/scripts/libScripts.js"></script>

    <title>Library</title>

</head>
	
<h1>Library</h1>
	<% if (lib.isAdmin()) {%>
   <a href="LibAdmin.jsp">Αρχική</a>
   <br>
   <% } %>
   <% if (lib.isAdmin()){ %>
<h2>Επεξεργασία</h2>
<%  } else %>
<h2>Προβολή</h2>
  <a href="LibMainSearch.jsp">Αναζήτηση</a>
	<br>
	<br>
<jsp:useBean id="rsb" type="libutil.rsBean" scope="session"></jsp:useBean>
<%
	Integer idx = Integer.parseInt(request.getParameter("index"));
	Integer pg = Integer.parseInt(request.getParameter("page"));
	mtBean mt = rsb.getRsBean(idx);
%>
<%--<body onLoad="selectedDropDowns('<%=mt.getSector()%>', '<%=mt.getTypeofDoc()%>', '<%=mt.getCategory()%>', '<%=mt.getNotes()%>', '<%=mt.getFolder()%>');fillTextAreas('<%=mt.getTitle()%>', '<%=mt.getSubjectKeywords()%>', '<%=mt.getAuthor()%>', '<%=mt.getPubRef()%>')">--%>
  <body>
  <form action="/Library/InputEditDelete?index=<%=idx%>&page=<%=pg%>" name="EditForm" method="POST">
    <table cellpadding=4 cellspacing=2 border=4>
      <tbody>        
        <tr bgcolor="#CCCCFF">
          <td valign=top> 
            <b>ΚΩΔΙΚΟΣ</b>
            <br>
            <input type="text" name="code" readonly="readonly" value="<%=mt.getCode()%>" onchange="validateCode()" size=1 maxlength=1>
            <input type="text" name="counter" readonly="readonly" value="<%=mt.getCounter().toString()%>" size=10 maxlength=20>
          <td  valign=top>
            <b>ΚΛΑΔΟΣ</b>
            <input type="text" style="DISPLAY: block" id="ddLSector"  name="sector" readonly="readonly" value="<%=mt.getSector()%>">
            <td  valign=top>
            <b>ΦΑΚΕΛΟΣ</b>
            <br>
            <input type="text" style="DISPLAY: block" id="ddLFolder" name="folder" readonly="readonly" value="<%=mt.getFolder()%>">
        </tr>
        <tr bgcolor="#CCCCFF">
          <td valign=top>
            <b>ΕΙΔΟΣ ΕΓΓΡΑΦΟΥ</b>
            <br>
            <input type="text" style="DISPLAY: block"  id="ddLTypeofDoc" name="tod" readonly="readonly" value="<%=mt.getTypeofDoc()%>">
          <td valign=top>
            <b>ΚΑΤΗΓΟΡΙΑ</b>
            <br>
            <input type="text" style="DISPLAY: block"  id="ddLCategory" name="category" readonly="readonly" value="<%=mt.getCategory()%>">
       
            <td valign=top>
            <b>ΘΕΣΗ ΑΡΧΕΙΟΘΕΤΗΣΗΣ</b>
            <br>
            <input type="text" name="file_location" readonly="readonly" value="<%=mt.getFileLocation()%>" size="25">
        </tr>

        <tr bgcolor="#CCCCFF">
          <td valign=top>

            <b>ΤΙΤΛΟΣ</b>
            <br>
            <textarea name="title" readonly="readonly" cols="40" rows="8"><%=mt.getTitle()%></textarea>
            
          
          <td valign=top>
            <b>ΛΕΞΕΙΣ ΚΛΕΙΔΙΑ</b>
            <br>
            <textarea name="subject_key_words" readonly="readonly" cols="40" rows="8"><%=mt.getSubjectKeywords()%></textarea>
          <td bgcolor="#CCCCFF">
          </tr>
        <tr bgcolor="#CCCCFF">
          <td valign=top>
            <b>ΣΥΓΓΡΑΦΕΑΣ<b>
                <br>
                <textarea name="author" readonly="readonly" cols="40" rows="8"><%=mt.getAuthor() %></textarea>
                <td valign=top>
                  <b>ΑΝΑΦΟΡΑ ΕΚΔΟΣΗΣ</b>
                  <br>
                  <textarea name="pub_ref" readonly="readonly" cols="40" rows="8"><%=mt.getPubRef()%></textarea>
                  <td valign=top>
             <b>ΣΗΜΕΙΩΣΕΙΣ</b>
            <br>
            <textarea name="notes" value="<%=mt.getNotes()%>" readonly="readonly" cols="40" rows="8"><%=mt.getSubjectKeywords()%></textarea>
                  </tr>
                  </tbody>
                  </table>
                 <%--
                  <input type="submit" value="Αποθήκευση Αλλαγών" name="esButton" >
                  <input type="submit" value="Διαγραφή Εγγραφής" name="edButton" >
                 --%>
                  </form>
                  </body>
                  </html>
