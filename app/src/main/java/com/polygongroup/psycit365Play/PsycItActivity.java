package com.polygongroup.psycit365Play;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
@SuppressWarnings("deprecation")
public class PsycItActivity extends TabActivity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Reusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, PointActivity.class);
        
        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("point").setIndicator(getString(R.string.point),//("Point",
              res.getDrawable(R.drawable.point_tab)).setContent(intent);
        tabHost.addTab(spec);
        
     // Do the same for the other tabs
        intent = new Intent().setClass(this, MixingActivity.class); 
        spec = tabHost.newTabSpec("mixing").setIndicator(getString(R.string.mixing),//Mixing
                res.getDrawable(R.drawable.mixing_tab)).setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, ProcessActivity.class); 
        spec = tabHost.newTabSpec("process").setIndicator(getString(R.string.process),//"Process",
                res.getDrawable(R.drawable.process_tab)).setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, SettingsActivity.class); 
        spec = tabHost.newTabSpec("settings").setIndicator(getString(R.string.settings),//"Settings",
                res.getDrawable(R.drawable.settings_tab)).setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, InfoActivity.class); 
        spec = tabHost.newTabSpec("info").setIndicator(getString(R.string.info),//"Info",
                res.getDrawable(R.drawable.info_tab)).setContent(intent);
        tabHost.addTab(spec);
       
        tabHost.setCurrentTab(3);
        
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        int storedPreference = preferences.getInt("storedInt", 0);
    }
}