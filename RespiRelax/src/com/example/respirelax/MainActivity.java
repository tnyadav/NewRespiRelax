package com.example.respirelax;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
	ImageView im;
	TextView tv;
	
    int duration=1,newDuration;
    int frequency=10;
    boolean end=false;
    
    int counter,tempCounter;

    float multiplayer;
    int tempMultiplayer,imHeight;
    Timer timer;
    int totalDistance,speed;
    Context context;
    FrameLayout.LayoutParams params;
    private enum Mode {START,PAUSE,RESUME};
    
    Mode mode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.savedInstanceState=savedInstanceState;
		setContentView(R.layout.activity_main);
		context=this;
	    tv=(TextView)findViewById(R.id.textView1);
		im=(ImageView)findViewById(R.id.im);
		mode=Mode.START;
		//layout=(LinearLayout)findViewById(R.id.l1);
	
		try {
			duration=getIntent().getIntExtra(Util.TIME, 1);
			frequency=getIntent().getIntExtra(Util.FREQUENCY, 20);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		 newDuration=((duration*60)%3600);
		Log.e(TAG, "duration : "+duration+" frequency : "+frequency);
	    params = (FrameLayout.LayoutParams) im
				.getLayoutParams();
		
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() { 
				    

                    imHeight=im.getHeight()-50;
                    params.topMargin = imHeight;
            		im.setLayoutParams(params);
            		im.setVisibility(View.VISIBLE);
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
