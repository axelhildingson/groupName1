package com.example.groupname;

import java.nio.charset.Charset;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;

public class SendDattActivity extends ActionBarActivity implements
		CreateNdefMessageCallback, OnNdefPushCompleteCallback {

	public final static String swithOptionVirus = "gameVirus";
	public final static String swithOptionAntidote = "hasAntidote";

	private NfcAdapter mNfcAdapter;
	private static final int MESSAGE_SENT = 1;
	private boolean gameStarted;
	private boolean gameVirus;
	private boolean hasAntidote;
	private MediaPlayer mediaPlayer1;
	private MediaPlayer mediaPlayer2;
	public static Typeface tf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datt);

		// Check for available NFC Adapter
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		tf = Typeface.createFromAsset(getAssets(), "fonts/Molot.otf");

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
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		// Register callback to set NDEF message
		mNfcAdapter.setNdefPushMessageCallback(this, this);
		// Register callback to listen for message-sent success
		mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
		
		mediaPlayer1 = new MediaPlayer();
		mediaPlayer2 = new MediaPlayer();
		


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.datt, menu);
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

			SendDattActivity activity = (SendDattActivity) getActivity();

			SharedPreferences settings = activity.getSharedPreferences(
					FirstActivity.prefName, 0);

			hasAntidote = settings.getBoolean("hasAntidote", false);

			View rootView = null;

			if (hasAntidote) {
				rootView = inflater.inflate(R.layout.fragment_datt, container,
						false);
			} else {

				rootView = inflater.inflate(R.layout.fragment_datt2, container,
						false);

			}

			final ImageView halfAntidote = (ImageView) rootView
					.findViewById(R.id.imageView1);
			final ImageView fullAntidote = (ImageView) rootView
					.findViewById(R.id.imageView2);
			final ImageView popAntidote = (ImageView) rootView
					.findViewById(R.id.imageView3);

			fullAntidote.setVisibility(View.GONE);
			popAntidote.setVisibility(View.GONE);

			int mShortAnimationDuration = 3000;

			fullAntidote.setAlpha(0f);
			fullAntidote.setVisibility(View.VISIBLE);

			fullAntidote.animate().alpha(1f)
					.setDuration(mShortAnimationDuration).setListener(null);

			halfAntidote.animate().alpha(0f)
					.setDuration(mShortAnimationDuration)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							
							
							SendDattActivity activity = (SendDattActivity) getActivity();
							halfAntidote.setVisibility(View.GONE);
							activity.playPoppingSound();

							int mShortAnimationDuration = 2000;

							popAntidote.setAlpha(0f);
							popAntidote.setVisibility(View.VISIBLE);

							popAntidote.animate().alpha(1f)
									.setDuration(mShortAnimationDuration)
									.setListener(null);
							

							


							fullAntidote.animate().alpha(0f)
									.setDuration(mShortAnimationDuration)
									.setListener(new AnimatorListenerAdapter() {
										@Override
										public void onAnimationEnd(
												Animator animation) {
											fullAntidote
													.setVisibility(View.GONE);
											SendDattActivity activity = (SendDattActivity) getActivity();
											activity.playBubblingSound();
											
											TextView mTextFieldhelp = (TextView) activity.findViewById(R.id.textView1);
											mTextFieldhelp.setText("Your antidote is finished! Give it to someone with NFC!");
											mTextFieldhelp.setTypeface(tf);
											mTextFieldhelp.setTextSize(20);
											mTextFieldhelp.setEms(12);


										}
									});

						}
					});

			return rootView;
		}
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
		} else if (hasAntidote) {
			msgContent = "hasAntidote";
		}

		NdefMessage msg = new NdefMessage(
				new NdefRecord[] { createMimeRecord(
						"application/com.example.groupname.datt",
						msgContent.getBytes())
				/**
				 * The Android Application Record (AAR) is commented out. When a
				 * device receives a push with an AAR in it, the application
				 * specified in the AAR is guaranteed to run. The AAR overrides
				 * the tag dispatch system. You can add it back in to guarantee
				 * that this activity starts when receiving a beamed message.
				 * For now, this code uses the tag dispatch system.
				 */
				});
		return msg;
	}

	private NdefRecord createMimeRecord(String mimeType, byte[] payload) {
		byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
		NdefRecord mimeRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
				mimeBytes, new byte[0], payload);
		return mimeRecord;
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


			mediaPlayer1.stop();
			mediaPlayer2.stop();

			SharedPreferences settings = getSharedPreferences(
					FirstActivity.prefName, 0);

			int point = settings.getInt("point", 0);
			point = point + 1;
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt("point", point);
			editor.commit();

			Intent intent = new Intent(this, HaveAntidoteActivity.class);
			startActivity(intent);
		

	}
	
	
	public void playBubblingSound(){
		
		mediaPlayer1 = MediaPlayer
				.create(this,
						R.raw.antidote_bubbling);
		mediaPlayer1.setLooping(true);
		mediaPlayer1.start();
		
	}
	
	
	public void playPoppingSound(){
		
		mediaPlayer2.stop();
		mediaPlayer2 = MediaPlayer
				.create(this,
						R.raw.antidotebottlepop);
		mediaPlayer2.setLooping(false);
		mediaPlayer2.start();
		
	}
	
	
	
	@Override
	public void onPause(){
		mediaPlayer1.stop();
		mediaPlayer2.stop();
		super.onPause();
	}
	
	@Override
	public void onResume(){
		mediaPlayer1.stop();
		mediaPlayer2.stop();
		mediaPlayer1.start();
		super.onResume();
	}
	
    @Override
    public void onStop() {
		mediaPlayer1.stop();
		mediaPlayer2.stop();
        super.onStop();

    }

   @Override
    public void onDestroy() {
		mediaPlayer1.stop();
		mediaPlayer2.stop();
        super.onDestroy();

    }

}
