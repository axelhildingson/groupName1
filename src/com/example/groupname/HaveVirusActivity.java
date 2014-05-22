package com.example.groupname;

import java.nio.charset.Charset;

import com.example.groupname.HaveAntidoteActivity.HelpFragment;

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
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.view.Gravity;
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

public class HaveVirusActivity extends Activity implements
		CreateNdefMessageCallback, OnNdefPushCompleteCallback {

	private NfcAdapter mNfcAdapter;
	private static final int MESSAGE_SENT = 1;
	public static long virusTime;
	private boolean gameStarted;
	private boolean gameVirus;
	private boolean hasAntidote;
	public int point;
	public static Typeface tf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_have_virus);

		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

		// add font
		tf = Typeface.createFromAsset(getAssets(), "fonts/Molot.otf");

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
		point = settings.getInt("point", point);

		// movement shit
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		// Register callback to set NDEF message
		mNfcAdapter.setNdefPushMessageCallback(this, this);
		// Register callback to listen for message-sent success
		mNfcAdapter.setOnNdefPushCompleteCallback(this, this);

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

			View rootView = inflater.inflate(R.layout.fragment_have_virus,
					container, false);

			// Add points
			HaveVirusActivity activity = (HaveVirusActivity) getActivity();
			SharedPreferences settings = activity.getSharedPreferences(
					FirstActivity.prefName, 0);
			int point = settings.getInt("point", 0);
			String imgName = "tally_" + String.valueOf(point);
			ImageView img = (ImageView) rootView.findViewById(R.id.imageView2);
			int imageresource = getResources().getIdentifier(
					"@drawable/" + imgName, "drawable",
					getActivity().getPackageName());
			img.setImageResource(imageresource);

			// time counter
			mTextField1 = (TextView) rootView.findViewById(R.id.timer1);
			mTextField2 = (TextView) rootView.findViewById(R.id.timer2);
			mTextField3 = (TextView) rootView.findViewById(R.id.timer3);
			mTextField4 = (TextView) rootView.findViewById(R.id.timer4);
			mTextField5 = (TextView) rootView.findViewById(R.id.timer5);

			new CountDownTimer(virusTime, 1000) {

				public void onTick(long millisUntilFinished) {

					if ((Object) millisUntilFinished == null) {

					} else {
						long seconds, minutes, hours, days;

						days = millisUntilFinished / (1000 * 60 * 60 * 24);
						hours = (millisUntilFinished - 1000 * 60 * 60 * 24
								* days)
								/ (1000 * 60 * 60);
						minutes = (millisUntilFinished - 1000 * 60 * 60 * 24
								* days - 1000 * 60 * 60 * hours)
								/ (1000 * 60);
						seconds = (millisUntilFinished - 1000 * 60 * 60 * 24
								* days - 1000 * 60 * 60 * hours - 1000 * 60 * minutes) / 1000;

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
						
						mTextField1.setTextSize(20);
						mTextField2.setTextSize(17);
						mTextField3.setTextSize(17);
						mTextField4.setTextSize(17);
						mTextField5.setTextSize(17);
						
						mTextField1.setGravity(Gravity.CENTER_HORIZONTAL);
						mTextField2.setGravity(Gravity.CENTER_HORIZONTAL);
						mTextField3.setGravity(Gravity.CENTER_HORIZONTAL);
						mTextField4.setGravity(Gravity.CENTER_HORIZONTAL);
						mTextField5.setGravity(Gravity.CENTER_HORIZONTAL);

					}
				}

				public void onFinish() {

					mTextField1.setText("done!");
					mTextField2.setText("done!");
					mTextField3.setText("done!");
					mTextField4.setText("done!");
					mTextField5.setText("done!");
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

	private NdefRecord createMimeRecord(String mimeType, byte[] payload) {
		byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
		NdefRecord mimeRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
				mimeBytes, new byte[0], payload);
		return mimeRecord;
	}

	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {

		Time time = new Time();
		time.setToNow();

		SharedPreferences settings = getSharedPreferences(
				FirstActivity.prefName, 0);

		gameStarted = settings.getBoolean("gameStarted", false);
		gameVirus = settings.getBoolean("gameVirus", false);
		hasAntidote = settings.getBoolean("hasAntidote", false);

		String msgContent = "0";

		if (!gameStarted) {
			Toast.makeText(getApplicationContext(),
					"Game not started, something is wrong", Toast.LENGTH_LONG)
					.show();
			return null;
		}

		if (gameVirus) {
			msgContent = "gameVirus";
		} else {
			Toast.makeText(getApplicationContext(),
					"You don't have the virus, something is wrong",
					Toast.LENGTH_LONG).show();
			return null;

		}

		NdefMessage msg = new NdefMessage(
				new NdefRecord[] { createMimeRecord(
						"application/com.example.groupname.datt",
						msgContent.getBytes())

				});
		return msg;
	}

	@Override
	public void onNdefPushComplete(NfcEvent event) {
		// A handler is needed to send messages to the activity when this
		// callback occurs, because it happens from a binder thread
		mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
	}

	/** This handler receives a message from onNdefPushComplete */
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_SENT:
				goBack();
				break;
			}
		}
	};

	private void goBack() {

		if (gameVirus) {
			// adding two hours to the countdown
			Long virusTime = (long) 0;
			SharedPreferences settings = getSharedPreferences(
					FirstActivity.prefName, 0);
			virusTime = settings.getLong("virusTime", virusTime);
			virusTime = virusTime + 21600000;

			// adding point to user
			int point = settings.getInt("point", 0);
			point = point + 1;

			SharedPreferences.Editor editor = settings.edit();
			editor.putLong("virusTime", virusTime);
			editor.putInt("point", point);
			editor.commit();

			Intent intent = new Intent(this, HaveVirusActivity.class);
			startActivity(intent);
			
		} else {
			Toast.makeText(getApplicationContext(),
					"Something is wrong with goBack() in haveVirus !!",
					Toast.LENGTH_LONG).show();
		}

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
			mTextFieldhelp.setText("To send a virus to another player put your phone close to the target and wait for the sound and then click on the screen to send. The target phone need to be unlocked. Good Luck!");
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

