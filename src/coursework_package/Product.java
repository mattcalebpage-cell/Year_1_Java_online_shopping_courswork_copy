package coursework_package;

public abstract class Product {

    private int productID;
    private ProductCategory productCategory;
    private String productName;
    private double purchaseCost;
    private int quantityInStock;
    private double price;

    // Constructor
    public Product(int productID, ProductCategory productCategory, String productName, double purchaseCost, int quantityInStock, double price) {
        this.productID = productID;
        this.productCategory = productCategory;
        this.productName = productName;
        this.purchaseCost = purchaseCost;
        this.quantityInStock = quantityInStock;
        this.price = price;
    }

    // Getters
    public int getProductID() {
        return this.productID;
    }

    public ProductCategory getProductCategory() {
        return this.productCategory;
    }

    public String getProductName() {
        return this.productName;
    }

    public double getPurchaseCost() {
        return this.purchaseCost;
    }

    public int getQuantityInStock() {
        return this.quantityInStock;
    }

    public double getPrice() {
        return this.price;
    }
    public void setQuantityInStock(int quantityInStock) {
    	this.quantityInStock = quantityInStock;
    }

    public abstract String toString();
    
    
}