package hust.soict.tido.aims.screen;

import hust.soict.tido.aims.cart.Cart;
import hust.soict.tido.aims.media.DigitalVideoDisc;
import hust.soict.tido.aims.media.Media;
import hust.soict.tido.aims.order.Order;
import hust.soict.tido.aims.payment.CreditCardPayment;
import hust.soict.tido.aims.payment.Payment;

import java.util.List;
import java.util.Scanner;

public class CartScreen {
    private Cart cart;
    private Scanner scanner;
    private static int orderCounter = 1;
    
    public CartScreen(Cart cart) {
        this.cart = cart;
        this.scanner = new Scanner(System.in);
    }
    
    public void show() {
        int choice;
        do {
            System.out.println("\n========== CART ==========");
            cart.displayCart();
            
            System.out.println("\nOptions:");
            System.out.println("1. Filter DVDs in cart");
            System.out.println("2. Sort DVDs in cart");
            System.out.println("3. Remove DVD from cart");
            System.out.println("4. Play a DVD");
            System.out.println("5. Get lucky (receive a free item)");
            System.out.println("6. Place order");
            System.out.println("0. Back to main menu");
            System.out.print("Please choose a number: 0-1-2-3-4-5-6: ");
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }
            
            switch (choice) {
                case 1:
                    filterDVDs();
                    break;
                case 2:
                    sortDVDs();
                    break;
                case 3:
                    removeDVD();
                    break;
                case 4:
                    playDVD();
                    break;
                case 5:
                    getLuckyItem();
                    break;
                case 6:
                    placeOrder();
                    break;
                case 0:
                    // Return to main menu
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
        } while (choice != 0);
    }
    
