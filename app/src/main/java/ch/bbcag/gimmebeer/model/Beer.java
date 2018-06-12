package ch.bbcag.gimmebeer.model;

import java.util.ArrayList;
import java.util.List;

public class Beer {
    private int id;
    private String name;
    private double abv;
    private String keynote;
    private String description;
    private List<String> foodpairing = new ArrayList<>();

    public Beer (int id, String name, double abv, String keynote, String description) {
        this.id = id;
        this.name = name;
        this.abv = abv;
        this.keynote = keynote;
        this.description = description;
    }

    public List<String> getFoodpairing() {
        return foodpairing;
    }
}
