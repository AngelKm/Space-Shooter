package com.example.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

// This class makes the controls of the explosion after a spaceship gets hit
public class Explosion {
    // create variables
    Bitmap[] explosion = new Bitmap[10];        // array stores the explosions resources
    int ex, ey;
    int explosionFrame;

    public Explosion(Context context, int ex, int ey) {
        explosion[0] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.explosion0);
        explosion[1] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.explosion1);
        explosion[2] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.explosion2);
        explosion[3] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.explosion3);
        explosion[4] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.explosion4);
        explosion[5] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.explosion5);
        explosion[6] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.explosion6);
        explosion[7] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.explosion7);
        explosion[8] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.explosion8);
        explosion[9] = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.explosion9);
        explosionFrame = 0;
        this.ex = ex;
        this.ey = ey;
    }

    public Bitmap getExplosion(int explosionFrame) {
        return explosion[explosionFrame];
    }
}
