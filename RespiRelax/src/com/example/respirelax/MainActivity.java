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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	
	private static final String START = "Start";
	private static final String PAUSE = "Pause";
	private static final String RESUME = "Resume";
	private static final int SECOND = 1000;
	private static final int MINUTE = SECOND*60;
	private static final String TAG = "MainActivity";
	private Bundle savedInstanceState;
	
	Button b1,b2;
	ImageView im;
	TextView tv;
	//LinearLayout layout;

	AnimationSet animationSet;
    int duration=6;
    int frequency=5;
    boolean end=false;
    int counter;
    int tempCounter;
    float multiplayer;
    Timer timer;
    int totalDistance,speed;
    Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.savedInstanceState=savedInstanceState;
		setContentView(R.layout.activity_main);
		context=this;
	    tv=(TextView)findViewById(R.id.textView1);
		im=(ImageView)findViewById(R.id.im);
		//layout=(LinearLayout)findViewById(R.id.l1);
	
		try {
			duration=getIntent().getIntExtra(Util.TIME, 1);
			frequency=getIntent().getIntExtra(Util.FREQUENCY, 5);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		Log.e(TAG, "duration : "+duration+" frequency : "+frequency);
		float f=(float)(60/8);
		Log.e(TAG, "f:"+f);
		double roundof = Math.round(f * 100.0) / 100.0;
		Log.e(TAG, "roundof:"+roundof);
		int i=(int) (roundof*100);
		Log.e(TAG, "i:"+i);
		multiplayer=(float)450/i;
		duration=duration*60*100;
		frequency=MINUTE/(frequency*2);
		Log.e(TAG, "duration : "+duration+" frequency : "+frequency+" multiplayer :"+multiplayer);
		timer=new Timer();
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				//int y=(int) layout.getTop();
				int imHeight=im.getHeight();
				int height=Util.convertDensityPixelToPixel(context, 300)-50;
			    
				int position=height/2;
				
				  totalDistance=Util.convertDensityPixelToPixel(context, 300);
				 speed = totalDistance/75;
			
				 Log.e("speed", totalDistance+":"+speed);	
				
				
			
				
				b1=(Button)findViewById(R.id.b1);
				
				b1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						
						
						String text=b1.getText().toString();
						if (text.equals(START)) {
							counter=0;
							timer.schedule(new MyTimerTask(), 000,10);
							b1.setText(PAUSE);
						}
						
						
						if (text.equals(PAUSE)) {
							
							end=true;
							b1.setText(RESUME);
							
							
						}
						if (text.equals(RESUME)) {
						
							end=false;
							b1.setText(PAUSE);
						}
					    
					}
					
				});
				
			}
		}, 1000);
		IntentFilter filter = new IntentFilter(Util.CLOSE_ACTION);
		registerReceiver(myBroadcastReceiver, filter);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (savedInstanceState!=null && !b1.getText().toString().equals(START)) {
	
			end=false;
			b1.setText(PAUSE);
		}
		
		
		Button btnSetting=(Button)findViewById(R.id.btnSetting);
		btnSetting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!b1.getText().toString().equals(START)) {
					
			
				end=true;
				b1.setText(RESUME);
				}
				/*startActivity(new Intent(getApplicationContext(), SettingActivity.class));
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);*/
			//	finish();
			}
		});

	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
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

		    @SuppressLint("NewApi")
			@Override
		    public void run() {
		    	if (!end) {
		    		 
		 		  // Log.e("tick", counter+":"+threashholeatime+":"+animatiomCounter);
		 		 if (counter%750==0) {
					tempCounter=0;
				}
		 		 Log.e("status", (counter)+":"+duration);
		 		if ((counter/750) % 2 == 0) {
		 			 tv.setText(getCounterText(counter/100));
					   
					   AbsoluteLayout.LayoutParams params = 
							    (AbsoluteLayout.LayoutParams)im.getLayoutParams();
							
					   params.y = (int)(tempCounter*.533);
		                  im.setLayoutParams(params);	
		 			} else {
		 				tv.setText(getCounterText(counter/100));
						   
						   AbsoluteLayout.LayoutParams params = 
								    (AbsoluteLayout.LayoutParams)im.getLayoutParams();
								
						   params.y = (totalDistance-50)-((int)((tempCounter)*.533));
			                  im.setLayoutParams(params);	
		 			}
		 		   
		 		if ((counter)==duration) {
					end=true;
					b1.setText(START);
					counter=0;
			 		  tempCounter=0;
				}
		 		  counter++;
		 		  tempCounter++;
				}
		   
		    }});
		  }
		  
		 }
	private String getCounterText(int second) {
		
		int minutes = (second % 3600) / 60;
		int seconds = second % 60;
		String strMinuts=""+minutes;
	    String strSeconds=""+seconds;
		if (minutes<10) {
			strMinuts="0"+minutes;
		}
		if (seconds<10) {
			strSeconds="0"+seconds;
		}
		
		
		return strMinuts + ":" + strSeconds;
		
	}
	private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	};
}
