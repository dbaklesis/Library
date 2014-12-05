<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <head>
    <title>Library - Επιτυχής Διαγραφή</title>
  </head>
  
  <a href="LibAdmin.jsp">Αρχική</a> 
    <br>
    <br>
    <% String str = request.getParameter("index");%> 
    <a href="LibDisplaySearchResults.jsp?page=<%=request.getParameter("index")%>">Αποτελέσματα Αναζήτησης</a>
    
  <body>
    <h1>Επιτυχής Εγγραφή</h1>
  </body>
</html>
