package com.bib.breathrelax;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.bib.breaterelax.util.SharedPreferencesUtil;
import com.bib.breaterelax.util.Util;

public class SettingActivity extends Activity {

	private final int MAX_TIME = 600;
	private final int MAX_FREQUENCY = 300;
	
	private final int MIN_PROGRESS_TIME = 3;
	private final int MIN_PROGRESS_FREQUENCY = 4;
	
	private int seekBarTimeProgressValue;
	private int seekBarFrequencyProgressValue;
	
	private SeekBar seekBarTime, seekBarFrequency;
	private Button btnDone;
	private TextView btnBack;
	private LinearLayout c1,c2,c3;
	private Context context;
	private int ratio=1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		context = this;

		int seekBarTimeProgress = SharedPreferencesUtil.getIntegerPreferences(context, Util.TIME);
		if (seekBarTimeProgress==-1) {
			seekBarTimeProgress=200;
		}
		
		int seekBarFrequencyProgress=SharedPreferencesUtil.getIntegerPreferences(context, Util.FREQUENCY);
		if (seekBarFrequencyProgress==-1) {
			seekBarFrequencyProgress=200;
		}
		ratio=	SharedPreferencesUtil.getIntegerPreferences(context, Util.RATIO_FACTOR);
		if (ratio==-1) {
			ratio=1;
		}
		
	//seekBarTime initialization
		seekBarTime = (SeekBar) findViewById(R.id.seekBarTime);
		seekBarTime.setMax(MAX_TIME);
		seekBarTime.setProgress(seekBarTimeProgress);
		seekBarTimeProgressValue = getFilterdProgress(
				seekBarTime.getProgress(), MIN_PROGRESS_TIME);
		seekBarTime.setThumb(writeOnDrawable(R.drawable.ball, ""
				+ seekBarTimeProgressValue));
		seekBarTime.setOnSeekBarChangeListener(seekBarChangeListenerForTime);
		
	//seekBarFrequency initialization
		seekBarFrequency = (SeekBar) findViewById(R.id.seekBarFrequency);
		seekBarFrequency.setMax(MAX_FREQUENCY);
		seekBarFrequency.setProgress(seekBarFrequencyProgress);
		seekBarFrequencyProgressValue = getFilterdProgress(
				seekBarFrequency.getProgress(), MIN_PROGRESS_FREQUENCY);
		seekBarFrequency.setThumb(writeOnDrawable(R.drawable.ball, ""
				+ seekBarFrequencyProgressValue));
		seekBarFrequency
				.setOnSeekBarChangeListener(seekBarChangeListenerForFrequency);
		c1=(LinearLayout)findViewById(R.id.c1);
		c1.setOnClickListener(boxOnClickListener);
		c2=(LinearLayout)findViewById(R.id.c2);
		c2.setOnClickListener(boxOnClickListener);
		c3=(LinearLayout)findViewById(R.id.c3);
		c3.setOnClickListener(boxOnClickListener);


		btnDone = (Button) findViewById(R.id.btnDone);
		btnDone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				SharedPreferencesUtil.saveIntegerPreferences(context, Util.TIME, seekBarTime.getProgress());
				SharedPreferencesUtil.saveIntegerPreferences(context, Util.FREQUENCY, seekBarFrequency.getProgress());
				SharedPreferencesUtil.saveIntegerPreferences(context, Util.RATIO_FACTOR, ratio);
				
			
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				
				intent.putExtra(Util.TIME, seekBarTimeProgressValue);
				intent.putExtra(Util.FREQUENCY, seekBarFrequencyProgressValue);
				intent.putExtra(Util.RATIO_FACTOR, ratio);
					startActivity(intent);
				overridePendingTransition(R.anim.slide_out_right,
						R.anim.slide_in_left);
				Intent sendIntent = new Intent();
				sendIntent.setAction(Util.CLOSE_ACTION);
				sendBroadcast(sendIntent);
				finish();

			}
		});
		Button guide=(Button)findViewById(R.id.guide);
		guide.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(new Intent(getApplicationContext(), GuideActivity.class));
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
			//	finish();
			}
		});
		btnBack = (TextView) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
				overridePendingTransition(R.anim.slide_out_right,
						R.anim.slide_in_left);
			}
		});
		
		switch (ratio) {
		case 0:
			selectRelexing();
			break;
		case 1:
			selectBalancing();
			break;
		case 2:
			selectStimulating();
			break;
		default:
			break;
		}
		
		
	}

	private OnSeekBarChangeListener seekBarChangeListenerForTime = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			seekBarTimeProgressValue = getFilterdProgress(progress,
					MIN_PROGRESS_TIME);
			seekBar.setThumb(writeOnDrawable(R.drawable.ball, ""
					+ seekBarTimeProgressValue));

		}
	};
	private OnSeekBarChangeListener seekBarChangeListenerForFrequency = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			seekBarFrequencyProgressValue =getFilterdProgress(progress, MIN_PROGRESS_FREQUENCY);
			seekBar.setThumb(writeOnDrawable(R.drawable.ball, ""
					+ seekBarFrequencyProgressValue));

		}
	};

	public BitmapDrawable writeOnDrawable(int drawableId, String text) {

		Bitmap bm = BitmapFactory.decodeResource(getResources(), drawableId)
				.copy(Bitmap.Config.ARGB_8888, true);

		Paint paint = new Paint();
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(50);
		paint.setTextAlign(Align.CENTER);

		Canvas canvas = new Canvas(bm);
		int xPos = (canvas.getWidth() / 2);
		int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint
				.ascent()) / 2));
		canvas.drawText(text, xPos, yPos, paint);

		return new BitmapDrawable(getResources(), bm);
	}

	private int getFilterdProgress(int progress, int minValue) {
		float f=((float)progress) / (float)100;
		int value=(int) f;
		String s=String.valueOf(f);
		int s1=s.indexOf('.'); 
		int i = Integer.parseInt(s.substring(s1 + 1));  
		if (i>50) {
			value=value+1;
		}
		return (int) (value + minValue);
	}
	OnClickListener boxOnClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			switch (v.getId()) {
			case R.id.c1:
				selectRelexing();
				break;
			case R.id.c2:
				selectBalancing();
			
				break;
			case R.id.c3:
				selectStimulating();
				break;
			default:
				break;
			}
			
		}
	};
	private void selectRelexing() {
		c1.setBackgroundResource(android.R.color.white);
		c2.setBackgroundResource(android.R.color.transparent);
		c3.setBackgroundResource(android.R.color.transparent);
		ratio = 0;
	}

	private void selectBalancing() {
		c2.setBackgroundResource(android.R.color.white);
		c1.setBackgroundResource(android.R.color.transparent);
		c3.setBackgroundResource(android.R.color.transparent);
		ratio = 1;
	}

	private void selectStimulating() {
		c3.setBackgroundResource(android.R.color.white);
		c2.setBackgroundResource(android.R.color.transparent);
		c1.setBackgroundResource(android.R.color.transparent);
		ratio = 2;
	}
}