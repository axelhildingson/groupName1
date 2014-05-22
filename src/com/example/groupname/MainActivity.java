package com.example.groupname;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.nfc.NfcAdapter;
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
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	
	ThemeMusicHandler m;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//remove statusbar
				View decorView = getWindow().getDecorView();
				// Hide the status bar.
				int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
				decorView.setSystemUiVisibility(uiOptions);
				// Remember that you should never show the action bar if the
				// status bar is hidden, so hide that too if necessary.
				android.app.ActionBar actionBar = getActionBar();
				actionBar.hide();
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
		adapter.setNdefPushMessage(null, this, this);


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
	
			m = new ThemeMusicHandler(this);
			m.load(true);
			m.play(6000);
			
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if (hasFocus) {

			// Generera citat

		}
	}

	public void BeginGame(View view) {
		m.pause(1);
		MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.iconclicked);
		mediaPlayer.start();
		
		Intent intent = new Intent(this, BeginActivity.class);
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

			ImageView helloText = (ImageView) rootView
					.findViewById(R.id.imageView1);
			Animation fadeIn = AnimationUtils.loadAnimation(getActivity(),
					R.animator.animation1);
			helloText.startAnimation(fadeIn);
			Animation fadeIn3 = AnimationUtils.loadAnimation(getActivity(),
					R.animator.animation3);


			Button beginButton = (Button) rootView.findViewById(R.id.button1);
			beginButton.startAnimation(fadeIn3);

			 MainActivity activity = (MainActivity) getActivity();			 
			 activity.datt_sound();

			return rootView;
		}
		
		
	}
	
	
	@Override
	public void onPause(){
		m.pause(6000);
		super.onPause();
	}
	
	@Override
	public void onResume(){
		m.play(6000);
		super.onResume();
	}
	
    @Override
    public void onStop() {
		m.pause(6000);
        super.onStop();

    }

   @Override
    public void onDestroy() {
		m.pause(6000);
        super.onDestroy();

    }
}
