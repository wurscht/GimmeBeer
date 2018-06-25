package ch.bbcag.gimmebeer;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ImageView;

import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import ch.bbcag.gimmebeer.helper.PunkApiParser;
import ch.bbcag.gimmebeer.model.Beer;

public class MainActivity extends AppCompatActivity {
    // URL of the API
    private static final String PUNK_API_URL_RANDOM = "https://api.punkapi.com/v2/beers/random";
    private ProgressBar progressBar;
    ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        picture = (ImageView) findViewById(R.id.random_image);

        // Button to show all beers
        Button showAllButton = (Button) findViewById(R.id.button_show_all);
        showAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShowAllActivity.class));
            }
        });

        // Random beer button
        Button randomBeerButton = (Button) findViewById(R.id.random_beer);
        randomBeerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent refresh = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(refresh);
                finish();
            }
        });

        // Listener for random light beer and random strong beer button
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowSearchActivity.class);
                intent.putExtra("buttonId", view.getId());
                startActivity(intent);
            }
        };

        // Initiate light beer and strong beer buttons
        Button randomLightBeerButton = (Button) findViewById(R.id.random_light_beer);
        Button randomStrongBeerButton = (Button) findViewById(R.id.random_strong_beer);

        // Set on click listener to buttons
        randomLightBeerButton.setOnClickListener(listener);
        randomStrongBeerButton.setOnClickListener(listener);

        // Execute the loadRandomPicture function to show a random picture
        loadRandomPicture(PUNK_API_URL_RANDOM);
    }

    // Function to load a random picture from the API
    private void loadRandomPicture(String url)
    {
        // Generate new RequestQueue
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Take a single beer from the API with the method parseSingleBeer from the PunkApiParser Class
                            final Beer random_beer = PunkApiParser.parseSingleBeer(response);
                            final ImageView beerImageView = (ImageView) findViewById(R.id.random_image);

                            // Load the random beer picture into the beerImageView with the picasso library
                            Picasso.get().load(random_beer.getImage()).into(beerImageView);

                            // Set a on click listener to redirect to details view if the random beer was clicked
                            picture.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                                    intent.putExtra("id", random_beer.getId());
                                    startActivity(intent);
                                }
                            });


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
        AlertDialog.Builder dialogBuilder;
        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Closes this activity
                finish();
            }
        });
        dialogBuilder.setMessage("The beer picture could not be loaded. Try it again later.").setTitle("Error");
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}
