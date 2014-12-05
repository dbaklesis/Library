/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libutil;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Dimitris
 */
public class rsBean {

  private List<mtBean> rowBean = new ArrayList();

  public void rsBean() {
  }

  // Add row
  public boolean addRsBean(mtBean rowBean) {
    try {

      this.rowBean.add(rowBean);

    } catch (RuntimeException e) {
      
      return (false);
    }
    
    return (true);
    
  }

  // Return specified row
  public mtBean getRsBean(int row) {
      mtBean tmp = new mtBean();

    if ((row <= 0) || (row > this.getRsSize()))
    	return(null);
    
    try {

      tmp = rowBean.get(row-1);

    } catch (RuntimeException e) {

      return (null);
    }
    
    return (tmp);
  }
  
//Return specified row
 public void setRsBean(int row, mtBean element) {

   try {

     this.rowBean.set(row-1, element);

   } catch (RuntimeException e) {
   }
   
   return;
 }

  //Remove specified row
  public mtBean removeRsBean(int row) {
    mtBean tmp = new mtBean();
    
    try {
      
        tmp = rowBean.remove(row-1);
        
    } catch (RuntimeException e) {
      
        return(null);
    }
    
    return (tmp);
  }

   //Remove specified row
  public void collapseRs() {
      
    rowBean.clear();
        
  }
  
  // Get the number of rows
  public int getRsSize() {
    
    return(rowBean.size());
  
  }

  // Check number of rows is 0
  public boolean isEmpty() {
    return (this.rowBean.isEmpty());
  }
  
  public Integer findRsBean(int counter) {
	  int cursor;
	  
	  for (cursor=1 ; cursor < this.getRsSize() ; cursor++) {
		  
		mtBean mt = this.getRsBean(cursor);
		
		if (mt.getCounter() == counter)
			return cursor;
		
	  }
	  
	  return (0);
  }
}