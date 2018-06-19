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
    public static final String HIGH_ABV = "8";
    public static final String LOW_ABV = "5";

    public Beer (int id, String name, double abv, String image, String keynote, String description) {
        this.id = id;
        this.name = name;
        this.abv = abv;
        this.image = image;
        this.keynote = keynote;
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setFoodPairing(List<String> foodpairing) {
        this.foodpairing = foodpairing;
    }

    public List<String> getFoodpairing() {
        return foodpairing;
    }

    public String getImage() {
        return image;
    }

    public String getKeynote() { return keynote; }

    public String getDescription() { return description; }
}

