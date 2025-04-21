package hust.soict.tido.aims.media;

import java.util.Comparator;
import java.time.LocalDate;

public abstract class Media {
    private int id;
    private String title;
    private String category;
    private float cost;
    private LocalDate dateAdded;
    
    public static final Comparator<Media> COMPARE_BY_TITLE_COST = 
            Comparator.comparing(Media::getTitle)
                    .thenComparing(Media::getCost, Comparator.reverseOrder());
    
    public static final Comparator<Media> COMPARE_BY_COST_TITLE = 
            Comparator.comparing(Media::getCost, Comparator.reverseOrder())
                    .thenComparing(Media::getTitle);
    
    // Constructor
    public Media() {
        this.dateAdded = LocalDate.now();
    }
    
    public Media(String title) {
        this();
        this.title = title;
    }
    
    public Media(String title, String category, float cost) {
        this(title);
        this.category = category;
        this.cost = cost;
    }
    
    public Media(int id, String title, String category, float cost) {
        this(title, category, cost);
        this.id = id;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public float getCost() {
        return cost;
    }
    
    public void setCost(float cost) {
        this.cost = cost;
    }
    
    public LocalDate getDateAdded() {
        return dateAdded;
    }
    
    // Methods
    public boolean isMatch(String title) {
        return this.title.toLowerCase().contains(title.toLowerCase());
    }
    
    public boolean isMatch(int id) {
        return this.id == id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Media)) return false;
        return this.id == ((Media) obj).id;
    }
    
    public abstract void play();
    
    @Override
    public String toString() {
        return String.format("ID: %d - [%s] - %s - %s: %.2f $", 
                id, category, title, "Cost", cost);
    }
} 