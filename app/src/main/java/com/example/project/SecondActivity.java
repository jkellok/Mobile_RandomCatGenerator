package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class SecondActivity extends AppCompatActivity {

    ImageView imageView;
    private RequestQueue queue;
    private String imageID;
    private String imageUrl;
    private String imageUrlFromMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        queue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        imageUrlFromMain = intent.getStringExtra("IMAGE_URL_FROM_MAIN");
        TextView textViewURL = findViewById(R.id.textViewURL);

        if (imageUrlFromMain != null) {
            imageView = findViewById(R.id.imageView);
            Glide.with(this).load(imageUrlFromMain).override(800,800).into(imageView);
            textViewURL.setText(imageUrlFromMain);
        }
        else {
            textViewURL.setText(R.string.noURL);
        }
    }

    // Activity lifecycle methods
    protected void onStart() {
        super.onStart(); // Activity is about to become visible
        Log.d("MY_APP", "MainActivity: onStartCalled");
    }
    protected void onResume() {
        super.onResume(); // Activity has become visible
    }
    protected void onPause() {
        super.onPause(); // Another activity is taking focus
    }
    protected void onStop() {
        super.onStop(); // Activity is no longer visible
    }
    protected void onDestroy() {
        super.onDestroy(); // Activity is about to be destroyed
    }

    // automatic callback from system called before activity destroyed
    // save ui state here
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putString("IMAGE_ID", imageID);
        outState.putString("IMAGE_URL", imageUrl);
        outState.putString("IMAGE_URL_FROM_MAIN", imageUrlFromMain);
    }

    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //imageID = savedInstanceState.getString("IMAGE_ID");
        imageUrl = savedInstanceState.getString("IMAGE_URL");
        imageUrlFromMain = savedInstanceState.getString("IMAGE_URL_FROM_MAIN");

        TextView textViewURL = findViewById(R.id.textViewURL);

        // show the first picture from intent if a new pic isn't fetched yet
        if (imageUrl == null) {
            imageView = findViewById(R.id.imageView);
            Glide.with(this).load(imageUrlFromMain).override(800,800).into(imageView);
            textViewURL.setText(imageUrlFromMain);
        }
        // keep the fetched picture
        else {
            textViewURL.setText(imageUrl);
            imageView = findViewById(R.id.imageView);
            Glide.with(this).load(imageUrl).override(800,800).into(imageView);
        }
    }

    public void openMainActivity(View view) {
        Intent openMain = new Intent(this, MainActivity.class);
        startActivity(openMain);
    }

    public void getCat(View view) {
        String url = "https://cataas.com/cat?json=true";

        // request string response from URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.d("CAT_IMAGE", response);
                    parseJson(response);
                }, error -> {
            Log.d("CAT_IMAGE", error.toString());
        });

        queue.add(stringRequest);
    }

    public void parseJson(String response) {
        String imageUrlBase = "https://cataas.com/cat/";

        try {
            JSONObject imageResponse = new JSONObject(response);

            imageID = imageResponse.getString("_id");

            imageUrl = imageUrlBase + imageID; // url of the random picture
            TextView textViewURL = findViewById(R.id.textViewURL);
            textViewURL.setText(imageUrl);
            imageView = findViewById(R.id.imageView); // show picture
            Glide.with(this).load(imageUrl).override(800,800).into(imageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}