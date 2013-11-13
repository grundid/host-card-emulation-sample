package de.grundid.hcedemo;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import de.grundid.hcedemo.IsoDepTranceiver.OnMessageReceived;

public class MainActivity extends Activity implements OnMessageReceived {

	private PendingIntent pendingIntent;
	private NfcAdapter nfcAdapter;
	private IntentFilter[] intentFiltersArrays;
	private String[][] techListsArrays;
	private ListView listView;
	private IsoDepAdapter isoDepAdapter;
	private TextView status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView);
		status = (TextView)findViewById(R.id.intro);
		isoDepAdapter = new IsoDepAdapter(getLayoutInflater());
		listView.setAdapter(isoDepAdapter);
		prepareForegroundDispatch();
	}

	private void prepareForegroundDispatch() {
		pendingIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
		intentFiltersArrays = new IntentFilter[] { ndef };
		techListsArrays = new String[][] { new String[] { IsoDep.class.getName() } };
	}

	@Override
	public void onPause() {
		super.onPause();
		nfcAdapter.disableForegroundDispatch(this);
		status.setText("IsoDep ForegroundDispatch disabled");
	}

	@Override
	public void onResume() {
		super.onResume();
		nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArrays, techListsArrays);
		status.setText("IsoDep ForegroundDispatch enabled");
	}

	@Override
	public void onNewIntent(Intent intent) {
		Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		IsoDep isoDep = IsoDep.get(tagFromIntent);
		isoDepAdapter.resetMessages();
		IsoDepTranceiver tranceiver = new IsoDepTranceiver(isoDep, this);
		Thread thread = new Thread(tranceiver);
		thread.start();
	}

	@Override
	public void onMessage(final byte[] message) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				isoDepAdapter.addMessage(new String(message));
			}
		});
	}

	@Override
	public void onError(Exception exception) {
		onMessage(exception.getMessage().getBytes());
	}
}
