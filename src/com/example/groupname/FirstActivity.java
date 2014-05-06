package com.example.groupname;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


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

		SharedPreferences settings = getSharedPreferences(
				FirstActivity.prefName, 0);

		gameStarted = settings.getBoolean("gameStarted", false);
		gameModeNormal = settings.getBoolean("gameModeNormal", false);
		gameModeChallenge = settings.getBoolean("gameModeChallenge", false);
		hasDatt = settings.getBoolean("hasDatt", false);

		if (!gameStarted) {
			intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		} else if (gameModeNormal && hasDatt) {
			intent = new Intent(this, NormalHaveDattActivity.class);
			startActivity(intent);
			finish();
		} else if (gameModeNormal && !hasDatt) {
			intent = new Intent(this, NormalHaveNotDattActivity.class);
			startActivity(intent);
			finish();
		} else if (gameModeChallenge && hasDatt) {
			intent = new Intent(this, ChallangeHaveDattActivity.class);
			startActivity(intent);
			finish();
		} else if (gameModeChallenge && !hasDatt) {
			intent = new Intent(this, ChallangeHaveNotDattActivity.class);
			startActivity(intent);
			finish();
		}

	}

}
