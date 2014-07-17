
package com.bib.breathrelax;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;

import com.bib.breathrelax.R;

public class AppLaunchActivity extends Activity {
 
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        new Handler().postDelayed(new Runnable() {
 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
 
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(AppLaunchActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        int targetWidth = bitmap.getWidth();
        int targetHeight =  bitmap.getHeight();
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, 
                                  targetHeight,Bitmap.Config.ARGB_8888);
        
        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
        ((float) targetHeight - 1) / 2,
        (Math.min(((float) targetWidth), 
                      ((float) targetHeight)) / 2),
        Path.Direction.CCW);
        
                      canvas.clipPath(path);
        Bitmap sourceBitmap = bitmap;
        canvas.drawBitmap(sourceBitmap, 
                                      new Rect(0, 0, sourceBitmap.getWidth(),
          sourceBitmap.getHeight()), 
                                      new Rect(0, 0, targetWidth,
          targetHeight), null);
        return targetBitmap;
       }
}
