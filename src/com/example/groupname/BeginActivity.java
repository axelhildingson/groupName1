package com.example.groupname;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
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
	
	//Hej hälsar Mean-Ergodic
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_begin);

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
	
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.Challange:
	            if (checked)
	                gameType = "challange";
	            break;
	        case R.id.Normal:
	            if (checked)
	                gameType = "normal";
	            break;
	    }
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
			View rootView = inflater.inflate(R.layout.fragment_begin,
					container, false);
			return rootView;
		}
	}
	
	public void startGameWithDatt(View view) {
		// USER HAS DATT
		Intent intent = new Intent(this, PlayerlistActivity.class);
		//EditText editText = (EditText) findViewById(R.id.edit_message);
		//String message = editText.getText().toString();
		intent.putExtra("msgDATT", msgDATT);
		startActivity(intent);
	}
	public void startGameWithoutDatt(View view) {
		//USER HAS NOT THE DATT
		Intent intent = new Intent(this, PlayerlistActivity.class);
		msgDATT = false;
		intent.putExtra("gameType", gameType);
		intent.putExtra("msgDATT", msgDATT);
		startActivity(intent);
	}
}
