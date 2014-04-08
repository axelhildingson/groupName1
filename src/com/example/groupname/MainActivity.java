package com.example.groupname;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
    
	public void datt_sound() {
    try {
        MediaPlayer m = new MediaPlayer();
        AssetFileDescriptor descriptor = getAssets().openFd("datt.mp3");
        m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
        descriptor.close();

        m.prepare();
        m.setVolume(1f, 1f);
        m.start();
    } catch (Exception e) {
    }
}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if (hasFocus) {

			ImageView helloText = (ImageView) findViewById(R.id.imageView1);
			Animation fadeIn = AnimationUtils.loadAnimation(MainActivity.this,
					R.animator.animation1);
			helloText.startAnimation(fadeIn);
			
		    TranslateAnimation tAnim1 = new TranslateAnimation(-400, 0, 0, 0);
		    tAnim1.setInterpolator(new BounceInterpolator());
		    tAnim1.setDuration(2200);
		    TranslateAnimation tAnim2 = new TranslateAnimation(-400, 0, 0, 0);
		    tAnim2.setInterpolator(new BounceInterpolator());
		    tAnim2.setDuration(2200);
		    tAnim2.setStartOffset(500);
		    TranslateAnimation tAnim3 = new TranslateAnimation(-400, 0, 0, 0);
		    tAnim3.setInterpolator(new BounceInterpolator());
		    tAnim3.setDuration(2200);
		    tAnim3.setStartOffset(1000);
		 
		    Button beginButton = (Button) findViewById(R.id.button1);
		    beginButton.startAnimation(tAnim1);
		   
		    Button scoreButton = (Button) findViewById(R.id.button2);
		    scoreButton.startAnimation(tAnim2);
		  
		    Button aboutButton = (Button) findViewById(R.id.button3);
		    aboutButton.startAnimation(tAnim3);
		    
		    datt_sound();
		}
	}
	
	public void BeginGame(View view) {
		Intent intent = new Intent(this, BeginActivity.class);
	    //EditText editText = (EditText) findViewById(R.id.edit_message);
	    //String message = editText.getText().toString();
	    //intent.putExtra(EXTRA_MESSAGE, message);
	    startActivity(intent);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			return rootView;
		}
	}

}
