package com.example.groupname;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.os.Build;

public class BeginActivity extends ActionBarActivity {
	public boolean msgDATT = true;
	public String gameType = "normal";

	// Hej hälsar Mean-Ergodic
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_begin);

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
		getMenuInflater().inflate(R.menu.begin, menu);
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
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_begin,
					container, false);
			return rootView;
		}
	}

	public void startGameNormal(View view) {
		// USER HAS DATT
			boolean gameStarted = true;
			boolean gameNormal = true;
			boolean gameVirus = false;
			boolean hasAntidote = false;
			long virusTime= System.currentTimeMillis();

			SharedPreferences settings = getSharedPreferences(
					FirstActivity.prefName, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean("gameStarted", gameStarted);
			editor.putBoolean("gameNormal", gameNormal);
			editor.putBoolean("gameVirus", gameVirus);
			editor.putBoolean("hasAntidote", hasAntidote);
			editor.putLong("virusTime" , virusTime);
			editor.commit();

			Intent intent = new Intent(this, HaveNormalActivity.class);
			startActivity(intent);
		
	}

	public void startGameVirus(View view) {
		// USER HAS NOT THE DATT

		boolean gameStarted = true;
		boolean gameNormal = false;
		boolean gameVirus = true;
		boolean hasAntidote = false;
		int point = 0;

		// get the time and adds the time for 48 h 
		long virusTime= System.currentTimeMillis();
		virusTime = virusTime + 172800000;
		
		SharedPreferences settings = getSharedPreferences(
				FirstActivity.prefName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("gameStarted", gameStarted);
		editor.putBoolean("gameNormal", gameNormal);
		editor.putBoolean("gameVirus", gameVirus);
		editor.putBoolean("hasAntidote", hasAntidote);
		editor.putLong("virusTime" , virusTime);
		editor.putInt("point" , point);
		editor.commit();

		Intent intent = new Intent(this, HaveVirusActivity.class);
		startActivity(intent);
	}
	
	public void startGameAntidote(View view) {
		// USER HAS NOT THE DATT

		boolean gameStarted = true;
		boolean gameNormal = false;
		boolean gameVirus = false;
		boolean hasAntidote = true;

		SharedPreferences settings = getSharedPreferences(
				FirstActivity.prefName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("gameStarted", gameStarted);
		editor.putBoolean("gameNormal", gameNormal);
		editor.putBoolean("gameVirus", gameVirus);
		editor.putBoolean("hasAntidote", hasAntidote);
		editor.commit();

		Intent intent = new Intent(this, HaveAntidoteActivity.class);
		startActivity(intent);
	}
}
