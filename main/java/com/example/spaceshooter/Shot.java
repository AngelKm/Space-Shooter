package com.example.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

// This class makes the controls of the shots from the spaceships
public class Shot {
    // create variables
    Bitmap shot;
    Context context;
    int sx, sy;

    // assign values to variables
    public Shot(Context context, int sx, int sy) {
        this.context = context;
        shot = BitmapFactory.decodeResource(context.getResources(), R.drawable.laseryellow2);
        this.sx = sx;
        this.sy = sy;
    }

    public Bitmap getShot() {
        return shot;
    }

    public  int getShotWidth() {
        return shot.getWidth();     // returns the bitmap width as an int
    }

    public  int getShotHeight() {
        return shot.getHeight();     // returns the bitmap height as an int
    }
}
