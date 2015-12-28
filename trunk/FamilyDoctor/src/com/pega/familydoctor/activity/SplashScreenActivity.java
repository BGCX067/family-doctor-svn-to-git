package com.pega.familydoctor.activity;

import android.app.Activity;
import android.os.Bundle;

import com.pega.familydoctor.R;
import com.pega.familydoctor.activity.asysn.SplashScreenAsysn;

public class SplashScreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen_activity);
		new SplashScreenAsysn(this).execute();
	}

}
