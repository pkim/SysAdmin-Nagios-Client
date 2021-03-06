package com.SysAdmin.Activity;


// com.SysAdmin
import java.util.ArrayList;

import com.SysAdmin.AppFacade;
import com.SysAdmin.FileHandler;
import com.SysAdmin.R;
import com.SysAdmin.EventListener.EventListener_Conclusion;
import com.SysAdmin.FileDialog.FileDialog;
import com.SysAdmin.Filter.Filter;
import com.SysAdmin.Filter.FilterList;
// android
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Last configuration activity.
 * Concludes the settings entered by the user.
 * 
 * @author Lukas Bernreiter
 * @version 0.7, 19/02/2012S
 * @since 0.1
 */
public class ConclusionActivity extends Activity 
{
	private EventListener_Conclusion mEventListener_Conclusion = null;
	private TextView mTextView = null;
	private TextView mURL = null;
	private ListView mListView = null;
	
	
	/** Called when the activity is first created. */
	protected void onCreate(Bundle _icicle)
	{
		super.onCreate(_icicle);
		this.setContentView(R.layout.conclusion);			
		
		this.initializeObjects();
	}	
	
	private void initializeObjects()
	{		
		this.mTextView = (TextView)this.findViewById(R.id.textView_Conclusion_Hostname);
		this.setHostname(AppFacade.GetHostname());
		
		this.mURL = (TextView)this.findViewById(R.id.textView_Conclusion_URL);
		this.setURL(AppFacade.GetURL());
		
		this.mListView = (ListView)this.findViewById(R.id.listView_Conclusion_Filter);
		this.mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		
		
		if(AppFacade.getFilterList() != null)
		{
			ArrayList<Filter> filterArray = AppFacade.getFilterList().getFilters();
		
			Integer size = AppFacade.getFilterList() .getFilters().size();
			String[] arrayAdapterSource = new String[size];
		
		
			for(int i=0; i < size; i++)
			{
				arrayAdapterSource[i] = String.format("%s - %s", 
							filterArray.get(i).getHostName(),
							filterArray.get(i).getServiceName()
							);
			}
		
			ArrayAdapter<String> dataSource = new ArrayAdapter<String>(this,R.layout.list_item, R.id.textView_list, arrayAdapterSource);
			this.mListView.setAdapter(dataSource);
		}
		
		this.mEventListener_Conclusion = new EventListener_Conclusion(this);
		this.mEventListener_Conclusion.setEvents();	
	}

	private FilterList getExSelected()
	{	
		try{		
			FilterList filterList = (FilterList)this.getIntent().getExtras().getSerializable(AppFacade.GetExSelected());
			
			return filterList;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	private void setHostname(String _hostname)
	{
		this.mTextView.setText("Hostname: " + _hostname);
	}
	
	private void setURL(String _url)
	{
		this.mURL.setText("URL: " + _url);
	}
	
	/** Called when the menu gets created */
	@Override
	public boolean onCreateOptionsMenu(Menu _menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.final_menu, _menu);
		return true;
	}
	
	/** Called whenever a menu item gets clicked */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
	    switch (item.getItemId()) {	     
	        case R.id.menuItemSave:
	            
	        	if(AppFacade.getFilterList()  != null)
	        		this.saveFile();
	        	
	        	break;
	        	
	        case R.id.menuItemFinish:
	        	this.setResult(RESULT_OK);
	        	this.finish();
	        	break;
	        
	        case R.id.menuItemAbort:
	        	this.setResult(RESULT_CANCELED);
	        	this.finish();
	        	
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	    
	    return true;
	}
	
	/**
	 * Returns the listView used in the conclusion activity
	 * @return The ListView
	 */
	public ListView getListView(){ return this.mListView; }
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(AppFacade.GetTag(), "Result received");
		
		if (resultCode == RESULT_OK)
		{
			if(requestCode == AppFacade.REQEUST_SAVE)
			{
				String path = data.getStringExtra(FileDialog.RESULT_PATH);

				if(AppFacade.getFilterList() != null)
				{
					try {
						AppFacade.getFilterList().serialize(path);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
	
	
	private void saveFile()
	{
		Intent intent = new Intent(getBaseContext(), FileDialog.class);
        intent.putExtra(FileDialog.START_PATH, "/sdcard/SysAdmin");
        
        //can user select directories or not
        intent.putExtra(FileDialog.CAN_SELECT_DIR, true);
        
        //alternatively you can set file filter
        //intent.putExtra(FileDialog.FORMAT_FILTER, new String[] { "png" });
        
        startActivityForResult(intent, AppFacade.REQEUST_SAVE);
	}
}
