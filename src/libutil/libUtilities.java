/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libutil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dimitris
 */
public class libUtilities {

  //private String v;

// Get last autoincrement value for unique COUNTER
  public long libGetLastId(Statement statement) throws SQLException {
  String sql;
  ResultSet rs;
  long id;
 
  sql = "SELECT MAX(counter) FROM lib_table_main";
  rs = statement.executeQuery(sql);
  rs.next();
  id = rs.getLong(1);
  
  return (id);
 }
  
//Get last autoincrement value for unique COUNTER
 public boolean libIsCommited(int counter, Statement statement) throws SQLException {
	 String sql;
	 ResultSet rs;
	
	 //sql = "SELECT MAX(counter) FROM lib_table_main";
	 sql = "SELECT COUNTER FROM lib_table_main WHERE COUNTER = " + counter;
	 rs = statement.executeQuery(sql);
	 
	 // If result set is empty
	 if (rs.next() == false)
		 return (false);
	 else
		 return (true);
}
 
 public String constructSelectString(String tblfield) {
 String query = null;
 
 	if (tblfield.equals("TYPE_OF_DOC"))
 		query = "SELECT TYPE_OF_DOC FROM lib_table_doc_type ORDER BY TYPE_OF_DOC ASC";
 	else if (tblfield.equals("CATEGORY"))
 		query = "SELECT CATEGORY FROM lib_table_category ORDER BY CATEGORY ASC";
 	else if (tblfield.equals("FOLDER"))
 		query = "SELECT FOLDER FROM lib_table_folder ORDER BY FOLDER ASC";
 	else if (tblfield.equals("SECTOR"))
 		query = "SELECT SECTOR FROM lib_table_sector ORDER BY SECTOR ASC";
 	
 return (query);
 
}
 
 public String constructUpdateString(String tblfield, String value) {
 String query = null;
 String str = null;
 
 	//if (! value.equals(""))
 		//	str = "'" + value + "'";
 	
 	if (tblfield.equals("TYPE_OF_DOC"))
 		query = "UPDATE lib_table_main SET TYPE_OF_DOC=" + str;
 	else if (tblfield.equals("CATEGORY"))
 		query = "UPDATE lib_table_main SET CATEGORY=" + str;
 	else if (tblfield.equals("FOLDER"))
		query = "UPDATE lib_table_main SET FOLDER=" + str;
	else if (tblfield.equals("SECTOR"))
		query = "UPDATE lib_table_main SET SECTOR=" + str;
 	
 	return (query);
}

public String constructDeleteString(String tblfield, String value) {
 String query = null;

 	if (tblfield.equals("COUNTER"))
 		//query = "DELETE FROM lib_table_main WHERE COUNTER=" + "'" + value + "'";
 		query = "DELETE FROM lib_table_main WHERE COUNTER=" + value;
 	if (tblfield.equals("TYPE_OF_DOC"))
 		query = "DELETE FROM lib_table_doc_type WHERE TYPE_OF_DOC=" + "'" + value + "'";
 	else if (tblfield.equals("CATEGORY"))
 		query = "DELETE FROM lib_table_category WHERE CATEGORY=" + "'" + value + "'";
 	else if (tblfield.equals("FOLDER"))
		query = "DELETE FROM lib_table_folder WHERE FOLDER=" + "'" + value + "'";
	else if (tblfield.equals("SECTOR"))
		query = "DELETE FROM lib_table_sector WHERE SECTOR=" + "'" + value + "'";
 	
 	return (query);
}


public int loadResultSet(String query, State lib, HttpServletRequest request, String obj) {
	libUtilities util = new libUtilities();
    rsBean rsb = new rsBean();

    try {

      Statement statement = lib.getConnection().createStatement();

      ResultSet resultSet = statement.executeQuery(query);
      
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
      
      HttpSession session = request.getSession();
      session.setAttribute(obj, rsb);
 
    } catch (SQLException sqle) {
      System.err.println("Error connecting: " + sqle);
    } catch (Exception ex) {
      System.err.println("Error with input: " + ex);
    }
    
    return (rsb.getRsSize());
  }

public String[] mapTable(String tableName) {
	
	String[] tblCategory = {"Κατηγορία", "lib_table_category", "CATEGORY"};
	String[] tblDocumentType = {"Είδος Εγγράφου", "lib_table_doc_type", "TYPE_OF_DOC"};
	String[] tblFolder = {"Φάκελος", "lib_table_folder", "FOLDER"};
	String[] tblSector = {"Κλάδος", "lib_table_sector", "SECTOR" };
 
	if (tableName.equals(tblCategory[0])) {
		return (tblCategory);
	}
	
	if (tableName.equals(tblDocumentType[0]))
		return tblDocumentType;
	
	if (tableName.equals(tblFolder[0]))
		return tblFolder;
	
	if (tableName.equals(tblSector[0]))
		return tblSector;
	
	return null;
}

public String nulltoblank(String target) {
	
	if (target == null)
		return ("");
	
	return (target);
}

public String normalize(String target) {

	if (target == null)
		return (null);
	
	if (target.isEmpty() == true)
		return(null);
	
	String str = target.trim();
	
	if (str.equals("null"))
		return (null);

	return(str);
}
}
