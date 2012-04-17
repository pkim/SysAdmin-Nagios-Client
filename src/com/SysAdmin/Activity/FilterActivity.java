package com.SysAdmin.Activity;

import java.util.HashMap;
import java.util.List;

import com.SysAdmin.AppFacade;
import com.SysAdmin.FileHandler;
import com.SysAdmin.MyExpandableListView;
import com.SysAdmin.R;
import com.SysAdmin.EventListener.EventListener_Filter;
import com.SysAdmin.FileDialog.FileDialog;
import com.SysAdmin.Filter.Filter;
import com.SysAdmin.Filter.FilterList;
import com.SysAdmin.Nagios.Entity.HostEntity;
import com.SysAdmin.Nagios.Entity.ServiceEntity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

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
	//private String[] mSelected = null;
	
	
	/** Called when the activity is first created. */
	protected void onCreate(Bundle _icicle)
	{
		super.onCreate(_icicle);
		this.setContentView(R.layout.filter);				
		
		if(null != AppFacade.GetCurrentEntity())
			this.addExpandableListView();
		
		this.initializeObjects();
		
		
		
		
		// set items
		View convertView = null;
		View view = null;
		
		for(int i = 0; i < 3; i++)
		{ 
			for(int j=0; j<5; j++)
			{

		 view = MyExpandableListView.getChildView(i, j, convertView);

		if(view != null)
		{
			CheckedTextView test = (CheckedTextView) view.findViewById(R.id.checkedText_Child);
			
			if(test != null)
				test.setChecked(true);
			
					
		}
		
		
			}
		}
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
	
	/*
	@SuppressWarnings("unchecked")
	private void checkSelectedItems()
	{		
		this.mSelected = new String[this.mExpandableListView.getCheckedItemCount()];			
		
		SparseBooleanArray checkedPositions = this.mExpandableListView.getCheckedItemPositions();
		for (int i = 0; i < checkedPositions.size(); i++)
		{
			if(checkedPositions.valueAt(i))
			{
				this.mSelected[i] = ((HashMap<String, String>) this.mExpandableListView.getAdapter().getItem(checkedPositions.keyAt(i))).get(MyExpandableListView.CHILDNAME);
			}
		}	
	}*/
		
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
            //this.checkSelectedItems();
	    	
            try {
            	AppFacade.setFilterList(this.createFilterArray());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            Intent intent = new Intent(this,ConclusionActivity.class);
            
            this.startActivityForResult(intent, AppFacade.GetConfigureRequestCode());
            
        	break;
	    
	    case R.id.menuItemLoad:
	        
	    	this.loadFile();
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
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		Log.d(AppFacade.GetTag(), "Result received");
		if (resultCode == RESULT_OK)
		{
			if(requestCode == AppFacade.REQEUST_LOAD)
			{
				String path = data.getStringExtra(FileDialog.RESULT_PATH);

				if(AppFacade.getFilterList() != null)
				{
					try {
						AppFacade.getFilterList().deserialize(path);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// set items
					View convertView = null;
					View view = null;
					
					for(int i = 0; i < 3; i++)
					{ 
						for(int j=0; j<5; j++)
						{
		
					 view = MyExpandableListView.getChildView(i, j, convertView);
		
					if(view != null)
					{
						CheckedTextView test = (CheckedTextView) view.findViewById(R.id.checkedText_Child);
						
						if(test != null)
							test.setChecked(true);
						
								
					}
					
					
						}
					}
				}
				
			}
			
			else
			{
				this.setResult(RESULT_OK);
				this.finish();
			}
		}
		else if (resultCode == RESULT_CANCELED)
		{
			if(requestCode != AppFacade.REQEUST_LOAD)
			{
				this.setResult(RESULT_CANCELED);
				this.finish();
			}
		}
		
    }
	
	public ExpandableListView getExpListView(){return this.mExpandableListView;}
	public CheckedTextView getChTxtView(){return (CheckedTextView) this.findViewById(R.id.checkedText_Child);}
	
	protected FilterList createFilterArray() throws Exception
	{
		FilterList filters = new FilterList();
		
		SparseBooleanArray checkedPositions = this.mExpandableListView.getCheckedItemPositions();
		
		String hostName    = new String();
		String serviceName = new String();
		
		int size = checkedPositions.size();
		
		for (int i = 0; i < size; i++)
		{
			Boolean checkedPosition = checkedPositions.valueAt(i);
			int itemPosition = checkedPositions.keyAt(i);
			
			if(checkedPosition)
			{
				ListAdapter listAdapter = (ListAdapter) this.mExpandableListView.getAdapter();
				HashMap<String,String> item = (HashMap<String, String>) listAdapter.getItem(itemPosition);
				
				serviceName = item.get(MyExpandableListView.CHILDNAME);
				hostName    = item.get(MyExpandableListView.GROUPNAME);
			
				if(hostName.isEmpty() || serviceName.isEmpty())
					throw new Exception("Couldn't determine a filter of a checked item");
				
				Filter filter = new Filter(hostName, serviceName);
				filters.addFilter(filter);
			}
		}
		
		return filters;
	}
	
	private void loadFile()
	{
		
		
		
		
		
		
		
		Intent intent = new Intent(getBaseContext(), FileDialog.class);
        intent.putExtra(FileDialog.START_PATH, "/sdcard");
        
        //can user select directories or not
        intent.putExtra(FileDialog.CAN_SELECT_DIR, true);
        
        //alternatively you can set file filter
        //intent.putExtra(FileDialog.FORMAT_FILTER, new String[] { "png" });
        
        startActivityForResult(intent, AppFacade.REQEUST_LOAD);
	}
	
	
}
