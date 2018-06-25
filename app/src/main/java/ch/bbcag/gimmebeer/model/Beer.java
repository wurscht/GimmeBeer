package ch.bbcag.gimmebeer.model;

import java.util.ArrayList;
import java.util.List;

public class Beer {
    private int id;
    private String name;
    private double abv;
    private String image;
    private String keynote;
    private String description;
    private List<String> foodpairing = new ArrayList<>();

    // Constructor
    public Beer (int id, String name, double abv, String image, String keynote, String description) {
        this.id = id;
        this.name = name;
        this.abv = abv;
        this.image = image;
        this.keynote = keynote;
        this.description = description;
    }

    // Getter and setter
    public int getId() { return id; }

    @Override
    public String toString() {
        return name;
    }

    public double getAbv() { return abv; }

    public String getImage() {
        return image;
    }

    public String getKeynote() { return keynote; }

    public String getDescription() { return description; }

    public void setFoodPairing(List<String> foodpairing) {
        this.foodpairing = foodpairing;
    }

    public List<String> getFoodpairing() {
        return foodpairing;
    }
}

