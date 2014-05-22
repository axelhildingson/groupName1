package com.example.groupname;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
	public static Typeface tf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_have_normal);
		
		// add font
		tf = Typeface.createFromAsset(getAssets(),"fonts/Molot.otf");
		
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
		
//		private TextView mTextField;
		
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
			View rootView = inflater.inflate(R.layout.fragment_have_normal,
					container, false);
			
			long timecounter = 3155760000L;
			
			// mTextField = (TextView) rootView.findViewById(R.id.counter);
	       // mTextField.setTypeface(tf);
	        
	        mTextField1 = (TextView) rootView.findViewById(R.id.timer1);
			mTextField2 = (TextView) rootView.findViewById(R.id.timer2);
			mTextField3 = (TextView) rootView.findViewById(R.id.timer3);
			mTextField4 = (TextView) rootView.findViewById(R.id.timer4);
			mTextField5 = (TextView) rootView.findViewById(R.id.timer5);
			
			new CountDownTimer(timecounter, 1000) {

			     public void onTick(long millisUntilFinished) {
			    	 
			    	 if((Object) millisUntilFinished == null){
			    		 			    		 
			    	 } else {
			    	 long timecounter = 3155760000L;
			    	 long timegone =  timecounter - millisUntilFinished;
			        // String _millis=String.valueOf( virusTime/1000 + timegone/1000);
			        // mTextField.setText("Seconds uneffected: " + _millis);
			         long timetot = virusTime + timegone;
			         long seconds, minutes, hours, days;
			         days = timetot / (1000*60*60*24);
					 hours = (timetot - 1000*60*60*24*days) / (1000*60*60);
					 minutes = (timetot - 1000*60*60*24*days - 1000*60*60*hours) / (1000*60);
					 seconds = (timetot - 1000*60*60*24*days - 1000*60*60*hours - 1000*60*minutes) / 1000;
					 
					 mTextField1.setText("You have survived the infection for:");
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
