package com.SysAdmin.Activity;

import com.SysAdmin.MyExpandableListView;
import com.SysAdmin.R;
import com.SysAdmin.EventListener.EventListener_Filter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

/**
 * Defines the filter activity. Adds the expandable listView to the layout.
 * 
 * @author Lukas Bernreiter
 * @author Markus Hinterleitner
 * @version 0.5, 03/28/2012
 * @since 0.5
 */
public class FilterActivity extends Activity 
{
	private EventListener_Filter mEventListener_Filter = null;
	
	/** Called when the activity is first created. */
	protected void onCreate(Bundle _icicle)
	{
		super.onCreate(_icicle);
		this.setContentView(R.layout.filter);
		
		this.initializeObjects();
		
		this.addExpandableListView();
	}
	
	private void initializeObjects()
	{
		this.mEventListener_Filter = new EventListener_Filter(this);
		this.mEventListener_Filter.setEvents();			
	}
	
	private void addExpandableListView()
	{
		ExpandableListView expand = new ExpandableListView(this);
		
		String[] groups = new String[]{"Group1", "Group2"};
		String[][] child = new String[][]{{"Child1", "Child2" }, {"Child3", "Child4"}};
		
		MyExpandableListView.createExpandableListView(this, groups, child, expand);
		
		LinearLayout layout = (LinearLayout)this.findViewById(R.id.layout_Filter);
		layout.addView(expand);
	}
	
	public boolean onCreateOptionsMenu(Menu _menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, _menu);
		return true;
	}
}
