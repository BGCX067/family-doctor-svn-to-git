package com.pega.familydoctor.activity.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pega.familydoctor.R;
import com.pega.familydoctor.data.model.User;

public class UsersAdapter extends ArrayAdapter<User> {

	private Context context;
	private int resource;
	private int current;
	private ImageView currentUser;
	private TextView phone;
	private TextView name;
	private ImageView remind;

	public UsersAdapter(Context context, int resource, List<User> items,
			int current) {
		super(context, resource, items);
		this.context = context;
		this.resource = resource;
		this.current = current;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View newView = convertView;

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		newView = inflater.inflate(this.resource, null);

		newView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "" + position, Toast.LENGTH_SHORT)
						.show();
			}
		});

		final User item = getItem(position);

		name = (TextView) newView.findViewById(R.id.users_item_tv_username);
		phone = (TextView) newView.findViewById(R.id.users_item_tv_phone);

		currentUser = (ImageView) newView
				.findViewById(R.id.users_item_img_view_tick);

		currentUser.setVisibility(View.INVISIBLE);

		remind = (ImageView) newView
				.findViewById(R.id.users_item_img_view_remind);

		if (item.getUserRemind())
			remind.setImageResource(R.drawable.on);
		else {
			remind.setImageResource(R.drawable.off);
			phone.setVisibility(View.INVISIBLE);
		}
		remind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "" + item.getUserRemind(),
						Toast.LENGTH_SHORT).show();
				if (item.getUserRemind()) {
					remind.setImageResource(R.drawable.off);
					phone.setVisibility(View.INVISIBLE);
					item.setUserRemind(!item.getUserRemind());
				} else {
					phone.setVisibility(View.VISIBLE);
					remind.setImageResource(R.drawable.on);
					item.setUserRemind(!item.getUserRemind());

				}
				notifyDataSetChanged();
			}
		});

		name.setText(item.getUserName());
		phone.setText(item.getUserPhone());

		if (item.getUserPhone() == null || item.getUserPhone().equals("")) {
			phone.setVisibility(View.INVISIBLE);
		}

		if (position == current)
			currentUser.setVisibility(View.VISIBLE);
		return newView;
	}
}
