/*
    Document   : BrowseUpdateDB.java
    Created on : 13 Μαϊ 2011, 10:56:14 πμ
    Author     : Dimitris Baklesis
*/

package libutil;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import java.sql.*;
import javax.servlet.http.*;

/**
 *
 * @author Dimitris Baklesis
 */
@WebServlet(name = "BrowseUpdateDB", urlPatterns = {"/BrowseUpdateDB"})
public class BrowseUpdateDB extends HttpServlet {


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
    //response.setContentType("text/html;charset=UTF-8");
	  
    request.setCharacterEncoding("UTF-8");

    // Get conection object
    
    HttpSession session = request.getSession();
    State lib = (State) session.getAttribute("state");
    if (lib == null) {
    	lib = new State();
    	session.setAttribute("state", lib);
    }
    
    libUtilities util = new libUtilities();
    rsBean rsb = (rsBean)session.getAttribute("rsb");
    String direction = request.getParameter("buttonpressed");
    String mode = request.getParameter("mode");
    Integer cursor = (Integer)session.getAttribute("cursor");
    
    // Mode is 'Browse'. Change cursor and return
    RequestDispatcher dispatcher;
    if (mode.equals("browse")) {
    	if (direction.equals("previous")) {
	    	if (cursor > 1) {
	    		cursor = cursor - 1;
	    		session.setAttribute("cursor", cursor);
	    	}
	    	dispatcher = request.getRequestDispatcher("/LibAddRecord.jsp?mode=browse&cursor=" + cursor);
	    	dispatcher.forward(request, response);
	    	return;
    	}
	    else if (direction.equals("after")) {
	    	if (cursor < rsb.getRsSize()) {
	    		cursor = cursor + 1;
	    		session.setAttribute("cursor", cursor);
	    	}
	    	dispatcher = request.getRequestDispatcher("/LibAddRecord.jsp?mode=browse&cursor=" + cursor);
	    	dispatcher.forward(request, response);
	    	return;
	    }
    	
    	return;
    }

    // Get data from form
    mtBean mt = getInput(request);
    
    if (mt.isEmpty() == false) {
    	
    	 boolean result=false;
	    if ((mode.equals("add") || (mode.equals("add_while_editing"))) && (cursor == rsb.getRsSize() && 
	    		(rsb.getRsBean(cursor).matches(mt) == true))) {
	    	
	    	if (util.libIsCommited(mt.getCounter(), lib.getConnection().createStatement()) == false) {
	    		result = mtBuildSQLInsert(rsb.getRsBean(cursor), mt, lib);
	    		if (result == false) {
	    			dispatcher = request.getRequestDispatcher("/WEB-INF/Message.jsp?message=Σφάλμα στήν βάση δεδομένων.");
	    			dispatcher.forward(request, response);
		    	return;
	    		}
	    	}
	    	
	    	rsb.setRsBean(cursor, mt);
	    	session.setAttribute("rsb", rsb);
	    	
	    	dispatcher = request.getRequestDispatcher("/LibAddRecord.jsp?mode=add_while_editing");
	    	dispatcher.forward(request, response);
	    	
	    	return;
	    }
	    else if (mode.equals("edit") || mode.equals("add_while_editing")) {
	    	
		    if (rsb.getRsBean(cursor).matches(mt) == false) {
		    	if (util.libIsCommited(mt.getCounter(), lib.getConnection().createStatement()) == false)
		    		result = mtBuildSQLInsert(rsb.getRsBean(cursor), mt, lib);
		    	else
		    		result = mtBuildSQLUpdate(rsb.getRsBean(cursor), mt, lib);
		   		
		   		rsb.setRsBean(cursor, mt);
			   	session.setAttribute("rsb", rsb);
		    	//else
		    		//result = mtBuildSQLUpdate(rsb.getRsBean(cursor), mt, lib);
		    } 
		    
		    if (mode.equals("add_while_editing")) {
		    	
		    	dispatcher = request.getRequestDispatcher("/LibAddRecord.jsp?mode=add_while_editing");
		    	dispatcher.forward(request, response);
		    	return;
		    }
		    
	    } else {
	 
		    	if (direction.equals("previous")) {
			    	if (cursor > 1) {
			    		cursor = cursor - 1;
			    		session.setAttribute("cursor", cursor);
			    	}
			    	dispatcher = request.getRequestDispatcher("/LibAddRecord.jsp?mode=edit&cursor=" + cursor);
			    	dispatcher.forward(request, response);
			    	return;
		    	}
			    else if (direction.equals("after")) {
			    	if (cursor < rsb.getRsSize()) {
			    		cursor = cursor + 1;
			    		session.setAttribute("cursor", cursor);
			    	}
			    	dispatcher = request.getRequestDispatcher("/LibAddRecord.jsp?mode=edit&cursor=" + cursor);
			    	dispatcher.forward(request, response);
			    	return;
			    }
	    
	    	if (result == false) {
	    		//dispatcher = request.getRequestDispatcher("/WEB-INF/NothingtoAdd.html");
	    		dispatcher = request.getRequestDispatcher("/WEB-INF/DatabaseError.html");
	    		dispatcher.forward(request, response);
	    	}
	    	
	    	return;
	    }
	  }
    
		    
    	if (direction.equals("previous")) {
	    	if (cursor > 1)
	    		cursor = cursor - 1;
    	}
	    else if (direction.equals("after")) {
		   	if (cursor < rsb.getRsSize())
		   		cursor = cursor + 1;
	    }
    		
