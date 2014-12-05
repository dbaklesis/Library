<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="libutil.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%
State lib = (State) session.getAttribute("state");
if (lib == null) {
	lib = new State();
	session.setAttribute("state", lib);
}

Statement statement = lib.getConnection().createStatement();
String query;
ResultSet resultSet;
String str=request.getParameter("queryString");

String sql = "SELECT DISTINCT AUTHOR FROM lib_table_main WHERE AUTHOR LIKE '" + str + "%' LIMIT 10";

statement.executeQuery(sql);
ResultSet rs= statement.getResultSet();

	while (rs.next()){
		//out.println("<li onclick='fill(" + rs.getString("AUTHOR") + ");'>" + rs.getString("AUTHOR")+" </i>");
		//out.println("<li onclick=\"fill('AUTHOR')\">" + rs.getString("AUTHOR")+ " </i>");
		out.println("<li style=\"list-style-type: none\" onclick=\"fill('" + rs.getString("AUTHOR") + "')\">" + rs.getString("AUTHOR")+" </li>");
	}
%>