    private void filterDVDs() {
        System.out.println("\n===== FILTER DVDs IN CART =====");
        System.out.println("1. Filter by ID");
        System.out.println("2. Filter by title");
        System.out.print("Your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            
            switch (choice) {
                case 1:
                    filterById();
                    break;
                case 2:
                    filterByTitle();
                    break;
                default:
                    System.out.println("Invalid choice. Returning to cart menu.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice. Returning to cart menu.");
        }
    }
    
    private void filterById() {
        System.out.print("Enter the ID to search for: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Media found = cart.searchById(id);
            
            if (found != null) {
                System.out.println("\nFound item in cart:");
                System.out.println(found);
            } else {
                System.out.println("No item with ID " + id + " found in the cart.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        }
        
        // Wait for user to continue
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private void filterByTitle() {
        System.out.print("Enter the title to search for: ");
        String title = scanner.nextLine();
        Media found = cart.searchByTitle(title);
        
        if (found != null) {
            System.out.println("\nFound item in cart:");
            System.out.println(found);
        } else {
            System.out.println("No item with title containing \"" + title + "\" found in the cart.");
        }
        
        // Wait for user to continue
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private void sortDVDs() {
        System.out.println("\n===== SORT DVDs IN CART =====");
        System.out.println("1. Sort by title");
        System.out.println("2. Sort by cost");
        System.out.print("Your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            
            switch (choice) {
                case 1:
                    cart.sortByTitle();
                    System.out.println("Cart sorted by title.");
                    break;
                case 2:
                    cart.sortByCost();
                    System.out.println("Cart sorted by cost.");
                    break;
                default:
                    System.out.println("Invalid choice. Returning to cart menu.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice. Returning to cart menu.");
        }
    }
    
    private void removeDVD() {
        if (cart.getItemsOrdered().isEmpty()) {
            System.out.println("Cart is empty. Nothing to remove.");
            return;
        }
        
        System.out.print("Enter the ID of the DVD to remove from cart: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            
            for (Media media : cart.getItemsOrdered()) {
                if (media.getId() == id) {
                    cart.removeMedia(media);
                    System.out.println("DVD removed from cart.");
                    return;
                }
            }
            
            System.out.println("No DVD with ID " + id + " found in cart.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        }
    }
    
    private void playDVD() {
        if (cart.getItemsOrdered().isEmpty()) {
            System.out.println("Cart is empty. No DVDs to play.");
            return;
        }
        
        List<Media> items = cart.getItemsOrdered();
        
        System.out.println("\nDVDs in cart:");
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof DigitalVideoDisc) {
                System.out.println((i+1) + ". " + items.get(i).getTitle());
            }
        }
        
        System.out.print("Enter the number of the DVD to play (or 0 to cancel): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            
            if (choice == 0) {
                return;
            }
            
            if (choice < 1 || choice > items.size()) {
                System.out.println("Invalid choice.");
                return;
            }
            
            Media media = items.get(choice - 1);
            if (media instanceof DigitalVideoDisc) {
                ((DigitalVideoDisc) media).play();
            } else {
                System.out.println("Selected item is not a DVD.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice. Please enter a number.");
        }
        
        // Wait for user to continue
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private void getLuckyItem() {
        if (cart.getItemsOrdered().isEmpty()) {
            System.out.println("Cart is empty. Add items to be eligible for a lucky item.");
            return;
        }
        
        if (cart.getItemsOrdered().size() < 3) {
            System.out.println("You need at least 3 items in your cart to get a lucky item.");
            return;
        }
        
        Media luckyItem = cart.getALuckyItem();
        if (luckyItem != null) {
            System.out.println("Congratulations! You got a free item: " + luckyItem.getTitle());
        } else {
            System.out.println("Sorry, no lucky item this time.");
        }
        
        // Wait for user to continue
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private void placeOrder() {
        if (cart.getItemsOrdered().isEmpty()) {
            System.out.println("Cart is empty. Cannot place an order.");
            return;
        }
        
        // Create a new order with the cart contents
        Order order = new Order(orderCounter++, cart);
        
        // Get customer info
        System.out.println("\n===== DELIVERY INFORMATION =====");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        order.setCustomerInfo(name, email);
        
        System.out.print("Enter your shipping address: ");
        String address = scanner.nextLine();
        System.out.print("Enter delivery instructions (if any): ");
        String instructions = scanner.nextLine();
        order.setShippingInfo(address, instructions);
        
        // Calculate delivery fee based on total mass
        float deliveryFee = calculateDeliveryFee(order.calculateTotalMass(), address);
        order.setDeliveryFee(deliveryFee);
        
        // Display order details
        System.out.println("\n===== ORDER PREVIEW =====");
        order.displayOrderDetails();
        
        // Confirm order
        System.out.print("\nConfirm order (y/n)? ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (!confirm.equals("y")) {
            System.out.println("Order cancelled.");
            return;
        }
        
        // Process payment
        CreditCardPayment.CreditCardInfo cardInfo = getCardInfo();
        if (cardInfo == null) {
            System.out.println("Payment cancelled. Order not placed.");
            return;
        }
        
        Payment payment = new CreditCardPayment();
        Payment.TransactionResult result = payment.processPayment(order, cardInfo);
        
        if (result.isSuccess()) {
            order.setTransactionId(result.getTransactionId());
            System.out.println("\n===== TRANSACTION SUCCESSFUL =====");
            System.out.println(result);
            System.out.println("\nOrder has been placed successfully!");
            System.out.println("Order information has been sent to your email: " + email);
            
            // Clear the cart
            cart.clearCart();
        } else {
            System.out.println("\n===== TRANSACTION FAILED =====");
            System.out.println(result);
            System.out.println("\nOrder could not be placed. Please try again.");
        }
        
        // Wait for user to continue
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private CreditCardPayment.CreditCardInfo getCardInfo() {
        System.out.println("\n===== PAYMENT INFORMATION =====");
        System.out.println("Enter credit card details:");
        
        try {
            System.out.print("Card number (16 digits): ");
            String cardNumber = scanner.nextLine().trim();
            
            System.out.print("Card expiry date (MM/YY): ");
            String expiryDate = scanner.nextLine().trim();
            
            System.out.print("CVV (3 digits): ");
            String cvv = scanner.nextLine().trim();
            
            System.out.print("Cardholder name: ");
            String cardholderName = scanner.nextLine().trim();
            
            return new CreditCardPayment.CreditCardInfo(cardNumber, expiryDate, cvv, cardholderName);
        } catch (Exception e) {
            System.out.println("Error processing card information: " + e.getMessage());
            return null;
        }
    }
    
    private float calculateDeliveryFee(float mass, String address) {
        // Base fee is $2.0
        float baseFee = 2.0f;
        
        // Add $0.5 per 0.5 kg
        float massFee = (float) Math.ceil(mass * 2) * 0.5f;
        
        // Distance fee (simplified, based on first character of address)
        float distanceFee = 1.0f;
        if (!address.isEmpty()) {
            char firstChar = Character.toLowerCase(address.charAt(0));
            distanceFee = (firstChar - 'a') % 5 + 1;
        }
        
        return baseFee + massFee + distanceFee;
    }
} 