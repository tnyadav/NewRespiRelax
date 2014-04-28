package com.example.respirelax;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
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
	private static final int SECOND = 60000;
	private static final String TAG = "MainActivity";
	
	Button b1,b2;
	ImageView im;
	TextView tv;
	LinearLayout layout;
    TranslateAnim t1,t2;
	AnimationSet animationSet;
    int duration=SECOND*60*2;
    int frequency=5;
    boolean end=false;
    int counter;
    Timer timer;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
	    tv=(TextView)findViewById(R.id.textView1);
		im=(ImageView)findViewById(R.id.im);
		layout=(LinearLayout)findViewById(R.id.l1);
		
		try {
			duration=getIntent().getIntExtra(Util.TIME, SECOND*60*5);
			frequency=getIntent().getIntExtra(Util.FREQUENCY, 5);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		frequency=(SECOND/frequency)/2;
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
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Button btnSetting=(Button)findViewById(R.id.btnSetting);
		btnSetting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), SettingActivity.class));
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
				finish();
			}
		});

	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	/*	counter=0;
		timer.cancel();
		animationSet.cancel();
		overridePendingTransition(0, 0);*/
		
	}
	class MyTimerTask extends TimerTask {

		  @Override
		  public void run() {
	
		   runOnUiThread(new Runnable(){

		    @Override
		    public void run() {
		    	if (!end) {
		    		 
		 		    counter++;
		 		   tv.setText(""+counter);
		 		    if (counter==duration) {
						end=true;
						t1.pause();
						t2.pause();
						b1.setText(START);
					}
				}
		   
		    }});
		  }
		  
		 }
}
