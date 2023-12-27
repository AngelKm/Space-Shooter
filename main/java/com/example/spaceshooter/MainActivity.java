package com.example.spaceshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Animation scaleUp, scaleDown;
    MediaPlayer mediaPlayer, mediaSound;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SpaceShooter(this));

        mediaSound = MediaPlayer.create(this, R.raw.click);
        mediaPlayer = MediaPlayer.create(this, R.raw.play);
        mediaPlayer.setLooping(true);

        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);
    }

    @Override
    public void onBackPressed() {
        //openCloseDialog();
        /* MANUAL DIALOG */
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to go back?")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(MainActivity.this, StartUp.class));
                        finish();
                    }
                })
                .setNegativeButton("NO", null)
                .show();
    }

    // FAILED TO IMPLEMENT
    private void openCloseDialog() {
        // inflate the close dialog layout
        dialog.setContentView(R.layout.close);
        // make the background transparent
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));

        // variables for the close image and ok button
        ImageView imageViewClose = dialog.findViewById(R.id.close_imageView);
        Button buttonYes = dialog.findViewById(R.id.button_yes);
        Button buttonNo = dialog.findViewById(R.id.button_no);

        // add a click listener for the close image
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaSound.start();
                dialog.dismiss();
            }
        });

        // add a click listener for the yes button
        buttonYes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mediaSound.start();
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonYes.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    buttonYes.startAnimation(scaleDown);
                }
                // GO TO STARTUP PAGE
                startActivity(new Intent(MainActivity.this, StartUp.class));
                finish();
                return true;
            }
        });

        // add a click listener for the ok button
        buttonNo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mediaSound.start();
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonNo.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    buttonNo.startAnimation(scaleDown);
                }
                // DISMISS THE DIALOG
                dialog.dismiss();
                return true;
            }
        });
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }
}










/* X BUTTON ATTRIBUTION
* <a href="https://www.flaticon.com/free-icons/delete" title="delete icons">Delete icons created by Freepik - Flaticon</a>
* <a href="https://www.flaticon.com/free-icons/cancel" title="cancel icons">Cancel icons created by Alfredo Hernandez - Flaticon</a>
*
* PLAY BUTTON
* <a href="https://www.flaticon.com/free-icons/play-button" title="play button icons">Play button icons created by Freepik - Flaticon</a>
*
* PAUSE BUTTON
*<a href="https://www.flaticon.com/free-icons/pause" title="pause icons">Pause icons created by Freepik - Flaticon</a>
*
* RESTART BUTTON
*<a href="https://www.flaticon.com/free-icons/recurrent" title="recurrent icons">Recurrent icons created by Freepik - Flaticon</a>
*
* LIFE
* <a href="https://www.flaticon.com/free-icons/heart" title="heart icons">Heart icons created by Freepik - Flaticon</a>
* <a href="https://www.flaticon.com/free-icons/heart" title="heart icons">Heart icons created by Icon Hubs - Flaticon</a>
* <a href="https://www.flaticon.com/free-icons/heart" title="heart icons">Heart icons created by IYAHICON - Flaticon</a>
*
* SPACESHIP
* <a href="https://www.flaticon.com/free-icons/space-invaders" title="space invaders icons">Space invaders icons created by Freepik - Flaticon</a>
* <a href="https://www.flaticon.com/free-icons/spaceship" title="spaceship icons">Spaceship icons created by Skyclick - Flaticon</a>
*
* ALIEN
* <a href="https://www.flaticon.com/free-icons/alien" title="alien icons">Alien icons created by Freepik - Flaticon</a>
* <a href="https://www.flaticon.com/free-icons/spaceship" title="spaceship icons">Spaceship icons created by Pixel Buddha - Flaticon</a>
*
* LASER
* <a href="https://www.flaticon.com/free-icons/laser" title="laser icons">Laser icons created by Freepik - Flaticon</a>
* <div> Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik"> Freepik </a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com'</a></div>
* <a target="_blank" href="https://icons8.com/icon/52076/laser-beam">Laser Beam</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
* */