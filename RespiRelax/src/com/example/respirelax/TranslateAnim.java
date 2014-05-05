package com.example.respirelax;

import android.util.Log;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

public class TranslateAnim extends TranslateAnimation{

	public TranslateAnim(int fromXType, float fromXValue, int toXType,
			float toXValue, int fromYType, float fromYValue, int toYType,
			float toYValue) {
		super(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue,
				toYType, toYValue);
		// TODO Auto-generated constructor stub
	}

	public TranslateAnim(float fromXValue, float toXValue, float fromYValue,
			float toYValue) {
		super(fromXValue, toXValue, fromYValue, toYValue);
		// TODO Auto-generated constructor stub
	}
    private long mElapsedAtPause=0;
    private boolean mPaused=false;
    private long counter=0;

    @Override
    public boolean getTransformation(long currentTime, Transformation outTransformation) {
       
    	 
		   
    	if(mPaused && mElapsedAtPause==0) {
            mElapsedAtPause=currentTime-getStartTime();
        }
        if(mPaused)
            setStartTime(currentTime-mElapsedAtPause);
        if (!mPaused) {
			counter++;
			//Log.e("TranslateAnim", ""+counter);
		}
        return super.getTransformation(currentTime, outTransformation);
    }

    public void pause() {
        mElapsedAtPause=0;
        mPaused=true;
    }

    public void resume() {
        mPaused=false;
    }
    public long getCounter() {
        return counter;
    }
}
