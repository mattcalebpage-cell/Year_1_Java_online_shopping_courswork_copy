package CLIBasis;
import java.util.Scanner;
import coursework_package.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AdminCLI {
    public final static String NOT_IMPLEMENTED = "Not implemented";
    public final static String INVALID = "Invalid input";

    public static void run(Scanner consoleInput, User currentUser, List<Product> products) {
        System.out.println("ADMIN VIEW");

        while (true) {
            printAdminMenu();

            int selection = Integer.parseInt(consoleInput.nextLine().trim());

            switch (selection) {
                case 1: // Viewing all products

                    // Creating a table for all products
                    System.out.println("-".repeat(140));
                    System.out.printf("%-6s | %-12s | %-15s | %-30s | %-8s | %-8s | %-20s | %-15s%n",
                            "ID", "Category", "Type", "Name", "Price", "In Stock", "Purchase Cost", "Extra Info");
                    System.out.println("-".repeat(140));
                    for (Product p : products) {
                        System.out.println(p);
                    }

                    System.out.println("-".repeat(140));
                    break;

                case 2: // Adding a new product
                	
                    takeProductInputs(consoleInput, products);
                    addNewProduct(products);
                    
                    
                    
                    break;

                case 0:
                    return;

                default:
                    System.out.println(INVALID);
                    System.out.println();
            }
        }
    }

    private static void printAdminMenu() {
        System.out.println("PLEASE SELECT ACTION BY INPUTTING THE CORRESPONDING NUMBER (or 0 for logout)");
        System.out.println("1) View all products");
        System.out.println("2) Add new product");
        System.out.println("0) Log out");
        
    }
    
    private static void takeProductInputs(Scanner consoleInput, List<Product> products) {
    	// Takes the user inputs for a product: productID, productCategory, type, productName, price,
    	// quantity, purchaseCost, and additional info
    	// Validates them all with their necessary checks
    	// eg productID must be 4 digits long, contain only numbers, and must not already
    	// be in use
    	// Then creates the necessary objects
    	
    	// Product id checks
    	int productId = 0;
    	while (true) {
    	    System.out.println("Enter product ID (4 digits): ");
    	    String input = consoleInput.nextLine().trim();
    	    
    	    // check it is exactly 4 characters
    	    if (input.length() != 4) {
    	        System.out.println("Invalid - product ID must be exactly 4 digits");
    	        continue;
    	    }
    	    
    	    // check it contains only numbers
    	    boolean isNums = true;
    	    for (char c : input.toCharArray()) {
    	        if (!Character.isDigit(c)) {
    	            isNums = false;
    	            break;
    	        }
    	    }
    	    
    	    if (!isNums) {
    	        System.out.println("Invalid - product ID must contain only numbers");
    	        continue;
    	    }
    	    
    	    // check it doesnt already exist - id must be unique
    	    boolean alreadyExists = false;
    	    for (Product p : products) {
    	        if (p.getProductID() == Integer.parseInt(input)) {
    	            alreadyExists = true;
    	            break;
    	        }
    	    }
    	    
    	    if (alreadyExists) {
    	        System.out.println("Invalid - product ID already exists");
    	        continue;
    	    }
    	    
    	    // all checks passed
    	    productId = Integer.parseInt(input);
    	    break;
    	}
    	
    	// category checks
    	// a product can only have categories: board game or accessory
    	String category = "";
    	while (true) {
    	    System.out.println("Enter category (board game/accessory): ");
    	    String input = consoleInput.nextLine().trim().toLowerCase();
    	    
    	    if (input.equals("board game") || input.equals("accessory")) {
    	        category = input;
    	        break;
    	    } else {
    	        System.out.println("Invalid - category must be either 'board game' or 'accessory'");
    	    }
    	}
    	
    	// type cchecks
    	// board games can be of type party or strategy
    	// accessories ca be of type accessory kit, miniature or dice
    	
    	// TODO IF TIME : use enums, as they can only be of that type
    	String type = "";
    	while (true) {
    	    if (category.equals("board game")) {
    	        System.out.println("Enter type (strategy/party): ");
    	        type = consoleInput.nextLine().trim().toLowerCase();
    	        
    	        if (type.equals("strategy") || type.equals("party")) {
    	            break;
    	            
    	        } else {
    	            System.out.println("Invalid - type must be 'strategy' or 'party'");
    	        }
    	        
    	    } else if (category.equals("accessory")) {
    	        System.out.println("Enter type (accessory kit/miniature/dice): ");
    	        type = consoleInput.nextLine().trim().toLowerCase();
    	        
    	        if (type.equals("accessory kit") || type.equals("miniature") || type.equals("dice")) {
    	            break;
    	            
    	        } else {
    	            System.out.println("Invalid - type must be 'accessory kit', 'miniature' or 'dice'");
    	        }
    	    }
    	}
    	String productName;
    	while (true) {
    		System.out.println("Enter a product name:");
        	productName = consoleInput.nextLine().trim();
        	if (productName == "") {
        		System.out.println("Product name cant be empty");
        		continue;
        	}
        	break;
    	}
    	
    	
    	// price and purchaseCost checks
    	// I used a helper function both fields undergo the same valdiation cheks
    	double price = validatePrice(consoleInput, "Enter price: ");
    	double purchaseCost = validatePrice(consoleInput, "Enter purchase cost: ");
    	
    	// Stock quantity
    	System.out.println("enter a quantity for the stock:");
    	int stockQuantity = Integer.parseInt(consoleInput.nextLine().trim());
    	
    	
    	// Additional info
    	// if boardgame, field is maxplayers
    	// if accessory, compatability
    	int maxPlayers = 0;
    	String compatibility = "";

    	if (category.equals("board game")) {
    	    while (true) {
    	        System.out.println("Enter max players:");
    	        String input = consoleInput.nextLine().trim();

    	        boolean isNumeric = true;
    	        for (char c : input.toCharArray()) {
    	            if (!Character.isDigit(c)) {
    	                isNumeric = false;
    	                break;
    	            }
    	        }

    	        if (!isNumeric || input.isEmpty()) {
    	            System.out.println("Invalid - max players must be a whole number");
    	            continue;
    	        }

    	        maxPlayers = Integer.parseInt(input);
    	        break;
    	    }
    	} else { // accessory
    	    System.out.println("Enter compatibility:");
    	    compatibility = consoleInput.nextLine().trim();
    	}
    	
    	if (category.equals("board game")) {
    		BoardGame bg = new BoardGame(productId,ProductCategory.BOARDGAME,type,productName, price, stockQuantity, purchaseCost, maxPlayers);
    		products.add(bg);
    	} else if (category.equals("accessory")) {
    		Accessory ac = new Accessory(productId,ProductCategory.ACCESSORY,type,productName, price, stockQuantity, purchaseCost, compatibility);
    		products.add(ac);
    	}
    	
    	
    	
    	
    
    }
    
    private static double validatePrice(Scanner consoleInput, String prompt) {
    	// Helper function which is used to validate both price, and purchase cost fields
    	// must be a number
    	// must contain a decimal point, with 2dp
    	// must contrain only one decimal point - cant have 0 or many 
        double price = 0.0;
        while (true) {
            System.out.println(prompt);
            String input = consoleInput.nextLine().trim();
            
            // check it contains only numbers and a decimal point
            boolean isValid = true;
            int decimalCount = 0;
            for (char c : input.toCharArray()) {
                if (!Character.isDigit(c) && c != '.') {
                    isValid = false;
                    break;
                }
                if (c == '.') decimalCount++;
            }
            
            if (!isValid) {
                System.out.println("Invalid - price must contain only numbers");
                continue;
            }
            
            
            if (decimalCount != 1) {
                System.out.println("Invalid - price must contain exactly one decimal point");
                continue;
            }
            
            // check there are exactly 2 decimal places
            int decimalPos = input.indexOf('.');
            if (input.length() - decimalPos - 1 != 2) {
                System.out.println("Invalid - must have exactly 2 decimal places");
                continue;
            }
            
            price = Double.parseDouble(input);
            break;
        }
        return price;
    }
    
    private static void addNewProduct(List<Product>products) {
    	// Gets the most recently added product p
    	// and writes to it to the stock file
    	Product p = products.get(products.size() - 1);
    	String type = "";
    	int maxPlayers = 0;
    	String compatibility = "";
    	
    	try (FileWriter myWriter = new FileWriter("Stock.txt", true)) {
    		
    		// boardgame
    		if (p instanceof BoardGame) {
    			type = ((BoardGame) p).getType();
    			maxPlayers = ((BoardGame) p).getMaxPlayers();
    			myWriter.write(p.getProductID() + " ; " + p.getProductCategory()
        		+ " ; " + type + ";" + p.getProductName() + " ; " + p.getPrice() + "; "
        		+ p.getQuantityInStock() + ";" + p.getPurchaseCost() + " ; " + maxPlayers);

    			// accesssory
    		}else if (p instanceof Accessory) {
    			type = ((Accessory) p).getType();
    			compatibility = ((Accessory) p).getCompatibility();
    			
    		}
    		
    		myWriter.write(p.getProductID() + " ; " + p.getProductCategory()
    		+ " ; " + type + ";" + p.getProductName() + " ; " + p.getPrice() + "; "
    		+ p.getQuantityInStock() + ";" + p.getPurchaseCost() + " ; " + compatibility);
    		
    		System.out.println("Added product to file");
    	}catch (IOException e) {
    	      System.out.println("An error occurred.");
    	      e.printStackTrace();
    	    }
    	
    	
    	
    }
    
}