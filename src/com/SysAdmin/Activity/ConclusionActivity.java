package com.SysAdmin.Activity;


// com.SysAdmin
import com.SysAdmin.AppFacade;
import com.SysAdmin.R;
import com.SysAdmin.EventListener.EventListener_Conclusion;
// android
import android.app.Activity;
import android.os.Bundle;
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
		
		this.mListView = (ListView)this.findViewById(R.id.listView_Conclusion_Filter);
		this.mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		
		ArrayAdapter<String> dataSource = new ArrayAdapter<String>(this,R.layout.list_item, R.id.textView_list,	this.getExSelected());
		this.mListView.setAdapter(dataSource);
		
		this.mEventListener_Conclusion = new EventListener_Conclusion(this);
		this.mEventListener_Conclusion.setEvents();	
	}

	private String[] getExSelected()
	{	
		try{		
			return this.getIntent().getExtras().getStringArray(AppFacade.GetExSelected());
		}catch (Exception e) {
			return new String[]{""};
		}
	}
	
	private void setHostname(String _hostname)
	{
		this.mTextView.setText("Hostname: " + _hostname);
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
}
