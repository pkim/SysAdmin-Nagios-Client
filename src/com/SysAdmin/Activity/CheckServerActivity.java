package com.SysAdmin.Activity;


import com.SysAdmin.AppFacade;
import com.SysAdmin.FilePathFacade;
import com.SysAdmin.R;
import com.SysAdmin.EventListener.EventListener_Server;
import com.SysAdmin.Nagios.XMLParser;
import com.SysAdmin.Nagios.Entity.NagiosEntity;
// android
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Lukas Bernreiter
 * @author Thomas Weber
 * @version 0.5, 22/02/2012
 * @since 0.1
 */
public class CheckServerActivity extends Activity {
	
	// Objects
	private EventListener_Server mEventListener_Configuration_Server = null;
	private Integer mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	private NagiosEntity mNagiosEntity = null;
	
	// Constructor
	public CheckServerActivity()
	{
		super();
	}
	
	/** Called when the activity is first created. */
	public void onCreate(Bundle _icicle)
	{
		super.onCreate(_icicle);
		
		// set layout
		this.setContentView(R.layout.configuration);
			
		// set result to canceled
		// in case the user cancels the process
		this.setResult(Activity.RESULT_CANCELED);

		this.getWidgetId();
		
		this.initializeObjects();
		
	}
	
	/** Initializes every object of the main configuration activity */
	private void initializeObjects()
	{
		// set events
		this.mEventListener_Configuration_Server = new EventListener_Server(this);
		this.mEventListener_Configuration_Server.setEvents();
	}
	
	/** Retrieves the application's widget id */
	private void getWidgetId()
	{		
		Bundle extras = this.getIntent().getExtras();
        if (null != extras)
    
        	this.mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
            								AppWidgetManager.INVALID_APPWIDGET_ID);
		
        // set the new id
		try{
			AppFacade.SetAppWidgetId(this.mAppWidgetId);
		}catch(Exception _e)
		{
			Log.e(AppFacade.GetTag(), _e.getMessage());
			this.finish();
		}
	}
	
	public boolean onCreateOptionsMenu(Menu _menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, _menu);
		return true;
	}
	
	/** Called whenever a menu item gets clicked */
	@Override
	public boolean onOptionsItemSelected(MenuItem _item)
	{
	    switch (_item.getItemId()) {
	    	
	    	// start the next activity
	        case R.id.menuItemNext:
	        	
	        	try 
	        	{
	        		this.mNagiosEntity = XMLParser.parse(FilePathFacade.GetTempFile());
	        	}
	        	
	        	catch (Exception e) 
	        	{
	        		Log.e(AppFacade.GetTag(), "Unable to parse xml file 2");
	        	}
	        	
	        	Intent intent = new Intent(this, FilterActivity.class);
	        	
	        	AppFacade.SetCurrentEntity(this.mNagiosEntity);
	        	AppFacade.SetHostname(this.getHostName());
	        	AppFacade.SetURL(this.getNagiosURL());
				
				this.startActivityForResult(intent, AppFacade.GetConfigureRequestCode());
	            break;
	            
	        case R.id.menuItemLoad:
	        	Intent loadIntent = new Intent(this, LoadWidgetActivity.class);
	        	this.startActivity(loadIntent);
	        	
	        	break;
	        
	        case R.id.menuItemAbort:
	        	this.finish();
	        	break;
	            
	        default:
	            return super.onOptionsItemSelected(_item);	            
	    }
	    
	    return true;
	}
	
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(AppFacade.GetTag(), "Result received");
		if (resultCode == RESULT_OK)
			this.createWidget();
    }
	
	private void createWidget()
	{
		Context context = this.getApplicationContext();			
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		
		appWidgetManager.notifyAppWidgetViewDataChanged(this.mAppWidgetId, R.id.listView_Status_List);
		
		Intent resultValue = new Intent();			
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppFacade.GetAppWidgetId());			
		this.setResult(Activity.RESULT_OK, resultValue);			
		this.finish();
	}
	
	/**
	 * Returns the check button
	 * @return the button used for checking the URL
	 * @see android.widget.Button
	 */
	public Button getButtonCheck(){ return (Button)this.findViewById(R.id.ButtonCheck);}

	/**
	 * Retrieves the content of the URL EditText.
	 * @return the content as string.
	 * @see java.lang.String
	 */
	public String getURL(){return ((EditText) this.findViewById(R.id.EditTextUrl)).getText().toString();}
	
	public void setDisplayResult(Boolean _result){this.setTextViewResult(_result); }
	
	private void setTextViewResult(Boolean _result)
	{
		if(_result)
		{
			((TextView)this.findViewById(R.id.textView_Result)).setText("OK");
			((TextView)this.findViewById(R.id.textView_Result)).setTextColor(Color.GREEN);
		}
		else
		{
			((TextView)this.findViewById(R.id.textView_Result)).setText("Failure");
			((TextView)this.findViewById(R.id.textView_Result)).setTextColor(Color.RED);
		}
	}
	
	private String getHostName(){return ((EditText)this.findViewById(R.id.editText_Host)).getText().toString();}
	private String getNagiosURL(){return ((EditText)this.findViewById(R.id.EditTextUrl)).getText().toString();}
}
