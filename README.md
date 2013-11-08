host-card-emulation-sample
==========================

Sample app for the Host-based Card Emulation released in Android 4.4

This app is a simple demo app where most of the code was taken from the Host-based Card Emulation documentation at:
http://developer.android.com/guide/topics/connectivity/nfc/hce.html

The HostApduService will simply wait for a SELECT AID apdu and then start a simple 
message exchange. Every message gets a unique message number.

This app is used in conjunction with a demo app based on the NfcTools for Java project 
where a connection from an ACR122U desktop NFC reader is made. The reader will connect to this 
app and exchange arbitrary messages with the Android device.

For the time being you need a Nexus 5 device for this to work.