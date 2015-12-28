package com.pega.familydoctor.activity;

import com.pega.familydoctor.R;
import com.pega.familydoctor.R.layout;
import com.pega.familydoctor.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ListIllnessActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_illness_activity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

}
