package com.example.spaceshooter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartUp extends AppCompatActivity {
    // create object variables
    Button buttonPlay, buttonTutorial, buttonSettings, buttonExit;
    Animation scaleUp, scaleDown;
    MediaPlayer mediaPlayer, mediaSound;
    Dialog dialog;
    AudioManager audioManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);

        buttonPlay = findViewById(R.id.button_play);
        buttonTutorial = findViewById(R.id.button_tutorial);
        buttonSettings = findViewById(R.id.button_settings); // INVISIBLE BUTTON
        buttonExit = findViewById(R.id.button_exit);
        dialog =  new Dialog(this);

        mediaPlayer = MediaPlayer.create(this, R.raw.click);
        mediaSound = MediaPlayer.create(this, R.raw.music);
        mediaSound.setLooping(true);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        SeekBar seekBarSound = findViewById(R.id.seekBarSound);
        int maxSound = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentSound = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekBarSound.setMax(maxSound);
        seekBarSound.setProgress(currentSound);

        seekBarSound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override       // when you drag te seek bar
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override       // when you put your finger on the seek bar
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override        // when you lift your finger from the seek bar
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
//*/

        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        buttonPlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mediaPlayer.start();
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonPlay.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    buttonPlay.startAnimation(scaleDown);
                }
                startActivity(new Intent(StartUp.this, MainActivity.class));
                finish();
                return true;
            }
        });


        buttonTutorial.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mediaPlayer.start();
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonTutorial.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    buttonTutorial.startAnimation(scaleDown);
                }
                // OPEN A CUSTOM DIALOG BOX LONG ENOUGH FOR INSTRUCTIONS BOTTOM BUTTON OF DONE
                openTutorialDialog();
                return true;
            }
        });
        
        // INVISIBLE BUTTON
        buttonSettings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mediaPlayer.start();
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonSettings.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    buttonSettings.startAnimation(scaleDown);
                }
                // OPEN A CUSTOM DIALOG BOX WITH SOUND AND MUSIC SLIDER CONTROLS AND BOTTOM BUTTON OF DONE
                openSettingsDialog();
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

    private void openSettingsDialog() {
        // inflate the settings dialog layout when you press the settings button
        dialog.setContentView(R.layout.settings);
        // make the background transparent
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));

        // variables for the close image and ok button
        ImageView imageViewClose = dialog.findViewById(R.id.close_imageView);
        Button buttonOk = dialog.findViewById(R.id.button_ok);

        // add a click listener for the close image
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                dialog.dismiss();
            }
        });

        // add a click listener for the ok button
        buttonOk.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mediaPlayer.start();
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonOk.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    buttonOk.startAnimation(scaleDown);
                }
                // DISMISS THE DIALOG
                dialog.dismiss();
                return true;
            }
        });

        dialog.show();
    }

    private void openTutorialDialog() {
        // inflate the tutorial dialog layout when you press the tutorial button
        dialog.setContentView(R.layout.tutorial);
        // make the background transparent
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));

        // variables for the close image and ok button
        ImageView imageViewClose = dialog.findViewById(R.id.close_imageView);
        Button buttonOk = dialog.findViewById(R.id.button_ok);

        // add a click listener for the close image
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                dialog.dismiss();
            }
        });

        // add a click listener for the ok button
        buttonOk.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mediaPlayer.start();
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonOk.startAnimation(scaleUp);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    buttonOk.startAnimation(scaleDown);
                }
                // DISMISS THE DIALOG
                dialog.dismiss();
                return true;
            }
        });

        dialog.show();
    }

    public void startGame(View view) {
        mediaPlayer.start();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}

/* INSIDE SETTINGS DIALOG
        VOLUME CONTROLS
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        SeekBar seekBarSound = findViewById(R.id.seekBarSound);
        int maxSound = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentSound = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekBarSound.setMax(maxSound);
        seekBarSound.setProgress(currentSound);

        seekBarSound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override       // when you drag te seek bar
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override       // when you put your finger on the seek bar
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override        // when you lift your finger from the seek bar
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        }); */

