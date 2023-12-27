package com.example.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

// This class makes the controls of the enemy alien spaceship
public class AlienSpaceship {
    // create variables
    Context context;
    Bitmap alienSpaceship;
    int ax, ay;
    int alienVelocity;
    Random random;

    // assign values to variables
    public AlienSpaceship(Context context) {
        this.context = context;
        // create a bitmap object from the drawable resource ufo.png
        alienSpaceship = BitmapFactory.decodeResource(context.getResources(), R.drawable.ufo);
        random = new Random();          // create a new random object
        ax = 200 + random.nextInt(400);
        ay = 0;
        alienVelocity = 16 + random.nextInt(10);
    }

    public Bitmap getAlienSpaceship() {
        return alienSpaceship;
    }

    int getAlienSpaceshipWidth() {
        return alienSpaceship.getWidth();   // returns the bitmap width as an int
    }

    int getAlienSpaceshipHeight() {
        return alienSpaceship.getHeight();  // returns the bitmap height as an int
    }
}
