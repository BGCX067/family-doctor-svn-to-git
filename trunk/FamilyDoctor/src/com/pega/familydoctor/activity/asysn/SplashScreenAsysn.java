package com.pega.familydoctor.activity.asysn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.pega.familydoctor.activity.UsersActivity;
import com.pega.familydoctor.config.Defines;

public class SplashScreenAsysn extends AsyncTask<Void, Void, Void> {

	private Context context;

	public SplashScreenAsysn(Context context) {
		this.context = context;
	}

	protected void onPreExecute() {
		super.onPreExecute();
	}

	protected Void doInBackground(Void... params) {

		try {

			Thread.sleep(Defines.SLEEP_TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	protected void onPostExecute(Void result) {
		Intent intent = new Intent(context, UsersActivity.class);
		context.startActivity(intent);
		((Activity) context).finish();

	}
}
