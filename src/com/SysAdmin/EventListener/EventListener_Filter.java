package com.SysAdmin.EventListener;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.SysAdmin.AppFacade;
import com.SysAdmin.R;
import com.SysAdmin.Activity.FilterActivity;

/**
 * Contains and handles every event of the filter activity.
 * 
 * @author Lukas Bernreiter
 * @version 0.5, 22/02/2012
 * @since 0.2
 */
public class EventListener_Filter implements OnChildClickListener
{
	private FilterActivity mFilter = null;
	
	public EventListener_Filter(FilterActivity _filter)
	{
		this.mFilter = _filter;		
	}
	
	public void setEvents()
	{
		this.mFilter.getExpListView().setOnChildClickListener(this);
	}

	public boolean onChildClick(ExpandableListView _expListView, View _view, int _group, int _child, long _id)
	{
		Log.d(AppFacade.GetTag(), "CLICK");
		
//		CheckedTextView ckTextView = (CheckedTextView)_expListView.findViewById(R.id.checkedText_Child);

		CheckedTextView ckTextView = (CheckedTextView)_view;
		
		ckTextView.setChecked(!ckTextView.isChecked());
		
		return true;
	}

}
