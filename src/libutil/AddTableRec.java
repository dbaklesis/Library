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
@WebServlet({ "/AddTableRec", "/Library/AddTableRec" })
public class AddTableRec extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTableRec() {
        super();
        // TODO Auto-generated constructor stub
    }
    
protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

/*
	response.setContentType("text/html");
	response.setCharacterEncoding("UTF-8");
  */
	
    request.setCharacterEncoding("UTF-8");
	HttpSession session = request.getSession();
	State lib = (State) session.getAttribute("state");
	
	if (lib == null) {
		lib = new State();
		session.setAttribute("state", lib);
	}
  
	libUtilities util = new libUtilities();
  
	String tblSelect = request.getParameter("tblSelect");
	String newrec = request.getParameter("tblNewRec");

	String tbl[];
	if ((tbl = util.mapTable(tblSelect)) == null)
	  return;

  	String sql = "INSERT INTO " + tbl[1] + " " + "(" + tbl[2] + ")"+ " VALUES (?)";
	      
  	PreparedStatement prstatement=null;
    try {
    	prstatement = lib.getConnection().prepareStatement(sql);
    	prstatement.setString(1, newrec);

    	prstatement.executeUpdate();
      
    } catch (SQLException e) {
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Message.jsp?message=Στοιχείο " + "'" + newrec + "'" + " υπάρχει ήδη στόν πίνακα.");
    	dispatcher.forward(request, response);
    	return;
    }
    
    RequestDispatcher dispatcher = request.getRequestDispatcher("/LibAddTableRec.jsp");
    dispatcher.forward(request, response);
    return;
    
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
