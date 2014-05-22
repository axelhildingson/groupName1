package com.example.groupname;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Build;

public class MovementsActivity extends Activity {

	private SensorManager mSensorManager;
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity
	private int nbrOfShakes = 0;
	private static int requiredAmountOfShakes = 7;
	private MediaPlayer mediaPlayer1;

	private final SensorEventListener mSensorListener = new SensorEventListener() {

		public void onSensorChanged(SensorEvent se) {

			// Case1
			// case2
			// case3

			float x = se.values[0];
			float y = se.values[1];
			float z = se.values[2];
			mAccelLast = mAccelCurrent;
			mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
			float delta = mAccelCurrent - mAccelLast;
			mAccel = mAccel * 0.9f + delta; // perform low-cut filter

			if (mAccel > 9) {
				nbrOfShakes = nbrOfShakes + 1;
				mediaPlayer1.start();

				playBubbleSound();

				if (nbrOfShakes == requiredAmountOfShakes) {
					nbrOfShakes = 0;
					shakeSuccess();
				}
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

	private void shakeSuccess() {
		mSensorManager.unregisterListener(mSensorListener);
		Intent intent = new Intent(this, SendDattActivity.class);
		startActivity(intent);
	}

	private void playBubbleSound() {

		mediaPlayer1.stop();

		mediaPlayer1 = MediaPlayer.create(this, R.raw.testtubeshake);
		mediaPlayer1.start();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movements);

		mediaPlayer1 = new MediaPlayer();

		// remove statusbar
		View decorView = getWindow().getDecorView();
		// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);
		// Remember that you should never show the action bar if the
		// status bar is hidden, so hide that too if necessary.
		android.app.ActionBar actionBar = getActionBar();
		actionBar.hide();

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		mAccel = 0.00f;
		mAccelCurrent = SensorManager.GRAVITY_EARTH;
		mAccelLast = SensorManager.GRAVITY_EARTH;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movements, menu);

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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private boolean hasAntidote;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(
					R.layout.fragment_movements, container, false);

			MovementsActivity activity = (MovementsActivity) getActivity();

			SharedPreferences settings = activity.getSharedPreferences(
					FirstActivity.prefName, 0);

			hasAntidote = settings.getBoolean("hasAntidote", false);

			ImageView myImageView = (ImageView) rootView.findViewById(R.id.imageView2);
			Animation myFadeInAnimation = AnimationUtils.loadAnimation(
					getActivity(), R.animator.tween);
			myImageView.startAnimation(myFadeInAnimation);

			return rootView;
		}
	}

}
