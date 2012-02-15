package com.SysAdmin.Activity;

import com.SysAdmin.R;
import com.SysAdmin.EventListener.EventListener_Widget;
import com.SysAdmin.R.id;
import com.SysAdmin.R.layout;

import android.app.ActionBar;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class WidgetConfigure_Server extends Activity {
	
	private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	private Button buttonSave = null;
	private EventListener_Widget eventListener = null;
	
	public WidgetConfigure_Server()
	{
		super();
	}
	
	public void onCreate(Bundle _icicle)
	{
		super.onCreate(_icicle);
		
		// set layout
		this.setContentView(R.layout.configuration);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		// set result to canceled
		// in case the user cancels the process
		this.setResult(Activity.RESULT_CANCELED);
		
		Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
		
        // check if the id is invalid
		if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
		
		this.buttonSave = (Button) this.findViewById(R.id.button1);
		
		this.eventListener = new EventListener_Widget(this);
		
	}
	
	public boolean onCreateOptionsMenu(Menu _menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, _menu);
		return true;
	}
		
	
	/**
	 * Returns the save button
	 */
	public Button getButtonSave(){ return this.buttonSave;}
	
	/**
	 * Returns the widget id
	 */
	public int getAppWidgetId(){return this.mAppWidgetId;}


}