package com.example.groupname;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class HaveNormalActivity extends Activity {
	
	public static long virusTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_have_normal);
		
		//remove statusbar
		View decorView = getWindow().getDecorView();
		// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);
		// Remember that you should never show the action bar if the
		// status bar is hidden, so hide that too if necessary.
		ActionBar actionBar = getActionBar();
		actionBar.hide();

		this.virusTime=virusTime;
		SharedPreferences settings = getSharedPreferences(FirstActivity.prefName, 0);
		virusTime = System.currentTimeMillis() - settings.getLong("virusTime" , virusTime);
		
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
		getMenuInflater().inflate(R.menu.have_not_datt, menu);
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
		
		private TextView mTextField;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_have_normal,
					container, false);
			
			long timecounter = 3155760000L;
			
			mTextField = (TextView) rootView.findViewById(R.id.counter);
			
			new CountDownTimer(timecounter, 1000) {

			     public void onTick(long millisUntilFinished) {
			    	 
			    	 if((Object) millisUntilFinished == null){
			    		 			    		 
			    	 } else {
			    	 long timecounter = 3155760000L;
			    	 long timegone =  timecounter - millisUntilFinished;
			         String _millis=String.valueOf( virusTime/1000 + timegone/1000);
			         mTextField.setText("Seconds uneffected: " + _millis);
			    	 }
			     }

			     public void onFinish() {
			         mTextField.setText("done!");
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

}
