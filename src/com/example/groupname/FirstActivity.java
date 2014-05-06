package com.example.groupname;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class FirstActivity extends Activity {
	
	public final static String prefName = "dattenPref";
	
	
	private boolean gameStarted;
	private boolean gameModeNormal;
	private boolean gameModeChallenge;
	private boolean hasDatt;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent;
		
	    SharedPreferences settings = getSharedPreferences(FirstActivity.prefName, 0);
	    
	    gameStarted = settings.getBoolean("gameStarted", false);
	    gameModeNormal = settings.getBoolean("gameModeNormal", false);    
	    gameModeChallenge = settings.getBoolean("gameModeChallenge", false);
	    hasDatt = settings.getBoolean("hasDatt", false);
	    
	    if(!gameStarted) {
		    intent = new Intent(this, MainActivity.class);
		    startActivity(intent);
		    finish();
	    } else if(gameModeNormal && hasDatt) {
	    	intent = new Intent(this, NormalHaveDattActivity.class);
		    startActivity(intent);
		    finish();
	    } else if(gameModeNormal && !hasDatt) {
	    	intent = new Intent(this, NormalHaveNotDattActivity.class);
		    startActivity(intent);
		    finish();
	    } else if(gameModeChallenge && hasDatt) {
	    	intent = new Intent(this, NormalHaveDattActivity.class);
		    startActivity(intent);
		    finish();
	    } else if(gameModeChallenge && !hasDatt) {
	    	intent = new Intent(this, NormalHaveNotDattActivity.class);
		    startActivity(intent);
		    finish();
	    }
	    
		//setContentView(R.layout.activity_first);

//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//
//		// Inflate the menu; this adds items to the action bar if it is present.
////		getMenuInflater().inflate(R.menu.first, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}



}
