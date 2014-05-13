package com.example.groupname;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


public class FirstActivity extends Activity {

	public final static String prefName = "dattenPref";

	private boolean gameStarted;
	private boolean hasVirus;
	private boolean hasAntidote;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent;

		SharedPreferences settings = getSharedPreferences(
				FirstActivity.prefName, 0);

		gameStarted = settings.getBoolean("gameStarted", false);
		hasVirus = settings.getBoolean("hasVirus", false);
		hasAntidote = settings.getBoolean("hasAntidote", false);

		if (!gameStarted) {
			intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		} else if (hasVirus) {
			intent = new Intent(this, NormalHaveDattActivity.class);
			startActivity(intent);
			finish();
		} else if (hasAntidote) {
			intent = new Intent(this, ChallangeHaveDattActivity.class);
			startActivity(intent);
			finish();
		} else {
			intent = new Intent(this, ChallangeHaveNotDattActivity.class);
			startActivity(intent);
			finish();
		}

	}

}
