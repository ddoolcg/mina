package com.lcggame.pvp.utils;

import android.util.Log;

import com.lcggame.pvp.BuildConfig;

public class L {
	private static final String TAG = "fast";

	public static void v(String msg) {
		v(TAG, msg);
	}

	public static void d(String msg) {
		d(TAG, msg);
	}

	public static void i(String msg) {
		i(TAG, msg);
	}

	public static void w(String msg) {
		w(TAG, msg);
	}

	public static void e(String msg) {
		e(TAG, msg);
	}

	public static void v(String tag, String msg) {
		if (BuildConfig.DEBUG)
			Log.v(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (BuildConfig.DEBUG)
			Log.d(tag, msg);
	}

	public static void i(String tag, String msg) {
		if (BuildConfig.DEBUG)
			Log.i(tag, msg);
	}

	public static void w(String tag, String msg) {
		if (BuildConfig.DEBUG)
			Log.w(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (BuildConfig.DEBUG)
			Log.e(tag, msg);
	}

}
