package kingswood.idphoto.log;

import android.util.Log;

public class AppLogger {
	
	private static final String TAG = "idphoto";
	
	public static void log(String msg){
		Log.d(TAG, msg);
	}
	
}
