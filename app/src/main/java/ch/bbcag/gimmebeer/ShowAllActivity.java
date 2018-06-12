package ch.bbcag.gimmebeer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.ArrayList;

import ch.bbcag.gimmebeer.helper.PunkApiParser;
import ch.bbcag.gimmebeer.model.Beer;

public class ShowAllActivity extends AppCompatActivity {
    private int beerId;
    private ProgressBar progressBar;
    private static final String PUNK_API_URL = "https://api.punkapi.com/v2/beers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);
        progressBar = findViewById(R.id.progressbar);
        Intent intent = getIntent();
        beerId = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        progressBar.setVisibility(View.VISIBLE);
        loadAllBeer(PUNK_API_URL);
    }

    /*
     * Function to load all the beers from the Punk API
     *
     * @param url the url of the Punk API
     *
     */
    private void loadAllBeer(String url)
    {
        final ArrayAdapter<Beer> beerInfoAdapter = new
                ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<Beer> beer = PunkApiParser.createBeerFromJsonString(response);
                            beerInfoAdapter.addAll(beer);
                            ListView beerInfoList = findViewById(R.id.list_all_beer);
                            beerInfoList.setAdapter(beerInfoAdapter);
                            progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            generateAlertDialog();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                generateAlertDialog();
            }
        });
        queue.add(stringRequest);
    }

    private void generateAlertDialog() {
        progressBar.setVisibility(View.GONE);
        AlertDialog.Builder dialogBuilder;
        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Closes this activity
                finish();
            }
        });
        dialogBuilder.setMessage("The beer details could not be loaded. Try it again later.").setTitle("Error");
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}