    session.setAttribute("cursor", cursor);
 	session.setAttribute("rsb", rsb);
    dispatcher = request.getRequestDispatcher("/LibAddRecord.jsp?mode=edit" + "&cursor=" + cursor);
    dispatcher.forward(request, response);
    return;
}

  private mtBean getInput(HttpServletRequest rq) {
	libUtilities util = new libUtilities();
    mtBean mt = new mtBean();

    mt.setCode(util.normalize(rq.getParameter("code")));
    mt.setTypeofDoc(util.normalize(rq.getParameter("tod")));
    mt.setTitle(util.normalize(rq.getParameter("title")));
    mt.setCounter(Integer.parseInt(rq.getParameter("counter")));
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

private boolean mtBuildSQLInsert(mtBean oldmt, mtBean newmt, State lib) {
    String sCol;
    String sVal;
    String q;
    Boolean added;
    String[] prepTable = new String[20];
	int index=0;
    
    q = "INSERT INTO lib_table_main ";
    sCol = "(";
    sVal = "VALUES (";

    added = false;

    if (((newmt.getCode() != null) && (oldmt.getCode() == null)) || ((newmt.getCode() == null) && (oldmt.getCode() != null))) {
    	if (added == true) {
    		sCol += ",";
    		sVal += ",";
        }
        sCol += "CODE";
        //sVal += "'" + newmt.getCode() + "'";
        sVal += "?";
        added = true;
        prepTable[index++] = newmt.getCode();
    } else if ((newmt.getCode() != null) && (oldmt.getCode() != null))
  		  if (newmt.getCode().equals(oldmt.getCode()) == false) {
  			if (added == true) {
  				sCol += ",";
  				sVal += ",";
  	        }
  	        sCol += "CODE";
  	        //sVal += "'" + newmt.getCode() + "'";
  	        sVal += "?";
  	        added = true;
  	        prepTable[index++] = newmt.getCode();
  		  }
    
    if (((newmt.getAuthor() != null) && (oldmt.getAuthor() == null)) || ((newmt.getAuthor() == null) && (oldmt.getAuthor() != null))) {
    	if (added == true) {
    		sCol += ",";
    		sVal += ",";
        }
        sCol += "AUTHOR";
        //sVal += "'" + newmt.getAuthor() + "'";
        sVal += "?";
        added = true;
        prepTable[index++] = newmt.getAuthor();
    } else if ((newmt.getAuthor() != null) && (oldmt.getAuthor() != null))
    	if (newmt.getAuthor().equals(oldmt.getAuthor()) == false) {
    		if (added == true) {
    			sCol += ",";
  				sVal += ",";
  	        }
  	        sCol += "AUTHOR";
  	        //sVal += "'" + newmt.getAuthor() + "'";
  	        sVal += "?";
  	        added = true;
  	        prepTable[index++] = newmt.getAuthor();
    	}
    
    if (((newmt.getTitle() != null) && (oldmt.getTitle() == null)) || ((newmt.getTitle() == null) && (oldmt.getTitle() != null))) {
    	if (added == true) {
    		sCol += ",";
    		sVal += ",";
        }
        sCol += "TITLE";
        //sVal += "'" + newmt.getTitle() + "'";
        sVal += "?";
        added = true;
        prepTable[index++] = newmt.getTitle();
    } else if ((newmt.getTitle() != null) && (oldmt.getTitle() != null))
    	if (newmt.getTitle().equals(oldmt.getTitle()) == false) {
    		if (added == true) {
    			sCol += ",";
  				sVal += ",";
  	        }
  	        sCol += "TITLE";
  	        //sVal += "'" + newmt.getTitle() + "'";
  	        sVal += "?";
  	        added = true;
  	        prepTable[index++] = newmt.getTitle();
    	}
    
    if (((newmt.getCategory() != null) && (oldmt.getCategory() == null)) || ((newmt.getCategory() == null) && (oldmt.getCategory() != null))) {
    	if (added == true) {
    		sCol += ",";
    		sVal += ",";
        }
        sCol += "CATEGORY";
        //sVal += "'" + newmt.getCategory() + "'";
        sVal += "?";
        added = true;
        prepTable[index++] = newmt.getCategory();
    } else if ((newmt.getCategory() != null) && (oldmt.getCategory() != null))
    	if (newmt.getCategory().equals(oldmt.getCategory()) == false) {
    		if (added == true) {
    			sCol += ",";
  				sVal += ",";
  	        }
  	        sCol += "CATEGORY";
  	        //sVal += "'" + newmt.getCategory() + "'";
  	        sVal += "?";
  	        added = true;
  	        prepTable[index++] = newmt.getCategory();
    	}

    if (((newmt.getSubjectKeywords() != null) && (oldmt.getSubjectKeywords() == null)) || ((newmt.getSubjectKeywords() == null) && (oldmt.getSubjectKeywords() != null))) {
    	if (added == true) {
    		sCol += ",";
    		sVal += ",";
        }
        sCol += "SUBJECT_KEY_WORDS";
        //sVal += "'" + newmt.getSubjectKeywords() + "'";
        sVal += "?";
        added = true;
        prepTable[index++] = newmt.getSubjectKeywords();
    } else if ((newmt.getSubjectKeywords() != null) && (oldmt.getSubjectKeywords() != null))
    	if (newmt.getSubjectKeywords().equals(oldmt.getSubjectKeywords()) == false) {
    		if (added == true) {
    			sCol += ",";
  				sVal += ",";
  	        }
  	        sCol += "SUBJECT_KEY_WORDS";
  	        //sVal += "'" + newmt.getSubjectKeywords() + "'";
  	        sVal += "?";
  	        added = true;
  	        prepTable[index++] = newmt.getSubjectKeywords();
    	}

    if (((newmt.getTypeofDoc() != null) && (oldmt.getTypeofDoc() == null)) || ((newmt.getTypeofDoc() == null) && (oldmt.getTypeofDoc() != null))) {
    	if (added == true) {
    		sCol += ",";
    		sVal += ",";
        }
        sCol += "TYPE_OF_DOC";
        //sVal += "'" + newmt.getTypeofDoc() + "'";
        sVal += "?";
        added = true;
        prepTable[index++] = newmt.getTypeofDoc();
    } else if ((newmt.getTypeofDoc() != null) && (oldmt.getTypeofDoc() != null))
    	if (newmt.getTypeofDoc().equals(oldmt.getTypeofDoc()) == false) {
    		if (added == true) {
    			sCol += ",";
  				sVal += ",";
  	        }
  	        sCol += "TYPE_OF_DOC";
  	        //sVal += "'" + newmt.getTypeofDoc() + "'";
  	        sVal += "?";
  	        added = true;
  	        prepTable[index++] = newmt.getTypeofDoc();
    	}
    
    if (((newmt.getPubRef() != null) && (oldmt.getPubRef() == null)) || ((newmt.getPubRef() == null) && (oldmt.getPubRef() != null))) {
    	if (added == true) {
    		sCol += ",";
    		sVal += ",";
        }
        sCol += "PUB_REF";
        //sVal += "'" + newmt.getPubRef() + "'";
        sVal += "?";
        added = true;
        prepTable[index++]  =  newmt.getPubRef();
    } else if ((newmt.getPubRef() != null) && (oldmt.getPubRef() != null))
    	if (newmt.getPubRef().equals(oldmt.getPubRef()) == false) {
    		if (added == true) {
    			sCol += ",";
  				sVal += ",";
  	        }
  	        sCol += "PUB_REF";
  	        //sVal += "'" + newmt.getPubRef() + "'";
  	        sVal += "?";
  	        added = true;
  	        prepTable[index++] = newmt.getPubRef();
    	}
 
    if (((newmt.getNotes() != null) && (oldmt.getNotes() == null)) || ((newmt.getNotes() == null) && (oldmt.getNotes() != null))) {
    	if (added == true) {
    		sCol += ",";
    		sVal += ",";
        }
        sCol += "NOTES";
        //sVal += "'" + newmt.getNotes() + "'";
        sVal += "?";
        added = true;
        prepTable[index++] = newmt.getNotes();
    } else if ((newmt.getNotes() != null) && (oldmt.getNotes() != null))
    	if (newmt.getNotes().equals(oldmt.getNotes()) == false) {
    		if (added == true) {
    			sCol += ",";
  				sVal += ",";
  	        }
  	        sCol += "NOTES";
  	        //sVal += "'" + newmt.getNotes() + "'";
  	        sVal += "?";
  	        added = true;
  	        prepTable[index++] = newmt.getNotes();
    	}
    
/*
    if (((newmt.getDateofEntry() != null) && (oldmt.getDateofEntry() == null)) || ((newmt.getDateofEntry() == null) && (oldmt.getDateofEntry() != null))) {
    	if (added == true) {
    		sCol += ",";
    		sVal += ",";
        }
        sCol += "DATE_OF_ENTRY";
        sVal += "'" + newmt.getDateofEntry() + "'";
        added = true;
    } else if ((newmt.getDateofEntry() != null) && (oldmt.getDateofEntry() != null))
    	if (newmt.getDateofEntry().equals(oldmt.getDateofEntry()) == false) {
    		if (added == true) {
    			sCol += ",";
  				sVal += ",";
  	        }
  	        sCol += "DATE_OF_ENTRY";
  	        sVal += "'" + newmt.getDateofEntry() + "'";
  	        added = true;
    	}
 */  

    if (((newmt.getSector() != null) && (oldmt.getSector() == null)) || ((newmt.getSector() == null) && (oldmt.getSector() != null))) {
    	if (added == true) {
    		sCol += ",";
    		sVal += ",";
        }
        sCol += "SECTOR";
        //sVal += "'" + newmt.getSector() + "'";
        sVal += "?";
        added = true;
        prepTable[index++] = newmt.getSector();
    } else if ((newmt.getSector() != null) && (oldmt.getSector() != null))
    	if (newmt.getSector().equals(oldmt.getSector()) == false) {
    		if (added == true) {
    			sCol += ",";
  				sVal += ",";
  	        }
  	        sCol += "SECTOR";
  	        //sVal += "'" + newmt.getSector() + "'";
  	        sVal += "?";
  	        added = true;
  	        prepTable[index++] = newmt.getSector();
    	}
    
    if (((newmt.getFolder() != null) && (oldmt.getFolder() == null)) || ((newmt.getFolder() == null) && (oldmt.getFolder() != null))) {
    	if (added == true) {
    		sCol += ",";
    		sVal += ",";
        }
        sCol += "FOLDER";
        //sVal += "'" + newmt.getFolder() + "'";
        sVal += "?";
        added = true;
        prepTable[index++] = newmt.getFolder();
    } else if ((newmt.getFolder() != null) && (oldmt.getFolder() != null))
    	if (newmt.getFolder().equals(oldmt.getFolder()) == false) {
    		if (added == true) {
    			sCol += ",";
  				sVal += ",";
  	        }
  	        sCol += "FOLDER";
  	        //sVal += "'" + newmt.getFolder() + "'";
  	        sVal += "?";
  	        added = true;
  	        prepTable[index++] = newmt.getFolder();
    	}

    if (((newmt.getFileLocation() != null) && (oldmt.getFileLocation() == null)) || ((newmt.getFileLocation() == null) && (oldmt.getFileLocation() != null))) {
    	if (added == true) {
    		sCol += ",";
    		sVal += ",";
        }
        sCol += "FILE_LOCATION";
        //sVal += "'" + newmt.getFileLocation() + "'";
        sVal += "?";
        added = true;
        prepTable[index++] = newmt.getFileLocation();
    } else if ((newmt.getFileLocation() != null) && (oldmt.getFileLocation() != null))
    	if (newmt.getFileLocation().equals(oldmt.getFileLocation()) == false) {
    		if (added == true) {
    			sCol += ",";
  				sVal += ",";
  	        }
  	        sCol += "FILE_LOCATION";
  	        //sVal += "'" + newmt.getFileLocation() + "'";
  	        sVal += "?";
  	        added = true;
  	        prepTable[index++] = newmt.getFileLocation();
    	}
    
    // Finish-up insert sql command
    
    if (added == true) {
    	sCol += ")";
    	sVal += ")";
    	q += sCol + " " + sVal;
    } else
    	return (false);
 
    // Prepare the statement (SQL prepared command)
	  PreparedStatement prstatement=null;
	  int i=0;
	    try {
	    	prstatement = lib.getConnection().prepareStatement(q);
	    	for (; i <= index; i++) {
	    		if (prepTable[i] == null)
	    				break;
	    		prstatement.setString(i+1, prepTable[i]);
	    	}

	    	prstatement.executeUpdate();
	      
	    } catch (SQLException e) {
	    	
	    	return (false);
	    }
    return (true);
    
  }
  
private boolean mtBuildSQLUpdate(mtBean oldmt, mtBean newmt, State lib) {
	  String q;
	  Boolean added;
	  String[] prepTable = new String[20];
	  int index=0;
	  
	  q = "UPDATE lib_table_main SET ";

	  added = false;

	  if (((newmt.getCode() != null) && (oldmt.getCode() == null)) || ((newmt.getCode() == null) && (oldmt.getCode() != null))) {
		  if (added == true) {
			  q += ",";
		  }
		  //q += "CODE=" +  "'"  + newmt.getCode() + "'";
		    q += "CODE=" +  "?";
		  added = true;
		  prepTable[index++] = newmt.getCode();
	  } else if ((newmt.getCode() != null) && (oldmt.getCode() != null))
			  if (newmt.getCode().equals(oldmt.getCode()) == false) {
				  if (added == true) {
					  q += ",";
				  }
				  //q += "CODE=" +  "'"  + newmt.getCode() + "'";
				  q += "CODE=" +  "?";
				  added = true;
				  prepTable[index++] = newmt.getCode();
			  }
				 
	  if (((newmt.getAuthor() != null) && (oldmt.getAuthor() == null)) || ((newmt.getAuthor() == null) && (oldmt.getAuthor() != null))) {
		  if (added == true) {
		      q += ",";
		    }
		    //q += "AUTHOR=" + "'" + newmt.getAuthor() + "'";
		  q += "AUTHOR=" + "?";
		    added = true;
		    prepTable[index++] = newmt.getAuthor();
	  } else if ((newmt.getAuthor() != null) && (oldmt.getAuthor() != null))
		  if (newmt.getAuthor().equals(oldmt.getAuthor()) == false) {
			  if (added == true) {
				  q += ",";
			  }
			  //q += "AUTHOR=" +  "'"  + newmt.getAuthor() + "'";
			  q += "AUTHOR=" +  "?";
			  added = true;
			  prepTable[index++] = newmt.getAuthor();
		  }
		    
	  if (((newmt.getTitle() != null) && (oldmt.getTitle() == null)) || ((newmt.getTitle() == null) && (oldmt.getTitle() != null))) {
		  if (added == true) {
		      q += ",";
		    }
		    //q += "TITLE=" + "'" + newmt.getTitle() + "'";
		  	q += "TITLE=" + "?";
		    added = true;
		    prepTable[index++] = newmt.getTitle();
	  } else if ((newmt.getTitle() != null) && (oldmt.getTitle() != null))
		  if (newmt.getTitle().equals(oldmt.getTitle()) == false) {
			  if (added == true) {
				  q += ",";
			  }
			  //q += "TITLE=" +  "'"  + newmt.getTitle() + "'";
			  q += "TITLE=" +  "?";
			  added = true;
			  prepTable[index++] = newmt.getTitle();
		  }
	  
	  if (((newmt.getCategory() != null) && (oldmt.getCategory() == null)) || ((newmt.getCategory() == null) && (oldmt.getCategory() != null))) {
		  if (added == true) {
		      q += ",";
		    }
		   //q += "CATEGORY=" + "'" + newmt.getCategory() + "'";
		  q += "CATEGORY=" + "?";
		    added = true;
		    prepTable[index++] = newmt.getCategory();
	  } else if ((newmt.getCategory() != null) && (oldmt.getCategory() != null))
		  if (newmt.getCategory().equals(oldmt.getCategory()) == false) {
			  if (added == true) {
				  q += ",";
			  }
			  //q += "CATEGORY=" +  "'"  + newmt.getCategory() + "'";
			  q += "CATEGORY=" + "?";
			  added = true;
			  prepTable[index++] = newmt.getCategory();
		  }
	  
	  if (((newmt.getSubjectKeywords() != null) && (oldmt.getSubjectKeywords() == null)) || ((newmt.getSubjectKeywords() == null) && (oldmt.getSubjectKeywords() != null))) {
		  if (added == true) {
		      q += ",";
		    }
		  //q += "SUBJECT_KEY_WORDS=" + "'" + newmt.getSubjectKeywords() + "'";
		  q += "SUBJECT_KEY_WORDS=" + "?";
		    added = true;
		    prepTable[index++] = newmt.getSubjectKeywords();
	  } else if ((newmt.getSubjectKeywords() != null) && (oldmt.getSubjectKeywords() != null))
		  if (newmt.getSubjectKeywords().equals(oldmt.getSubjectKeywords()) == false) {
			  if (added == true) {
				  q += ",";
			  }
			  //q += "SUBJECT_KEY_WORDS=" +  "'"  + newmt.getSubjectKeywords() + "'";
			  q += "SUBJECT_KEY_WORDS=" + "?";
			  added = true;
			  prepTable[index++] = newmt.getSubjectKeywords();
		  }

	  if (((newmt.getTypeofDoc() != null) && (oldmt.getTypeofDoc() == null)) || ((newmt.getTypeofDoc() == null) && (oldmt.getTypeofDoc() != null))) {
		  if (added == true) {
		      q += ",";
		  }
		  //q += "TYPE_OF_DOC=" + "'" + newmt.getTypeofDoc() + "'";
		  q += "TYPE_OF_DOC=" + "?";
		    added = true;
		    prepTable[index++] = newmt.getTypeofDoc();
	  } else if ((newmt.getTypeofDoc() != null) && (oldmt.getTypeofDoc() != null))
		  if (newmt.getTypeofDoc().equals(oldmt.getTypeofDoc()) == false) {
			  if (added == true) {
				  q += ",";
			  }
			  //q += "TYPE_OF_DOC=" +  "'"  + newmt.getTypeofDoc() + "'";
			  q += "TYPE_OF_DOC=" + "?";
			  added = true;
			  prepTable[index++] = newmt.getTypeofDoc();
		  }
	  
	  if (((newmt.getPubRef() != null) && (oldmt.getPubRef() == null)) || ((newmt.getPubRef() == null) && (oldmt.getPubRef() != null))) {
		  if (added == true) {
		      q += ",";
		    }
		    //q += "PUB_REF=" + "'" + newmt.getPubRef() + "'";
		  q += "PUB_REF=" + "?";
		    added = true;
		    prepTable[index++] = newmt.getPubRef();
	  } else if ((newmt.getPubRef() != null) && (oldmt.getPubRef() != null))
		  if (newmt.getPubRef().equals(oldmt.getPubRef()) == false) {
			  if (added == true) {
				  q += ",";
			  }
			  //q += "PUB_REF=" +  "'"  + newmt.getPubRef() + "'";
			  q += "PUB_REF=" + "?";
			  added = true;
			  prepTable[index++] = newmt.getPubRef();
		  }

	  if (((newmt.getNotes() != null) && (oldmt.getNotes() == null)) || ((newmt.getNotes() == null) && (oldmt.getNotes() != null))) {
		  if (added == true) {
		      q += ",";
		    }
		    //q += "NOTES=" + "'" + newmt.getNotes() + "'";
		    q += "NOTES=" + "?";
		    added = true;
		    prepTable[index++] = newmt.getNotes();
	  } else if ((newmt.getNotes() != null) && (oldmt.getNotes() != null))
		  if (newmt.getNotes().equals(oldmt.getNotes()) == false) {
			  if (added == true) {
				  q += ",";
			  }
			  //q += "NOTES=" +  "'"  + newmt.getNotes() + "'";
			  q += "NOTES=" + "?";
			  added = true;
			  prepTable[index++] = newmt.getNotes();
		  }

	  /*
	  if (newmt.getDateofEntry().equals(oldmt.getDateofEntry()) == false) {
	    if (added == true) {
	      q += ",";
	    }
	    q += "DATE_OF_ENTRY=" + "'" + newmt.getDateofEntry() + "'";
	    added = true;
	  }
	 */

	  if (((newmt.getSector() != null) && (oldmt.getSector() == null)) || ((newmt.getSector() == null) && (oldmt.getSector() != null))) {
		  if (added == true) {
		      q += ",";
		    }
		    //q += "SECTOR=" + "'" + newmt.getSector() + "'";
		  	q += "SECTOR=" + "?";
		    added = true;
		    prepTable[index++] = newmt.getSector();
	  } else if ((newmt.getSector() != null) && (oldmt.getSector() != null))
		  if (newmt.getSector().equals(oldmt.getSector()) == false) {
			  if (added == true) {
				  q += ",";
			  }
			  //q += "SECTOR=" +  "'"  + newmt.getSector() + "'";
			  q += "SECTOR=" + "?";
			  added = true;
			  prepTable[index++] = newmt.getSector();
		  }
	  
	  if (((newmt.getFolder() != null) && (oldmt.getFolder() == null)) || ((newmt.getFolder() == null) && (oldmt.getFolder() != null))) {
		  if (added == true) {
		      q += ",";
		    }
		    //q += "FOLDER=" + "'" + newmt.getFolder() + "'";
		  	q += "FOLDER=" + "?";
		    added = true;
		    prepTable[index++] = newmt.getFolder();
	  } else if ((newmt.getFolder() != null) && (oldmt.getFolder() != null))
		  if (newmt.getFolder().equals(oldmt.getFolder()) == false) {
			  if (added == true) {
				  q += ",";
			  }
			  //q += "FOLDER=" +  "'"  + newmt.getFolder() + "'";
			  q += "FOLDER=" + "?";
			  added = true;
			  prepTable[index++] = newmt.getFolder();
		  }


	  if (((newmt.getFileLocation() != null) && (oldmt.getFileLocation() == null)) || ((newmt.getFileLocation() == null) && (oldmt.getFileLocation() != null))) {
		  if (added == true) {
		      q += ",";
		    }
		    //q += "FILE_LOCATION=" + "'" + newmt.getFileLocation() + "'";
		  	q += "FILE_LOCATION=" + "?";
		    added = true;
		    
	  } else if ((newmt.getFileLocation() != null) && (oldmt.getFileLocation() != null))
		  if (newmt.getFileLocation().equals(oldmt.getFileLocation()) == false) {
			  if (added == true) {
				  q += ",";
			  }
			  //q += "FILE_LOCATION=" +  "'"  + newmt.getFileLocation() + "'";
			  q += "FILE_LOCATION=" + "?";
			  added = true;
			  prepTable[index++] = newmt.getFileLocation();
		  }
	  
	  if (added == false)
	  	return (false);
	  	
	  //q += " WHERE COUNTER=" + newmt.getCounter();
	  q += " WHERE COUNTER=" + "?";
	  //int counter = newmt.getCounter();
	  prepTable[index++] =  newmt.getCounter().toString();
	  
	  // Prepare the statement (SQL prepared command)
	  PreparedStatement prstatement=null;
	  int i=0;
	  try {
		  prstatement = lib.getConnection().prepareStatement(q);
		  for (; i < index; i++) {
			  if (prepTable[i] == null)
				  prepTable[i] = "";
			  	  //break;
			  prstatement.setString(i+1, prepTable[i]);
		  }
		  // Add the integer --> counter
		  //statement.setString(i+1, counter.tp);

		  prstatement.executeUpdate();
	      
	    } catch (SQLException e) {
	    	
	    	return (false);
	    }
	    
	  return (true);
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
