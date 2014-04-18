package com.example.respirelax;

import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

public class TranslateAnim extends TranslateAnimation{

    public TranslateAnim(int fromXType, float fromXValue, int toXType, float toXValue, int fromYType, float fromYValue, int toYType, float toYValue) 
    		{
        super(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue, toYType, toYValue);
        // TODO Auto-generated constructor stub
    }

    private long mElapsedAtPause=0;
    private boolean mPaused=false;

    @Override
    public boolean getTransformation(long currentTime, Transformation outTransformation) {
        if(mPaused && mElapsedAtPause==0) {
            mElapsedAtPause=currentTime-getStartTime();
        }
        if(mPaused)
            setStartTime(currentTime-mElapsedAtPause);
        return super.getTransformation(currentTime, outTransformation);
    }

    public void pause() {
        mElapsedAtPause=0;
        mPaused=true;
    }

    public void resume() {
        mPaused=false;
    }
}
