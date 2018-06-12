package ch.bbcag.gimmebeer.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ch.bbcag.gimmebeer.model.Beer;

public class PunkApiParser {
    public static Beer[] createBeerFromJsonString(String response) throws JSONException {
        JSONArray beerArray = new JSONArray(response);
        for (int i = 0; i < beerArray.length(); i++) {
            JSONObject beerObject = beerArray.getJSONObject(i);
            Beer beer = new Beer(Integer.parseInt(beerObject.getString("id")), beerObject.getString("name"), beerObject.getDouble("abv"), beerObject.getString("image"), beerObject.getString("tagline"), beerObject.getString("description"));
            JSONArray foodPairingJson = beerObject.getJSONArray("food_pairing");
        }
    }
}
