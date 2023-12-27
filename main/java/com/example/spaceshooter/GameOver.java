package com.example.spaceshooter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    Button buttonReplay, buttonExit;
    TextView pointsTextV, highPointsTextV;
    Animation scaleUp, scaleDown;
    MediaPlayer mediaPlayer, mediaSound;
    int lastScore, bestScore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        // get the points scored from the space shooter class
        int points = getIntent().getExtras().getInt("points");
        pointsTextV = findViewById(R.id.points_textV);
        highPointsTextV = findViewById(R.id.highPoints_textV);

        // get old score
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        lastScore = preferences.getInt("lastScore", 0);
        bestScore = preferences.getInt("best", 0);

        // compare scores, to update highest score if necessary
        if (lastScore > bestScore) {
            int temp = bestScore;
            bestScore = lastScore;      // swap the scores
            lastScore = temp;

            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best", bestScore);
            editor.putInt("lastScore", lastScore);
            editor.apply();
        }
        else if (lastScore == bestScore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best", bestScore);
            editor.putInt("lastScore", lastScore);
            editor.apply();
        }

        // display points
        highPointsTextV.setText("Highest Points: " + bestScore);
        pointsTextV.setText("Points Scored: " + points);

        buttonReplay = findViewById(R.id.button_replay);
        buttonExit = findViewById(R.id.button_exit);

        mediaPlayer = MediaPlayer.create(this, R.raw.click);
        mediaSound = MediaPlayer.create(this, R.raw.music);
        mediaSound.setLooping(true);

        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        buttonReplay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mediaPlayer.start();
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonReplay.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    buttonReplay.startAnimation(scaleDown);
                }
                Intent intent = new Intent(GameOver.this, StartUp.class);
                startActivity(intent);
                finish();
                return true;
            }
        });

        buttonExit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mediaPlayer.start();
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonExit.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    buttonExit.startAnimation(scaleDown);
                }
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaSound.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaSound.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaSound.pause();
    }

    // restart the game
    public void restartGame(View view) {
        mediaPlayer.start();
        Intent intent = new Intent(GameOver.this, StartUp.class);
        startActivity(intent);
        finish();
    }

    // close the app
    public void exitGame(View view) {
        mediaPlayer.start();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
