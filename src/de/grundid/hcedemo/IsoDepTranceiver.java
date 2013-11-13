package de.grundid.hcedemo;

import java.io.IOException;

import android.nfc.tech.IsoDep;

public class IsoDepTranceiver implements Runnable {

	public interface OnMessageReceived {

		void onMessage(byte[] message);

		void onError(Exception exception);
	}

	private IsoDep isoDep;
	private OnMessageReceived onMessageReceived;

	public IsoDepTranceiver(IsoDep isoDep, OnMessageReceived onMessageReceived) {
		this.isoDep = isoDep;
		this.onMessageReceived = onMessageReceived;
	}

	@Override
	public void run() {
		int messageCounter = 0;
		try {
			isoDep.connect();
			while (isoDep.isConnected() && !Thread.interrupted()) {
				String message = "Message from IsoDep " + messageCounter++;
				byte[] response = isoDep.transceive(message.getBytes());
				onMessageReceived.onMessage(response);
			}
			isoDep.close();
		}
		catch (IOException e) {
			onMessageReceived.onError(e);
		}
	}
}
