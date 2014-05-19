package com.example.groupname;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


public class FirstActivity extends Activity {

	public final static String prefName = "dattenPref";

	private boolean gameStarted;
	private boolean gameNormal;
	private boolean gameVirus;
	private boolean hasAntidote;
	private long virusTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent;

		SharedPreferences settings = getSharedPreferences(
				FirstActivity.prefName, 0);
		virusTime = 0;
		gameStarted = settings.getBoolean("gameStarted", false);

		gameNormal = settings.getBoolean("gameNormal", false);
		gameVirus = settings.getBoolean("gameVirus", false);
		hasAntidote = settings.getBoolean("hasAntidote", false);
		virusTime = settings.getLong("virusTime" , virusTime);
		long time= System.currentTimeMillis();

		if (!gameStarted) {
			intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		} else if (hasAntidote) {
			intent = new Intent(this, HaveAntidoteActivity.class);
			startActivity(intent);
			finish();
		} else if (gameNormal) {
			intent = new Intent(this, HaveNormalActivity.class);
			startActivity(intent);
			finish();
		} else if (gameVirus) {
			if(virusTime >= time){
				//you are dead
				gameStarted = false; 
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean("gameStarted", gameStarted);
			}else{
				intent = new Intent(this, HaveVirusActivity.class);
				startActivity(intent);
				finish();
			}
		}

	}

}
