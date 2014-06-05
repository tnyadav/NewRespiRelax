package com.tn.respirelax;

import java.util.Locale;

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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.tn.respirelax.R;

public class GuideActivity extends Activity {

	
	private Button btnBack;
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide);
		context = this;

		btnBack = (Button) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
				overridePendingTransition(R.anim.slide_out_right,
						R.anim.slide_in_left);
			}
		});
		WebView webView = (WebView) findViewById(R.id.text);
		String str = Locale.getDefault().getLanguage();
		
		if (str.equalsIgnoreCase("en")) {
			webView.loadUrl("file:///android_asset/index.html");
		} else if (str.equalsIgnoreCase("fr")) {
			webView.loadUrl("file:///android_asset/indexfr.html");
		} else if (str.equalsIgnoreCase("fr")) {
			webView.loadUrl("file:///android_asset/indexsp.html");
		} else {
			webView.loadUrl("file:///android_asset/index.html");
		}

	}


}