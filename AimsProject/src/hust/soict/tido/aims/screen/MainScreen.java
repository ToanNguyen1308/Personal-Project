package hust.soict.tido.aims.screen;

import hust.soict.tido.aims.cart.Cart;
import hust.soict.tido.aims.store.Store;

import java.util.Scanner;

public class MainScreen {
    private Store store;
    private Cart cart;
    private Scanner scanner;
    
    public MainScreen(Store store) {
        this.store = store;
        this.cart = new Cart();
        this.scanner = new Scanner(System.in);
    }
    
    public void showMainMenu() {
        int choice;
        do {
            System.out.println("\n========== AIMS: An Internet Media Store ==========");
            System.out.println("1. View store");
            System.out.println("2. Update store");
            System.out.println("3. See current cart");
            System.out.println("0. Exit");
            System.out.print("Please choose a number: 0-1-2-3: ");
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }
            
            switch (choice) {
                case 1:
                    new StoreScreen(store, cart).show();
                    break;
                case 2:
                    new StoreManagerScreen(store).show();
                    break;
                case 3:
                    new CartScreen(cart).show();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
        } while (choice != 0);
    }
    
    public static void main(String[] args) {
        // Initialize the store
        Store store = new Store();
        
        // Add some sample DVDs to the store
        // ID, title, category, director, length, cost
        store.addMedia(new hust.soict.tido.aims.media.DigitalVideoDisc(
                1, "The Lion King", "Animation", "Roger Allers", 87, 19.95f));
        store.addMedia(new hust.soict.tido.aims.media.DigitalVideoDisc(
                2, "Star Wars", "Science Fiction", "George Lucas", 124, 24.95f));
        store.addMedia(new hust.soict.tido.aims.media.DigitalVideoDisc(
                3, "Aladdin", "Animation", "John Musker", 90, 18.99f));
        store.addMedia(new hust.soict.tido.aims.media.DigitalVideoDisc(
                4, "Interstellar", "Science Fiction", "Christopher Nolan", 169, 30.99f));
        store.addMedia(new hust.soict.tido.aims.media.DigitalVideoDisc(
                5, "Frozen", "Animation", "Chris Buck", 102, 22.99f));
        store.addMedia(new hust.soict.tido.aims.media.DigitalVideoDisc(
                6, "Avatar", "Science Fiction", "James Cameron", 162, 29.99f));
        store.addMedia(new hust.soict.tido.aims.media.DigitalVideoDisc(
                7, "Inception", "Science Fiction", "Christopher Nolan", 148, 25.99f));
        store.addMedia(new hust.soict.tido.aims.media.DigitalVideoDisc(
                8, "The Shawshank Redemption", "Drama", "Frank Darabont", 142, 15.99f));
        store.addMedia(new hust.soict.tido.aims.media.DigitalVideoDisc(
                9, "The Dark Knight", "Action", "Christopher Nolan", 152, 28.99f));
        store.addMedia(new hust.soict.tido.aims.media.DigitalVideoDisc(
                10, "Pulp Fiction", "Crime", "Quentin Tarantino", 154, 21.99f));
        
        // Show the main menu
        new MainScreen(store).showMainMenu();
    }
} 