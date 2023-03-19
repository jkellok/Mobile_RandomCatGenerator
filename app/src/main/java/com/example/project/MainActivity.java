package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // this will be the same picture
        imageView = findViewById(R.id.imageView3);
        Glide.with(this).load("https://cataas.com/cat/cute/says/welcome").override(800,800).into(imageView);
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

    public void openSecondActivity(View view) {
        Intent openSecond = new Intent(this, SecondActivity.class);
        // this will be the same picture
        openSecond.putExtra("IMAGE_URL_FROM_MAIN", "https://cataas.com/cat/says/hello");
        startActivity(openSecond);
    }
}