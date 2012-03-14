package com.SysAdmin;

// android
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * 
 * @author bernreiter
 *
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
		
		this.mCursor = this.getTestCursor();
		
		// Download, parse and fill cursor
	}

	public void onDestroy() {
		if(null != this.mCursor)
			this.mCursor.close();
	}
	
	private Cursor getTestCursor()
	{
		MatrixCursor cursor = new MatrixCursor(new String[]{"_id","name"});
		
		for(int i = 0; i<20; ++i)
		{
			cursor.addRow(new Object[]{new Integer(i), "asdf"+i});
		}
		
		return cursor;
	}
}