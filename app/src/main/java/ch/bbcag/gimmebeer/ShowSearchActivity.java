package ch.bbcag.gimmebeer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ShowSearchActivity extends AppCompatActivity {
    private int beerId;
    private String abv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_search);
        Intent intent = getIntent();
        beerId = intent.getIntExtra("id", 0);
        abv = intent.getStringExtra("abv");
    }
}
