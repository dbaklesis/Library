/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libutil;

import java.sql.*;

/**
 *
 * @author Dimitris
 */
class ConnBean {

    private String driver;
    private String url;
    //private String username;
    //private String password;
    private Connection connection;


    protected ConnBean () {
        
        driver = "com.mysql.jdbc.Driver";
        //url = "jdbc:mysql://localhost:3306/library";
        url = "jdbc:mysql://localhost:3306/library?useUnicode=yes&characterEncoding=UTF-8";
        String username = "lib";
        String password = "1234";
        
        //Properties properties = new Properties();
        //properties.put("user", "lib");
        //properties.put ("password", "1234");
        //properties.put ("charSet", "UTF-8");
        
        // Connect to database
        synchronized(this) {      
        try {
            // Load driver
            Class.forName(driver).newInstance();
            // Establish network connection to database.
            connection = DriverManager.getConnection(url, username, password);
            //connection = DriverManager.getConnection(url, properties);
            
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Error loading driver: " + cnfe);
        } catch (SQLException sqle) {
            System.err.println("Error connecting: " + sqle);
        } catch (Exception ex) {
            System.err.println("Error with input: " + ex);
        }

        /* 
        finally
        {
            if (connection != null)
            {
                try
                {
                    connection.close ();
                }
                catch (Exception e) {  }
            }
    
        }
	*/
     }
     
 }
    
    public String getDriver() {
        return (driver);
    }
    
    public Connection getConnection() {
        return (connection);
    }

}