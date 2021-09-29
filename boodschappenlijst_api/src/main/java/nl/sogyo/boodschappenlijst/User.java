package nl.sogyo.boodschappenlijst;

public class User {
	
	private String Name;
	
	public void setName(String name) {
		this.Name = name;
	}
	
	public String getName() {
		return this.Name;
	}
	
	private String Password;
	
	public void setPassword(String password) {
		this.Password = password;
	}
	
	public String getPassword() {
		return this.Password;
	}
	
	private String Email;
	
	public void setEmail(String email) {
		this.Email = email;
	}
	
	public String getEmail() {
		return this.Email;
	}
	
	private int UserCode;
	
	public void setUserCode(int usercode) {
		this.UserCode = usercode;
	}
	
	public int getUserCode() {
		return this.UserCode;
	}
}
