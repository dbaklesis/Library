  /*
" * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libutil;

/**
 *
 * @author Dimitris
 */
public class mtBean {
        private String code;
        private String author;
        private String title;
        private String category;
        private String subjkeywords;
        private String typeofdoc;
        private String pubref;
        private String notes;
        private String dateofentry;
        private Integer counter;
        private String sector;
        private String folder;
        private String filelocation;
                
                
        public  mtBean() {
        
        }
        
        public String getCode() {
          if (this.code == null)
            return ("");
          else
            return(this.code);
        }
        
        public String getAuthor() {
        	
        	if (this.author != null)
        		return (this.author);
        	else
        		return ("");
        }
        
        public String getAuthor(int count) {
        	String str;
        	
        	if (this.author == null)
        		return ("");
        	
        	if (this.author.length() < count) {
        		count = this.author.length();
        		str = this.author.substring(0, count);
        	}
        	else
        		str = this.author.substring(0, count) + "...";
        	
        	return (str);
        }
        
        public String getTitle() {
        	
        	if (this.title != null)
        		return (this.title);
        	else
        		return ("");
        	
        }
        
        public String getTitle(int count) {
        	String str;
        	
        	if (this.title == null)
        		return ("");
        	
        	if (this.title.length() < count) {
        		count = this.title.length();
        		str = this.title.substring(0, count);
        	}
        	else
        		str = this.title.substring(0, count) + "...";
        	
        	return (str);
        }
       
        public String getCategory() {
        	
        	if (this.category != null)
        		return (this.category);
        	else
        		return (null);
        }
        
        public String getCategory(int count) {
        	String str;
        	
        	if (this.category == null)
        		return ("");
        	
        	if (this.category.length() < count) {
        		count = this.category.length();
        		str = this.category.substring(0, count);
        	}
        	else
        		str = this.category.substring(0, count) + "...";
        	
        	return (str);
        }
        
        public String getSubjectKeywords() {
        	
        	if (this.subjkeywords != null)
        		return (this.subjkeywords);
        	else
        		return ("");
        }
        
        public String getSubjectKeywords(int count) {
        	String str;
        	
        	if (this.subjkeywords == null)
        		return ("");
        	
        	if (this.subjkeywords.length() < count) {
        		if (this.subjkeywords.length() == 0)
        			return (null);
        		count = this.subjkeywords.length();
        		str = this.subjkeywords.substring(0, count);
        	}
        	else
        		str = this.subjkeywords.substring(0, count) + "->>";
        	
        	return (str);
        }
           
        public String getTypeofDoc() {
        	
        	if (this.typeofdoc != null)
        		return (this.typeofdoc);
        	else
        		return (null);
        }
        
        public String getTypeofDoc(int count) {
        	String str;
        	
        	if (this.typeofdoc == null)
        		return ("");
        	
        	if (this.typeofdoc.length() < count) {
        		count = this.typeofdoc.length();
        		str = this.typeofdoc.substring(0, count);
        	}
        	else
        		str = this.typeofdoc.substring(0, count) + "...";
        	
        	return (str);
        }
        
        public String getPubRef() {
        	
        	if (this.pubref == null)
        		return ("");
        	else
        		return (this.pubref);
        }
        
        public String getPubRef(int count) {
        	String str;
        	
        	if (this.pubref == null)
        		return ("");
        	
        	if (this.pubref.length() < count) {
        		count = this.pubref.length();
        		str = this.pubref.substring(0, count);
        	}
        	else
        		str = this.pubref.substring(0, count) + "...";
        	
        	return (str);
        }
        
        public String getNotes() {
        	
        	if (this.notes != null)
        		return (this.notes);
        	else
        		return ("");
        }
        
        public String getNotes(int count) {
        	String str;
        	
        	if (this.notes == null)
        		return ("");
        	
        	if (this.notes.length() < count) {
        		count = this.notes.length();
        		str = this.notes.substring(0, count);
        	}
        	else
        		str = this.notes.substring(0, count) + "...";
        	
        	return (str);
        }
        
        public String getDateofEntry() {
        	
        	if (this.dateofentry != null)
        		return (this.dateofentry);
        	else
        		return ("");
        }
        
        public Integer getCounter() {	
     
        	if (this.counter == null)
        		return (null);
        	else
        		return(this.counter);
        }
        
        public String getSector() {
        	
        	if (this.sector != null)
        		return (this.sector);
        	else
        		return (null);
        }
        
        public String getSector(int count) {
        	String str;
        	
        	if (this.sector == null)
        		return ("");
        	
        	if (this.sector.length() < count) {
        		count = this.sector.length();
        		str = this.sector.substring(0, count);
        	}
        	else
        		str = this.sector.substring(0, count) + "...";
        	
        	return (str);
        }
        
         public String getFolder() {
        	 
        	 if (this.folder != null)
        		 return (this.folder);
        	 else
        		 return (null);
        }
         
         public String getFolder(int count) {
         	String str;
         	
         	if (this.folder == null)
         		return ("");
         	
         	if (this.folder.length() < count) {
         		count = this.folder.length();
         		str = this.folder.substring(0, count);
         	}
         	else
         		str = this.folder.substring(0, count) + "...";
         	
         	return (str);
         }
         
