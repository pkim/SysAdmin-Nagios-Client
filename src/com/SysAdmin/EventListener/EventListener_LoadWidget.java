package com.SysAdmin.EventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.SysAdmin.AppFacade;
import com.SysAdmin.FilePathFacade;
import com.SysAdmin.Activity.LoadWidgetActivity;

/**
 * Contains and handles every of the loadWidget activity.
 * 
 * @author Lukas Bernreiter
 * @version 0.5, 21/03/2012
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
		String item = ((TextView)_view).getText().toString();
		
		try{
			File clickedItem = new File(FilePathFacade.GetSavedDirectory() + item);
			BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(clickedItem)));			
			
			String readString = new String();
			
			while(null != (readString = bufReader.readLine()))
			{
				
			}
			
		}catch(Exception _e){Log.e(AppFacade.GetTag(), _e.getMessage());}
		
	}
}
