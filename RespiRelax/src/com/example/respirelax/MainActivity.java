package com.example.respirelax;

import java.util.Timer;
import java.util.TimerTask;

import org.w3c.dom.Text;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
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
	Button b1,b2;
	ImageView im;
	TextView tv;
	LinearLayout layout;
    TranslateAnim t1,t2;
	 AnimationSet animationSet;
    int duration=5000;
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
		timer=new Timer();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		counter=0;
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
				
				
				t1.setDuration(duration);
				t2.setDuration(duration);
				t2.setStartOffset(duration);
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
						if (text.equals("Start")) {
							im.startAnimation(animationSet);
							timer.schedule(new MyTimerTask(), 000,1000);
							b1.setText("Pouse");
						}
						
						
						if (text.equals("Pouse")) {
							t1.pause();
							t2.pause();
							end=true;
							b1.setText("Resume");
							
							
						}
						if (text.equals("Resume")) {
							t1.resume();
							t2.resume();
							end=false;
							b1.setText("Pouse");
						}
					    
					
								
							
						

					}
					
				});
				
			}
		}, 1000);
		

	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		counter=0;
		timer.cancel();
		animationSet.cancel();
		overridePendingTransition(0, 0);
		
	}
	class MyTimerTask extends TimerTask {

		  @Override
		  public void run() {
	
		   runOnUiThread(new Runnable(){

		    @Override
		    public void run() {
		    	if (!end) {
		    		 tv.setText(""+counter);
		 		    counter++;
				}
		   
		    }});
		  }
		  
		 }
}
