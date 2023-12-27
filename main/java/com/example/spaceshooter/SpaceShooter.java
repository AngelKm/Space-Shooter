package com.example.spaceshooter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

// This class makes the controls of the app gameplay
public class SpaceShooter extends View {
    // create variables
    Context context;
    Bitmap background, lifeImage, pause;
    Handler handler;
    Button pauseButton;
    long updateMillis = 30;
    static  int screenWidth, screenHeight;
    int points = 0;
    int life = 3;
    Paint scorePaint;
    int textSize = 60;
    boolean paused = false;
    PlayerSpaceship playerSpaceship;
    AlienSpaceship alienSpaceship;
    Random random;
    ArrayList<Shot> playerShots, alienShots;
    Explosion explosion;
    ArrayList<Explosion> explosions;
    boolean alienShotAction = false;
    MediaPlayer mediaPlayer, mediaLaser, mediaBeam, mediaExplode, mediaDead;
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    // assign values to variables
    public SpaceShooter(Context context) {
        super(context);
        this.context = context;
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        random = new Random();
        playerShots = new ArrayList<>();
        alienShots = new ArrayList<>();
        explosions = new ArrayList<>();
        playerSpaceship = new PlayerSpaceship(context);
        alienSpaceship = new AlienSpaceship(context);
        handler = new android.os.Handler();
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.bgg);
        lifeImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.favorite);
        pause = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause);
        pauseButton = new Button(((Activity) getContext()));
        scorePaint = new Paint();
        scorePaint.setColor(Color.CYAN);
        scorePaint.setTextSize(textSize);
        scorePaint.setTextAlign(Paint.Align.LEFT);
        mediaPlayer = MediaPlayer.create(((Activity) getContext()), R.raw.bum);
        mediaExplode = MediaPlayer.create(((Activity) getContext()), R.raw.explode);
        mediaLaser = MediaPlayer.create(((Activity) getContext()), R.raw.laser);
        mediaBeam = MediaPlayer.create(((Activity) getContext()), R.raw.bolt);
        mediaDead = MediaPlayer.create(((Activity) getContext()), R.raw.dead);
        pauseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //  Draw the background, points and life on the canvas
        canvas.drawBitmap(background, 0, 0,null);           // display the background
        canvas.drawText("Pt: " + points, 0, textSize, scorePaint);  // display score points
        // NOT IMPLEMENTED
        canvas.drawBitmap(pause,5, 100, null);                // display the pause

        pauseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //  FIX PROBLEM: 2 hearts = 1 life
        for (int i = life; i >= 1; i--) {       // for displaying lives on the screen
            // place lives on the top right side of the screen
            canvas.drawBitmap(lifeImage, screenWidth - lifeImage.getWidth() * i, 0, null);
        }

        // When the life is 0, stop the game and launch game over activity with points
        if (life == 0) {
            mediaDead.start();          // start playing the music indicating the player lsot
            paused = true;              // set boolean paused variable to true
            handler = null;             // set the handler variable to null

            // Create a shared preferences object named preferences, the string "PREFS" is used to
            // access the shared preferences in the class that requires it
            SharedPreferences preferences = context.getSharedPreferences("PREFS", 0);

            // Create a shared preferences editor named editor to edit the preferences
            SharedPreferences.Editor editor = preferences.edit();

            // place the number of points into the editor
            editor.putInt("lastScore", points);
            editor.apply();                        // call th apply() method on editor

            // Create an new intent object called intent that send information from the
            // SpaceShooter.java class to the GameOver.class
            Intent intent = new Intent(context, GameOver.class);

            // Store the points into the intent, the name "points" is used to access the number of
            // points in the GameOver.class
            intent.putExtra("points", points);
            context.startActivity(intent);          // Pass the intent and start the new activity
            ((Activity) context).finish();          // finish all operations in this activity
        }

        // Move the enemy alien spaceship
        alienSpaceship.ax += alienSpaceship.alienVelocity;
        // if the alien spaceship collides with the right wall, reverse the alienVelocity
        if (alienSpaceship.ax + alienSpaceship.getAlienSpaceshipWidth() >= screenWidth) {
            alienSpaceship.alienVelocity *= -1;
        }
        // if the spaceship collides with the left wall, reverse the alienVelocity again
        if (alienSpaceship.ax <= 0) {
            alienSpaceship.alienVelocity *= -1;
        }
        // if the alien spaceship is not shot, the alien spaceship should continuously fire shots
        // from random travelled distances
        if (!alienShotAction) {
            if (alienSpaceship.ax >= 200 + random.nextInt(400)) {
                Shot alienShot = new Shot(context, alienSpaceship.ax +
                        alienSpaceship.getAlienSpaceshipWidth() / 2, alienSpaceship.ay);

                alienShots.add(alienShot);  // add the new alienShot object to the arrayList
                mediaLaser.start();
                alienShotAction = true; // to make the alien spaceship take a shot at a time
            }
            if (alienSpaceship.ax >= 400 + random.nextInt(800)) {
                Shot alienShot = new Shot(context, alienSpaceship.ax +
                        alienSpaceship.getAlienSpaceshipWidth() / 2, alienSpaceship.ay);

                alienShots.add(alienShot);  // add the new alienShot object to the arrayList
                mediaLaser.start();
                alienShotAction = true; // to make the alien spaceship take a shot at a time
            }
            else {
                Shot alienShot = new Shot(context, alienSpaceship.ax +
                        alienSpaceship.getAlienSpaceshipWidth() / 2, alienSpaceship.ay);

                alienShots.add(alienShot);  // add the new alienShot object to the arrayList
                mediaLaser.start();
                alienShotAction = true; // to make the alien spaceship take a shot at a time
            }
        }

        // draw the alien spaceship
        canvas.drawBitmap(alienSpaceship.getAlienSpaceship(), alienSpaceship.ax, alienSpaceship.ay, null);

        // draw the player's spaceship within the screen edges
        if (playerSpaceship.px > screenWidth - playerSpaceship.getPlayerSpaceshipWidth()) {
            playerSpaceship.px = screenHeight - playerSpaceship.getPlayerSpaceshipWidth();
        }
        else if (playerSpaceship.px < 0) {
            playerSpaceship.px = 0;
        }

        // draw the player's spaceship
        canvas.drawBitmap(playerSpaceship.getPlayerSpaceship(), playerSpaceship.px, playerSpaceship.py, null);

        // Draw the alien shots downwards towards the player's spaceship
        // if the player's spaceship gets hit, decrement life,
        // remove the shot object from alienShots ArrayList and show an explosion.
        // Else if, the shot goes away through the bottom edge of the screen also remove
        // the shot object from alienShots.
        // When there is no alienShots on the screen, change alienShotAction to false, so the alien
        // spaceship can shoot.

        // loop through the number of the elements in the array list
        for (int i = 0; i < alienShots.size(); i++) {
            alienShots.get(i).sy += 15;     //  FIND OUT WHAT THE 15 IS FOR!!
            canvas.drawBitmap(alienShots.get(i).getShot(), alienShots.get(i).sx, alienShots.get(i).sy, null);

            if ((alienShots.get(i).sx >= playerSpaceship.px) &&
            alienShots.get(i).sx <= playerSpaceship.px + playerSpaceship.getPlayerSpaceshipWidth() &&
            alienShots.get(i).sy >= playerSpaceship.py &&
            alienShots.get(i).sy <= screenHeight) {
                life--;                     // decrement life
                alienShots.remove(i);       // remove the alien shots
                // create a new explosion object
                explosion = new Explosion(context, playerSpaceship.px, playerSpaceship.py);
                explosions.add(explosion);      // add the new explosion object to the arrayList
                mediaExplode.start();
            }
            else if (alienShots.get(i).sy >= screenHeight) {
                alienShots.remove(i);
            }

            if (alienShots.size() < 1) {
                alienShotAction = false;
            }
        }

        // draw the player's spaceship shots towards the enemy.
        // If the player's shot collides with the enemy spaceship increment points,
        // remove the shot from playerShots and create a new Explosion object.
        // Else if, player's shot goes away through the top edge of the screen and remove
        // the shot object from alienShots ArrayList.

        // loop through the number of the elements in the array list
        for (int i = 0; i < playerShots.size(); i++) {
            playerShots.get(i).sy -= 15;
            canvas.drawBitmap(playerShots.get(i).getShot(), playerShots.get(i).sx, playerShots.get(i).sy, null);
            mediaBeam.start();

            if ((playerShots.get(i).sx >= alienSpaceship.ax) &&
                    playerShots.get(i).sx <= alienSpaceship.ax + alienSpaceship.getAlienSpaceshipWidth() &&
                    playerShots.get(i).sy <= alienSpaceship.getAlienSpaceshipWidth() &&
                    playerShots.get(i).sy >= alienSpaceship.ay) {
                points++;       // increment the points
                playerShots.remove(i);
                // create a new explosion object
                explosion = new Explosion(context, alienSpaceship.ax, alienSpaceship.ay);
                explosions.add(explosion);      // add the new explosion object to the arrayList
                mediaPlayer.start();
            }
            else if (playerShots.get(i).sy <= 0) {
                playerShots.remove(i);
            }
        }

        // make the explosion
        // loop through the number of the elements in the array list
        for (int i = 0; i < explosions.size(); i++) {
            canvas.drawBitmap(explosions.get(i).getExplosion(explosions.get(i).explosionFrame), explosions.get(i).ex, explosions.get(i).ey, null);
            explosions.get(i).explosionFrame++;

            if (explosions.get(i).explosionFrame > 9) { // number of explosion frames is 9
                explosions.remove(i);
            }
        }

        // If not paused, call the postDelayed() method on handler object which will cause the
        // run method inside Runnable to be executed after 30 milliseconds, that is the value inside
        // updateMillis.
        if(!paused)
            handler.postDelayed(runnable, updateMillis);
    }

    // CONTROL THE PLAYER SPACESHIP WITH THE USER TOUCH ON THE SCREEN
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int)event.getX();
        // When event.getAction() is MotionEvent.ACTION_UP, if ourShots arraylist size < 1,
        // create a new Shot.
        // This restricts the player to only make one shot at a time, on the screen.
        if(event.getAction() == MotionEvent.ACTION_UP){
            if(playerShots.size() < 1){
                Shot playerShot = new Shot(context, playerSpaceship.px + playerSpaceship.getPlayerSpaceshipWidth() / 2, playerSpaceship.py);
                playerShots.add(playerShot);
            }
        }
        // When event.getAction() is MotionEvent.ACTION_DOWN, control playerSpaceship
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            playerSpaceship.px = touchX;
        }
        // When event.getAction() is MotionEvent.ACTION_MOVE, control playerSpaceship
        // along with the touch.
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            playerSpaceship.px = touchX;
        }
        return true; // no further handling is required
    }

    private void pauseGame() {

    }

}
