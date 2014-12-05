/**
 * 
 */
package libutil;

/**
 * @author Δημήτρης
 *
 */
public class State extends ConnBean {
	private ConnBean conn = new ConnBean();
	private boolean admin;
	
	public State() {
		admin = false;
}

	public boolean isAdmin() {
		return (this.admin);
	}
	
	public void setAdmin(boolean value) {
		this.admin= value;
	}
}
