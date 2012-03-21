package com.SysAdmin.EventListener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.SysAdmin.Activity.LoadWidgetActivity;

/**
 * Contains and handles every of the loadWidget activity.
 * 
 * @author Lukas Bernreiter
 * @version 0.3, 21/03/2012
 * @since 0.3
 */
public class EventListener_LoadWidget implements OnItemClickListener
{
	private LoadWidgetActivity mLoadWidget = null;
	
	public EventListener_LoadWidget(LoadWidgetActivity _loadWidget)
	{
		this.mLoadWidget = _loadWidget;				
	}
	
	public void setEvents()
	{
		this.mLoadWidget.getListView().setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> _adapter, View _view, int _position, long _id)
	{		
//		String item = ((TextView)_view).getText().toString();
	}
}
