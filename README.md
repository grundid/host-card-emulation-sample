host-card-emulation-sample
==========================

Sample app for the Host-based Card Emulation released in Android 4.4

This app is a simple demo app where most of the code was taken from the Host-based Card Emulation documentation at:
http://developer.android.com/guide/topics/connectivity/nfc/hce.html

The HostApduService will simply wait for a SELECT AID apdu and then start a simple 
message exchange. Every message gets a unique message number.

You can find a precompiled APK in the releases section of this repository.

This app is used in conjunction with a demo app based on the NfcTools for Java project 
where a connection from an ACR122U desktop NFC reader is made. The reader will connect to this 
app and exchange arbitrary messages with the Android device.

Have a look at the blog post how this all works together:
http://blog.opendatalab.de/hack/2013/11/07/android-host-card-emulation-with-acr122/

I've also updated this app to demonstrate the Reader-Mode in Android 4.4 which allows you to communicate
to a second Android device in host-based card emulation mode.

For the time being you need an Android 4.4 device for this to work.