        public String getFileLocation() {
        	
        	if (this.filelocation != null)
        		return (this.filelocation);
        	else
        		return ("");
        }
        
        public String getFileLocation(int count) {
         	String str;
         	
         	if (this.filelocation == null)
         		return ("");
         	
         	if (this.filelocation.length() < count) {
         		count = this.filelocation.length();
         		str = this.filelocation.substring(0, count);
         	}
         	else
         		str = this.filelocation.substring(0, count) + "...";
         	
         	return (str);
        }
        
        public void setCode(String code) {
          this.code = code;
        }
        
        public void setAuthor(String author) {
            this.author = author;
        }
        
        public void setTitle(String title) {
            this.title = title;
        } 
       
        public void setCategory(String category) {
             this.category = category;
        }
        
        public void setSubjectKeywords(String subjkeywords) {
            this.subjkeywords = subjkeywords;
        }
         
        public void setTypeofDoc(String typeofdoc) {
            this.typeofdoc = typeofdoc;
        }
        
        public void setPubRef(String pubref) {
            this.pubref = pubref;
        }
        
        public void setNotes(String notes) {
            this.notes = notes;
        }
        
        public void setDateofEntry(String dateofentry) {
            this.dateofentry = dateofentry;
        }
        
        public void setCounter(Integer counter) {
            this.counter = counter;
        }
        
        public void setSector(String sector) {
            this.sector = sector;
        }
        
        public void setFolder(String folder) {
            this.folder = folder;
        }
        
        public void setFileLocation(String filelocation) {
            this.filelocation = filelocation;
        }
        
public boolean matches(mtBean mt) {
	
	if ((this.author != null) && (mt.author != null)) {
		if (this.author.equals(mt.author) == false)
			return (false);
	} else if (((this.author != null) && (mt.author == null)) || ((this.author == null) && (mt.author != null)))
		return (false);
	
	if ((this.category != null) && (mt.category != null)) {
			if (this.category.equals(mt.category) == false)
				return (false);
	} else if (((this.category != null) && (mt.category == null)) || ((this.category == null) && (mt.category != null)))
		return (false);
   
    if ((this.code != null) && (mt.code != null)) {
			if (this.code.equals(mt.code) == false)
				return (false);
	} else if (((this.code != null) && (mt.code == null)) || ((this.code == null) && (mt.code != null)))
		return (false);
    
	if ((this.filelocation != null) && (mt.filelocation != null)) {
		if (this.filelocation.equals(mt.filelocation) == false)
			return (false);
	} else if (((this.filelocation != null) && (mt.filelocation == null)) || ((this.filelocation == null) && (mt.filelocation != null)))
		return (false);
	
	if ((this.folder != null) && (mt.folder != null)) {
		if (this.folder.equals(mt.folder) == false)
			return (false);
	} else if (((this.folder != null) && (mt.folder == null)) || ((this.folder == null) && (mt.folder != null)))
		return (false);
	
	if ((this.notes != null) && (mt.notes != null)) {
		if (this.notes.equals(mt.notes) == false)
			return (false);
	} else if (((this.notes != null) && (mt.notes == null)) || ((this.notes == null) && (mt.notes != null)))
		return (false);
   
	if ((this.pubref != null) && (mt.pubref != null)) {
		if (this.pubref.equals(mt.pubref) == false)
			return (false);
	} else if (((this.pubref != null) && (mt.pubref == null)) || ((this.pubref == null) && (mt.pubref != null)))
		return (false);
	
	if ((this.sector != null) && (mt.sector != null)) {
		if (this.sector.equals(mt.sector) == false)
			return (false);
	} else if (((this.sector != null) && (mt.sector == null)) || ((this.sector == null) && (mt.sector != null)))
		return (false);
   	
	if ((this.subjkeywords != null) && (mt.subjkeywords != null)) {
		if (this.subjkeywords.equals(mt.subjkeywords) == false)
			return (false);
	} else if (((this.subjkeywords != null) && (mt.subjkeywords == null)) || ((this.subjkeywords == null) && (mt.subjkeywords != null)))
		return (false);
	
 
	if ((this.title != null) && (mt.title != null)) {
		if (this.title.equals(mt.title) == false)
			return (false);
	} else if (((this.title != null) && (mt.title == null)) || ((this.title == null) && (mt.title != null)))
		return (false);
 
	if ((this.typeofdoc != null) && (mt.typeofdoc != null)) {
		if (this.typeofdoc.equals(mt.typeofdoc) == false)
			return (false);
	} else if (((this.typeofdoc != null) && (mt.typeofdoc == null)) || ((this.typeofdoc == null) && (mt.typeofdoc != null)))
		return (false);
    
    return (true);
}


public boolean isEmpty() {
     
	if (this.code != null)
		return (false);
	
	if (this.author != null)
		return (false);
	
	if (this.title != null)
		return (false);
	
	if (this.category != null)
		return (false);
	
	if (this.subjkeywords != null)
		return (false);
	
	if (this.typeofdoc != null)
		return (false);
	
	if (this.pubref != null)
			return (false);
	
	if (this.notes != null)
		return (false);
	
	if (this.sector != null)
		return (false);
	
	if (this.folder != null)
		return (false);
	
	if (this.filelocation != null)
		return (false);
	
	return (true);
}

}