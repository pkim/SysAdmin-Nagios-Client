package com.SysAdmin;

// android
import com.SysAdmin.Nagios.XMLParser;
import com.SysAdmin.Nagios.Entity.NagiosEntity;
import com.SysAdmin.Nagios.Entity.ServiceEntity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * Provides a cursor containing the displaying items
 * 
 * @author Lukas Bernreiter
 * @version 0.5, 21/03/2012
 * @since 0.3
 */
public class UpdateService extends RemoteViewsService 
{
	@Override
	public RemoteViewsFactory onGetViewFactory(Intent _intent) {
		return new UpdateRemoteViewsFactory(this.getApplicationContext(), _intent);
	}
}

class UpdateRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory
{
	private Context mContext = null;
	private Cursor mCursor = null;

	public UpdateRemoteViewsFactory(Context _context, Intent _intent)
	{
		this.mContext = _context;
	}
	
	public int getCount() {
		if(mCursor != null)
			return mCursor.getCount();
		else
			return 0;
	}

	public long getItemId(int _position) {
		return _position;
	}

	public RemoteViews getLoadingView() {
		return null;
	}

	public RemoteViews getViewAt(int _position) 
	{
		String name = "";
		
		if(mCursor.moveToPosition(_position))
			name = mCursor.getString(mCursor.getColumnIndex("name"));
		
		RemoteViews rViews = new RemoteViews(this.mContext.getPackageName(), R.layout.list_item);
		rViews.setTextViewText(R.id.textView_list, name);			
		
		return rViews;
	}

	public int getViewTypeCount() {
		return 1;
	}

	public boolean hasStableIds() {
		return true;
	}

	public void onCreate() {		
		// no-op
	}

	public void onDataSetChanged() {
		if(null != this.mCursor)
			this.mCursor.close();
		
		this.mCursor = this.getCurrentData();
		
		// Download, parse and fill cursor
	}

	public void onDestroy() {
		if(null != this.mCursor)
			this.mCursor.close();
	}
	
	private Cursor getCurrentData()
	{
		MatrixCursor cursor = new MatrixCursor(new String[]{"_id","name"});
		NagiosEntity newEntity = null;
		NagiosEntity current = AppFacade.GetCurrentEntity();
		
		try 
    	{
			StatusFacade.downloadStatus(AppFacade.GetURL());    	
    	}    	
    	catch (Exception e) {Log.e(AppFacade.GetTag(), "Download error");}
		
		try
		{
			newEntity = XMLParser.parse(FilePathFacade.GetTempFile());
		}
		catch (Exception e) {Log.e(AppFacade.GetTag(), "Parse error during update");}				
		
		if(null != current)
			for(int i =0; i<current.getServices().length; i++)
			{
				ServiceEntity currentService = current.getServices()[i];
				if(currentService.isChecked())
				{
					// Contains the new downloaded data
					ServiceEntity newCurrentService = newEntity.getServices()[i];
					
					String service = String.format("%s: %s", currentService.getServiceDescription(), newCurrentService.getPluginOutput());
					cursor.addRow(new Object[]{new Integer(i), service});
				}
			}
		
		return cursor;
	}
}