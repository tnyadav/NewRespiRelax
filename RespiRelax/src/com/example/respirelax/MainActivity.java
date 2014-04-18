package com.example.respirelax;

import android.os.Bundle;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	Button b1,b2;
	ImageView im;
    TranslateAnim t1,t2;
    int duration=3000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		im=(ImageView)findViewById(R.id.im);
		t1 = new TranslateAnim(Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
				0.0f, Animation.RELATIVE_TO_PARENT, 1.0f);
		t1.setDuration(duration);
		
		t2=new TranslateAnim(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT,1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		
		t2.setDuration(duration);
		t2.setStartOffset(duration);
		//t2.setRepeatCount(Animation.INFINITE);
		final AnimationSet animationSet=new AnimationSet(true);
		animationSet.addAnimation(t1);
		animationSet.addAnimation(t2);
		animationSet.setRepeatMode(Animation.RESTART);
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
		b1.setText("Start");
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				String text=b1.getText().toString();
				if (text.equals("Start")) {
					im.startAnimation(animationSet);
					b1.setText("Pouse");
				}
				if (text.equals("Pouse")) {
					t1.pause();
					t2.pause();
					b1.setText("Resume");
					
				}
				if (text.equals("Resume")) {
					t1.resume();
					t2.resume();
					b1.setText("Pouse");
				}
				
				
			}
		});
	
	   /* t2.setAnimationListener(new AnimationListener() {
			
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
				// TODO Auto-generated method stub
				
			}
		});
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});*/
	}

	

}
