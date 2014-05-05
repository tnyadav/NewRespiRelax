package com.example.respirelax;

import android.content.Context;


public class Util {

	public static final String TIME = "time";
	public static final String FREQUENCY = "frequency";
	public static final String CLOSE_ACTION = "close";
	
	public static int convertDensityPixelToPixel(Context context, int i) {
		return (int) ((i * context.getResources().getDisplayMetrics().density) + 0.5);
	}
}
