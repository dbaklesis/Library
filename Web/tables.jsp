<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="libutil.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body>

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
String lbl = request.getParameter("lbl");
String param = request.getParameter("tablename");

String[] mapt = util.mapTable(param);

String sql = "SELECT " + mapt[2] + " FROM " + mapt[1] + " ORDER BY "  + mapt[2] + " ASC";;

statement.executeQuery(sql);
ResultSet rs= statement.getResultSet();

	String str=null;
	if (lbl != null)
		str = "<label style='color:#ffffff'>" + lbl + "</label>";
	str += "<select id='tblOpt' name='tblOpt'>";
	str += "<option></option>";
	while (rs.next()) {
		str += "<option>" +  rs.getString(1) +  "</option>";
	}
	str += "</select>";
	out.print(str);
%>
</body>
</html>