package com.example.spaceshooter;

import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

// This class Makes the animation for the progress bar
public class ProgressBarAnimation extends Animation {
    // create variables
    private Context context;
    private ProgressBar progressBar;
    private TextView textView;
    private float from, to;

    // assign values to variables
    public ProgressBarAnimation(Context context, ProgressBar progressBar, TextView textView,
                                float from, float to) {
        this.context = context;
        this.progressBar = progressBar;
        this.textView = textView;
        this.from = from;
        this.to = to;
    }

    // perform the animation
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int)value);
        textView.setText((int)value +"%");      // set text on the textView as an int

        if (value == to) {      // start the activity
            context.startActivity(new Intent(context, StartUp.class));
        }
    }
}
