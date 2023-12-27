package com.example.spaceshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

// This Class makes the first loading screen of the app
public class LoadingScreen extends AppCompatActivity {
    // create variables
    ProgressBar progressBar;
    TextView textView;
    ImageView imageView;
    private int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        // connect the layout controls to the java code
        progressBar = findViewById(R.id.progress_bar);
        textView = findViewById(R.id.progress_textV);
        imageView = findViewById(R.id.imageView);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar.setMax(100);
        progressBar.setScaleY(3f);

        progressBarAnimation();     // call the animation method

        // ANIMATES THE SPACESHIP
        LoadingScreen.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setRotation(x++);

                new Handler().postDelayed(this, 200);
            }
        });

        // reference for handler, the handler will help create a thread that will run after a few seconds
        Handler handler = new Handler();
        // after some seconds it will call the run method
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // call the startup activity
                // imageView.setRotation(x++); // doesn't animate
                startActivity(new Intent(LoadingScreen.this, StartUp.class));
                finish(); // so the activity doesn't start up again
            }
        },8000); // display load screen for 8 seconds then call next activity
    }

    public void progressBarAnimation() {
        ProgressBarAnimation animation = new ProgressBarAnimation(this, progressBar,
                textView, 0f, 100f);       // create an animation object
        animation.setDuration(8000);                // run animation for 8 seconds
        progressBar.setAnimation(animation);        // Set animation to the progress bar
    }
}