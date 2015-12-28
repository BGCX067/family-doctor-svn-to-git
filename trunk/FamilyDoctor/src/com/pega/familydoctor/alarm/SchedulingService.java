package com.pega.familydoctor.alarm;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.pega.familydoctor.activity.RemindActivity;
import com.pega.familydoctor.config.Defines;
import com.pega.familydoctor.data.pref.Prefs;

/**
 * This {@code IntentService} does the app's actual work.
 * {@code SampleAlarmReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class SchedulingService extends IntentService {
	public SchedulingService() {
		super("SchedulingService");
	}

	// An ID used to post the notification.
	// The string the app searches for in the Google home page content. If the
	// app finds
	// the string, it indicates the presence of a doodle.
	// The Google home page URL from which the app fetches content.
	// You can find a list of other Google domains with possible doodles here:
	// http://en.wikipedia.org/wiki/List_of_Google_domains

	@Override
	protected void onHandleIntent(Intent intent) {

		Log.d("MyAPP", "scheduling service");
		// BEGIN_INCLUDE(service_onhandle)
		// The URL from which to fetch content.

		int hourOfDay = intent.getIntExtra(Defines.TIMEOFDAY,
				Defines.TYPE_MORNING);

		Log.d("MyApp", hourOfDay + " onHandleIntent");
		Log.d("MyApp", Prefs.sharedInstance(this).getRemind() + "");

		Intent alarmIntent = new Intent(this, RemindActivity.class);

		alarmIntent.putExtra(Defines.TIMEOFDAY, hourOfDay);
		alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (Prefs.sharedInstance(this).getRemind())
			startActivity(alarmIntent);

	}
}
