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
        // Create a JSON array with the string from the API
        JSONArray beerArray = new JSONArray(response);
        // Create a new array list with beer objects
        ArrayList<Beer> allBeers = new ArrayList<>();
        /*
         * For every element in the beerArray create a JSON object.
         * Create a new Beer object and fill the attributes from the API in.
         * Create a JSON array for the foodpairing and fill the attribute form the API in.
         * Create a new List<String> called foodpairing
         * For every Element in the JSON array foodPairingJson add the attribute food_pairing from the API to the foodpairing List<String>
         * Add the foodpairing array to the beer object and add the object to the allBeers array.
         * return the array allBeers which contains now all the beer objects.
         */

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