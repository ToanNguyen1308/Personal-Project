package hust.soict.tido.aims.screen;

import hust.soict.tido.aims.cart.Cart;
import hust.soict.tido.aims.media.DigitalVideoDisc;
import hust.soict.tido.aims.media.Media;
import hust.soict.tido.aims.store.Store;

import java.util.List;
import java.util.Scanner;

public class StoreScreen {
    private Store store;
    private Cart cart;
    private Scanner scanner;
    
    public StoreScreen(Store store, Cart cart) {
        this.store = store;
        this.cart = cart;
        this.scanner = new Scanner(System.in);
    }
    
    public void show() {
        int choice;
        do {
            System.out.println("\n========== STORE ==========");
            store.displayItems();
            
            System.out.println("\nOptions:");
            System.out.println("1. Search for DVDs by title");
            System.out.println("2. Search for DVDs by category");
            System.out.println("3. Search for DVDs by price");
            System.out.println("4. View DVD details");
            System.out.println("5. Add DVD to cart");
            System.out.println("6. Play a DVD");
            System.out.println("0. Back to main menu");
            System.out.print("Please choose a number: 0-1-2-3-4-5-6: ");
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }
            
            switch (choice) {
                case 1:
                    searchDVDsByTitle();
                    break;
                case 2:
                    searchDVDsByCategory();
                    break;
                case 3:
                    searchDVDsByPrice();
                    break;
                case 4:
                    viewDVDDetails();
                    break;
                case 5:
                    addDVDToCart();
                    break;
                case 6:
                    playDVD();
                    break;
                case 0:
                    // Return to main menu
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
        } while (choice != 0);
    }
    
    private void searchDVDsByTitle() {
        System.out.print("Enter the title keywords: ");
        String title = scanner.nextLine();
        
        List<Media> results = store.searchByTitle(title);
        displaySearchResults(results, "title");
        
        if (!results.isEmpty()) {
            System.out.println("\nOptions:");
            System.out.println("1. Add a DVD to cart");
            System.out.println("2. Play a DVD");
            System.out.println("3. View DVD details");
            System.out.println("0. Back to store menu");
            System.out.print("Your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        addDVDToCartFromList(results);
                        break;
                    case 2:
                        playDVDFromList(results);
                        break;
                    case 3:
                        viewDVDDetailsFromList(results);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid choice. Returning to store menu.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Returning to store menu.");
            }
        }
    }
    
    private void searchDVDsByCategory() {
        System.out.print("Enter the category: ");
        String category = scanner.nextLine();
        
        List<Media> results = store.searchByCategory(category);
        displaySearchResults(results, "category");
        
        if (!results.isEmpty()) {
            System.out.println("\nOptions:");
            System.out.println("1. Add a DVD to cart");
            System.out.println("2. Play a DVD");
            System.out.println("3. View DVD details");
            System.out.println("0. Back to store menu");
            System.out.print("Your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        addDVDToCartFromList(results);
                        break;
                    case 2:
                        playDVDFromList(results);
                        break;
                    case 3:
                        viewDVDDetailsFromList(results);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid choice. Returning to store menu.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Returning to store menu.");
            }
        }
    }
    
    private void searchDVDsByPrice() {
        float minCost = 0f;
        float maxCost = 0f;
        boolean validInput = false;
        
        while (!validInput) {
            System.out.println("Enter price range:");
            System.out.print("1. Minimum cost (0 for no minimum): ");
            try {
                minCost = Float.parseFloat(scanner.nextLine());
                if (minCost < 0) {
                    System.out.println("Minimum cost cannot be negative. Using 0.");
                    minCost = 0;
                }
                
                System.out.print("2. Maximum cost: ");
                maxCost = Float.parseFloat(scanner.nextLine());
                if (maxCost <= 0) {
                    System.out.println("Maximum cost must be positive.");
                    continue;
                }
                
                if (maxCost < minCost) {
                    System.out.println("Maximum cost cannot be less than minimum cost.");
                    continue;
                }
                
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter valid numbers.");
            }
        }
        
        List<Media> results = store.searchByCost(minCost, maxCost);
        displaySearchResults(results, "price range");
        
        if (!results.isEmpty()) {
            System.out.println("\nOptions:");
            System.out.println("1. Add a DVD to cart");
            System.out.println("2. Play a DVD");
            System.out.println("3. View DVD details");
            System.out.println("0. Back to store menu");
            System.out.print("Your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        addDVDToCartFromList(results);
                        break;
                    case 2:
                        playDVDFromList(results);
                        break;
                    case 3:
                        viewDVDDetailsFromList(results);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid choice. Returning to store menu.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Returning to store menu.");
            }
        }
    }
    
