package com.example.respirelax;

import java.util.Timer;
import java.util.TimerTask;

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
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
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
	LinearLayout layout;
    TranslateAnim t1,t2;
	AnimationSet animationSet;
    int duration=6;
    int frequency=5;
    boolean end=false;
    int counter;
    int threashholeatime;
    Timer timer;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.savedInstanceState=savedInstanceState;
		setContentView(R.layout.activity_main);
	    tv=(TextView)findViewById(R.id.textView1);
		im=(ImageView)findViewById(R.id.im);
		layout=(LinearLayout)findViewById(R.id.l1);
		
		try {
			duration=getIntent().getIntExtra(Util.TIME, 6);
			frequency=getIntent().getIntExtra(Util.FREQUENCY, 5);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		Log.e(TAG, "duration : "+duration+" frequency : "+frequency);
		threashholeatime=duration*60;
		duration=duration*MINUTE;
		frequency=MINUTE/(frequency*2);
		Log.e(TAG, "duration : "+duration+" frequency : "+frequency);
		timer=new Timer();
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				//int y=(int) layout.getTop();
				int imHeight=im.getHeight();
				int height=layout.getHeight()-50;
			    
				int position=height/2;
				
				
				t1 = new TranslateAnim(
						0,
						0,
						-position, 
						position);
				
				 t2=new TranslateAnim(
						0,
						0,
						position,
						-position);
				
				/*t1 = new TranslateAnim(
						Animation.RELATIVE_TO_PARENT, 0.0f,
						Animation.RELATIVE_TO_PARENT, 0.0f,
						Animation.RELATIVE_TO_PARENT,0.0f, 
						Animation.RELATIVE_TO_PARENT, 1.0f);
				
				 t2=new TranslateAnim(
						Animation.RELATIVE_TO_PARENT, 0.0f,
						Animation.RELATIVE_TO_PARENT, 0.0f,
						Animation.RELATIVE_TO_PARENT,1.0f,
						Animation.RELATIVE_TO_PARENT, 0.0f);*/
				
				
				t1.setDuration(frequency);
				t2.setDuration(frequency);
				t2.setStartOffset(frequency);
				//t2.setRepeatCount(Animation.INFINITE);
			    animationSet=new AnimationSet(true);
				animationSet.addAnimation(t1);
				animationSet.addAnimation(t2);
				animationSet.setRepeatMode(Animation.REVERSE);
				//animationSet.setRepeatCount(5);
				animationSet.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationRepeat(Animation arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation arg0) {
						
						im.startAnimation(animationSet);
						
					}
				});
				
				b1=(Button)findViewById(R.id.b1);
				
				b1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						
						
						String text=b1.getText().toString();
						if (text.equals(START)) {
							counter=0;
							im.startAnimation(animationSet);
							timer.schedule(new MyTimerTask(), 000,1000);
							b1.setText(PAUSE);
						}
						
						
						if (text.equals(PAUSE)) {
							t1.pause();
							t2.pause();
							end=true;
							b1.setText(RESUME);
							
							
						}
						if (text.equals(RESUME)) {
							t1.resume();
							t2.resume();
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
			t1.resume();
			t2.resume();
			end=false;
			b1.setText(PAUSE);
		}
		
		
		Button btnSetting=(Button)findViewById(R.id.btnSetting);
		btnSetting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!b1.getText().toString().equals(START)) {
					
				t1.pause();
				t2.pause();
				end=true;
				b1.setText(RESUME);
				}
				startActivity(new Intent(getApplicationContext(), SettingActivity.class));
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
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

		    @Override
		    public void run() {
		    	if (!end) {
		    		 
		 		    counter++;
		 		   tv.setText(getCounterText(counter));
		 		   Log.e("tick", counter+":"+threashholeatime);
		 		    if (counter==threashholeatime) {
						end=true;
						t1.pause();
						t2.pause();
						b1.setText(START);
					}
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
