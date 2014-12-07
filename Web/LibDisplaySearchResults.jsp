<%--
    Document   : LibDisplaySearchResults.jsp
    Created on : 24 Μαϊ 2011, 3:31:18 μμ
    Author     : Dimitris
--%>

<%@ page import= "javax.servlet.*" %>
<%@ page import= "javax.servlet.http.*" %>
<%@ page import= "javax.servlet.ServletRequest" %>
<%@ page import= "libutil.*" %>
<%@ page import= "libutil.*" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>

<html>
	<meta http-equiv="Content-Type" content="text/html">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/LibDisplaySearchResults.css">

<script src="${pageContext.request.contextPath}/scripts/libScripts.js"></script>
  <head>
    <title>Library - Αποτελέσματα Αναζήτησης</title>  
  </head>
  <body>
  
  <br>
  <h1>Library - </h1><h2>Αποτελέσματα Αναζήτησης</h2>
  <br>
  <br>
      <jsp:useBean id="rsb" type="libutil.rsBean" scope="session"></jsp:useBean>
   <%
 		//Get state 
 		State lib = (State) session.getAttribute("state");
	    if (lib == null) {
	    	lib = new State();
	    	session.setAttribute("state", lib);
	    }
 		
      	mtBean mt;
 		int pg=0;
      	try {
			pg = Integer.parseInt(request.getParameter("page"));
      	} catch (Exception ex) {
      		System.err.println("Error with input: " + ex);
      	}
      	
      	int rowsperpage = (Integer) session.getAttribute("rowsperpage");
      	String str=null;
      	int numpages=0;
      	
      	if (rsb.getRsSize() >= rowsperpage) {
      		numpages = rsb.getRsSize() / rowsperpage;
      	 	if ((rsb.getRsSize() % rowsperpage) != 0)
      	 		numpages++;
  		}
      	else
      		numpages = 1;
      	
      	if (pg > numpages)
      		pg = numpages;
  		
  		str = "Σελίδα " + pg + " από " + numpages + "   (" + rsb.getRsSize() + " εγγραφές)";
      %>
      <% if (lib.isAdmin()) { %>
      <a href="LibAdmin.jsp">Αρχική</a>
      <br>
      <%} %>
      <br>
      <a href="LibMainSearch.jsp">Αναζήτηση</a>
      <br>
      <br>
	  <%--<h1>Αποτελέσματα Αναζήτησης</h1>--%>
	  <%=str %>
	  <br>
	  <br>
      <%
      	int start;
		int r = rowsperpage;
       	if (numpages == 1)
       		start = 1;
       	else
        	start = (pg-1) * r + 1;
       	
        if (rsb.getRsSize() <= 0) {
        	 RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/NotFound.jsp");
            dispatcher.forward(request, response);
        }
      %>
     <form id="searchresults">
	<%
			if (pg > 1) {
	%>
	  			<input class="steer" type="button" name=firstbtn onclick="dispResultsButtonPressed('first', 1)" value="<<">
	  			<input class="steer" type="button" name="previousbtn" onclick="dispResultsButtonPressed('previous', <%=pg-1%>)" value="<">
	  	<%	} 
			else {
		%>
				<input class="steer" type="button" name="firstbtn" disabled="disabled" value="<<">
				<input class="steer" type="button" name="previousbtn" disabled="disabled" value="<">
		<%
			}
		%>
		<%
			if (pg < numpages) {
  		%>
				<input class="steer" type="button" name="nextbtn" onclick="dispResultsButtonPressed('next', <%=pg+1%>)" value=">">
				<input class="steer" type="button" name="lastbtn" onclick="dispResultsButtonPressed('last', <%=numpages%>)" value=">>">
		<%
			} 
			else {
		%>
			<input class="steer" type="button" name="nextbtn" disabled="disabled" value=">">
			<input class="steer" type="button" name="lastbtn" disabled="disabled" value=">>">
		<%
			}
		%>	
  <table cellpadding=1 cellspacing=5 border=0>
    <thead>
      <tr bgcolor="#f2d8f8">
        <th>ΚΩΔΙΚΟΣ</th>
        <th>ΤΙΤΛΟΣ</th>
        <th>ΣΥΓΓΡΑΦΕΑΣ</th>
        <th>ΚΑΤΗΓΟΡΙΑ</th>
        <th>ΛΕΞΕΙΣ ΚΛΕΙΔΙΑ</th>
        <th>ΕΙΔΟΣ ΕΓΓΡΑΦΟΥ</th>
        <th>ΑΝΑΦΟΡΑ ΕΚΔΟΣΗΣ</th>
        <th>ΣΗΜΕΙΩΣΕΙΣ</th>
        <%--<th>ΗΜΕΡΟΜΗΝΙΑ ΕΓΓΡΑΦΗΣ</th>--%>
        <th>ΚΛΑΔΟΣ</th>
        <th>ΦΑΚΕΛΟΣ</th>
        <th>ΘΕΣΗ ΑΡΧΕΙΟΘΕΤΗΣΗΣ</th>
      </tr>
    </thead>
    <tbody>
      <%
      	while ((start <= (rsb.getRsSize())) && (r-- > 0)) {
          mt = rsb.getRsBean(start++);
      %>
      
      <tr bgcolor="#c8d8f8">
        <td valign=top rowspan="1" align="left">
          <br>
          <%if (mt.getCode().isEmpty()) {%>
          	<%if (lib.isAdmin()) {%>
          		<%--<a href="LibAddRecord.jsp?mode=edit&counter=<%=mt.getCounter().toString()%>&index=<%=start - 1%>&page=<%=pg%>"><%=mt.getCounter()%></a>--%>
          		<a href="LibAddRecord.jsp?mode=edit&cursor=<%=start - 1%>"><%=mt.getCounter()%></a>
				<%--<a href="LibAddRecord.jsp?mode=edit&counter=<%=mt.getCounter().toString()%>&page=<%=pg%>"><%=mt.getCounter()%>--%>          		
          	<%} else { 
          	%>
          		<%--<a href="LibDisplayecord.jsp?counter=<%=mt.getCounter().toString()%>&index=<%=start - 1%>&page=<%=pg%>"><%=mt.getCounter()%></a>--%>
   				<%--<a href="LibAddRecord.jsp?mode=browse&counter=<%=mt.getCounter().toString()%>&index=<%=start - 1%>&page=<%=pg%>"><%=mt.getCounter()%></a>--%>
   				<a href="LibAddRecord.jsp?mode=browse&cursor=<%=start - 1%>"><%=mt.getCounter()%></a>
          	<%} %>
          <br>
          <%} else {%>
          	<%if (lib.isAdmin()) { %>
          		<%--<a href="LibAddRecord.jsp?mode=edit&counter=<%=mt.getCounter().toString()%>&index=<%=start - 1%>&page=<%=pg%>&first=edit"><%=mt.getCode()%><%=mt.getCounter()%></a>--%>
          		<a href="LibAddRecord.jsp?mode=edit&cursor=<%=start - 1%>"><%=mt.getCode()%><%=mt.getCounter()%></a>
          	<%} else { %>
          		<%--<a href="LibDisplayRecord.jsp?counter=<%=mt.getCounter().toString()%>&index=<%=start - 1%>&page=<%=pg%>"><%=mt.getCode()%><%=mt.getCounter()%></a>--%>
          		<%--<a href="LibAddRecord.jsp?mode=browse&counter=<%=mt.getCounter().toString()%>&index=<%=start - 1%>&page=<%=pg%>&first=edit"><%=mt.getCode()%><%=mt.getCounter()%></a>--%>
          		<a href="LibAddRecord.jsp?mode=browse&&cursor=<%=start - 1%>"><%=mt.getCode()%><%=mt.getCounter()%></a>
          	<%} %>
          <br>
          <%
            }
          %>
        </td>
        <td valign=top rowspan="1" align="left"> 
          <br>
          <%Integer limit = 90; %>
          <%=mt.getTitle(limit)%></td>
        <td valign=top rowspan="1" align="left">
          <br>
          <%=mt.getAuthor(limit)%></td>
        <td valign=top rowspan="1" align="left"> 
          <br>
          <%=mt.getCategory(limit)%></td>
        <td valign=top rowspan="1" align="left"> 
          <br>
          <%=mt.getSubjectKeywords(limit)%></td>
        <td valign=top rowspan="1" align="left"> 
          <br>
          <%=mt.getTypeofDoc(limit)%></td>
        <td valign=top rowspan="1" align="left"> 
          <br>
          <%=mt.getPubRef(limit)%></td>
        <td valign=top rowspan="1" align="left"> 
          <br>
          <%=mt.getNotes(limit)%></td>
        <%--<td valign=top rowspan="1" align="left"> 
          <br>
          <%=mt.getDateofEntry()%></td>--%>
        <td valign=top rowspan="1" align="left"> 
          <br>
          <%=mt.getSector(limit)%></td>
        <td valign=top rowspan="1" align="left"> 
          <br>
          <%=mt.getFolder(limit)%></td>
        <td valign=top rowspan="1" align="left"> 
          <br>
          <%=mt.getFileLocation(limit)%></td>
          <%}%>
      </tr>
   	</tbody>

  </table>
  
		<%
			if (pg > 1) {
		%>
	  			<input class="steer" type="button" name=firstbtn onclick="dispResultsButtonPressed('first', 1)" value="<<">
	  			<input class="steer" type="button" name="previousbtn" onclick="dispResultsButtonPressed('previous', <%=pg-1%>)" value="<">
	  	<%	} 
			else {
		%>
				<input class="steer" type="button" name="firstbtn" disabled="disabled" value="<<">
				<input class="steer" type="button" name="previousbtn" disabled="disabled" value="<">
		<%
			}
		%>
		<%
			if (pg < numpages) {
  		%>
				<input class="steer" type="button" name="nextbtn" onclick="dispResultsButtonPressed('next', <%=pg+1%>)" value=">">
				<input class="steer" type="button" name="lastbtn" onclick="dispResultsButtonPressed('last', <%=numpages%>)" value=">>">
		<%
			} 
			else {
		%>
			<input class="steer" type="button" name="nextbtn" disabled="disabled" value=">">
			<input class="steer" type="button" name="lastbtn" disabled="disabled" value=">>">
		<%
			}
		%>	
 </form>
</body>
</html>