    private void displaySearchResults(List<Media> results, String searchType) {
        if (results.isEmpty()) {
            System.out.println("No DVDs found for that " + searchType + ".");
            return;
        }
        
        System.out.println("\nSearch Results:");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i+1) + ". " + results.get(i));
        }
    }
    
    private void viewDVDDetails() {
        System.out.print("Enter the ID of the DVD to view: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Media media = findMediaById(id);
            
            if (media instanceof DigitalVideoDisc) {
                DigitalVideoDisc dvd = (DigitalVideoDisc) media;
                displayDVDDetails(dvd);
                
                System.out.println("\nOptions:");
                System.out.println("1. Add to cart");
                System.out.println("2. Play DVD");
                System.out.println("0. Back to store menu");
                System.out.print("Your choice: ");
                
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        cart.addMedia(dvd);
                        break;
                    case 2:
                        dvd.play();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid choice. Returning to store menu.");
                }
            } else {
                System.out.println("Media with ID " + id + " is not a DVD or was not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        }
    }
    
    private void viewDVDDetailsFromList(List<Media> mediaList) {
        System.out.print("Enter the number of the DVD to view (1-" + mediaList.size() + "): ");
        try {
            int number = Integer.parseInt(scanner.nextLine());
            if (number < 1 || number > mediaList.size()) {
                System.out.println("Invalid number. Returning to store menu.");
                return;
            }
            
            Media media = mediaList.get(number - 1);
            if (media instanceof DigitalVideoDisc) {
                DigitalVideoDisc dvd = (DigitalVideoDisc) media;
                displayDVDDetails(dvd);
                
                System.out.println("\nOptions:");
                System.out.println("1. Add to cart");
                System.out.println("2. Play DVD");
                System.out.println("0. Back to search results");
                System.out.print("Your choice: ");
                
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        cart.addMedia(dvd);
                        break;
                    case 2:
                        dvd.play();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid choice. Returning to store menu.");
                }
            } else {
                System.out.println("Selected media is not a DVD.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter a number.");
        }
    }
    
    private void displayDVDDetails(DigitalVideoDisc dvd) {
        System.out.println("\n========== DVD DETAILS ==========");
        System.out.println("ID: " + dvd.getId());
        System.out.println("Title: " + dvd.getTitle());
        System.out.println("Category: " + dvd.getCategory());
        System.out.println("Director: " + dvd.getDirector());
        System.out.println("Length: " + dvd.getLength() + " minutes");
        System.out.println("Cost: " + dvd.getCost() + " $");
        System.out.println("=================================");
    }
    
    private void addDVDToCart() {
        System.out.print("Enter the ID of the DVD to add to cart: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Media media = findMediaById(id);
            
            if (media instanceof DigitalVideoDisc) {
                cart.addMedia(media);
            } else {
                System.out.println("Media with ID " + id + " is not a DVD or was not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        }
    }
    
    private void addDVDToCartFromList(List<Media> mediaList) {
        System.out.print("Enter the number of the DVD to add to cart (1-" + mediaList.size() + "): ");
        try {
            int number = Integer.parseInt(scanner.nextLine());
            if (number < 1 || number > mediaList.size()) {
                System.out.println("Invalid number. Returning to store menu.");
                return;
            }
            
            Media media = mediaList.get(number - 1);
            if (media instanceof DigitalVideoDisc) {
                cart.addMedia(media);
            } else {
                System.out.println("Selected media is not a DVD.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter a number.");
        }
    }
    
    private void playDVD() {
        System.out.print("Enter the ID of the DVD to play: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Media media = findMediaById(id);
            
            if (media instanceof DigitalVideoDisc) {
                ((DigitalVideoDisc) media).play();
            } else {
                System.out.println("Media with ID " + id + " is not a DVD or was not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        }
    }
    
    private void playDVDFromList(List<Media> mediaList) {
        System.out.print("Enter the number of the DVD to play (1-" + mediaList.size() + "): ");
        try {
            int number = Integer.parseInt(scanner.nextLine());
            if (number < 1 || number > mediaList.size()) {
                System.out.println("Invalid number. Returning to store menu.");
                return;
            }
            
            Media media = mediaList.get(number - 1);
            if (media instanceof DigitalVideoDisc) {
                ((DigitalVideoDisc) media).play();
            } else {
                System.out.println("Selected media is not a DVD.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter a number.");
        }
    }
    
    private Media findMediaById(int id) {
        List<Media> allMedia = store.getItemsInStore();
        for (Media media : allMedia) {
            if (media.getId() == id) {
                return media;
            }
        }
        return null;
    }
} 