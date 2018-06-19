package ch.bbcag.gimmebeer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.w3c.dom.Text;

import ch.bbcag.gimmebeer.helper.PunkApiParser;
import ch.bbcag.gimmebeer.model.Beer;

public class DetailsActivity extends AppCompatActivity {
    private int beerId;
    private static final String PUNK_API_URL = "https://api.punkapi.com/v2/beers";
    CardView txtKeyNotes;
    CardView txtDescription;
    CardView txtFood;
    TextView detailTxtKeyNotes;
    TextView detailTxtDescription;
    TextView detailTxtFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        beerId = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");

        txtKeyNotes = (CardView) findViewById(R.id.cardViewKeyNotes);
        txtDescription = (CardView) findViewById(R.id.cardViewDescription);
        txtFood = (CardView) findViewById(R.id.cardViewFood);

        detailTxtKeyNotes = (TextView) findViewById(R.id.detail_txt_key_notes);
        detailTxtDescription = (TextView) findViewById(R.id.detail_txt_description);
        detailTxtFood = (TextView) findViewById(R.id.detail_txt_food);

        loadSpecificBeer(PUNK_API_URL + "/" + beerId);

        txtKeyNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailTxtKeyNotes.getVisibility() == View.VISIBLE) {
                    detailTxtKeyNotes.setVisibility(View.GONE);
                } else {
                    TransitionManager.beginDelayedTransition(txtKeyNotes);
                    detailTxtKeyNotes.setVisibility(View.VISIBLE);
                }
            }
        });

        txtDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailTxtDescription.getVisibility() == View.VISIBLE) {
                    detailTxtDescription.setVisibility(View.GONE);
                } else {
                    TransitionManager.beginDelayedTransition(txtDescription);
                    detailTxtDescription.setVisibility(View.VISIBLE);
                }
            }
        });

        txtFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailTxtFood.getVisibility() == View.VISIBLE) {
                    detailTxtFood.setVisibility(View.GONE);
                } else {
                    TransitionManager.beginDelayedTransition(txtFood);
                    detailTxtFood.setVisibility(View.VISIBLE);
                }
            }
        });

        Button showAllButton = (Button) findViewById(R.id.button_home);
        showAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShowAllActivity.class));
            }
        });
    }

    private void loadSpecificBeer(String url)
    {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Beer specific_beer = PunkApiParser.parseSingleBeer(response);
                            TextView textDetailKeynotes = (TextView) findViewById(R.id.detail_txt_key_notes);
                            textDetailKeynotes.setText(specific_beer.getKeynote());
                            TextView textDetailDescription = (TextView) findViewById(R.id.detail_txt_description);
                            textDetailDescription.setText(specific_beer.getDescription());
                            TextView textDetailFoodpairing = (TextView) findViewById(R.id.detail_txt_food);


                            StringBuilder builder = new StringBuilder();
                            for (String value : specific_beer.getFoodpairing()) {
                                builder.append(value + ".\n");
                            }
                            String foodText = builder.toString();
                            textDetailFoodpairing.setText(foodText);
                            TextView textDetailName = (TextView) findViewById(R.id.detail_beer_name);
                            textDetailName.setText(specific_beer.toString());
                            TextView textAbv = (TextView) findViewById(R.id.detail_abv);
                            String abv = Double.toString(specific_beer.getAbv());
                            textAbv.setText(abv + " %");
                            ImageView picture = (ImageView) findViewById(R.id.imageDetail);
                            Picasso.get().load(specific_beer.getImage()).into(picture);

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
        dialogBuilder.setMessage("The beer details could not be loaded. Try it again later.").setTitle("Error");
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}
