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
 * Servlet implementation class AddTableRec
 */
@WebServlet({ "/DelTableRec", "/Library/DelTableRec" })
public class DelTableRec extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DelTableRec() {
        super();
        // TODO Auto-generated constructor stub
    }
    
protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

	request.setCharacterEncoding("UTF-8");
    HttpSession session = request.getSession();
    //String tblname = (String)session.getAttribute("tblname");
    String tblname = request.getParameter("tblname");
    String tblentry = request.getParameter("tblOpt");

      libUtilities util = new libUtilities();
	    
	    State lib = (State) session.getAttribute("state");
	    if (lib == null) {
	    	lib = new State();
	    	session.setAttribute("state", lib);
	    }
	    
	    String tbl[];
	    if ((tbl = util.mapTable(tblname)) == null)
	    	return ;
	    
	    String sql = "DELETE FROM " + tbl[1] + " WHERE " + tbl[2] + "=?";
	    
	    // Delete entry from table
	    
	    PreparedStatement prstatement=null;
	    try {
	    	prstatement = lib.getConnection().prepareStatement(sql);
	    	prstatement.setString(1, tblentry);
	    	
	    	prstatement.executeUpdate();
	    	
	    } catch (SQLException e) {
	    	System.out.println(e.getMessage());
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Message.jsp?message=Σφάλμα στήν βάση δεδομένων κάτά τήν διαγραφή στοιχείου '" + tblentry + "' από τόν πίνακα.");
	    	dispatcher.forward(request, response);
	    	return ;
	    } finally {
	    	if (prstatement != null)
	    		prstatement.close();
	    }
	    
	    // Replace all matching entries from main table with SPACE
	    
	    sql = "UPDATE lib_table_main " + "SET " + tbl[2] + "=?" +  " WHERE " + tbl[2] + "=?";
	    
	    // Update all records
	    
	    try {
	    	prstatement = lib.getConnection().prepareStatement(sql);
	    	prstatement.setNull(1, java.sql.Types.VARCHAR);
	    	prstatement.setString(2, tblentry);
	    	
	    	prstatement.executeUpdate();
	    	
	    } catch (SQLException e) {
	    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Message.jsp?message=Σφάλμα στήν βάση δεδομένων κάτά τήν διαγραφή στοιχείου  '" + tblentry + "' από τόν πίνακα από τόν πίνακα.");
	    	dispatcher.forward(request, response);
	    	return;
	    } finally {
	    	if (prstatement != null)
	    		prstatement.close();
	    }
	    
	 // Reload page for next request

	    RequestDispatcher dispatcher = request.getRequestDispatcher("LibDeleteTableRec.jsp");
	    dispatcher.forward(request, response);
	    
	    return;
       
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processRequest(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processRequest(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
