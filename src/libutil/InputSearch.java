package libutil;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

/** A simple servlet that connects to a database and
 *  presents the results frofhe query in an HTML
 *  table. The driver, URL, username, password,
 *  and query are taken from form input parameters.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages 2nd Edition
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2003 Marty Hall and Larry Brown.
 *  May be freely used or adapted.
 */
public class InputSearch extends HttpServlet {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
/*
 * public void doPost(HttpServletRequest request,
          HttpServletResponse response)
          throws ServletException, IOException {
*/
/*
	request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
*/
	request.setCharacterEncoding("UTF-8");
	
    // Get conection object
    HttpSession session = request.getSession();
    State lib = (State) session.getAttribute("state");
    if (lib == null) {
    	lib = new State();
    	session.setAttribute("state", lib);
    }
    
    session.setAttribute("page", 1);    // Initially 10 rows displayed per page
    session.setAttribute("rowsperpage", 10);
      
    String[] tbl = new String[20];
    String query = parseInput(request, lib, tbl);
      
    if (query == null) {
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/LibMainSearch.jsp?error=1");
	    dispatcher.forward(request, response);
	    return;
	}
      
      rsBean rsb = (rsBean)session.getAttribute("rsb");
      if (rsb != null) {
        rsb.collapseRs();
        session.setAttribute("rsb", null);
      }
      
      int count = loadResultSetPrep(query, tbl, lib, request, "rsb");
      
