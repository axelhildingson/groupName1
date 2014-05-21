package com.example.groupname;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class HaveVirusActivity extends Activity {

	private SensorManager mSensorManager;
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity
	public static long virusTime;
	public static int point;
	public static Typeface tf;

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

			if (mAccel > 13) {
				mSensorManager.unregisterListener(this);
				goToSend();
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

	public void goToSend() {
		Intent intent = new Intent(this, MovementsActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_have_virus);

		// add font
		tf = Typeface.createFromAsset(getAssets(),"fonts/Molot.otf");

		View decorView = getWindow().getDecorView();
		// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);
		// Remember that you should never show the action bar if the
		// status bar is hidden, so hide that too if necessary.
		ActionBar actionBar = getActionBar();
		actionBar.hide();


		// virus timer
		this.virusTime = virusTime;
		SharedPreferences settings = getSharedPreferences(
				FirstActivity.prefName, 0);
		virusTime = settings.getLong("virusTime", virusTime)
				- System.currentTimeMillis();

		// point counter

		int point = 0;
		this.point = point;
		point = settings.getInt("point", point);

		// movement shit

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

		NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
		adapter.setNdefPushMessage(null, this, this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.challange_have_datt, menu);
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


		private TextView mTextField1;
		private TextView mTextField2;
		private TextView mTextField3;
		private TextView mTextField4;
		private TextView mTextField5;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(
					R.layout.fragment_have_virus, container, false);

			// Add points 
			String imgName = "tally_" + point;
			ImageView img= (ImageView) rootView.findViewById(R.id.imageView3);
			int imageresource = getResources().getIdentifier("@drawable/" + imgName, "drawable", getActivity().getPackageName());        
			img.setImageResource(imageresource);

			//time counter 
			mTextField1 = (TextView) rootView.findViewById(R.id.timer1);
			mTextField2 = (TextView) rootView.findViewById(R.id.timer2);
			mTextField3 = (TextView) rootView.findViewById(R.id.timer3);
			mTextField4 = (TextView) rootView.findViewById(R.id.timer4);
			mTextField5 = (TextView) rootView.findViewById(R.id.timer5);


			new CountDownTimer(virusTime, 1000) {

				public void onTick(long millisUntilFinished) {


					if((Object) millisUntilFinished == null){

					} else {
						long seconds, minutes, hours, days;
						
						days = millisUntilFinished / (1000*60*60*24);
						hours = (millisUntilFinished - 1000*60*60*24*days) / (1000*60*60);
						minutes = (millisUntilFinished - 1000*60*60*24*days - 1000*60*60*hours) / (1000*60);
						seconds = (millisUntilFinished - 1000*60*60*24*days - 1000*60*60*hours - 1000*60*minutes) / 1000;
						
						mTextField1.setText("Time left until you're dead:");
						mTextField2.setText(seconds + "\t" + "second(s)");
						mTextField3.setText(minutes + "\t" + "minute(s)");
						mTextField4.setText(hours + "\t" + "hour(s)");
						mTextField5.setText(days + "\t" + "day(s)");
						
						mTextField1.setTypeface(tf);
						mTextField2.setTypeface(tf);
						mTextField3.setTypeface(tf);
						mTextField4.setTypeface(tf);
						mTextField5.setTypeface(tf);

					}
				}

				public void onFinish() {

					mTextField1.setText("done!");
				}
			}.start();

			return rootView;
		}
	}

	public void abortGame(View view) {
		boolean gameStarted = false;
		boolean gameVirus = false;
		boolean gameNormal = false;
		boolean hasAntidote = false;

		SharedPreferences settings = getSharedPreferences(
				FirstActivity.prefName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("gameStarted", gameStarted);
		editor.putBoolean("gameVirus", gameVirus);
		editor.putBoolean("gameNormal", gameNormal);
		editor.putBoolean("hasAntidote", hasAntidote);
		editor.commit();

		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		mSensorManager.unregisterListener(mSensorListener);
		super.onPause();
	}
}
