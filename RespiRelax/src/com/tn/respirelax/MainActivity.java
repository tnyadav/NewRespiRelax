package com.tn.respirelax;

import java.util.Timer;
import java.util.TimerTask;

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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tn.respirelax.util.ScalingUtilities;
import com.tn.respirelax.util.ScalingUtilities.ScalingLogic;
import com.tn.respirelax.util.Util;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private Bundle savedInstanceState;
	boolean goToUp, goToDown;

	private Button btnStart;
	private FrameLayout l1;
	LinearLayout im;
	private TextView timeCounter;

	int duration = 5, newDuration;
	int frequency = 6;
	boolean end = false;

	int globalCounter, upDownTempCounter;
	float upDisplacementFactor, downDisplacementFactor;
	int baseDisplacementHeight, height, width, completeCycleFactor,
			upCycleTime, downCycleTime;
	private Timer timer;
	private FrameLayout.LayoutParams params;

	private enum Mode {
		START, PAUSE, RESUME
	};

	private Mode mode = Mode.START;
	private Bitmap back, front;
	private int upCycleCounter = 0;
	private int downCycleCounter = 0;
	private int ratio = 1;
	float ratioFactor = 0.5f;

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.savedInstanceState = savedInstanceState;
		setContentView(R.layout.activity_main);
		timeCounter = (TextView) findViewById(R.id.textView1);
		l1 = (FrameLayout) findViewById(R.id.l1);
		im = (LinearLayout) findViewById(R.id.im);

		height = Util.getScreenHeightPixel(this);
		width = Util.getScreenWidthPixel(this);

		back = ScalingUtilities.decodeResource(getResources(),
				R.drawable.metter_back, Util.getScreenWidthPixel(this),
				Util.getScreenHeightPixel(this) - 500, ScalingLogic.FIT);
		back = ScalingUtilities.createScaledBitmap(back, width, height - 200,
				ScalingLogic.FIT);
		front = ScalingUtilities.decodeResource(getResources(),
				R.drawable.metter, Util.getScreenWidthPixel(this),
				Util.getScreenHeightPixel(this) - 500, ScalingLogic.FIT);
		front = ScalingUtilities.createScaledBitmap(front, width, height - 200,
				ScalingLogic.FIT);

		try {
			duration = getIntent().getIntExtra(Util.TIME, 5);
			frequency = getIntent().getIntExtra(Util.FREQUENCY, 6);
			ratio = getIntent().getIntExtra(Util.RATIO_FACTOR, 1);
		} catch (Exception e) {

			e.printStackTrace();
		}
		switch (ratio) {
		case 0:
			ratioFactor = 1.2f;
			break;
		case 1:
			ratioFactor = 1.0f;
			break;
		case 2:
			ratioFactor = .8f;
			break;
		default:
			break;
		}

		newDuration = ((duration * 60) % 3600);

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				front.getWidth(), front.getHeight());
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		lp.addRule(RelativeLayout.CENTER_VERTICAL);
		l1.setLayoutParams(lp);

		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			l1.setBackgroundDrawable(new BitmapDrawable(getResources(), back));
			im.setBackgroundDrawable(new BitmapDrawable(getResources(), front));
		} else {
			l1.setBackground(new BitmapDrawable(getResources(), back));
			im.setBackground(new BitmapDrawable(getResources(), front));
			;
		}

		Bitmap boble = BitmapFactory.decodeResource(getResources(),
				R.drawable.boble);
		boble = ScalingUtilities.createScaledBitmap(boble, front.getWidth(),
				500, ScalingLogic.FIT);

		baseDisplacementHeight = front.getHeight() - boble.getHeight();
		boble.recycle();
		params = new FrameLayout.LayoutParams(front.getWidth(),
				front.getHeight());
		params.topMargin = baseDisplacementHeight;
		im.setLayoutParams(params);
		im.setVisibility(View.VISIBLE);

		Log.e(TAG, "duration : " + duration + " frequency : " + frequency);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				float f = ((float) 60 / (float) (frequency));
				Log.e(TAG, "" + f);
				double roundof = Math.round(f * 100.0) / 100.0;

				completeCycleFactor = (int) (roundof * 100);
				upCycleTime = (int) ((completeCycleFactor / 2) * ratioFactor);
				downCycleTime = completeCycleFactor - upCycleTime;
				duration = duration * 60 * 100;
				upDisplacementFactor = (float) ((float) baseDisplacementHeight / (float) upCycleTime);
				downDisplacementFactor = (float) ((float) baseDisplacementHeight / (float) downCycleTime);

				btnStart = (Button) findViewById(R.id.b1);

				btnStart.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						switch (mode) {
						case START:
							globalCounter = 0;
							timer = new Timer();
							timer.schedule(new MyTimerTask(), 000, 10);
							mode = Mode.PAUSE;
							btnStart.setBackgroundResource(R.drawable.pause);
							break;
						case PAUSE:
							end = true;
							mode = Mode.RESUME;
							btnStart.setBackgroundResource(R.drawable.play);
							break;
						case RESUME:
							end = false;
							mode = Mode.PAUSE;
							btnStart.setBackgroundResource(R.drawable.pause);
							break;
						default:
							break;
						}

					}

				});

			}
		}, 500);
		timeCounter.setText(getCounterText(0));
		IntentFilter filter = new IntentFilter(Util.CLOSE_ACTION);
		registerReceiver(myBroadcastReceiver, filter);
	}

	@Override
	protected void onResume() {

		super.onResume();
		if (savedInstanceState != null && mode == Mode.START) {

			end = false;
			mode = Mode.PAUSE;
			;
		}

		Button btnSetting = (Button) findViewById(R.id.btnSetting);
		btnSetting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mode != Mode.START) {
					end = true;
					mode = Mode.RESUME;
				}
				startActivity(new Intent(getApplicationContext(),
						SettingActivity.class));
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
				// finish();
			}
		});
		Button guide = (Button) findViewById(R.id.guide);
		guide.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mode != Mode.START) {
					end = true;
					mode = Mode.RESUME;
				}
				startActivity(new Intent(getApplicationContext(),
						GuideActivity.class));
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
				// finish();
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
		try {
			unregisterReceiver(myBroadcastReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onDestroy();

	}

	class MyTimerTask extends TimerTask {

		@Override
		public void run() {

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (!end) {

						timeCounter
								.setText(getCounterText(globalCounter / 100));
						if (globalCounter < duration) {

							if (globalCounter <= upCycleTime
									+ (completeCycleFactor * upCycleCounter)
									&& !goToDown) {
								goToUp = true;
								params.topMargin = (baseDisplacementHeight)
										- ((int) ((upDownTempCounter) * upDisplacementFactor));
								im.setLayoutParams(params);
								if (globalCounter == (float) upCycleTime
										+ (completeCycleFactor * upCycleCounter)) {
									upCycleCounter++;
									upDownTempCounter = 0;
									goToUp = false;
									goToDown = true;
								}

							} else if (globalCounter <= completeCycleFactor
									+ (completeCycleFactor * downCycleCounter)
									&& !goToUp) {
								goToDown = true;
								params.topMargin = (int) ((globalCounter - ((float) upCycleTime + (completeCycleFactor * downCycleCounter))) * downDisplacementFactor);
								im.setLayoutParams(params);

								if (globalCounter == completeCycleFactor
										+ (completeCycleFactor * downCycleCounter)) {
									upDownTempCounter = 0;
									downCycleCounter++;
									goToDown = false;
									goToUp = true;
								}

							}

						}

						if (globalCounter == duration) {
							Log.e(TAG, "counter : " + (globalCounter / 100));
							end = true;
							timer.cancel();
							params.topMargin = baseDisplacementHeight;
							im.setLayoutParams(params);
							mode = Mode.START;
							globalCounter = 0;
							upDownTempCounter = 0;
						}
						globalCounter++;
						upDownTempCounter++;
					}

				}
			});
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
