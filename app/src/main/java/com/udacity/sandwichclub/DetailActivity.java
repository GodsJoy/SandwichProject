package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        TextView originTv = findViewById(R.id.origin_tv);
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);
        TextView ingredientTv = findViewById(R.id.ingredients_tv);
        TextView descTv = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            Log.d("DetailActivity", "In position");
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            Log.d("DetailActivity", "In json");
            closeOnError();
            return;
        }

        populateUI(sandwich, originTv, alsoKnownTv, ingredientTv, descTv);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    //populate textviews with appropriate data
    private void populateUI(Sandwich sandwich, TextView origin, TextView aka, TextView ing, TextView desc) {
        origin.setText(sandwich.getPlaceOfOrigin());

        //update also known as
        List<String> akaArray = sandwich.getAlsoKnownAs();
        String akaString = "";
        for(int i=0; i<akaArray.size(); i++){
            akaString += akaArray.get(i);
            if(i != akaArray.size()-1)
                akaString += ", ";
        }
        aka.setText(akaString);

        //update ingredients
        List<String> ingArray = sandwich.getIngredients();
        String ingString = "";
        for(int i=0; i< ingArray.size(); i++){
            ingString += ingArray.get(i);
            if(i != ingArray.size()-1)
                ingString += ", ";
        }
        ing.setText(ingString);

        //update description
        desc.setText(sandwich.getDescription());

    }
}
