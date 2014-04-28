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

	private SeekBar seekBarTime,seekBarFrequency;
	private Button done;
    private Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        context=this;
      seekBarTime=(SeekBar)findViewById(R.id.seekBarTime);
    // seekBarTime.setMax(9);
      seekBarTime.setOnSeekBarChangeListener(seekBarChangeListenerForTime);
      seekBarTime.setProgress(0);
      seekBarTime.setThumb(writeOnDrawable(R.drawable.ball, ""+0));
      seekBarFrequency=(SeekBar)findViewById(R.id.seekBarFrequency);
     //seekBarTime.setMax(7);
      seekBarFrequency.setOnSeekBarChangeListener(seekBarChangeListenerForFrequency);
      seekBarFrequency.setProgress(0);
      seekBarFrequency.setThumb(writeOnDrawable(R.drawable.ball, ""+0));
      done = (Button) findViewById(R.id.btnDone);
      done.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent =new Intent(getApplicationContext(), MainActivity.class);
			intent.putExtra(Util.TIME, 5*60*1000);
			intent.putExtra(Util.FREQUENCY, 4);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_out_right,
					R.anim.slide_out_right);
			finish();
			
			
		}
	});
    }


	private OnSeekBarChangeListener seekBarChangeListenerForTime=new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			seekBar.setThumb(writeOnDrawable(R.drawable.ball, ""+progress));
			//TextView tv=(TextView)findViewById(R.id.textView2);
			//tv.setText(Integer.toString(progress)+"%");
			
		}
	};
private OnSeekBarChangeListener seekBarChangeListenerForFrequency=new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			seekBar.setThumb(writeOnDrawable(R.drawable.ball, ""+progress));
			//TextView tv=(TextView)findViewById(R.id.textView2);
			//tv.setText(Integer.toString(progress)+"%");
			
		}
	};
	public BitmapDrawable writeOnDrawable(int drawableId, String text){

        Bitmap bm = BitmapFactory.decodeResource(getResources(), drawableId).copy(Bitmap.Config.ARGB_8888, true);

        Paint paint = new Paint(); 
        paint.setStyle(Style.FILL);  
        paint.setColor(Color.BLACK); 
        paint.setTextSize(50); 
        paint.setTextAlign(Align.CENTER);

        Canvas canvas = new Canvas(bm);
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) ; 
        canvas.drawText(text, xPos, yPos, paint);

        return new BitmapDrawable(bm);
    }
}