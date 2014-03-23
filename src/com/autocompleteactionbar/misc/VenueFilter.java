package com.autocompleteactionbar.misc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Filter;

public class VenueFilter extends Filter {
	
	@Override
	protected FilterResults performFiltering(CharSequence constraint) {
		List<JSONObject> list = new ArrayList<JSONObject>();
		FilterResults result = new FilterResults();
		String substr = constraint.toString().toLowerCase();
		// if no constraint is given, return the whole list
		if (substr == null || substr.length() == 0) {
			result.values = list;
			result.count = list.size();
		} else {
			// iterate over the list of venues and find if the venue matches the
			// constraint. if it does, add to the result list
			final ArrayList<JSONObject> retList = new ArrayList<JSONObject>();
			for (JSONObject obj : list) {
				try {
					if (obj.getString("myString").toLowerCase()
							.contains(constraint)) {
						retList.add(obj);
					}
				} catch (JSONException e) {
					// Log.i(Consts.TAG, e.getMessage());
				}
			}
			result.values = retList;
			result.count = retList.size();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void publishResults(CharSequence constraint, FilterResults results) {
		// we clear the adapter and then populate it with the new results
		// searchAdapter.clear();
		if (results.count > 0) {
			for (JSONObject o : (ArrayList<JSONObject>) results.values) {
				// searchAdapter.add(o);
			}
		}
	}

}