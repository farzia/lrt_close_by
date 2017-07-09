package com.brtc.nearby.myLogs;

import android.util.Log;

import com.brtc.nearby.staticvar.StaticVars;

public class AppsLogs {

	public AppsLogs(Object log) {
		Log.d(StaticVars.LOG_TAG, log.toString());
		Log.w(StaticVars.LOG_TAG, log.toString());
		Log.e(StaticVars.LOG_TAG, log.toString());
		Log.i(StaticVars.LOG_TAG, log.toString());
	}
}
