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
    private static final String PUNK_API_URL_RANDOM = "https://api.punkapi.com/v2/beers/random";
    private ProgressBar progressBar;
    ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        picture = findViewById(R.id.random_image);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DetailsActivity.class));
            }
        });

        // Show all button
        Button showAllButton = findViewById(R.id.button_show_all);
        showAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShowAllActivity.class));
            }
        });

        // Random beer button
        Button randomBeerButton = findViewById(R.id.random_beer);
        randomBeerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent refresh = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(refresh);
                finish();
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowSearchActivity.class);
                intent.putExtra("buttonId", view.getId());
                startActivity(intent);
            }
        };

        Button randomLightBeerButton = findViewById(R.id.random_light_beer);
        Button randomStrongBeerButton = findViewById(R.id.random_strong_beer);

        randomLightBeerButton.setOnClickListener(listener);
        randomStrongBeerButton.setOnClickListener(listener);

        loadRandomPicture(PUNK_API_URL_RANDOM);
    }

    private void loadRandomPicture(String url)
    {
        final ArrayAdapter<Beer> beerPictureInfoAdapter = new
                ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Beer random_beer = PunkApiParser.parseSingleBeer(response);
                            ImageView beerImageView = findViewById(R.id.random_image);
                            Picasso.get().load(random_beer.getImage()).into(beerImageView);

                        } catch (JSONException e) {
                            Log.e("loadImage", "jsonException");
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

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
