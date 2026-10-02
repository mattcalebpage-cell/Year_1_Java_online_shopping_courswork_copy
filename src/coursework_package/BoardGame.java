package coursework_package;

public class BoardGame extends Product {

    private int maxPlayers;
    private String type;

    public BoardGame(int productID, ProductCategory productCategory,String type, String productName,double price,
            int quantityInStock, double purchaseCost,  int maxPlayers) {
        super(productID, productCategory, productName,price, quantityInStock,purchaseCost);
        this.maxPlayers = maxPlayers;
        this.type = type;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }
    
    public String getType() {
    	return this.type;
    }

    @Override
    public String toString() {
        return String.format("%-6d | %-12s | %-15s | %-30s | %-8.2f | %-8d | %-20.2f | %-15d",
            getProductID(),
            getProductCategory(),
            getType(),
            getProductName(),
            getPrice(),
            getQuantityInStock(),
            getPurchaseCost(),
            getMaxPlayers());
    }
    
    // needs separate tostring for same reason as accessory
    public String custToString() {
        return String.format("%-6d | %-12s | %-15s | %-30s | %-8.2f | %-8d |  %-15d",
            getProductID(),
            getProductCategory(),
            getType(),
            getProductName(),
            getPrice(),
            getQuantityInStock(),
            getMaxPlayers());
    }
}


	
	
	
	


