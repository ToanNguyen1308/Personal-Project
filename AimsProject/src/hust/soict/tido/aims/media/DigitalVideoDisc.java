package hust.soict.tido.aims.media;

public class DigitalVideoDisc extends Media {
    private String director;
    private int length;
    
    // Constructors
    public DigitalVideoDisc(String title) {
        super(title);
    }
    
    public DigitalVideoDisc(String category, String title, float cost) {
        super(title, category, cost);
    }
    
    public DigitalVideoDisc(String director, String category, String title, float cost) {
        super(title, category, cost);
        this.director = director;
    }
    
    public DigitalVideoDisc(String title, String category, String director, int length, float cost) {
        super(title, category, cost);
        this.director = director;
        this.length = length;
    }
    
    public DigitalVideoDisc(int id, String title, String category, String director, int length, float cost) {
        super(id, title, category, cost);
        this.director = director;
        this.length = length;
    }
    
    // Getters and Setters
    public String getDirector() {
        return director;
    }
    
    public void setDirector(String director) {
        this.director = director;
    }
    
    public int getLength() {
        return length;
    }
    
    public void setLength(int length) {
        this.length = length;
    }
    
    // Methods
    @Override
    public void play() {
        if (this.getLength() <= 0) {
            System.out.println("DVD cannot be played: Invalid length!");
            return;
        }
        
        System.out.println("Playing DVD: " + this.getTitle());
        System.out.println("DVD length: " + this.getLength() + " minutes");
    }
    
    @Override
    public String toString() {
        return String.format("DVD - ID: %d - [%s] - %s - %s - %d minutes: %.2f $", 
                this.getId(), this.getCategory(), this.getTitle(), 
                director, length, this.getCost());
    }
    
    @Override
    public boolean isMatch(String keyword) {
        // Check if any word in the title contains the keyword (case-insensitive)
        String[] titleWords = this.getTitle().split("\\s+");
        String lowercaseKeyword = keyword.toLowerCase();
        
        for (String word : titleWords) {
            if (word.toLowerCase().contains(lowercaseKeyword)) {
                return true;
            }
        }
        return false;
    }
} 