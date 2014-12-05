
package libutil;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dimitris
 */
@WebServlet(name = "/RenameTableRec", urlPatterns = {"/Library/RenameTableRec"})
public class RenameTableRec extends HttpServlet {

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
 * @throws SQLException 
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException, SQLException {
	  
	  request.setCharacterEncoding("UTF-8");
    
    HttpSession session = request.getSession();
    State lib = (State) session.getAttribute("state");
    
    if (lib == null) {
	  	lib = new State();
	  	session.setAttribute("state", lib);
	}
    
    libUtilities util = new libUtilities();
    
    // Extract parameters
    
    String tblname = request.getParameter("tbl");
    String from = request.getParameter("tblOpt");
    String to = request.getParameter("tblto");
    
    // Check if entry exists in type table
    
    String tbl[];
    if ((tbl = util.mapTable(tblname)) == null)
    	return;
    
    //Entry exists in table. Update it.
    
    String sql = "UPDATE IGNORE " + tbl[1] + " SET " + tbl[2] + "=?" + " WHERE " + tbl[2] + "=?";
    
    PreparedStatement prstatement=null;
	prstatement = lib.getConnection().prepareStatement(sql);
	prstatement.setString(1, to);
	prstatement.setString(2, from);
	prstatement.executeUpdate();

    // Change item in main table
    
    sql = "UPDATE IGNORE lib_table_main" + " SET " + tbl[2] + "=?" + " WHERE " + tbl[2] + "=" + "?";
    
    prstatement=null;
    prstatement = lib.getConnection().prepareStatement(sql);
    prstatement.setString(1, to);
    prstatement.setString(2, from);

   	prstatement.executeUpdate();
   	
   	// Reload page for next request

    RequestDispatcher dispatcher = request.getRequestDispatcher("LibTableRename.jsp");
    dispatcher.forward(request, response);
        
    return;
 
}
  

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
    try {
		processRequest(request, response);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
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
    try {
		processRequest(request, response);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
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
