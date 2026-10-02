package coursework_package;

public class Accessory extends Product {

    private String compatibility;
    private String type;

    public Accessory(int productID, ProductCategory productCategory,String type, String productName, double purchaseCost,
            int quantityInStock, double price, String compatibility) {
        super(productID, productCategory, productName, purchaseCost, quantityInStock, price);
        this.compatibility = compatibility;
        this.type = type;
    }

    public String getCompatibility() {
        return this.compatibility;
    }
    public String getType() {
    	return this.type;
    }

    @Override
    public String toString() {
        return String.format("%-6d | %-12s | %-15s | %-30s | %-8.2f | %-8d | %-20.2f | %-15s",
            getProductID(),
            getProductCategory(),
            getType(),
            getProductName(),
            getPrice(),
            getQuantityInStock(),
            getPurchaseCost(),
            getCompatibility());
    }
    
    // customer needs own tostring method as they shouldnt be able to see purchse cost
    public String custToString() {
        return String.format("%-6d | %-12s | %-15s | %-30s | %-8.2f | %-8d | %-15s",
            getProductID(),
            getProductCategory(),
            getType(),
            getProductName(),
            getPrice(),
            getQuantityInStock(),
            getCompatibility());
    }
    
    
    
    
    
}

