package com.pega.familydoctor.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.pega.familydoctor.R;
import com.pega.familydoctor.activity.adapter.UsersAdapter;
import com.pega.familydoctor.data.model.User;

public class UsersActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.users_activity);

		ArrayList<User> users = new ArrayList<User>();
		users.add(new User("Me", null, true));
		users.add(new User("My Mom", "097111000", true));
		users.add(new User("My Honey", "09265425", false));
		users.add(new User("My sister", "07580452", true));

		ListView listView = (ListView) findViewById(R.id.users_listusers);
		UsersAdapter adapter = new UsersAdapter(this, R.layout.users_item,
				users, 0);
		listView.setAdapter(adapter);

		ImageView addNewUser = (ImageView) findViewById(R.id.users_imv_add);
		addNewUser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(UsersActivity.this,
						UserAddActivity.class));
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

}
