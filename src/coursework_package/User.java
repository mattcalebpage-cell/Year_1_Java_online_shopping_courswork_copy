package coursework_package;


public class User {
	private int userID;
	private String name;
	private Address address;
	private String role;
	
	public User(int userID, String name, Address address, String role) {
        this.userID = userID;
        this.name = name;
        this.address = address;
        this.role = role;
    }
	
	// Getters
	public int getUserID() { 
		return this.userID; }
	
    public String getName() { 
    	return this.name; }
    
    public Address getAddress() {
    	return this.address; }
    
    public String getRole() { 
    	return this.role; }
	

}
