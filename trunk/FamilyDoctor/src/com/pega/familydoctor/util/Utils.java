package com.pega.familydoctor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.pega.familydoctor.R;
import com.pega.familydoctor.config.Defines;

public class Utils {
	private static Utils instance = null;

	private ProgressDialog progressSpinnerDialog;

	private PackageManager pm;

	private int progressSpinnerDialogCount;

	public Utils() {
		progressSpinnerDialogCount = 0;
	}	

	public static Utils sharedInstance() {
		if (instance == null) {
			instance = new Utils();
		}
		return instance;
	}

	/**
	 * show a dialog
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param icon
	 * @param negButName
	 * @param negButListener
	 * @param posButName
	 * @param posButListener
	 */
	public void showDialog(Context context, int title, int message, int icon,
			int negButName, OnClickListener negButListener, int posButName,
			OnClickListener posButListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle(title).setMessage(message)
				.setNegativeButton(negButName, negButListener)
				.setPositiveButton(posButName, posButListener).setIcon(icon);
		builder.create().show();
	}

	public void showDialog1(Context context, String title, String message,
			int icon, String posButName, OnClickListener posButListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle(title).setMessage(message)
				.setPositiveButton(posButName, posButListener).setIcon(icon);
		builder.create().show();

	}

	/**
	 * show a dialog
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param icon
	 * @param negButName
	 * @param negButListener
	 * @param posButName
	 * @param posButListener
	 */
	public void showDialog(Context context, String title, String message,
			int icon, String negButName, OnClickListener negButListener,
			String posButName, OnClickListener posButListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setTitle(title).setMessage(message)
				.setNegativeButton(negButName, negButListener)
				.setPositiveButton(posButName, posButListener).setIcon(icon);
		builder.create().show();

	}

	/**
	 * show a progress spinner dialog when run another thread
	 * 
	 * @param context
	 * @param title
	 * @param message
	 */
	public void showProgressSpinnerDialog(Context context, String title,
			String message) {
		if (progressSpinnerDialogCount == 0) {
			progressSpinnerDialog = new ProgressDialog(context,
					ProgressDialog.STYLE_SPINNER);
			progressSpinnerDialog.setTitle(title);
			progressSpinnerDialog.setMessage(message);
			progressSpinnerDialog.show();
		}
		progressSpinnerDialogCount += 1;
	}

	/**
	 * dismiss above progress spinner dialog
	 */
	public void dismissProgressSpinnerDialog() {
		progressSpinnerDialogCount -= 1;
		if (progressSpinnerDialogCount == 0) {
			if (progressSpinnerDialog != null) {
				if (progressSpinnerDialog.isShowing())
					progressSpinnerDialog.dismiss();
				progressSpinnerDialog = null;
			}
		}
	}

	/**
	 * show a Toast in Length Long
	 * 
	 * @param context
	 * @param message
	 */
	public void showToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * log if debugging
	 * 
	 * @param context
	 * @param message
	 */
	public void log(Context context, String message) {
		if (Defines.DEBUG)
			Log.d(context.getString(R.string.app_name), message);
	}

	/**
	 * Start system SMS to send a SMS with given message.
	 * 
	 * @param context
	 * @param message
	 */
	public void sendSMS(Context context, String message) {
		Intent sendIntent = new Intent(Intent.ACTION_VIEW);
		sendIntent.putExtra("sms_body", message);
		sendIntent.setType("vnd.android-dir/mms-sms");
		context.startActivity(sendIntent);
	}

	/**
	 * check does device support FEATURE_TELEPHONY
	 * 
	 * @param context
	 * @return
	 */
	public Boolean checkSupportFEATURE_TELEPHONY(Context context) {
		pm = context.getPackageManager();
		return pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
	}

	/**
	 * get location of a view was located on screen
	 * 
	 * @param v
	 * @return Point
	 */
	private Point getLocationOfView(View v) {
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		Point p = new Point();
		p.x = location[0];
		p.y = location[1];
		return p;
	}

	/**
	 * set always show Action Overflow if device has hardware option key
	 * 
	 * @param context
	 */
	public void setAlwaysShowActionOverflow(Context context) {
		try {
			ViewConfiguration config = ViewConfiguration.get(context);
			java.lang.reflect.Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getConnect(String url) {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.d("MYAPP", "Can't connect to server");
			}
		} catch (ClientProtocolException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return builder.toString();
	}
	
	public Date getCurrentDate() {
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}

}
