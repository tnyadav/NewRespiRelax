package com.tn.respirelax;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class GuideActivity extends Activity {

	
	private Button btnBack;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		setContentView(R.layout.guide);
		
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
		 if  (str.equalsIgnoreCase("fr")) {
			webView.loadUrl("file:///android_asset/indexfr.html");
		} else if (str.equalsIgnoreCase("es")) {
			webView.loadUrl("file:///android_asset/indexsp.html");
		} else {
			webView.loadUrl("file:///android_asset/index.html");
		}

	}


}