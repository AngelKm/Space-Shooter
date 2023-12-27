package com.example.spaceshooter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatDialogFragment {

    Dialog dialog;
    Animation scaleUp, scaleDown;
    MediaPlayer mediaPlayer;
    AudioManager audioManager;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.settings, null);

        //builder.setView(view);

        dialog =  new Dialog(((Activity) getContext()));
        // inflate the settings dialog layout when you press the settings button
        dialog.setContentView(R.layout.settings);
        // make the background transparent
        dialog.getWindow();
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));

        SeekBar seekBarSound = dialog.findViewById(R.id.seekBarSound);

        // variables for button animations
        scaleUp = AnimationUtils.loadAnimation(((Activity) getContext()), R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(((Activity) getContext()), R.anim.scale_down);

        mediaPlayer = MediaPlayer.create(((Activity) getContext()), R.raw.click);
        // variables for the close image and ok button
        ImageView imageViewClose = dialog.findViewById(R.id.close_imageView);
        Button buttonOk = dialog.findViewById(R.id.button_ok);
        // add a click listener for the close image
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                dialog.dismiss();
                //builder.dismiss();
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



        return builder.create();
/*



        mediaPlayer = MediaPlayer.create(((Activity) getContext()), R.raw.click);

        dialog =  new Dialog(((Activity) getContext()));
        // inflate the settings dialog layout when you press the settings button
        dialog.setContentView(R.layout.settings);
        // make the background transparent
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));

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



        dialog.show();

        */
    }
}
