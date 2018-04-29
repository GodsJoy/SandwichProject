package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            //create a JSONObject from the json string
            //and retrieve all data in it to create a sandwich object.
            JSONObject jObj = new JSONObject(json);

            JSONObject jName = jObj.getJSONObject("name");

            JSONArray akaJson = jName.getJSONArray("alsoKnownAs");
            ArrayList<String> akaArray = new ArrayList<>();
            for(int i=0; i<akaJson.length(); i++)
                akaArray.add((String)akaJson.get(i));

            JSONArray ingJson = jObj.getJSONArray("ingredients");
            ArrayList<String> ingArray = new ArrayList<>();
            for(int i=0; i<ingJson.length(); i++)
                ingArray.add((String)ingJson.get(i));

            //create a sandwich object
            Sandwich sandwich = new Sandwich((String)jName.get("mainName"),
                    akaArray,
                    (String)jObj.get("placeOfOrigin"),
                    (String)jObj.get("description"),
                    (String)jObj.get("image"),
                    ingArray);
            return sandwich;
        }
        catch (Exception e){
            Log.d("JSONUtils", e.getMessage());
            return null;
        }


    }
}
