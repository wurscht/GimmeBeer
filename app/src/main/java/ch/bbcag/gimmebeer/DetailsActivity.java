package ch.bbcag.gimmebeer;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    private int beerId;
    private static final String PUNK_API_URL = "https://api.punkapi.com/v2/beers";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        beerId = intent.getIntExtra("id", 0);

    }

//    void collapseExpandTextView() {
//        if (txtDescription.getVisibility() == View.GONE) {
//            // it's collapsed - expand it
//            txtDescription.setVisibility(View.VISIBLE);
//        } else {
//            // it's expanded - collapse it
//            txtDescription.setVisibility(View.GONE);
//            txtDescription.setImageResource(R.drawable.ic_expand_more_black_24dp);
//        }
//
//        ObjectAnimator animation = ObjectAnimator.ofInt(txtDescription, "maxLines", txtDescription.getMaxLines());
//        animation.setDuration(200).start();
//    }

}
