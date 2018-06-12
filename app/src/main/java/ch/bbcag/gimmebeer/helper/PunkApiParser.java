package ch.bbcag.gimmebeer.helper;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ch.bbcag.gimmebeer.model.Beer;

public class PunkApiParser {
    public static ArrayList<Beer> createBeerFromJsonString(String response) throws JSONException {
        JSONArray beerArray = new JSONArray(response);
        ArrayList<Beer> allBeers = new ArrayList<>();
        for (int i = 0; i < beerArray.length(); i++) {
            JSONObject beerObject = beerArray.getJSONObject(i);
            Beer beer = new Beer(Integer.parseInt(beerObject.getString("id")), beerObject.getString("name"), beerObject.getDouble("abv"), beerObject.getString("image"), beerObject.getString("tagline"), beerObject.getString("description"));
            JSONArray foodPairingJson = beerObject.getJSONArray("food_pairing");
            List<String> foodpairing = new ArrayList<String>();
            for (int j = 0; j < foodPairingJson.length(); j++) {
                foodpairing.add(foodPairingJson.getJSONObject(j).getString("food_pairing"));
            }
            beer.addFoodPairing(foodpairing);
            allBeers.add(beer);
        }
        return allBeers;
    }
}
