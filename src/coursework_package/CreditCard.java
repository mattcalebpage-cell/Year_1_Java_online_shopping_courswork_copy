package coursework_package;


import java.time.LocalDate;

public class CreditCard implements Payment_Method{
	
	private String cardNumber;
    private String securityCode;

    public CreditCard(String cardNumber, String securityCode) {
        this.cardNumber = cardNumber;
        this.securityCode = securityCode;
    }
    
    @Override
    public Receipt processPayment(double total, Address address) {
        String date = LocalDate.now().toString();

        String receiptMessage = String.format("£%.2f paid by Credit Card %s on %s. Billing address: %s %s %s",
            total,
            this.cardNumber,
            date,
            address.getHouseNumber(),
            address.getPostcode(),
            address.getCity());

        return new Receipt(receiptMessage);
    }
	
	

}
