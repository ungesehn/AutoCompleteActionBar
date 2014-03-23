package com.autocompleteactionbar;

import com.autocompleteactionbar.adapter.DropdownAdapter;
import com.autocompleteactionbar.misc.ClearableAutoCompleteTextView;
import com.autocompleteactionbar.misc.ClearableAutoCompleteTextView.OnClearListener;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ActionBar actionBar = getActionBar(); // you can use ABS or the non-bc
												// ActionBar
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
				| ActionBar.DISPLAY_SHOW_HOME);

		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View v = inflater.inflate(R.layout.searchbox, null);
		AutoCompleteTextView textView = (AutoCompleteTextView) v
				.findViewById(R.id.search_box);

		textView.setAdapter(new DropdownAdapter(this, R.id.dropdown_item));

		final ImageView searchIcon = (ImageView) v
				.findViewById(R.id.search_icon);
		final ClearableAutoCompleteTextView searchBox = (ClearableAutoCompleteTextView) v
				.findViewById(R.id.search_box);

		// start with the text view hidden in the action bar
		searchBox.setVisibility(View.INVISIBLE);
		searchIcon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				toggleSearch(false);
			}
		});

		searchBox.setOnClearListener(new OnClearListener() {

			@Override
			public void onClear() {
				toggleSearch(true);
			}
		});

		searchBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// handle clicks on search results here
			}

		});

		actionBar.setCustomView(v);
	}

	// this toggles between the visibility of the search icon and the search box
	// to show search icon - reset = true
	// to show search box - reset = false
	protected void toggleSearch(boolean reset) {
		ClearableAutoCompleteTextView searchBox = (ClearableAutoCompleteTextView) findViewById(R.id.search_box);
		ImageView searchIcon = (ImageView) findViewById(R.id.search_icon);
		if (reset) {
			// hide search box and show search icon
			searchBox.setText("");
			searchBox.setVisibility(View.GONE);
			searchIcon.setVisibility(View.VISIBLE);
			// hide the keyboard
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(searchBox.getWindowToken(), 0);
		} else {
			// hide search icon and show search box
			searchIcon.setVisibility(View.GONE);
			searchBox.setVisibility(View.VISIBLE);
			searchBox.requestFocus();
			// show the keyboard
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(searchBox, InputMethodManager.SHOW_IMPLICIT);
		}

	}

}
