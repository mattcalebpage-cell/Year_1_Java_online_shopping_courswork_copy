package coursework_package;



import java.time.LocalDate;

public class Paypal implements Payment_Method{
	
	
	private String email;

	
	public Paypal(String email) {
		this.email = email;

	}
	
	
	@Override
	public Receipt processPayment(double total, Address address) {
		String date = LocalDate.now().toString();
		
		String receiptMessage = String.format("£%.2f paid via PayPal using %s on %s. Billing address: %s %s %s",
	            total,
	            this.email,
	            date,
	            address.getHouseNumber(),
	            address.getPostcode(),
	            address.getCity());

	        return new Receipt(receiptMessage);
		
	}
	
	
	

}
