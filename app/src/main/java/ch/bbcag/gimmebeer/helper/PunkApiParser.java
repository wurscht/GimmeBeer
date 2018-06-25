package ch.bbcag.gimmebeer.helper;

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

        // For every element in the beerArray create a JSON object.
        for (int i = 0; i < beerArray.length(); i++) {
            JSONObject beerObject = beerArray.getJSONObject(i);
            allBeers.add(parseSingleBeer(beerObject));
        }

        // Return the array allBeers which contains now all the beer objects.
        return allBeers;
    }

    private static Beer parseSingleBeer(JSONObject jsonBeer) throws JSONException {
        // Create a new Beer object and fill the attributes from the API in.
        Beer beer = new Beer(Integer.parseInt(jsonBeer.getString("id")), jsonBeer.getString("name"), jsonBeer.getDouble("abv"), jsonBeer.getString("image_url"), jsonBeer.getString("tagline"), jsonBeer.getString("description"));
        // Create a JSON array for the foodpairing and fill the attribute form the API in.
        JSONArray foodPairingJson = jsonBeer.getJSONArray("food_pairing");
        // Create a new List<String> called foodpairing
        List<String> foodpairing = new ArrayList<String>();

        // For every Element in the JSON array foodPairingJson add the attribute food_pairing from the API to the foodpairing List<String>
        for (int j = 0; j < foodPairingJson.length(); j++) {
            foodpairing.add(foodPairingJson.getString(j));
        }

        // Set the foodpairing array to the beer object.
        beer.setFoodPairing(foodpairing);

        return beer;
    }

    public static Beer parseSingleBeer(String stringBeer) throws JSONException {
        JSONArray jsonBeerArray = new JSONArray(stringBeer);
        JSONObject jsonBeer = jsonBeerArray.getJSONObject(0);

        return parseSingleBeer(jsonBeer);
    }
}
