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

	private SensorManager mSensorManager;
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity

	private final SensorEventListener mSensorListener = new SensorEventListener() {

		public void onSensorChanged(SensorEvent se) {
			float x = se.values[0];
			float y = se.values[1];
			float z = se.values[2];
			mAccelLast = mAccelCurrent;
			mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
			float delta = mAccelCurrent - mAccelLast;
			mAccel = mAccel * 0.9f + delta; // perform low-cut filter
			
			
			 if (mAccel > 13) { Toast toast = Toast.makeText(getApplicationContext(), "Device has shaken.", Toast.LENGTH_LONG); toast.show(); }		
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
			
			
			
			
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
		adapter.setNdefPushMessage(null, this, this);
		
	    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	    mAccel = 0.00f;
	    mAccelCurrent = SensorManager.GRAVITY_EARTH;
	    mAccelLast = SensorManager.GRAVITY_EARTH;

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
			m.setDataSource(descriptor.getFileDescriptor(),
					descriptor.getStartOffset(), descriptor.getLength());
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

			// Generera citat

		}
	}

	public void BeginGame(View view) {
		Intent intent = new Intent(this, BeginActivity.class);
		// EditText editText = (EditText) findViewById(R.id.edit_message);
		// String message = editText.getText().toString();
		// intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		mSensorManager.unregisterListener(mSensorListener);
		super.onPause();
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
			Animation fadeIn2 = AnimationUtils.loadAnimation(getActivity(),
					R.animator.animation2);
			Animation fadeIn3 = AnimationUtils.loadAnimation(getActivity(),
					R.animator.animation3);
			Animation fadeIn4 = AnimationUtils.loadAnimation(getActivity(),
					R.animator.animation4);

			// TranslateAnimation tAnim1 = new TranslateAnimation(-800, 0, 0,
			// 0);
			// tAnim1.setInterpolator(new BounceInterpolator());
			// tAnim1.setDuration(1800);
			// tAnim1.setStartOffset(250);
			// TranslateAnimation tAnim2 = new TranslateAnimation(-800, 0, 0,
			// 0);
			// tAnim2.setInterpolator(new BounceInterpolator());
			// tAnim2.setDuration(2200);
			// tAnim2.setStartOffset(500);
			// TranslateAnimation tAnim3 = new TranslateAnimation(-800, 0, 0,
			// 0);
			// tAnim3.setInterpolator(new BounceInterpolator());
			// tAnim3.setDuration(1500);
			// tAnim3.setStartOffset(1500);

			Button beginButton = (Button) rootView.findViewById(R.id.button1);
			beginButton.startAnimation(fadeIn3);

			Button scoreButton = (Button) rootView.findViewById(R.id.button2);
			scoreButton.startAnimation(fadeIn2);

			Button aboutButton = (Button) rootView.findViewById(R.id.button3);
			aboutButton.startAnimation(fadeIn4);

			// datt_sound();

			return rootView;
		}
	}

}
