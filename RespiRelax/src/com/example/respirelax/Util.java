package com.example.respirelax;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;


public class Util {

	public static final String TIME = "time";
	public static final String FREQUENCY = "frequency";
	public static final String CLOSE_ACTION = "close";
	
	public static int convertDensityPixelToPixel(Context context, int i) {
		return (int) ((i * context.getResources().getDisplayMetrics().density) + 0.5);
	}
	
	
	public static int getScreenHeightPixel(Activity activity) {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay()
				.getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		return height;
	}
	public static int getScreenWidthPixel(Activity activity) {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay()
				.getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		return height;
	}
}
