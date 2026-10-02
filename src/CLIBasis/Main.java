package CLIBasis;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import coursework_package.*;


public class Main {
	
	private static List<User> users = new ArrayList<>(); 
	private static List<Product> products = new ArrayList<>();

    public static void main(String[] args) {
    	
    	

        Scanner consoleInput = new Scanner(System.in);

        System.out.println("WELCOME");
        
        loadUsers("userAccounts.txt");
        loadProducts("Stock.txt");
        
        while (true) {
            printWelcomeMenu();

            String line = consoleInput.nextLine().trim();

            int selection = Integer.parseInt(line.trim());
            
            // I changed the main file from a switch statement to an if statement
            // because the customers cant be hard coded

            if (selection == 0) {
                System.out.println("Goodbye");
                System.out.println("Closing program...");
                return;
            }
            
            // Checks the user input is not 1 , or an invalid character
            if (selection < 1 || selection > users.size()) {
                System.out.println("Invalid input");
                continue;
            }

            // selects the desired user object
            User selectedUser = users.get(selection - 1);

            if (selectedUser.getRole().equals("admin")) {
                AdminCLI.run(consoleInput, selectedUser, products);
            } else if (selectedUser.getRole().equals("customer")) {
                CustomerCLI.run(consoleInput, selectedUser, products);
            }
        }
    }

    private static void printWelcomeMenu() {
        System.out.println("PLEASE SELECT USER BY INPUTTING THE CORRESPONDING NUMBER (or 0 for exit)");
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            System.out.println((i + 1) + ") " + u.getUserID() + " | " + u.getName() + " | " + u.getRole());
        }
        System.out.println("0) Exit");
    }

    private static String readLine(Scanner consoleInput) {
        if (!consoleInput.hasNextLine()) {
            return null;
        }
        return consoleInput.nextLine();
    }
    
    
    // gets the names, userIDs and roles of each user in the user.txt file,
    // creates objects for them then stores them in the 
    // arraylist users
    private static void loadUsers(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            
            reader.readLine(); // skip header line

            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(";");

                // Check we have the right number of fields
                if (parts.length != 6) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                int userId = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                int houseNumber = Integer.parseInt(parts[2].trim());
                String postcode = parts[3].trim();
                String city = parts[4].trim();
                String role = parts[5].trim();

                Address address = new Address(houseNumber, postcode, city);
                User user = new User(userId, name, address, role);
                users.add(user);
            }
            reader.close();
            System.out.println("Loaded " + users.size() + " users successfully");

        } catch (FileNotFoundException e) {
            System.out.println("User file not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading user file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in user file: " + e.getMessage());
        }
    }
    
    // Loads all of the products that are stored in the product file
    // Stores both accessories and boardgames together in
    // arraylist products
    private static void loadProducts(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;

            reader.readLine(); // skip the header

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(";");

                // trim leading and trailing white space for all fields
                int productId = Integer.parseInt(parts[0].trim());
                String category = parts[1].trim();     
                String type = parts[2].trim();
                String productName = parts[3].trim();
                double price = Double.parseDouble(parts[4].trim());
                int quantityInStock = Integer.parseInt(parts[5].trim());
                double purchaseCost = Double.parseDouble(parts[6].trim());
                String additionalInfo = parts[7].trim(); 

                // Creates a boardgame or accessory object, based on the category attribute for each object
                if (category.equals("board game")) {
                    int maxPlayers = Integer.parseInt(additionalInfo);
                    BoardGame bg = new BoardGame(productId, ProductCategory.BOARDGAME, type, productName, purchaseCost, quantityInStock, price, maxPlayers);
                    products.add(bg);

                } else if (category.equals("accessory")) {
                    Accessory acc = new Accessory(productId, ProductCategory.ACCESSORY,type, productName, purchaseCost, quantityInStock, price, additionalInfo);
                    products.add(acc);
                }
            }
            reader.close();
            System.out.println("Loaded " + products.size() + " products successfully");


        } catch (FileNotFoundException e) {
            System.out.println("Product file not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading product file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in product file: " + e.getMessage());
        }
    }
    
    
    
}