/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libutil;

//import libutil.*;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;

import java.sql.*;

import javax.servlet.http.*;

//import org.w3c.dom.css.Counter;

/**
 *
 * @author dimitris baklesis
 */
@WebServlet(name = "InputEditDelete", urlPatterns = {"/InputEditDelete"})
public class InputEditDelete extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.setContentType("text/html;charset=UTF-8");
    	request.setCharacterEncoding("UTF-8");
        //PrintWriter out = response.getWriter();
        
        // Get connection object
        HttpSession session = request.getSession();
        State lib = (State) session.getAttribute("state");
	    if (lib == null) {
	    	lib = new State();
	    	session.setAttribute("state", lib);
	    }
	    
	    boolean result = delRecordFromMainTable(request, lib);
	    RequestDispatcher dispatcher;
	    if (result == false) {
	    	dispatcher = request.getRequestDispatcher("/WEB-INF/DatabaseError.html");
	    	dispatcher.forward(request, response);
	    }
        
	    // Success return to add/edit page
	    
	    rsBean rsb = (rsBean)session.getAttribute("rsb");
	    if (rsb.getRsSize() == 0)
	    	dispatcher = request.getRequestDispatcher("/LibAdmin.jsp");
	    else
	    	dispatcher = request.getRequestDispatcher("/LibAddRecord.jsp?mode=deleted");
	    
        dispatcher.forward(request, response);
}
    
private boolean delRecordFromMainTable(HttpServletRequest rq, State lib) {
	String sql;
    libUtilities util = new libUtilities();
    	
    HttpSession session = rq.getSession();
    Integer cursor = (Integer)session.getAttribute("cursor");
    rsBean rsb = (rsBean)session.getAttribute("rsb");
 	    
    sql = util.constructDeleteString("COUNTER", rsb.getRsBean(cursor).getCounter().toString());
    if (sql == null)
    	return (false);
        
    // Execute SQL DELETE
        
    try {        
    	Statement statement = lib.getConnection().createStatement();
        statement.executeUpdate(sql);  
    } catch (SQLException e) { 
          return(false);
    }
    	
    rsb.removeRsBean(cursor);
    if (cursor > 1)
    	cursor--;
    	
    else
    	cursor = 1;
    
    session.setAttribute("cursor", cursor);
    session.setAttribute("rsb", rsb);
        
    return(true);
}
/* 
    private mtBean getInput(HttpServletRequest rq) {
    	libUtilities util = new libUtilities();
    mtBean mt = new mtBean();
    
    mt.setCode(util.normalize(rq.getParameter("code")));
    mt.setCounter(Integer.parseInt(rq.getParameter("counter")));
    mt.setTypeofDoc(util.normalize(rq.getParameter("tod")));
    mt.setTitle(util.normalize(rq.getParameter("title")));
    mt.setAuthor(util.normalize(rq.getParameter("author")));
    mt.setPubRef(util.normalize(rq.getParameter("pub_ref")));
    mt.setNotes(util.normalize(rq.getParameter("notes")));
    mt.setSector(util.normalize(rq.getParameter("sector")));
    mt.setCategory(util.normalize(rq.getParameter("category")));
    mt.setSubjectKeywords(util.normalize(rq.getParameter("subject_key_words")));
    mt.setFolder(util.normalize(rq.getParameter("folder")));
    mt.setFileLocation(util.normalize(rq.getParameter("file_location")));

    return (mt);
}
*/  
/*
    private String mtBuildSQL(mtBean mt) {
    String q;
    Boolean added;

    q = "UPDATE lib_table_main SET ";

    added = false;

    if ((mt.getCode() != null) && (mt.getCode().isEmpty() == false)) {
      if (added == true) {
        q += ",";
      }
      q += "CODE=" +  "'"  + mt.getCode() + "'";
      added = true;
    }

    if ((mt.getAuthor() != null) && (mt.getAuthor().isEmpty() == false)) {
      if (added == true) {
        q += ",";
      }
      q += "AUTHOR=" + "'" + mt.getAuthor() + "'";
      added = true;
    }

    if ((mt.getTitle() != null) && (mt.getTitle().isEmpty() == false)) {
      if (added == true) {
        q += ",";
      }
      q += "TITLE=" + "'" + mt.getTitle() + "'";
      added = true;
    }

    if ((mt.getCategory() != null) && (mt.getCategory().isEmpty() == false)) {
      if (added == true) {
        q += ",";
      }
      q += "CATEGORY=" + "'" + mt.getCategory() + "'";
      added = true;
    }

    if ((mt.getSubjectKeywords() != null) && (mt.getSubjectKeywords().isEmpty() == false)) {
      if (added == true) {
        q += ",";
      }
      q += "SUBJECT_KEY_WORDS=" + "'" + mt.getSubjectKeywords() + "'";
      added = true;
    }

    if ((mt.getTypeofDoc() != null) && (mt.getTypeofDoc().isEmpty() == false)) {
      if (added == true) {
        q += ",";
      }
      q += "TYPE_OF_DOC=" + "'" + mt.getTypeofDoc() + "'";
      added = true;
    }

    if ((mt.getPubRef() != null) && (mt.getPubRef().isEmpty() == false)) {
      if (added == true) {
        q += ",";
      }
      q += "PUB_REF=" + "'" + mt.getPubRef() + "'";
      added = true;
    }

    if ((mt.getNotes() != null) && (mt.getNotes().isEmpty() == false)) {
      if (added == true) {
        q += ",";
      }
      q += "NOTES=" + "'" + mt.getNotes() + "'";
      added = true;
    }

    if ((mt.getDateofEntry() != null) && (mt.getDateofEntry().isEmpty() == false)) {
      if (added == true) {
        q += ",";
      }
      q += "DATE_OF_ENTRY=" + "'" + mt.getDateofEntry() + "'";
      added = true;
    }

    if ((mt.getSector() != null) && (mt.getSector().isEmpty() == false)) {
      if (added == true) {
        q += ",";
      }
      q += "SECTOR=" + "'" + mt.getSector() + "'";
      added = true;
    }

    if ((mt.getFolder() != null) && (mt.getFolder().isEmpty() == false)) {
      if (added == true) {
        q += ",";
      }
      q += "FOLDER=" + "'" + mt.getFolder() + "'";
      added = true;
    }

    if ((mt.getFileLocation() != null) && (mt.getFileLocation().isEmpty() == false)) {
      if (added == true) {
        q += ",";
      }
      q += "FILE_LOCATION=" + "'" + mt.getFileLocation() + "'";
      added = true;
    }

    q += " WHERE COUNTER=" + mt.getCounter();
    
    return (q);
  }
  */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
