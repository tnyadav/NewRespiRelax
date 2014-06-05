package com.tn.respirelax;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import com.tn.respirelax.R;
import com.tn.respirelax.util.ScalingUtilities;
import com.tn.respirelax.util.Util;
import com.tn.respirelax.util.ScalingUtilities.ScalingLogic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	
/*	private static final String START = "Start";
	private static final String PAUSE = "Pause";
	private static final Mode RESUME = "Resume";*/
	private static final int SECOND = 1000;
	private static final int MINUTE = SECOND*60;
	private static final String TAG = "MainActivity";
	private Bundle savedInstanceState;
	
	Button b1,b2,guide;
	FrameLayout l1;
	LinearLayout im;
	TextView tv;
	
    int duration=1,newDuration;
    int frequency=10;
    boolean end=false;
    
    int counter,tempCounter;

    float multiplayer;
    int tempMultiplayer,imHeight,height,width;
    Timer timer;
    int totalDistance,speed;
    Context context;
    FrameLayout.LayoutParams params;
    private enum Mode {START,PAUSE,RESUME};
    
    Mode mode= Mode.START;
    Bitmap back,front;
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.savedInstanceState=savedInstanceState;
		setContentView(R.layout.activity_main);
		context=this;
	    tv=(TextView)findViewById(R.id.textView1);
	    l1=(FrameLayout)findViewById(R.id.l1);
		im=(LinearLayout)findViewById(R.id.im);
		
		   height=Util.getScreenHeightPixel(this);
		   width=Util.getScreenWidthPixel(this);
		
		 back = ScalingUtilities.decodeResource(getResources(),
				R.drawable.metter_back, Util.getScreenWidthPixel(this),
				Util.getScreenHeightPixel(this) - 500, ScalingLogic.FIT);
		 back =ScalingUtilities.createScaledBitmap(back, width, height-200, ScalingLogic.FIT);
		 front=ScalingUtilities.decodeResource(getResources(),
				R.drawable.metter, Util.getScreenWidthPixel(this),
				Util.getScreenHeightPixel(this) - 500, ScalingLogic.FIT);
		 front =ScalingUtilities.createScaledBitmap(front, width, height-200, ScalingLogic.FIT);
		 
		 
		
		try {
			duration=getIntent().getIntExtra(Util.TIME, 6);
			frequency=getIntent().getIntExtra(Util.FREQUENCY, 6);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		 newDuration=((duration*60)%3600);

			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(front.getWidth(), front.getHeight());
			lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
			lp.addRule(RelativeLayout.CENTER_VERTICAL);
			l1.setLayoutParams(lp);
			
			int sdk = android.os.Build.VERSION.SDK_INT;
			if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
				    l1.setBackgroundDrawable(new BitmapDrawable(getResources(), back));
					im.setBackgroundDrawable(new BitmapDrawable(getResources(), front));
			} else {
				    l1.setBackground(new BitmapDrawable(getResources(), back));
					im.setBackground(new BitmapDrawable(getResources(), front));;
			}
			
		   
				
			Bitmap boble=BitmapFactory.decodeResource(getResources(), R.drawable.boble);
			boble=ScalingUtilities.createScaledBitmap(boble, front.getWidth(), 500, ScalingLogic.FIT);
			
			imHeight=front.getHeight()-boble.getHeight();
			boble.recycle();
			params = new FrameLayout.LayoutParams(front.getWidth(), front.getHeight());
			params.topMargin = imHeight;
			im.setLayoutParams(params);
	   		im.setVisibility(View.VISIBLE);
			
		Log.e(TAG, "duration : "+duration+" frequency : "+frequency);
	   
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() { 
				 	
			
				
				    float f=((float)60/(float)(2*frequency));
					Log.e(TAG, ""+f);
				    double roundof = Math.round(f * 100.0) / 100.0;
					
					tempMultiplayer=(int) (roundof*100);
					multiplayer=(float) ((float)imHeight/(float)tempMultiplayer);
					Log.e(TAG, "multiplayer"+multiplayer);
					   
					duration=duration*60*100;
					Log.e(TAG, "imHeight : "+imHeight+" tempMultiplayer : "+tempMultiplayer+" multiplayer : "+multiplayer+" duration : "+duration);
				
				b1=(Button)findViewById(R.id.b1);
				
				b1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {

						switch (mode) {
						case START:
							counter = 0;
							timer=new Timer();
							timer.schedule(new MyTimerTask(), 000, 10);
							mode = Mode.PAUSE;
							break;
						case PAUSE:
							end = true;
							mode = Mode.RESUME;
							break;
						case RESUME:
							end = false;
							mode = Mode.PAUSE;
							break;
						default:
							break;
						}

					}
					
				});
				
			}
		}, 500);
	    tv.setText(getCounterText(0));
		IntentFilter filter = new IntentFilter(Util.CLOSE_ACTION);
		registerReceiver(myBroadcastReceiver, filter);
	}

	@Override
	protected void onResume() {
		
		super.onResume();
		if (savedInstanceState!=null && mode==Mode.START) {
	
			end=false;
			mode= Mode.PAUSE;;
		}
		
		
		Button btnSetting=(Button)findViewById(R.id.btnSetting);
		btnSetting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if ( mode!=Mode.START) {
				end=true;
				mode=Mode.RESUME;
				}
				startActivity(new Intent(getApplicationContext(), SettingActivity.class));
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
			//	finish();
			}
		});
		Button guide=(Button)findViewById(R.id.guide);
		guide.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mode!=Mode.START) {
				end=true;
				mode=Mode.RESUME;
				}
				startActivity(new Intent(getApplicationContext(), GuideActivity.class));
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
			//	finish();
			}
		});

	}
	@Override
	protected void onPause() {
		
		super.onPause();
		

		
	}
	@Override
	protected void onDestroy() {
		Log.e("Mainactivity  :", "onDestroy");
		try{
			unregisterReceiver(myBroadcastReceiver);
		}catch (Exception e){
			e.printStackTrace();
		}
			super.onDestroy();
		
	}

	class MyTimerTask extends TimerTask {

		  @Override
		  public void run() {
	
		   runOnUiThread(new Runnable(){

		   
			@Override
				public void run() {
					if (!end ) {

						if (counter % tempMultiplayer == 0) {
							tempCounter = 0;
						}
						tv.setText(getCounterText(counter / 100));
						if (counter<duration) {
						
							if ((counter / tempMultiplayer) % 2 == 0) {
								params.topMargin=imHeight;
								im.setLayoutParams(params);
								params.topMargin= (imHeight)
										- ((int) ((tempCounter) * multiplayer));
								im.setLayoutParams(params);
							} else {
								params.topMargin = 0;
								im.setLayoutParams(params);
                                params.topMargin = (int) (tempCounter * multiplayer);
								im.setLayoutParams(params);
							}
						}
						
                        
                        
						if (counter == duration) {
							Log.e(TAG, "counter : "+(counter/ 100));
							end = true;
							timer.cancel();
							params.topMargin = imHeight;
							im.setLayoutParams(params);
							mode = Mode.START;
							counter = 0;
							tempCounter = 0;
						}
						counter++;
						tempCounter++;	
					}

				}});
		  }
		  
		 }

	private String getCounterText(int second) {
		second = newDuration - second;
		int minutes = (second % 3600) / 60;
		second = second % 60;
		return twoDigitString(minutes) + " : " + twoDigitString(second);

	}

	private String twoDigitString(int number) {

		if (number == 0) {
			return "00";
		}
		if (number / 10 == 0) {
			return "0" + number;
		}
		return String.valueOf(number);
	}
	private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	};
}
