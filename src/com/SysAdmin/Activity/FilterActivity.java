package com.SysAdmin.Activity;

import java.util.List;

import com.SysAdmin.AppFacade;
import com.SysAdmin.MyExpandableListView;
import com.SysAdmin.R;
import com.SysAdmin.EventListener.EventListener_Filter;
import com.SysAdmin.Nagios.Entity.HostEntity;
import com.SysAdmin.Nagios.Entity.ServiceEntity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

/**
 * Defines the filter activity. Adds the expandable listView to the layout.
 * 
 * @author Lukas Bernreiter
 * @author Markus Hinterleitner
 * @version 0.6, 03/28/2012
 * @since 0.5
 */
public class FilterActivity extends Activity 
{
	private EventListener_Filter mEventListener_Filter = null;
	private String[] mGroups = null;
	private String[][] mChilds = null;
	private ExpandableListView mExpandableListView = null;
	
	/** Called when the activity is first created. */
	protected void onCreate(Bundle _icicle)
	{
		super.onCreate(_icicle);
		this.setContentView(R.layout.filter);
		
		this.initializeObjects();
		
		if(null != AppFacade.GetCurrentEntity())
			this.addExpandableListView();
	}
	
	private void initializeObjects()
	{
		this.mEventListener_Filter = new EventListener_Filter(this);
		this.mEventListener_Filter.setEvents();			
	}
	
	private void addExpandableListView()
	{
		this.mExpandableListView = new ExpandableListView(this);				
		
		// Retrieve the data for the ListView
		this.fillData();			
		
		MyExpandableListView.createExpandableListView(this, this.mGroups, this.mChilds, this.mExpandableListView);
		
		LinearLayout layout = (LinearLayout)this.findViewById(R.id.layout_Filter);
		layout.addView(this.mExpandableListView);
	}
	
	private void fillData()
	{
		HostEntity[] hosts = AppFacade.GetCurrentEntity().getHosts();
		this.mGroups = new String[hosts.length];
		this.mChilds = new String[hosts.length][];
		
		// Create the parent items
		for (int i = 0; i < hosts.length; i++) {
			this.mGroups[i] = hosts[i].getHostName();
			// Retrieve the services of a host
			List<ServiceEntity> services = hosts[i].getServices();
			this.mChilds[i] = new String[services.size()];
			for (int j = 0; j < services.size(); j++) 
			{				
				this.mChilds[i][j] = services.get(j).getServiceDescription(); 
			}
		}
	}
	
	private void checkSelectedItems()
	{
		for(int i=0; i < this.mExpandableListView.getChildCount(); i++)
		{
			CheckBox checkBox = (CheckBox) this.mExpandableListView.getChildAt(i).findViewById(R.id.checkBox_ChildItem);
			if(checkBox != null && checkBox.isChecked())
				Log.d(AppFacade.GetTag(), ""+i);			
		}
	}
	
	public boolean onCreateOptionsMenu(Menu _menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, _menu);
		return true;
	}	
	
	/** Called whenever a menu item gets clicked */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
	    switch (item.getItemId()) {	     
	    
	    case R.id.menuItemNext:
            this.checkSelectedItems();
        	break;
	    
	    case R.id.menuItemLoad:
	            
	        break;
	    case R.id.menuItemAbort:
	    	this.setResult(RESULT_CANCELED);
	        this.finish();
	        break;
	        
	    default:
	    	return super.onOptionsItemSelected(item);
	    }
	    
	    return true;
	}
}
