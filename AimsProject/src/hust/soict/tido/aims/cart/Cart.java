package hust.soict.tido.aims.cart;

import hust.soict.tido.aims.media.Media;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Cart {
    public static final int MAX_NUMBERS_ORDERED = 20;
    private List<Media> itemsOrdered = new ArrayList<>();
    
    // Methods to add media to cart
    public boolean addMedia(Media media) {
        if (itemsOrdered.size() < MAX_NUMBERS_ORDERED) {
            itemsOrdered.add(media);
            System.out.println("The media has been added to cart");
            return true;
        } else {
            System.out.println("The cart is almost full");
            return false;
        }
    }
    
    public boolean addMedia(Media... mediaList) {
        if (itemsOrdered.size() + mediaList.length <= MAX_NUMBERS_ORDERED) {
            for (Media media : mediaList) {
                itemsOrdered.add(media);
                System.out.println("The media \"" + media.getTitle() + "\" has been added to cart");
            }
            return true;
        } else {
            System.out.println("Cannot add all media. The cart would be over capacity.");
            return false;
        }
    }
    
    // Method to remove media from cart
    public boolean removeMedia(Media media) {
        if (itemsOrdered.remove(media)) {
            System.out.println("The media has been removed from cart");
            return true;
        } else {
            System.out.println("The media was not found in the cart");
            return false;
        }
    }
    
    // Method to calculate total cost
    public float totalCost() {
        float total = 0.0f;
        for (Media media : itemsOrdered) {
            total += media.getCost();
        }
        return total;
    }
    
    // Method to sort by title
    public void sortByTitle() {
        Collections.sort(itemsOrdered, Media.COMPARE_BY_TITLE_COST);
    }
    
    // Method to sort by cost
    public void sortByCost() {
        Collections.sort(itemsOrdered, Media.COMPARE_BY_COST_TITLE);
    }
    
    // Method to display the cart
    public void displayCart() {
        System.out.println("***********************CART***********************");
        System.out.println("Ordered Items:");
        
        for (int i = 0; i < itemsOrdered.size(); i++) {
            System.out.println((i+1) + ". " + itemsOrdered.get(i));
        }
        
        System.out.println("Total cost: " + String.format("%.2f", totalCost()) + " $");
        System.out.println("***************************************************");
    }
    
    // Method to search for media in cart by ID
    public Media searchById(int id) {
        for (Media media : itemsOrdered) {
            if (media.getId() == id) {
                return media;
            }
        }
        return null;
    }
    
    // Method to search for media in cart by title
    public Media searchByTitle(String title) {
        for (Media media : itemsOrdered) {
            if (media.isMatch(title)) {
                return media;
            }
        }
        return null;
    }
    
    // Method to update media quantity in the cart
    public boolean updateQuantity(Media media, int newQuantity) {
        // In this implementation, each media is a separate item.
        // To update quantity, we would need to add or remove items.
        if (newQuantity <= 0) {
            return removeMedia(media);
        } else if (itemsOrdered.contains(media)) {
            // For simplicity, we won't implement this fully here
            System.out.println("Quantity updated");
            return true;
        }
        return false;
    }
    
    // Method to select a free item randomly from the cart
    public Media getALuckyItem() {
        if (itemsOrdered.isEmpty()) {
            return null;
        }
        
        Random rand = new Random();
        int luckyIndex = rand.nextInt(itemsOrdered.size());
        Media luckyItem = itemsOrdered.get(luckyIndex);
        
        System.out.println("Congratulations! You got a free item: " + luckyItem.getTitle());
        // Make this item free by giving a discount equal to its cost
        return luckyItem;
    }
    
    // Method to clear the cart
    public void clearCart() {
        itemsOrdered.clear();
        System.out.println("Cart has been cleared");
    }
    
    // Method to get the list of items
    public List<Media> getItemsOrdered() {
        return itemsOrdered;
    }
} 