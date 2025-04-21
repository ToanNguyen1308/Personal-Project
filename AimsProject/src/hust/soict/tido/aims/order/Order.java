package hust.soict.tido.aims.order;

import hust.soict.tido.aims.media.Media;
import hust.soict.tido.aims.cart.Cart;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    public static final float VAT_RATE = 0.1f; // 10% VAT
    
    private int id;
    private LocalDateTime orderDate;
    private String customerName;
    private String customerEmail;
    private String shippingAddress;
    private String deliveryInstructions;
    private float deliveryFee;
    private List<Media> orderedItems = new ArrayList<>();
    private OrderStatus status;
    private String transactionId;
    
    // Order status enum
    public enum OrderStatus {
        PENDING,
        APPROVED,
        REJECTED,
        SHIPPED,
        DELIVERED
    }
    
    // Constructor
    public Order(int id) {
        this.id = id;
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }
    
    public Order(int id, Cart cart) {
        this(id);
        if (cart != null) {
            for (Media media : cart.getItemsOrdered()) {
                orderedItems.add(media);
            }
        }
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    
    public void setCustomerInfo(String name, String email) {
        this.customerName = name;
        this.customerEmail = email;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public String getCustomerEmail() {
        return customerEmail;
    }
    
    public void setShippingInfo(String address, String instructions) {
        this.shippingAddress = address;
        this.deliveryInstructions = instructions;
    }
    
    public String getShippingAddress() {
        return shippingAddress;
    }
    
    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }
    
    public void setDeliveryFee(float deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
    
    public float getDeliveryFee() {
        return deliveryFee;
    }
    
    public OrderStatus getStatus() {
        return status;
    }
    
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    // Method to calculate subtotal (before VAT)
    public float calculateSubtotal() {
        float subtotal = 0f;
        for (Media item : orderedItems) {
            subtotal += item.getCost();
        }
        return subtotal;
    }
    
    // Method to calculate total before delivery fee
    public float calculateTotal() {
        float subtotal = calculateSubtotal();
        return subtotal + (subtotal * VAT_RATE);
    }
    
    // Method to calculate final total including delivery fee
    public float calculateFinalTotal() {
        return calculateTotal() + deliveryFee;
    }
    
    // Method to calculate total mass (for delivery fee calculation)
    public float calculateTotalMass() {
        // In a real system, each media would have a mass attribute
        // For simplicity, we'll return a fixed value here
        return orderedItems.size() * 0.2f; // Assume each DVD weighs 200 grams
    }
    
    // Method to add a new item to the order
    public boolean addItem(Media media) {
        return orderedItems.add(media);
    }
    
    // Method to remove an item from the order
    public boolean removeItem(Media media) {
        return orderedItems.remove(media);
    }
    
    // Method to get all ordered items
    public List<Media> getOrderedItems() {
        return orderedItems;
    }
    
    // Method to approve the order
    public void approve() {
        if (status == OrderStatus.PENDING) {
            status = OrderStatus.APPROVED;
            System.out.println("Order #" + id + " has been approved.");
        } else {
            System.out.println("Order #" + id + " cannot be approved. Current status: " + status);
        }
    }
    
    // Method to reject the order
    public void reject() {
        if (status == OrderStatus.PENDING) {
            status = OrderStatus.REJECTED;
            System.out.println("Order #" + id + " has been rejected.");
        } else {
            System.out.println("Order #" + id + " cannot be rejected. Current status: " + status);
        }
    }
    
    // Method to display order details
    public void displayOrderDetails() {
        System.out.println("***********************ORDER***********************");
        System.out.println("Order ID: " + id);
        System.out.println("Date: " + orderDate);
        System.out.println("Status: " + status);
        System.out.println("Customer: " + customerName);
        System.out.println("Email: " + customerEmail);
        System.out.println("Shipping Address: " + shippingAddress);
        System.out.println("Delivery Instructions: " + deliveryInstructions);
        
        System.out.println("\nOrdered Items:");
        for (int i = 0; i < orderedItems.size(); i++) {
            System.out.println((i+1) + ". " + orderedItems.get(i));
        }
        
        System.out.println("\nSubtotal: " + String.format("%.2f", calculateSubtotal()) + " $");
        System.out.println("VAT (10%): " + String.format("%.2f", calculateSubtotal() * VAT_RATE) + " $");
        System.out.println("Total: " + String.format("%.2f", calculateTotal()) + " $");
        System.out.println("Delivery Fee: " + String.format("%.2f", deliveryFee) + " $");
        System.out.println("Final Total: " + String.format("%.2f", calculateFinalTotal()) + " $");
        
        if (transactionId != null) {
            System.out.println("\nTransaction ID: " + transactionId);
        }
        
        System.out.println("***************************************************");
    }
} 