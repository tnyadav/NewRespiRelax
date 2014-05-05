package com.example.respirelax;

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
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SettingActivity extends Activity {

	private final int MAX_TIME = 600;
	private final int MAX_FREQUENCY = 500;
	private final int MIN_PROGRESS_TIME = 3;
	private final int MIN_PROGRESS_FREQUENCY = 4;
	private int seekBarTimeProgressValue;
	private int seekBarFrequencyProgressValue;
	
	private SeekBar seekBarTime, seekBarFrequency;
	private Button btnBack,btnDone;
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		context = this;

	//seekBarTime initialization
		seekBarTime = (SeekBar) findViewById(R.id.seekBarTime);
		seekBarTime.setMax(MAX_TIME);
		seekBarTime.setProgress(MAX_TIME / 2);
		seekBarTimeProgressValue = getFilterdProgress(
				seekBarTime.getProgress(), MIN_PROGRESS_TIME);
		seekBarTime.setThumb(writeOnDrawable(R.drawable.ball, ""
				+ seekBarTimeProgressValue));
		seekBarTime.setOnSeekBarChangeListener(seekBarChangeListenerForTime);
		
	//seekBarFrequency initialization
		seekBarFrequency = (SeekBar) findViewById(R.id.seekBarFrequency);
		seekBarFrequency.setMax(MAX_FREQUENCY);
		seekBarFrequency.setProgress(MAX_FREQUENCY / 2);
		seekBarFrequencyProgressValue = getFilterdProgress(
				seekBarFrequency.getProgress(), MIN_PROGRESS_FREQUENCY);
		seekBarFrequency.setThumb(writeOnDrawable(R.drawable.ball, ""
				+ seekBarFrequencyProgressValue));
		seekBarFrequency
				.setOnSeekBarChangeListener(seekBarChangeListenerForFrequency);


		btnDone = (Button) findViewById(R.id.btnDone);
		btnDone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				intent.putExtra(Util.TIME, 1/*seekBarTimeProgressValue*/);
				intent.putExtra(Util.FREQUENCY, seekBarFrequencyProgressValue);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_out_right,
						R.anim.slide_in_left);
				Intent sendIntent = new Intent();
				sendIntent.setAction(Util.CLOSE_ACTION);
				sendBroadcast(sendIntent);
				finish();

			}
		});
		btnBack = (Button) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
				overridePendingTransition(R.anim.slide_out_right,
						R.anim.slide_in_left);
			}
		});
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
		paint.setColor(Color.BLACK);
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
		return (progress / 100) + minValue;
	}
}