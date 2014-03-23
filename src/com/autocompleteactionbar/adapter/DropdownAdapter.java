package com.autocompleteactionbar.adapter;

import java.util.ArrayList;

import com.autocompleteactionbar.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class DropdownAdapter extends BaseAdapter implements Filterable {

	Context ctx;

	ArrayList<String> originalEntries = new ArrayList<String>();
	ArrayList<String> filteredEntries = new ArrayList<String>();

	private Filter filter;

	public DropdownAdapter(Context context, ArrayList<String> entries) {
		this.ctx = context;
		this.originalEntries = entries;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.dropdown_item, parent,
					false);
		}

		TextView text = (TextView) convertView.findViewById(R.id.dropdown_item);
		text.setText(filteredEntries.get(position));

		return convertView;

	}

	@Override
	public Filter getFilter() {
		if (filter == null) {
			filter = new SampleFilter();
		}
		return filter;
	}

	@Override
	public int getCount() {
		return filteredEntries.size();
	}

	@Override
	public String getItem(int position) {
		return originalEntries.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	private class SampleFilter extends Filter {

		@SuppressLint("DefaultLocale")
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();

			if (constraint != null) {
				// load more data here.. eg from webservice
				final String prefixString = constraint.toString().toLowerCase();
				ArrayList<String> values = originalEntries;
				int count = values.size();

				ArrayList<String> newValues = new ArrayList<String>(count);

				for (int i = 0; i < count; i++) {
					String item = values.get(i);
					if (item.toLowerCase().startsWith(prefixString)) {
						newValues.add(item);
					}
				}

				results.values = newValues;
				results.count = newValues.size();
			}

			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {

			if (results.values != null) {
				filteredEntries = (ArrayList<String>) results.values;
			} else {
				filteredEntries = new ArrayList<String>();
			}
			if (results.count > 0) {
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}
		}

	}

}
