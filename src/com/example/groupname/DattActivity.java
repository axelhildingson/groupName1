package com.example.groupname;

import java.nio.charset.Charset;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;

public class DattActivity extends ActionBarActivity implements CreateNdefMessageCallback,
OnNdefPushCompleteCallback{
	TextView mInfoText;
	private NfcAdapter mNfcAdapter;
	private static final int MESSAGE_SENT = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datt);

		
		mInfoText = (TextView) findViewById(R.id.textView);
        // Check for available NFC Adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
        if (mNfcAdapter == null) {
            mInfoText = (TextView) findViewById(R.id.textView);
            mInfoText.setText("NFC is not available on this device.");
        }
        // Register callback to set NDEF message
        mNfcAdapter.setNdefPushMessageCallback(this, this);
        // Register callback to listen for message-sent success
        mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
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

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_datt, container,
					false);
			return rootView;
		}
	}

	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
        Time time = new Time();
        time.setToNow();
        String text = ("Beam me up!\n\n" +
                "Beam Time: " + time.format("%H:%M:%S"));
        NdefMessage msg = new NdefMessage(
                new NdefRecord[] { createMimeRecord(
                        "application/com.example.groupname.datt", text.getBytes())
         /**
          * The Android Application Record (AAR) is commented out. When a device
          * receives a push with an AAR in it, the application specified in the AAR
          * is guaranteed to run. The AAR overrides the tag dispatch system.
          * You can add it back in to guarantee that this
          * activity starts when receiving a beamed message. For now, this code
          * uses the tag dispatch system.
          */
          //,NdefRecord.createApplicationRecord("com.example.android.beam")
        });
        return msg;
	}

	private NdefRecord createMimeRecord(String mimeType, byte[] payload) {
		byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
        NdefRecord mimeRecord = new NdefRecord(
                NdefRecord.TNF_MIME_MEDIA, mimeBytes, new byte[0], payload);
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
                Toast.makeText(getApplicationContext(), "Message sent!", Toast.LENGTH_LONG).show();
                break;
            }
        }
    };
    
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
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        mInfoText.setText(new String(msg.getRecords()[0].getPayload()));
    }

	@Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

}
