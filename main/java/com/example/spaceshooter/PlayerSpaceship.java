package com.example.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

// This class makes the controls of the player's spaceship
public class PlayerSpaceship {
    // create variables
    Context context;
    Bitmap playerSpaceship;
    int px, py;
    Random random;

    // assign values to variables
    public PlayerSpaceship(Context context) {
        this.context = context;
        playerSpaceship = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship1);
        random = new Random();
        px = random.nextInt(SpaceShooter.screenWidth);
        py = SpaceShooter.screenHeight - playerSpaceship.getHeight();
    }

    public Bitmap getPlayerSpaceship() {
        return playerSpaceship;
    }

    int getPlayerSpaceshipWidth() {
        return playerSpaceship.getWidth();      // returns the bitmap width as an int
    }
}
