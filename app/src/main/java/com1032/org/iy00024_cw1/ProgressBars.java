package com1032.org.iy00024_cw1;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * Created by ImadEddine on 08/03/2016.
 * This activity is linked with the boot up screen
 */
public class ProgressBars extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Linking with the boot up screen (check res/layout/boot_up_screen.xml)
        setContentView(R.layout.boot_up_screen);

        // Handler instance is only for visual purposes
        // Application does not do anything for 2 seconds
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Inflating the progress bar
                ProgressBar mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
                ObjectAnimator progressAnim = ObjectAnimator.ofInt(mProgressBar, "progressing", 0, 100);

                // Setting duration of progress bar of 2 seconds
                progressAnim.setDuration(2000);
                progressAnim.setInterpolator(new LinearInterpolator());
                progressAnim.start();
                // At the end of the 3 seconds, MainActivity class is called
                Intent intent = new Intent(ProgressBars.this, MainActivity.class);
                startActivity(intent);
                // Animating the transition between both activities (check res/layout/slide_up.xml and res/layout/slide_out_up.xml)
                overridePendingTransition(R.anim.slide_up, R.anim.slide_out_up);

            }
        }, 2000);



    }
}
