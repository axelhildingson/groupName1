package com.example.groupname;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;
import android.os.Build;

public class HaveAntidoteActivity extends Activity {

	private SensorManager mSensorManager;
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity
	private int point;
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

			if (mAccel > 12) {
				mSensorManager.unregisterListener(this);
				doMovements();
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

	public void doMovements() {

		Intent intent = new Intent(this, MovementsActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_have_antidote);
		
		//remove statusbar
		View decorView = getWindow().getDecorView();
		// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);
		// Remember that you should never show the action bar if the
		// status bar is hidden, so hide that too if necessary.
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		//fonts
		tf = Typeface.createFromAsset(getAssets(), "fonts/Molot.otf");
		
		//point counter
		int point = 0;
		this.point=point;
		SharedPreferences settings = getSharedPreferences(FirstActivity.prefName, 0);
		point = settings.getInt("point" , point);
		
		
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
		getMenuInflater().inflate(R.menu.have_datt, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_have_antidote,
					container, false);
			
			// Add points
			HaveAntidoteActivity activity = (HaveAntidoteActivity) getActivity();
			SharedPreferences settings = activity.getSharedPreferences(FirstActivity.prefName, 0);
			int point = settings.getInt("point", 0);
			String imgName = "tally_" + String.valueOf(point);
			ImageView img2= (ImageView) rootView.findViewById(R.id.imageView3);
			int imageresource = getResources().getIdentifier("@drawable/" + imgName, "drawable", getActivity().getPackageName());        
			img2.setImageResource(imageresource);
			
			// tube animation
			ImageView img1 = (ImageView) rootView.findViewById(R.id.imageView1);
			img1.setBackgroundResource(R.animator.tubeanimation);
			AnimationDrawable frameAnimation = (AnimationDrawable) img1
					.getBackground();
			frameAnimation.start();

			return rootView;
		}
	}

	public void abortGame(View view) {
		boolean gameStarted = false;
		boolean gameVirus = false;
		boolean gameNormal = false;
		boolean hasAntidote = false;
		long virusTime = 0L;
		int point = 0;

		SharedPreferences settings = getSharedPreferences(
				FirstActivity.prefName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("gameStarted", gameStarted);
		editor.putBoolean("gameVirus", gameVirus);
		editor.putBoolean("gameNormal", gameNormal);
		editor.putBoolean("hasAntidote", hasAntidote);	
		editor.putLong("virusTime", virusTime);
		editor.putInt("point", point);
		
		editor.commit();

		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	public void getHelp(View view) {
		// Create new fragment and transaction
		Fragment newFragment = new HelpFragment();
		android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

		// Replace whatever is in the fragment_container view with this fragment,
		// and add the transaction to the back stack
		transaction.replace(R.id.container, newFragment);
		transaction.addToBackStack(null);

		// Commit the transaction
		transaction.commit();
	}
	
	public static class HelpFragment extends Fragment {

		private TextView mTextFieldhelp;
		
		public HelpFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_help,
					container, false);
			mTextFieldhelp = (TextView) rootView.findViewById(R.id.textView1);
			mTextFieldhelp.setText("To send an antidote to another player first shake your antiodote, put your phone close to the target, wait for the sound and then click on the screen to send. The target phone needs to be unlocked. Good Luck!");
			mTextFieldhelp.setTypeface(tf);
			mTextFieldhelp.setTextSize(20);
			mTextFieldhelp.setEms(12);
			
			// tube animation
			ViewAnimator img1 = (ViewAnimator) rootView.findViewById(R.id.viewAnimator1);
			img1.setBackgroundResource(R.animator.clutchanimation);
			AnimationDrawable frameAnimation = (AnimationDrawable) img1
					.getBackground();
			frameAnimation.start();
			
			return rootView;
		}	
	}

	public void goBack(View view){
		Intent intent = new Intent(this, FirstActivity.class);
		startActivity(intent);
	}
}
