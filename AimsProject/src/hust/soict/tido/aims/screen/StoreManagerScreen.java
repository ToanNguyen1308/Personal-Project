package hust.soict.tido.aims.screen;

import hust.soict.tido.aims.media.DigitalVideoDisc;
import hust.soict.tido.aims.order.Order;
import hust.soict.tido.aims.store.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StoreManagerScreen {
    private Store store;
    private Scanner scanner;
    private List<Order> pendingOrders;
    private static int nextDvdId = 11; // Starting after the initial 10 DVDs
    
    public StoreManagerScreen(Store store) {
        this.store = store;
        this.scanner = new Scanner(System.in);
        this.pendingOrders = new ArrayList<>();
        
        // Add some sample pending orders for demonstration
        initializeSampleOrders();
    }
    
    private void initializeSampleOrders() {
        Order order1 = new Order(1);
        order1.setCustomerInfo("John Doe", "john.doe@example.com");
        order1.setShippingInfo("123 Main St, City", "Leave at door");
        order1.addItem(new DigitalVideoDisc(1, "The Lion King", "Animation", "Roger Allers", 87, 19.95f));
        order1.addItem(new DigitalVideoDisc(2, "Star Wars", "Science Fiction", "George Lucas", 124, 24.95f));
        order1.setDeliveryFee(5.5f);
        order1.setTransactionId("TX-12345678");
        
        Order order2 = new Order(2);
        order2.setCustomerInfo("Jane Smith", "jane.smith@example.com");
        order2.setShippingInfo("456 Oak St, Town", "Call upon arrival");
        order2.addItem(new DigitalVideoDisc(5, "Frozen", "Animation", "Chris Buck", 102, 22.99f));
        order2.addItem(new DigitalVideoDisc(7, "Inception", "Science Fiction", "Christopher Nolan", 148, 25.99f));
        order2.setDeliveryFee(4.0f);
        order2.setTransactionId("TX-87654321");
        
        pendingOrders.add(order1);
        pendingOrders.add(order2);
    }
    
    public void show() {
        int choice;
        do {
            System.out.println("\n========== STORE MANAGEMENT ==========");
            System.out.println("1. View all DVDs in store");
            System.out.println("2. Add a new DVD to store");
            System.out.println("3. Remove a DVD from store");
            System.out.println("4. View pending orders");
            System.out.println("0. Back to main menu");
            System.out.print("Please choose a number: 0-1-2-3-4: ");
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }
            
            switch (choice) {
                case 1:
                    viewAllDVDs();
                    break;
                case 2:
                    addNewDVD();
                    break;
                case 3:
                    removeDVD();
                    break;
                case 4:
                    viewPendingOrders();
                    break;
                case 0:
                    // Return to main menu
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
        } while (choice != 0);
    }
    
    private void viewAllDVDs() {
        System.out.println("\n===== ALL DVDs IN STORE =====");
        store.displayItems();
        
        // Wait for user to continue
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private void addNewDVD() {
        System.out.println("\n===== ADD NEW DVD =====");
        
        try {
            System.out.print("Enter DVD title: ");
            String title = scanner.nextLine();
            
            if (title.trim().isEmpty()) {
                System.out.println("DVD title cannot be empty. Operation cancelled.");
                return;
            }
            
            System.out.print("Enter DVD category: ");
            String category = scanner.nextLine();
            
            System.out.print("Enter DVD director: ");
            String director = scanner.nextLine();
            
            System.out.print("Enter DVD length (in minutes): ");
            int length = Integer.parseInt(scanner.nextLine());
            if (length < 0) {
                System.out.println("DVD length cannot be negative. Operation cancelled.");
                return;
            }
            
            System.out.print("Enter DVD cost: ");
            float cost = Float.parseFloat(scanner.nextLine());
            if (cost < 0) {
                System.out.println("DVD cost cannot be negative. Operation cancelled.");
                return;
            }
            
            // Create and add the new DVD
            DigitalVideoDisc newDVD = new DigitalVideoDisc(nextDvdId++, title, category, director, length, cost);
            
            if (store.addMedia(newDVD)) {
                System.out.println("DVD added to store successfully!");
            } else {
                System.out.println("Failed to add DVD to store.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. DVD not added.");
        } catch (Exception e) {
            System.out.println("Error adding DVD: " + e.getMessage());
        }
        
        // Wait for user to continue
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private void removeDVD() {
        System.out.println("\n===== REMOVE DVD =====");
        System.out.print("Enter the ID of the DVD to remove: ");
        
        try {
            int id = Integer.parseInt(scanner.nextLine());
            
            if (store.removeMediaById(id)) {
                System.out.println("DVD with ID " + id + " removed successfully.");
            } else {
                System.out.println("Failed to remove DVD. ID not found in store.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        }
        
        // Wait for user to continue
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private void viewPendingOrders() {
        if (pendingOrders.isEmpty()) {
            System.out.println("\nNo pending orders.");
            
            // Wait for user to continue
            System.out.print("\nPress Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        while (true) {
            System.out.println("\n===== PENDING ORDERS =====");
            for (int i = 0; i < pendingOrders.size(); i++) {
                Order order = pendingOrders.get(i);
                System.out.println((i+1) + ". Order #" + order.getId() + 
                        " - " + order.getCustomerName() + 
                        " - " + order.getOrderDate() + 
                        " - Status: " + order.getStatus());
            }
            
            System.out.println("\nOptions:");
            System.out.println("1-" + pendingOrders.size() + ". View order details");
            System.out.println("0. Back to store management");
            System.out.print("Your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                if (choice == 0) {
                    break;
                }
                
                if (choice < 1 || choice > pendingOrders.size()) {
                    System.out.println("Invalid choice. Please try again.");
                    continue;
                }
                
                Order selectedOrder = pendingOrders.get(choice - 1);
                viewOrderDetails(selectedOrder);
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number.");
            }
        }
    }
    
    private void viewOrderDetails(Order order) {
        while (true) {
            System.out.println("\n===== ORDER DETAILS =====");
            order.displayOrderDetails();
            
            System.out.println("\nOptions:");
            System.out.println("1. Approve order");
            System.out.println("2. Reject order");
            System.out.println("0. Back to pending orders");
            System.out.print("Your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        order.approve();
                        pendingOrders.remove(order);
                        return;
                    case 2:
                        order.reject();
                        pendingOrders.remove(order);
                        return;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number.");
            }
        }
    }
} 