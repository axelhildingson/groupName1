package com.example.groupname;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class ReceiveDattActivity extends ActionBarActivity {
	TextView mInfoText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receive_datt);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
		adapter.setNdefPushMessage(null, this, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.receive_datt, menu);
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

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_receive_datt,
					container, false);
			return rootView;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		// Check to see that the Activity started due to an Android Beam
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			processIntent(getIntent());
		}
	}

	/**
	 * Parses the NDEF Message from the intent and prints to the TextView
	 */
	void processIntent(Intent intent) {

		Parcelable[] rawMsgs = intent
				.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		// only one message sent during the beam
		NdefMessage msg = (NdefMessage) rawMsgs[0];

		String msgContent = new String(msg.getRecords()[0].getPayload());

		SharedPreferences settings = getSharedPreferences(
				FirstActivity.prefName, 0);
		boolean hasDatt;
		boolean gameStarted;
		boolean gameModeNormal;
		boolean gameModeChallenge;

		switch (msgContent) {
		case "0":
			Toast.makeText(getApplicationContext(),
					"Weird! 0! Something is wrong", Toast.LENGTH_LONG).show();

			break;
		case "normalModeDatt":

			// Hämta och kolla om nuvarande spelinställnningar överenstämmer med
			// NFC-meddelandet

			gameStarted = settings.getBoolean("gameStarted", false);
			gameModeNormal = settings.getBoolean("gameModeNormal", false);
			gameModeChallenge = settings.getBoolean("gameModeChallenge", false);
			hasDatt = settings.getBoolean("hasDatt", false);

			if (!gameStarted) {
				Toast.makeText(getApplicationContext(),
						"No game currently onGoing!", Toast.LENGTH_LONG).show();
				intent = new Intent(this, MainActivity.class);

			} else if (gameModeNormal && hasDatt) {
				Toast.makeText(getApplicationContext(),
						"You already have the DATT?!", Toast.LENGTH_LONG)
						.show();
				intent = new Intent(this, HaveAntidoteActivity.class);

			} else if (gameModeChallenge && hasDatt) {
				Toast.makeText(
						getApplicationContext(),
						"You are playing a different GAME-SETUP from your friend!",
						Toast.LENGTH_LONG).show();
				intent = new Intent(this, HaveVirusActivity.class);
			} else {

				hasDatt = true;

				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean("hasDatt", hasDatt);
				editor.commit();

				intent = new Intent(this, HaveAntidoteActivity.class);
				Toast.makeText(
						getApplicationContext(),
						"You got the DATT in normal mode, YOU SUCK!  (que song)",
						Toast.LENGTH_LONG).show();

			}

			startActivity(intent);

			break;
		case "challengeModeDatt":

			// Hämta och kolla om nuvarande spelinställnningar överenstämmer med
			// NFC-meddelandet

			break;
		case "challengeModeNotDatt":

			// KLATSCH!

			break;
		default:

			Toast.makeText(
					getApplicationContext(),
					"Something is wrong... Received string: "
							+ new String(msg.getRecords()[0].getPayload()),
					Toast.LENGTH_LONG).show();
			break;

		}

	}

}
