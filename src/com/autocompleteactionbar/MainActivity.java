
package com.autocompleteactionbar;

import java.util.ArrayList;
import java.util.Arrays;

import com.autocompleteactionbar.adapter.DropdownAdapter;
import com.autocompleteactionbar.misc.ClearableAutoCompleteTextView;
import com.autocompleteactionbar.misc.ClearableAutoCompleteTextView.OnClearListener;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.searchbox, null);

        ArrayList<String> sampleEntries = createSampleList();

        final ImageView searchIcon = (ImageView) v
                .findViewById(R.id.search_icon);
        final ClearableAutoCompleteTextView searchBox = (ClearableAutoCompleteTextView) v
                .findViewById(R.id.search_box);

        searchBox.setAdapter(new DropdownAdapter(this, sampleEntries));

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
                // ...
            }

        });

        getActionBar().setCustomView(v);
    }

    private ArrayList<String> createSampleList() {
        String[] countries = getResources().getStringArray(R.array.countries_array);
        return new ArrayList<String>(Arrays.asList(countries));
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

            // add FANCY animation
            Animation a = AnimationUtils.loadAnimation(this,
                    R.anim.push_left_in);

            searchBox.setVisibility(View.VISIBLE);
            searchBox.findViewById(R.id.search_box).startAnimation(a);
            searchBox.requestFocus();
            // show the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(searchBox, InputMethodManager.SHOW_IMPLICIT);
        }

    }

}
