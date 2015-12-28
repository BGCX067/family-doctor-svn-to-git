package com.pega.familydoctor.alarm;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.pega.familydoctor.config.Defines;
import com.pega.familydoctor.data.pref.Prefs;

/**
 * When the alarm fires, this WakefulBroadcastReceiver receives the broadcast
 * Intent and then starts the IntentService {@code SampleSchedulingService} to
 * do some work.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {
	// The app's AlarmManager, which provides access to the system alarm
	// services.
	private AlarmManager alarmMgr;
	// The pending intent that is triggered when the alarm fires.
	private PendingIntent alarmIntentMorning;
	private PendingIntent alarmIntentAfternoon;
	private PendingIntent alarmIntentEvening;

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.d("MyAPP", "alarm receiver");

		// BEGIN_INCLUDE(alarm_onreceive)
		/*
		 * If your receiver intent includes extras that need to be passed along
		 * to the service, use setComponent() to indicate that the service
		 * should handle the receiver's intent. For example:
		 * 
		 * ComponentName comp = new ComponentName(context.getPackageName(),
		 * MyService.class.getName());
		 * 
		 * // This intent passed in this call will include the wake lock extra
		 * as well as // the receiver intent contents.
		 * startWakefulService(context, (intent.setComponent(comp)));
		 * 
		 * In this example, we simply create a new intent to deliver to the
		 * service. This intent holds an extra identifying the wake lock.
		 */

		int hourOfday = intent.getIntExtra(Defines.TIMEOFDAY,
				Defines.TYPE_MORNING);
		Log.d("MyAPP", hourOfday + "      hourOfday");
		Intent service = new Intent(context, SchedulingService.class);
		service.putExtra(Defines.TIMEOFDAY, hourOfday);

		// Start the service, keeping the device awake while it is launching.
		startWakefulService(context, service);
		// END_INCLUDE(alarm_onreceive)
	}

	// BEGIN_INCLUDE(set_alarm)
	/**
	 * Sets a repeating alarm that runs once a day at approximately 8:30 a.m.
	 * When the alarm fires, the app broadcasts an Intent to this
	 * WakefulBroadcastReceiver.
	 * 
	 * @param context
	 */
	public void setAlarm(Context context) {

		Log.d("MyAPP", "set alarm");
		Log.d("MyAPP", Prefs.sharedInstance(context).getRemind() + "");
		Log.d("MyAPP", Prefs.sharedInstance(context).getObserverPhone() + "");

		alarmMgr = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		Intent intentMorning = new Intent(context, AlarmReceiver.class);
		intentMorning.putExtra(Defines.TIMEOFDAY, Defines.TYPE_MORNING);

		alarmIntentMorning = PendingIntent.getBroadcast(context,
				Defines.TYPE_MORNING, intentMorning, 0);

		Calendar calendarMorning = Calendar.getInstance();
		calendarMorning.setTimeInMillis(System.currentTimeMillis());
		// Set the alarm's trigger time to 8:30 a.m.
		String morningTime = Prefs.sharedInstance(context).getMorningAlarm();
		String[] morningTimeSplit = morningTime.split(":");
		int morningHour = Integer.parseInt(morningTimeSplit[0]);
		int morningMinute = Integer.parseInt(morningTimeSplit[1]);

		Log.d("MyApp", "morningHour" + morningHour);
		Log.d("MyApp", "morningMinute" + morningMinute);

		if (calendarMorning.get(Calendar.HOUR_OF_DAY) > morningHour
				|| (calendarMorning.get(Calendar.HOUR_OF_DAY) == morningHour && calendarMorning
						.get(Calendar.MINUTE) >= morningMinute)) {
			calendarMorning.set(Calendar.DAY_OF_YEAR,
					calendarMorning.get(Calendar.DAY_OF_YEAR) + 1);
		}
		calendarMorning.set(Calendar.HOUR_OF_DAY, morningHour);
		calendarMorning.set(Calendar.MINUTE, morningMinute);

		/*
		 * If you don't have precise time requirements, use an inexact repeating
		 * alarm the minimize the drain on the device battery.
		 * 
		 * The call below specifies the alarm type, the trigger time, the
		 * interval at which the alarm is fired, and the alarm's associated
		 * PendingIntent. It uses the alarm type RTC_WAKEUP ("Real Time Clock"
		 * wake up), which wakes up the device and triggers the alarm according
		 * to the time of the device's clock.
		 * 
		 * Alternatively, you can use the alarm type ELAPSED_REALTIME_WAKEUP to
		 * trigger an alarm based on how much time has elapsed since the device
		 * was booted. This is the preferred choice if your alarm is based on
		 * elapsed time--for example, if you simply want your alarm to fire
		 * every 60 minutes. You only need to use RTC_WAKEUP if you want your
		 * alarm to fire at a particular date/time. Remember that clock-based
		 * time may not translate well to other locales, and that your app's
		 * behavior could be affected by the user changing the device's time
		 * setting.
		 * 
		 * Here are some examples of ELAPSED_REALTIME_WAKEUP:
		 * 
		 * // Wake up the device to fire a one-time alarm in one minute.
		 * alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
		 * SystemClock.elapsedRealtime() + 60*1000, alarmIntentMorning);
		 * 
		 * // Wake up the device to fire the alarm in 30 minutes, and every 30
		 * minutes // after that.
		 * alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
		 * AlarmManager.INTERVAL_HALF_HOUR, AlarmManager.INTERVAL_HALF_HOUR,
		 * alarmIntentMorning);
		 */

		// Set the alarm to fire at approximately 8:30 a.m., according to the
		// device's
		// clock, and to repeat once a day.
		alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
				calendarMorning.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
				alarmIntentMorning);

		Intent intentAfternoon = new Intent(context, AlarmReceiver.class);
		intentAfternoon.putExtra(Defines.TIMEOFDAY, Defines.TYPE_NOON);

		alarmIntentAfternoon = PendingIntent.getBroadcast(context,
				Defines.TYPE_NOON, intentAfternoon, 0);

		String afternoonTime = Prefs.sharedInstance(context)
				.getAfternoonAlarm();
		String[] afternoonTimeSplit = afternoonTime.split(":");
		int afternoonHour = Integer.parseInt(afternoonTimeSplit[0]);
		int afternoonMinute = Integer.parseInt(afternoonTimeSplit[1]);

		Log.d("MyApp", "afternoonHour" + afternoonHour);
		Log.d("MyApp", "afternoonMinute" + afternoonMinute);

		Calendar calendarAfternoon = Calendar.getInstance();
		calendarAfternoon.setTimeInMillis(System.currentTimeMillis());

		if (calendarAfternoon.get(Calendar.HOUR_OF_DAY) > afternoonHour
				|| (calendarAfternoon.get(Calendar.HOUR_OF_DAY) == afternoonHour && calendarAfternoon
						.get(Calendar.MINUTE) >= afternoonMinute)) {
			calendarAfternoon.set(Calendar.DAY_OF_YEAR,
					calendarAfternoon.get(Calendar.DAY_OF_YEAR) + 1);
		}

		calendarAfternoon.set(Calendar.HOUR_OF_DAY, afternoonHour);
		calendarAfternoon.set(Calendar.MINUTE, afternoonMinute);

		alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
				calendarAfternoon.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
				alarmIntentAfternoon);

		Intent intentEvening = new Intent(context, AlarmReceiver.class);
		intentEvening.putExtra(Defines.TIMEOFDAY, Defines.TYPE_EVERNING);

		alarmIntentEvening = PendingIntent.getBroadcast(context,
				Defines.TYPE_EVERNING, intentEvening, 0);

		String everningTime = Prefs.sharedInstance(context).getEveningAlarm();
		String[] everningTimeSplit = everningTime.split(":");
		int everningHour = Integer.parseInt(everningTimeSplit[0]);
		int everningMinute = Integer.parseInt(everningTimeSplit[1]);

		Log.d("MyApp", "everningHour" + everningHour);
		Log.d("MyApp", "everningMinute" + everningMinute);

		Calendar calendarEverning = Calendar.getInstance();
		calendarEverning.setTimeInMillis(System.currentTimeMillis());

		if (calendarEverning.get(Calendar.HOUR_OF_DAY) > everningHour
				|| (calendarEverning.get(Calendar.HOUR_OF_DAY) == everningHour && calendarEverning
						.get(Calendar.MINUTE) >= everningMinute)) {
			calendarEverning.set(Calendar.DAY_OF_YEAR,
					calendarEverning.get(Calendar.DAY_OF_YEAR) + 1);
		}

		calendarEverning.set(Calendar.HOUR_OF_DAY, everningHour);
		calendarEverning.set(Calendar.MINUTE, everningMinute);

		alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
				calendarEverning.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
				alarmIntentEvening);

		// Enable {@code SampleBootReceiver} to automatically restart the alarm
		// when the
		// device is rebooted.
		ComponentName receiver = new ComponentName(context, BootReceiver.class);
		PackageManager pm = context.getPackageManager();

		pm.setComponentEnabledSetting(receiver,
				PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
				PackageManager.DONT_KILL_APP);
	}

	// END_INCLUDE(set_alarm)

	/**
	 * Cancels the alarm.
	 * 
	 * @param context
	 */
	// BEGIN_INCLUDE(cancel_alarm)
	public void cancelAlarm(Context context) {
		// If the alarm has been set, cancel it.
		if (alarmMgr != null) {
			alarmMgr.cancel(alarmIntentMorning);
			alarmMgr.cancel(alarmIntentAfternoon);
			alarmMgr.cancel(alarmIntentEvening);
		}

		// Disable {@code SampleBootReceiver} so that it doesn't automatically
		// restart the
		// alarm when the device is rebooted.
		ComponentName receiver = new ComponentName(context, BootReceiver.class);
		PackageManager pm = context.getPackageManager();

		pm.setComponentEnabledSetting(receiver,
				PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
				PackageManager.DONT_KILL_APP);
	}
	// END_INCLUDE(cancel_alarm)
}
