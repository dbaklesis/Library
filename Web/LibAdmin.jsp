<%-- 
	Project	   : v3.0
    Document   : LibAdmin.jsp
    Created on : 13 Ιουν 2011, 2:27:36 μμ
    Author     : Δημήτρης
--%>
<%@page import="libutil.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="LibAdmin.css">
<title>Library</title>
</head>
<h1>Library</h1>
<h2>Αρχική Σελίδα</h2>
<body>
	<a class="button" href="LibMainSearch.jsp"><span>Αναζήτηση</span></a>
	<br>
	<a class="button" href="LibAddRecord.jsp?mode=add"><span>Προσθήκη/Επεξ. Εγγραφής</span></a>
	<br>
	<a class="button" href="LibTablesAdmin.jsp"><span>Διαχείρηση Πινάκων</span></a>
	<br>
	<%
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
	%>
</body>
</html>
