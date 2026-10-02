package coursework_package;

public class Address {
    private int houseNumber;
    private String postcode;
    private String city;

    public Address(int houseNumber, String postcode, String city) {
        this.houseNumber = houseNumber;
        this.postcode = postcode;
        this.city = city;
    }
    
    public int getHouseNumber() {
    	return this.houseNumber;
    }
    
    public String getPostcode() {
    	return this.postcode;
    }
    
    public String getCity() {
    	return this.city;
    }
}