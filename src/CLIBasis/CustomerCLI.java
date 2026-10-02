package CLIBasis;
import java.util.Scanner;
import coursework_package.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerCLI {
	public final static String NOT_IMPLEMENTED = "Not implemented";
	public final static String INVALID = "Invalid input";
	
	private static List<Product> basket = new ArrayList<>();

    public static void run(Scanner consoleInput, User currentUser, List<Product> products) {
    	
    	
    	System.out.println("CUSTOMER VIEW - Welcome " + currentUser.getName());

        System.out.println("CUSTOMER VIEW");
        while (true) {
        	printCustomerMenu();
        	
        	int selection = Integer.parseInt(consoleInput.nextLine().trim());
        	
        	switch (selection) {
        	
        		case (1): // View all products - dont show purchase cost field
        			
        			System.out.println("-".repeat(140));
                	System.out.printf("%-6s | %-12s | %-15s | %-30s | %-8s | %-8s | %-15s%n",
                        "ID", "Category", "Type", "Name", "Price", "In Stock",  "Extra Info");
                	System.out.println("-".repeat(140));
        			for (Product p : products) {
        			    if (p instanceof BoardGame) {
        			        System.out.println(((BoardGame) p).custToString());
        			    } else if (p instanceof Accessory) {
        			        System.out.println(((Accessory) p).custToString());
        			    }
        			}
        			break;
        		
        		case (2): // Add product to shopping basket
        			
        			while (true) {
        				System.out.println("Enter the games ID, to add the product to basket");
        				System.out.println("Enter e to exit");
        				String input = consoleInput.nextLine().trim();
        				
        				if (input.toLowerCase().equals("e")) {
        			        break;
        			    }
        				
        				// need to perform valid id checks (same as in admin class)
        				boolean isNums = true;
        			    for (char c : input.toCharArray()) {
        			        if (!Character.isDigit(c)) {
        			            isNums = false;
        			            break;
        			        }
        			    }
        			    if (!isNums) {
        			        System.out.println("Invalid - please enter a valid product ID");
        			        continue;
        			    }
        			    
        			    int productId = Integer.parseInt(input);
        			    Boolean found = false;
        			    Product productToAdd = null;

        			    for (Product p : products) {
        			        if (p.getProductID() == productId) {
        			            found = true;
        			            productToAdd = p;
        			            break;
        			        }
        			    }

        			    if (found == false) {
        			        System.out.println("Product not found - please enter a valid product ID");
        			        continue;
        			    }
        			    
        			    if (productToAdd.getQuantityInStock() == 0) {
        			    	System.out.println("Sorry product not in stock");
        			    	continue;
        			    }

        			    // add to basket
        			    basket.add(productToAdd);
        			    System.out.println(productToAdd.getProductName() + " added to basket");
        			
        			}
        			
	    			System.out.println();
	    			System.out.println();
	    			System.out.println();
	    			System.out.println();
	    			System.out.println();
	    			break;
	    			
        		case (3): // view contents of shopping basket
        			
        			if (basket.isEmpty()) {
        				System.out.println("Your basket is empty");
        		        break;
        			}
        			
	    			System.out.println("Your list of products are:");
	    			System.out.println("-".repeat(60));
	    		    System.out.printf("%-6s | %-30s | %-10s%n",
	    		        "ID", "Name", "Price");
	    		    System.out.println("-".repeat(60));
	    			double total = calculateTotal(basket);
	    			
	    			System.out.println("-".repeat(60));
	    			System.out.printf("Total: £%.2f\n", total);
	    			
	    			
	    			break;
	    			
        		case (4): // purchase items in basket
        			
        			
        			// we need to check if the basket is empty, and that they havent gotten too many items
        			// eg getting 10000 of a single product, as we dont have that much stock
        			if (basket.isEmpty()) {
        		        System.out.println("Your basket is empty");
        		        break;
        		    }

        		    // check stock levels for each item in basket
        		    boolean stockOk = true;
        		    for (Product p : basket) {
        		        // count how many times this product appears in basket
        		        int count = 0;
        		        for (Product b : basket) {
        		            if (b.getProductID() == p.getProductID()) {
        		                count++;
        		            }
        		        }
        		        // check if enough stock
        		        if (count > p.getQuantityInStock()) {
        		            System.out.println("Sorry, not enough stock for: " + p.getProductName() +
        		                               " (requested: " + count + ", available: " + p.getQuantityInStock() + ")");
        		            stockOk = false;
        		        }
        		    }

        		    if (stockOk == false) {
        		        System.out.println("Your basket has been cleared. Ensure you dont add too many items.");
        		        basket.clear();
        		        break;
        		    }
        		    
        		    System.out.println("Please choose a payment option:");
        		    System.out.println("Press 1 for paypal");
        		    System.out.println("Press 2 for credit card");
        		    
        		    int paymentSelection = Integer.parseInt(consoleInput.nextLine().trim());
        		    
        		    
        		    Payment_Method payment;
        		    if (paymentSelection == 1) {
        		    	String email;
        		    	
        		    	while (true) {
        		    		// email checks
        		    		// cant be blank and we the the @ symbol somewhere in teh middle
        		    		System.out.println("Enter your email");
        		    		email = consoleInput.nextLine().trim();
        		    		
        		    		if (email.isEmpty()) {
        		    			System.out.println("Cant leave email blank");
        		    			continue;
        		    		}
        		    		
        		    		if (email.contains("@") == false) {
        		    			System.out.println("System needs an @ symbol");
        		    			continue;
        		    		}
        		    		
        		    		if (email.indexOf("@") == 0 || email.indexOf("@") == email.length() - 1) {
        		    			System.out.println("There must be letters eitehr side of the @ symbol");
        		    			continue;
        		    		}
        		    		
        		    		break;
        		    		
        		    	}
        		    	
        		    	
        		    	payment = new Paypal(email);
        		    	
        		    }else if (paymentSelection == 2) {
        		    	
        		    	String cardNum = "";
        		    	
        		    	// need to do checks for account num and security code ie right length etc
        		    	while (true) {
        		    		System.out.println("Enter your 6 digit card number");
            		    	cardNum = consoleInput.nextLine().trim();
            		    	if (cardNum.length() != 6) {
            		    		System.out.println("Ensure the card number is 6 digits long");
            		    		continue;
            		    	}
            		    	boolean isNums = true;
            		        for (char c : cardNum.toCharArray()) {
            		            if (!Character.isDigit(c)) {
            		                isNums = false;
            		                break;
            		            }
            		        }
            		        
            		        if (!isNums) {
            		            System.out.println("Invalid - card number must contain only numbers");
            		            continue;
            		        }
            		        
            		        break;
        		    		
        		    	}
        		    	
        		    	String securityCode = "";
        		    	while (true) {
        		    	    System.out.println("Enter your 3-digit security code: ");
        		    	    securityCode = consoleInput.nextLine().trim();
        		    	    
        		    	    if (securityCode.length() != 3) {
        		    	        System.out.println("Invalid - security code must be exactly 3 digits");
        		    	        continue;
        		    	    }
        		    	    
        		    	    boolean isNumeric = true;
        		    	    for (char c : securityCode.toCharArray()) {
        		    	        if (!Character.isDigit(c)) {
        		    	            isNumeric = false;
        		    	            break;
        		    	        }
        		    	    }
        		    	    
        		    	    if (!isNumeric) {
        		    	        System.out.println("Invalid - security code must contain only numbers");
        		    	        continue;
        		    	    }
        		    	    payment = new CreditCard(cardNum, securityCode);
        		    	    
        		    	    break;
        		    	}
        		    	
        		    	
        		    	
        		    	
        		    } else {
        		        System.out.println("Invalid payment method");
        		        break;
        		    }

        		    
        		    
        		  //TODO CREATE CREDT CARD OBJECT
        		    
        		    double total1 = calculateTotal(basket);
    		    	Receipt receipt = payment.processPayment(total1, currentUser.getAddress());
    		    	
    		    	// Update quantities for each product in products list
    		    	for (Product p : basket) {
    		            p.setQuantityInStock(p.getQuantityInStock() - 1);
    		        }
    		    	
    		    	// update quanitities for each product on the file
    		    	updateFile("Stock.txt", products);
    		    	

    		        basket.clear();
    		        System.out.println(receipt);
    		        System.out.println();
    		        System.out.println();
    		        System.out.println();
    		        System.out.println();
    		        break;

    		        
        		case (5):// cancel shopping basket
        			basket.clear();
        		System.out.println("Your basket has been successfully cleared");      		
	    			System.out.println();
	    			System.out.println();
	    			
	    			break;
	    			
        		case (6): // lookup with productID
        			
        			
        			// need to check the id is valid and that it exists
        			System.out.println("Please enter a valid productID to search byy");
        		String pID = consoleInput.nextLine().trim();
        		boolean validID = true;
        		
        		if (pID.length() != 4) {
        			validID = false;
        		}
        		
        		
	    	    for (char c : pID.toCharArray()) {
	    	        if (!Character.isDigit(c)) {
	    	        	validID = false;
	    	            break;
	    	        }
	    	    }
	    	    if (validID == false) {
	    	    	break;
	    	    }
	    	    int productID = Integer.parseInt(pID);
	    	    
	    	    boolean foundID = false;
	    	    for (Product p: products) {
	    	    	if (p.getProductID() == productID) {
	    	    		System.out.println(p.toString());
	    	    		foundID = true;
	    	    		break;
	    	    	}
	    	    	
	    	    }
	    	    if (foundID == false) {
	    	    	System.out.println("There is no product with that id");
	    	    }
	    	    System.out.println();
	    	    break;
	    	    

	    			
        		case (7): // Search by compatability 
        			System.out.println("Search by compatability");
	        		String userComp = consoleInput.nextLine().trim();
	        		
	        		if (userComp == null || userComp.isBlank()) {
	        			System.out.println("Ensure you enter something");
	        		}
	        		
	        		String comp;
	        		boolean foundComp = false;
	        		
	        		// checks accessories and checks if their comp matches the input
	        		for (Product p: products) {
	        			if (p instanceof Accessory) {
	        				comp = ((Accessory) p).getCompatibility();
	        				if (comp.toLowerCase().equals(userComp.toLowerCase())) {
	        					System.out.println(p.toString());
	        					System.out.print("\n");
	        					foundComp = true;
	        				}
	        				
	        				
	        			}
	        			
	        		}
	        		if (foundComp == false) {
	        			System.out.println("No games were found with that compatability");
	        		}
	        		break;
	    			
        		case (0):
        			return;
        		
        		default:
        			System.out.println(INVALID);
        			System.out.println();
        			
        		
        		
        	}
        }
        
    }
    
    private static double calculateTotal(List<Product>basket) {
    	double total = 0;
		for (Product p : basket) {
			System.out.printf("%-6d | %-30s | %-10.2f%n",
				    p.getProductID(),
				    p.getProductName(),
				    p.getPrice());
		            
			total += p.getPrice();
		}
		return total;
    	
    }
    
    private static void updateFile(String filename, List<Product>products) {
    	// we need to decrease the quantity field after purchase
        try {
            FileWriter writer = new FileWriter(filename, false); // false = overwrite mode

            for (Product p : products) {
                String category;
                String additionalInfo;
                String type;

                if (p instanceof BoardGame) {
                    additionalInfo = String.valueOf(((BoardGame) p).getMaxPlayers());
                    type = ((BoardGame) p).getType();
                    category = "board game";
                } else {
                    additionalInfo = ((Accessory) p).getCompatibility();
                    type = ((Accessory) p).getType();
                    category = "accessory";
                }

                String line = p.getProductID() + "; " +
                              category + "; " +
                              type + "; " +
                              p.getProductName() + "; " +
                              String.format("%.2f", p.getPrice()) + "; " +
                              p.getQuantityInStock() + "; " +
                              String.format("%.2f", p.getPurchaseCost()) + "; " +
                              additionalInfo;

                writer.write(line + "\n");
            }
            writer.close();
            System.out.println("Stock updated successfully");

        } catch (IOException e) {
            System.out.println("Error saving stock: " + e.getMessage());
        }
    }
    
    private static void printCustomerMenu() {
        System.out.println("PLEASE SELECT ACTION BY INPUTTING THE CORRESPONDING NUMBER (or 0 for logout)");
        System.out.println("1) View all products");
        System.out.println("2) Add product to shopping basket");
        System.out.println("3) View contents of shopping basket");
        System.out.println("4) Purchase items in the basket");
        System.out.println("5) Cancel shopping basket");
        System.out.println("6) Lookup with product ID");
        System.out.println("7) Search/filter based on compatilbility");

        System.out.println("0) Log out");

    }
}