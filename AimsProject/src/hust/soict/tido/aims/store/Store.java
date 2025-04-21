package hust.soict.tido.aims.store;

import hust.soict.tido.aims.media.Media;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Store {
    private List<Media> itemsInStore = new ArrayList<>();
    
    // Method to add a media to the store
    public boolean addMedia(Media media) {
        if (media == null) {
            return false;
        }
        
        // Check if the media with same ID already exists
        for (Media item : itemsInStore) {
            if (item.getId() == media.getId()) {
                System.out.println("Media with ID " + media.getId() + " already exists in the store.");
                return false;
            }
        }
        
        // Add the media to the store
        itemsInStore.add(media);
        System.out.println("Media added to store: " + media.getTitle());
        return true;
    }
    
    // Method to add multiple media items to the store
    public boolean addMedia(Media... mediaList) {
        boolean allAdded = true;
        for (Media media : mediaList) {
            if (!addMedia(media)) {
                allAdded = false;
            }
        }
        return allAdded;
    }
    
    // Method to remove a media from the store
    public boolean removeMedia(Media media) {
        if (itemsInStore.remove(media)) {
            System.out.println("Media removed from store: " + media.getTitle());
            return true;
        }
        System.out.println("Media not found in store.");
        return false;
    }
    
    // Method to remove a media by its ID
    public boolean removeMediaById(int id) {
        for (Media media : itemsInStore) {
            if (media.getId() == id) {
                itemsInStore.remove(media);
                System.out.println("Media removed from store: " + media.getTitle());
                return true;
            }
        }
        System.out.println("Media with ID " + id + " not found in store.");
        return false;
    }
    
    // Method to get all media in the store
    public List<Media> getItemsInStore() {
        // Return a sorted list based on date added (newest first)
        List<Media> sortedList = new ArrayList<>(itemsInStore);
        Collections.sort(sortedList, Comparator.comparing(Media::getDateAdded).reversed());
        return sortedList;
    }
    
    // Method to search media by title
    public List<Media> searchByTitle(String title) {
        List<Media> results = new ArrayList<>();
        
        // Split the search string into keywords
        String[] keywords = title.toLowerCase().split("\\s+");
        
        for (Media media : itemsInStore) {
            for (String keyword : keywords) {
                if (media.isMatch(keyword)) {
                    results.add(media);
                    break; // Break once we've found a match for this media
                }
            }
        }
        
        // Sort results by date added (newest first)
        Collections.sort(results, Comparator.comparing(Media::getDateAdded).reversed());
        return results;
    }
    
    // Method to search media by category
    public List<Media> searchByCategory(String category) {
        List<Media> results = new ArrayList<>();
        String lowercaseCategory = category.toLowerCase();
        
        for (Media media : itemsInStore) {
            // Category match is case-insensitive
            if (media.getCategory() != null && 
                media.getCategory().toLowerCase().equals(lowercaseCategory)) {
                results.add(media);
            }
        }
        
        // Sort results by date added (newest first)
        Collections.sort(results, Comparator.comparing(Media::getDateAdded).reversed());
        return results;
    }
    
    // Method to search media by cost range
    public List<Media> searchByCost(float maxCost) {
        return searchByCost(0, maxCost);
    }
    
    public List<Media> searchByCost(float minCost, float maxCost) {
        List<Media> results = new ArrayList<>();
        
        for (Media media : itemsInStore) {
            if (media.getCost() >= minCost && media.getCost() <= maxCost) {
                results.add(media);
            }
        }
        
        // Sort results by date added (newest first)
        Collections.sort(results, Comparator.comparing(Media::getDateAdded).reversed());
        return results;
    }
    
    // Method to display all items in the store
    public void displayItems() {
        List<Media> sortedItems = getItemsInStore();
        System.out.println("***********************STORE***********************");
        System.out.println("Items in store:");
        
        for (int i = 0; i < sortedItems.size(); i++) {
            System.out.println((i+1) + ". " + sortedItems.get(i));
        }
        
        System.out.println("***************************************************");
    }
} 