      RequestDispatcher dispatcher;
      if (count > 0) {
        //dispatcher = request.getRequestDispatcher("LibDisplaySearchResults.jsp?page=1");
        dispatcher = request.getRequestDispatcher("LibDisplaySearchResults.jsp?page=1");
        dispatcher.forward(request, response);
      } else if (count == 0) {
          dispatcher = request.getRequestDispatcher("/WEB-INF/Message.jsp?message=Καμμία εγγραφή μέ τέτοια στοχεία");
          dispatcher.forward(request, response);
      } else if (count < 0) {
          dispatcher = request.getRequestDispatcher("/LibMainSearch.jsp?error=1");
          dispatcher.forward(request, response); 
      }
          return;
  }
  
  private String parseInput(HttpServletRequest rq, State lib, String[] prepTable) {
    String q;
    Boolean added = false;
    int index=0;
    libUtilities util = new libUtilities();
    
    String counter = util.normalize(rq.getParameter("counter"));
    String tod = util.normalize(rq.getParameter("tod"));
    String title = util.normalize(rq.getParameter("title"));
    String author = util.normalize(rq.getParameter("author"));
    String pub_ref = util.normalize(rq.getParameter("pub_ref"));
    String notes = util.normalize(rq.getParameter("notes"));
    String sector = util.normalize(rq.getParameter("sector"));
    String category = util.normalize(rq.getParameter("category"));
    String subject_key_words = util.normalize(rq.getParameter("subject_key_words"));
    String folder = util.normalize(rq.getParameter("folder"));
    String file_location = util.normalize(rq.getParameter("file_location"));

    q = "SELECT * FROM lib_table_main WHERE ";

    if (counter != null) {
    	if (added == true)
    		q += "AND ";
      	q += "(COUNTER = " + counter + ") ";
      	added = true;
    }

    if (tod != null) {
      if (added == true)
        q += " AND ";
      //q += "(TYPE_OF_DOC = '" + tod + "')";   <<<*************************** no %
      q += "(TYPE_OF_DOC = ?)";
      prepTable[index++] = tod;
      added = true;
    }

    if (title != null) {
      if (added == true)
        q += " AND ";
      //q += "(TITLE LIKE '%" + title + "%')";
      q += "(TITLE LIKE ?)";
      prepTable[index++] = "%" + title + "%";
      added = true;
    }

    if (author != null) {
      if (added == true) 
        q += " AND ";
      //q += "(AUTHOR LIKE '%" + author + "%')";
      q += "(AUTHOR LIKE ?)";
      prepTable[index++] = "%" + author + "%";
      added = true;
    }

    if (pub_ref != null) {
      if (added == true)
        q += " AND ";
      //q += "(PUB_REF LIKE ?)";
      q += "(PUB_REF LIKE ?)";
      prepTable[index++] = "%" + pub_ref + "%";
      added = true;
    }

    if (notes != null) {
      if (added == true)
        q += " AND ";
      //q += "(NOTES LIKE ?)";
      q += "(NOTES LIKE ?)";
      prepTable[index++] = "%" + notes + "%";
      added = true;
    }

    if (sector != null) {
      if (added == true)
        q += " AND ";
      //q += "(SECTOR = '" + sector + "')";			<<<*************************** no %
      q += "(SECTOR = ?)";
      prepTable[index++] = sector;
      added = true;
    }

    if (category != null) {
      if (added == true)
        q += " AND ";
      //q += "(CATEGORY = '" + category + "')"; 	<<<*************************** no %
      q += "(CATEGORY = ?)";
      prepTable[index++] = category;
      added = true;
    }

    if (subject_key_words != null) {
      if (added == true)
        q += " AND ";
      
      // Extracts key words.
      StringTokenizer st = new StringTokenizer(subject_key_words);
      while (st.hasMoreTokens()) {
        //q = q + " (SUBJECT_KEY_WORDS LIKE '%" + st.nextToken() + "%')";
    	  q = q + " (SUBJECT_KEY_WORDS LIKE ?)";
    	  prepTable[index++] = "%" + st.nextToken() + "%";
        if (st.hasMoreTokens())
          q = q + " OR";
    }
      added = true;
    }

    if (folder != null) {
      if (added == true)
        q += " AND ";
      //q += "(FOLDER = '" + folder + "')";			<<<*************************** no %
      q += "(FOLDER = ?)";
      prepTable[index++] = folder;
      added = true;
    }

    if (file_location != null) {
      if (added == true)
        q += " AND ";
      //q += "(FILE_LOCATION LIKE '%" + file_location + "%')";
      q += "(FILE_LOCATION LIKE ?)";
      prepTable[index++] = "%" + file_location + "%";
      added = true;
    }

    if (added == false) {
      q = "SELECT * FROM lib_table_main";
    }
    q = q + " ORDER BY COUNTER ASC";
 	    
    return (q);
  }
  
 private int loadResultSetPrep(String sql, String[] prepTable, State lib, HttpServletRequest request, String obj) {
		libUtilities util = new libUtilities();
	    rsBean rsb = new rsBean();
	 	    
	 
	 	ResultSet resultSet=null;
		PreparedStatement prstatement=null;
	    try {
	    	
	    	// Prepare the statement (SQL prepared command)
	    	int i=0;
		 	prstatement = lib.getConnection().prepareStatement(sql);
		 	while(prepTable[i] != null) {
		 	    prstatement.setString(i+1, prepTable[i]);
		 	    i++;
		 	}

		 	resultSet = prstatement.executeQuery();
		 	    
			while (resultSet.next()) {
  
				mtBean mtRow = new mtBean();

		 	    mtRow.setCode(util.normalize(resultSet.getString(resultSet.findColumn("CODE"))));
			    mtRow.setAuthor(util.normalize(resultSet.getString(resultSet.findColumn("AUTHOR"))));
			    mtRow.setTitle(util.normalize(resultSet.getString(resultSet.findColumn("TITLE"))));
			    mtRow.setCategory(resultSet.getString(resultSet.findColumn("CATEGORY")));
		        mtRow.setSubjectKeywords(util.normalize(resultSet.getString(resultSet.findColumn("SUBJECT_KEY_WORDS"))));
		        mtRow.setTypeofDoc(util.normalize(resultSet.getString(resultSet.findColumn("TYPE_OF_DOC"))));
		        mtRow.setPubRef(util.normalize(resultSet.getString(resultSet.findColumn("PUB_REF"))));
		        mtRow.setNotes(util.normalize(resultSet.getString(resultSet.findColumn("NOTES"))));
		        mtRow.setDateofEntry(util.normalize(resultSet.getString(resultSet.findColumn("DATE_OF_ENTRY"))));
		        mtRow.setCounter(Integer.parseInt(resultSet.getString(resultSet.findColumn("COUNTER"))));
		        mtRow.setSector(util.normalize(resultSet.getString(resultSet.findColumn("SECTOR"))));
		        mtRow.setFolder(util.normalize(resultSet.getString(resultSet.findColumn("FOLDER"))));
		        mtRow.setFileLocation(util.normalize(resultSet.getString(resultSet.findColumn("FILE_LOCATION"))));
		
			    if (rsb.addRsBean(mtRow) == false) {
			          return (-1);
			    }
			}
	      
	    } catch (SQLException sqle) {
	    	System.err.println("Error connecting: " + sqle);
	    } catch (Exception ex) {
	    	System.err.println("Error with input: " + ex);
	    }
	    
	      
	    HttpSession session = request.getSession();
	    session.setAttribute(obj, rsb);
	    
	    return (rsb.getRsSize());
	
 }
 
/**
 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
 */
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
	processRequest(request, response);
}

/**
 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
 */
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
	processRequest(request, response);
}
}

