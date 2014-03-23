package com.autocompleteactionbar.adapter;

import org.json.JSONObject;

import com.autocompleteactionbar.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

public class DropdownAdapter extends ArrayAdapter<JSONObject> {

	Context ctx;

	public DropdownAdapter(Context context, int resource) {
		super(context, resource);
		this.ctx = context;
	}

	private Filter filter;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
			convertView =  inflater.inflate(
					R.layout.dropdown_item, parent, false);
		}

		TextView venueName = (TextView) convertView
				.findViewById(R.id.dropdown_item);

		final JSONObject venue = this.getItem(position);
		convertView.setTag(venue);
		return convertView;

	}

	@Override
	public Filter getFilter() {
		if (filter == null) {
			// filter = new VenueFilter();
		}
		return filter;
	}

}
