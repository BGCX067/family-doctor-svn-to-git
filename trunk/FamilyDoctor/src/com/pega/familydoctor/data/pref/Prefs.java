package com.pega.familydoctor.data.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Prefs {

	private SharedPreferences prefs;

	SharedPreferences.Editor editor;

	Context context;

	private static Prefs instance = null;

	private int versionInt = 1;

	public static void init(Context context) {
		if (instance == null) {
			instance = new Prefs(context);
		}
	}

	public static Prefs sharedInstance(Context context) {
		if (instance == null) {
			instance = new Prefs(context);
		}
		return instance;
	}

	public Prefs(Context context) {
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		editor = prefs.edit();
		this.context = context;
	}

	public void updateMorningAlarm(String time) {
		editor.putString("MorningAlarm", time);
		editor.commit();
	}

	public String getMorningAlarm() {
		return prefs.getString("MorningAlarm", "10:40");
	}

	public void updateAfternoonAlarm(String time) {
		editor.putString("AfternoonAlarm", time);
		editor.commit();
	}

	public String getAfternoonAlarm() {
		return prefs.getString("AfternoonAlarm", "10:39");
	}

	public void updateEveningAlarm(String time) {
		editor.putString("EveningAlarm", time);
		editor.commit();
	}

	public String getEveningAlarm() {
		return prefs.getString("EveningAlarm", "10:38");
	}

	public String getObserverPhone() {
		return prefs.getString("ObserverPhone", "0902675405");
	}

	public void updateObserverPhone(String ObserverPhone) {
		editor.putString("ObserverPhone", ObserverPhone);
		editor.commit();
	}

	public Boolean getRemind() {
		return prefs.getBoolean("Remind", true);
	}

	public void updateRemind(Boolean boolean1) {
		editor.putBoolean("Remind", boolean1);
		editor.commit();
	}
}
